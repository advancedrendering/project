delimiter $$

CREATE TABLE `healthserverbyesites` (
  `receivedDate` datetime NOT NULL,
  `ESite` varchar(45) NOT NULL NULL,
  `statusVal` int(11) NOT NULL,
  `numMessages` int(11) DEFAULT NULL, 
`avgDiskUsagePercent` int(11) DEFAULT NULL,
  `maxDiskUsagePercent` int(11) DEFAULT NULL,
   `avgLoadPercent` int(11) DEFAULT NULL,
	`maxLoadPercent` int(11) DEFAULT NULL,
  PRIMARY KEY (`receivedDate`, `ESite`, `statusVal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

