delimiter $$

CREATE TABLE macro_networkflow (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$