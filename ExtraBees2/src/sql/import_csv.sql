LOAD DATA LOCAL INFILE 'FILL\\IN\\PATH\\nf-chunk3.csv'
INTO TABLE NetworkFlow
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(TimeSeconds, parsedDate, dateTimeStr, ipLayerProtocol, ipLayerProtocolCode, firstSeenSrcIP, firstSeenDestIP, firstSeenSrcPort, firstSeenDestPort, moreFragments, contFragments, durationSeconds, firstSeenSrcPayloadBytes, firstSeenDestPayloadBytes, firstSeenSrcTotalBytes, firstSeenDestTotalBytes, firstSeenSrcPacketCount, firstSeenDestPacketCount,recordForceOut)
