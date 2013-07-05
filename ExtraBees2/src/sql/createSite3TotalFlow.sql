CREATE  TABLE `datavis`.`site3totalflow` (
  `parsedDate` DATETIME NOT NULL ,
  `ipAddr` VARCHAR(50) NOT NULL ,
  `totalBytes` INT NULL ,
  PRIMARY KEY (`parsedDate`, `ipAddr`) );
