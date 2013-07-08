delimiter $$

CREATE TABLE `site1Networkflow` (
    `Starttime` datetime DEFAULT NULL,
	`IP` varchar(45) NOT NULL,
	`Trafficload` BIGINT DEFAULT NULL,
  PRIMARY KEY (`Starttime`,`IP`, `Trafficload`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

