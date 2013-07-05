CREATE  TABLE `datavis`.`site1totalflow` (
  `parsedDate` DATETIME NOT NULL ,
  `ipAddr` VARCHAR(50) NOT NULL ,
  `totalBytes` INT NULL ,
  PRIMARY KEY (`parsedDate`, `ipAddr`) );
