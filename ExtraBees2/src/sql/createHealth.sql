delimiter $$

CREATE TABLE `health` (
	`id` int(11) NOT NULL AUTO_INCREMENT, 
  `hostname` varchar(100) DEFAULT NULL,
  `servicename` varchar(100) DEFAULT NULL,
  `statusVal` varchar(100) DEFAULT NULL,
  `ipAddr` varchar(100) DEFAULT NULL,
   `diskUsagePercent` BIGINT DEFAULT 0,
	`pageFileUsagePercent` BIGINT DEFAULT 0,
	`numProcs` BIGINT DEFAULT 0,
  `loadAveragePercent` BIGINT DEFAULT 0,
  `physicalMemoryUsagePercent` BIGINT DEFAULT 0,
  `parsedDate` datetime DEFAULT NULL,
   PRIMARY KEY (ID),
  UNIQUE KEY ID_UNIQUE (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

