CREATE  TABLE `datavis`.`macro_networkflow` (
  `srcESite` VARCHAR(45) NOT NULL ,
  `destESite` VARCHAR(45) NOT NULL ,
  `ipLayerProtocol` INT NOT NULL ,
  `SumTotalBytesSrc` BIGINT NULL ,
  `SumTotalBytesDest` BIGINT NULL ,
  `SumDuration` BIGINT NULL ,
  `SumConnections` INT NULL ,
  `StarttimeSeconds` BIGINT NOT NULL ,
  PRIMARY KEY (`srcESite`, `StarttimeSeconds`, `destESite`, `ipLayerProtocol`) );
