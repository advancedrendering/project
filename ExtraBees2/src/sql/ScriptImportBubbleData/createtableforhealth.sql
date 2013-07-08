delimiter $$

CREATE TABLE `site1health` (
    `Starttime` datetime DEFAULT NULL,
	`IP` varchar(45) NOT NULL,
	`Health` INT DEFAULT NULL,
  PRIMARY KEY (`Starttime`,`IP`, `Health`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

