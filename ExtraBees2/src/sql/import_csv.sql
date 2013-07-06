LOAD DATA LOCAL INFILE 'C:\\Users\\Maarten Bieshaar\\Downloads\\VAST2013MC3_NetworkFlow(1)\\nf\\nf-chunk1.csv'
#LOAD DATA LOCAL INFILE 'C:\\Users\\Maarten Bieshaar\\Downloads\\week2data\\nf-week2.csv'
INTO TABLE NetworkFlow0
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(TimeSeconds, parsedDate, dateTimeStr, ipLayerProtocol, ipLayerProtocolCode, firstSeenSrcIP, firstSeenDestIP, firstSeenSrcPort, firstSeenDestPort, moreFragments, contFragments, durationSeconds, firstSeenSrcPayloadBytes, firstSeenDestPayloadBytes, firstSeenSrcTotalBytes, firstSeenDestTotalBytes, firstSeenSrcPacketCount, firstSeenDestPacketCount,recordForceOut)
