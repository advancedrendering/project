delimiter $$

CREATE TABLE `healthserver` (
  `receivedDate` datetime NOT NULL,
  `servicename` varchar(45) NOT NULL,
  `ipAddr` varchar(45) NOT NULL,
  `statusVal` int(11) NOT NULL,
  `numMessages` int(11) DEFAULT NULL,
  `avgDiskUsagePercent` int(11) DEFAULT NULL,
  `maxDiskUsagePercent` int(11) DEFAULT NULL,
   `avgLoadPercent` int(11) DEFAULT NULL,
	`maxLoadPercent` int(11) DEFAULT NULL,
  PRIMARY KEY (`receivedDate`,`servicename`,`ipAddr`,`statusVal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

