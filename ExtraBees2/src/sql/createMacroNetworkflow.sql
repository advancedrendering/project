CREATE  TABLE `datavis`.`macro_networkflow` (
  `Starttime` DATETIME NOT NULL ,
  `srcESite` VARCHAR(45) NOT NULL ,
  `destESite` VARCHAR(45) NOT NULL ,
  `ipLayerProtocol` INT NOT NULL ,
  `SumTotalBytesSrc` BIGINT NULL ,
  `SumTotalBytesDest` BIGINT NULL ,
  `SumDuration` BIGINT NULL ,
  `SumConnections` INT NULL ,
  PRIMARY KEY (`Starttime`, `srcESite`, `destESite`, `ipLayerProtocol`) );
