delimiter $$

CREATE TABLE macro_networkflow (
  Starttime datetime NOT NULL,
  srcESite varchar(45) NOT NULL,
  destESite varchar(45) NOT NULL,
  ipLayerProtocol int(11) NOT NULL,
  SumTotalBytesSrc bigint(20) DEFAULT NULL,
  SumTotalBytesDest bigint(20) DEFAULT NULL,
  SumPacketSrc bigint(20) DEFAULT NULL,
  SumPacketDest bigint(20) DEFAULT NULL,
  SumDuration bigint(20) DEFAULT NULL,
  SumConnections bigint(20) DEFAULT NULL,
  PRIMARY KEY (Starttime,srcESite,destESite,ipLayerProtocol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$