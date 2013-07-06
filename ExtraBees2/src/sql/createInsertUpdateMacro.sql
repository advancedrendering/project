CREATE TABLE datavis.macro_temp (
  Starttime datetime NOT NULL,
  srcESite varchar(45) NOT NULL,
  destESite varchar(45) NOT NULL,
  ipLayerProtocol int(11) NOT NULL,
  SumTotalBytesSrc bigint(20) DEFAULT NULL,
  SumTotalBytesDest bigint(20) DEFAULT NULL,
  SumPacketSrc int(11) DEFAULT NULL,
  SumPacketDest int(11) DEFAULT NULL,
  SumDuration bigint(20) DEFAULT NULL,
  SumConnections int(11) DEFAULT NULL,
  PRIMARY KEY (Starttime,srcESite,destESite,ipLayerProtocol)
) ENGINE=MEMORY;

#fill temporary table with data.
INSERT INTO datavis.macro_temp
(Starttime,
srcESite,
destESite,
ipLayerProtocol,
SumTotalBytesSrc,
SumTotalBytesDest,
SumPacketSrc,
SumPacketDest,
SumDuration,
SumConnections)
SELECT parsedDate, 
firstSeenSrcIP, 
firstSeenDestIP, 
ipLayerProtocol,
SUM(firstSeenSrcTotalBytes), 
SUM(firstSeenDestTotalBytes), 
SUM(firstSeenSrcPacketCount), 
SUM(firstSeenDestPacketCount), 
SUM(durationSeconds),  
COUNT(*) FROM datavis.networkflow2
GROUP BY parsedDate, firstSeenSrcIP, firstSeenDestIP, ipLayerProtocol;

#update conflicted data in marco_networkflow table
SET SQL_SAFE_UPDATES=0; #disable save update necessary because updating without where-clause.
UPDATE datavis.macro_networkflow
SET
SumTotalBytesSrc = SumTotalBytesSrc + (SELECT SumTotalBytesSrc FROM datavis.macro_temp WHERE (macro_networkflow.Starttime =  
datavis.macro_temp.Starttime AND macro_networkflow.srcESite = datavis.macro_temp.srcESite AND 
macro_networkflow.destESite = datavis.macro_temp.destESite AND macro_networkflow.ipLayerProtocol = datavis.macro_temp.ipLayerProtocol)),

SumTotalBytesDest = SumTotalBytesDest + (SELECT SumTotalBytesDest FROM datavis.macro_temp WHERE (macro_networkflow.Starttime =  
datavis.macro_temp.Starttime AND macro_networkflow.srcESite = datavis.macro_temp.srcESite AND 
macro_networkflow.destESite = datavis.macro_temp.destESite AND macro_networkflow.ipLayerProtocol = datavis.macro_temp.ipLayerProtocol)),

SumPacketSrc = SumPacketSrc + (SELECT SumPacketSrc FROM datavis.macro_temp WHERE (macro_networkflow.Starttime =  
datavis.macro_temp.Starttime AND macro_networkflow.srcESite = datavis.macro_temp.srcESite AND 
macro_networkflow.destESite = datavis.macro_temp.destESite AND macro_networkflow.ipLayerProtocol = datavis.macro_temp.ipLayerProtocol)),

SumPacketDest = SumPacketDest + (SELECT SumPacketDest FROM datavis.macro_temp WHERE (macro_networkflow.Starttime =  
datavis.macro_temp.Starttime AND macro_networkflow.srcESite = datavis.macro_temp.srcESite AND 
macro_networkflow.destESite = datavis.macro_temp.destESite AND macro_networkflow.ipLayerProtocol = datavis.macro_temp.ipLayerProtocol)),

SumDuration = SumDuration +  (SELECT SumDuration FROM datavis.macro_temp WHERE (macro_networkflow.Starttime =  
datavis.macro_temp.Starttime AND macro_networkflow.srcESite = datavis.macro_temp.srcESite AND 
macro_networkflow.destESite = datavis.macro_temp.destESite AND macro_networkflow.ipLayerProtocol = datavis.macro_temp.ipLayerProtocol)),

SumConnections = SumConnections + (SELECT SumConnections FROM datavis.macro_temp WHERE (macro_networkflow.Starttime =  
datavis.macro_temp.Starttime AND macro_networkflow.srcESite = datavis.macro_temp.srcESite AND 
macro_networkflow.destESite = datavis.macro_temp.destESite AND macro_networkflow.ipLayerProtocol = datavis.macro_temp.ipLayerProtocol))

WHERE EXISTS (SELECT * FROM datavis.macro_temp WHERE (datavis.macro_temp.Starttime = macro_networkflow.Starttime) AND
(datavis.macro_temp.srcESite = macro_networkflow.srcESite) AND
(datavis.macro_temp.destESite = macro_networkflow.destESite) AND
(datavis.macro_temp.ipLayerProtocol = macro_networkflow.ipLayerProtocol));

SET SQL_SAFE_UPDATES=1; #enable save updates again.

#insert non-conflicted data in macro_networkflow table
INSERT INTO datavis.macro_networkflow
(Starttime,
srcESite,
destESite,
ipLayerProtocol,
SumTotalBytesSrc,
SumTotalBytesDest,
SumPacketSrc,
SumPacketDest,
SumDuration,
SumConnections)
SELECT datavis.macro_temp.Starttime,
datavis.macro_temp.srcESite,
datavis.macro_temp.destESite,
datavis.macro_temp.ipLayerProtocol,
datavis.macro_temp.SumTotalBytesSrc,
datavis.macro_temp.SumTotalBytesDest,
datavis.macro_temp.SumPacketSrc,
datavis.macro_temp.SumPacketDest,
datavis.macro_temp.SumDuration,
datavis.macro_temp.SumConnections FROM datavis.macro_temp
WHERE NOT EXISTS (SELECT macro_networkflow.Starttime,macro_networkflow.srcESite,macro_networkflow.destESite,macro_networkflow.ipLayerProtocol
FROM datavis.macro_networkflow WHERE (datavis.macro_temp.Starttime = macro_networkflow.Starttime) AND
(datavis.macro_temp.srcESite = macro_networkflow.srcESite) AND
(datavis.macro_temp.destESite = macro_networkflow.destESite) AND
(datavis.macro_temp.ipLayerProtocol = macro_networkflow.ipLayerProtocol));

#drop temporary table
drop table datavis.macro_temp;

