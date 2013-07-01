SELECT parsedDate as 'Starttime', firstSeenSrcIP as 'srcESite', firstSeenDestIP as 'destESite', ipLayerProtocol as 'ipLayerProtocol',
SUM(firstSeenSrcTotalBytes) as 'SumTotalBytesSrc', SUM(firstSeenDestTotalBytes) as 'SumTotalBytesDest', 
SUM(firstSeenSrcPacketCount) as 'SumPacketSrc', SUM(firstSeenDestPacketCount) as 'SumPacketDest', 
SUM(durationSeconds) as 'SumDuration',  COUNT(*) as 'SumConnections' FROM datavis.networkflow
GROUP BY parsedDate, firstSeenSrcIP, firstSeenDestIP, ipLayerProtocol;