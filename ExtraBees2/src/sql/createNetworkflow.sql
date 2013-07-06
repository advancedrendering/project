delimiter $$

CREATE TABLE networkflow0 (
  ID int(11) NOT NULL AUTO_INCREMENT,
  TimeSeconds double DEFAULT NULL,
  parsedDate datetime DEFAULT NULL,
  dateTimeStr varchar(45) DEFAULT NULL,
  ipLayerProtocol int(11) DEFAULT NULL,
  ipLayerProtocolCode varchar(45) DEFAULT NULL,
  firstSeenSrcIP varchar(45) DEFAULT NULL,
  firstSeenDestIP varchar(45) DEFAULT NULL,
  firstSeenSrcPort int(11) DEFAULT NULL,
  firstSeenDestPort int(11) DEFAULT NULL,
  moreFragments varchar(45) DEFAULT NULL,
  contFragments varchar(45) DEFAULT NULL,
  durationSeconds int(11) DEFAULT NULL,
  firstSeenSrcPayloadBytes int(11) DEFAULT NULL,
  firstSeenDestPayloadBytes int(11) DEFAULT NULL,
  firstSeenSrcTotalBytes int(11) DEFAULT NULL,
  firstSeenDestTotalBytes int(11) DEFAULT NULL,
  firstSeenSrcPacketCount int(11) DEFAULT NULL,
  firstSeenDestPacketCount int(11) DEFAULT NULL,
  recordForceOut varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY ID_UNIQUE (ID)
) ENGINE=InnoDB AUTO_INCREMENT=46204541 DEFAULT CHARSET=utf8$$

