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
SELECT
networkflow1.parsedDate,
networkflow1.firstSeenSrcIP,
networkflow1.firstSeenDestIP,
networkflow1.ipLayerProtocol,
SUM(networkflow1.firstSeenSrcTotalBytes), 
SUM(networkflow1.firstSeenDestTotalBytes), 
SUM(networkflow1.firstSeenSrcPacketCount), 
SUM(networkflow1.firstSeenDestPacketCount), 
SUM(networkflow1.durationSeconds),  
COUNT(*)
FROM datavis.networkflow1
WHERE NOT EXISTS ( SELECT Starttime, srcESite, destESite, ipLayerProtocol 
FROM datavis.macro_networkflow
WHERE (macro_networkflow.Starttime =  networkflow1.parsedDate AND macro_networkflow.srcESite = networkflow1.firstSeenSrcIP
AND macro_networkflow.destESite = networkflow1.firstSeenDestIP AND macro_networkflow.ipLayerProtocol = networkflow1.ipLayerProtocol))
GROUP BY parsedDate, firstSeenSrcIP, firstSeenDestIP, ipLayerProtocol;