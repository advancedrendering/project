CREATE  TABLE `datavis`.`site2totalflow` (
  `parsedDate` DATETIME NOT NULL ,
  `ipAddr` VARCHAR(50) NOT NULL ,
  `totalBytes` INT NULL ,
  PRIMARY KEY (`parsedDate`, `ipAddr`) );
