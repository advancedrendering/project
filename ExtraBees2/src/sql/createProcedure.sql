DELIMITER $$

CREATE PROCEDURE filterLongEntries(interval_length INT)
BEGIN
	DECLARE varID INT;
	DECLARE numit INT;
	DECLARE starttime DATETIME;
	DECLARE varDurationSeconds INT;
	DECLARE numit_decimal DECIMAL;
	DECLARE counter INT;
	DECLARE loc_interval_length INT;
	DECLARE bDone INT;

	#declare a cursor
	DECLARE curs CURSOR FOR 
	SELECT ID, durationSeconds, CEIL(durationSeconds / CAST(inteval_length as DECIMAL)), parsedDate 
	FROM datavis.networkflow2 WHERE durationSeconds > inteval_length;
	
	#define handler for when cursor
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET bDone = 1;
	OPEN curs;

	SET bDone = 0;
	REPEAT
		FETCH curs INTO varID, varDurationSeconds, numit, starttime;
		SET counter = 1;
		SET numit_decimal = CAST(numit as DECIMAL);
		UPDATE datavis.networkflow2
			SET
			durationSeconds = interval_length,
			firstSeenSrcPayloadBytes = firstSeenSrcPayloadBytes / numit_decimal,
			firstSeenDestPayloadBytes = firstSeenDestPayloadBytes / numit_decimal,
			firstSeenSrcTotalBytes = firstSeenSrcTotalBytes / numit_decimal,
			firstSeenDestTotalBytes = firstSeenDestTotalBytes / numit_decimal,
			firstSeenSrcPacketCount = firstSeenSrcPacketCount / numit_decimal,
			firstSeenDestPacketCount = firstSeenDestPacketCount / numit_decimal
		WHERE ID = varID;
		#iterate num iteration (numit) times and create new entries.
		WHILE (counter < numit) DO
			SET loc_interval_length = IF(counter != (numit - 1), interval_length, MOD(varDurationSeconds, numit));
			INSERT INTO datavis.networkflow2
				(TimeSeconds,
				parsedDate,
				dateTimeStr,
				ipLayerProtocol,
				ipLayerProtocolCode,
				firstSeenSrcIP,
				firstSeenDestIP,
				firstSeenSrcPort,
				firstSeenDestPort,
				moreFragments,
				contFragments,
				durationSeconds,
				firstSeenSrcPayloadBytes,
				firstSeenDestPayloadBytes,
				firstSeenSrcTotalBytes,
				firstSeenDestTotalBytes,
				firstSeenSrcPacketCount,
				firstSeenDestPacketCount,
				recordForceOut)
			SELECT TimeSeconds, DATE_ADD(DATE_FORMAT(parsedDate, "%Y-%m-%d %H:%i:00"), INTERVAL MINUTE(parsedDate) % (5 * counter) MINUTE), 
			dateTimeStr, ipLayerProtocol, ipLayerProtocolCode, firstSeenSrcIP, firstSeenDestIP, firstSeenSrcPort, firstSeenDestPort,
			moreFragments, contFragments, loc_interval_length,  firstSeenSrcPayloadBytes / numit_decimal, firstSeenDestPayloadBytes / numit_decimal,
			firstSeenSrcTotalBytes / numit_decimal, firstSeenDestTotalBytes / numit_decimal, firstSeenSrcPacketCount / numit_decimal,
			firstSeenDestPacketCount / numit_decimal, recordForceOut FROM datavis.networkflow2 WHERE varID = ID;
			SET counter = counter + 1;
	END WHILE;
	UNTIL bDone END REPEAT;
END;
