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
SELECT parsedDate, 
firstSeenSrcIP, 
firstSeenDestIP, 
ipLayerProtocol,
SUM(firstSeenSrcTotalBytes), 
SUM(firstSeenDestTotalBytes), 
SUM(firstSeenSrcPacketCount), 
SUM(firstSeenDestPacketCount), 
SUM(durationSeconds),  
COUNT(*) FROM datavis.networkflow
GROUP BY parsedDate, firstSeenSrcIP, firstSeenDestIP, ipLayerProtocol;
