INSERT INTO `datavis`.`HealthServer`
(`receivedDate`,
`servicename`,
`ipAddr`,
`statusVal`,
`numMessages`,
`avgDiskUsagePercent`,
  `maxDiskUsagePercent`,
   `avgLoadPercent`,
	`maxLoadPercent`)
SELECT DATE_SUB(DATE_FORMAT(parsedDate, "%Y-%m-%d %H:%i:00"), INTERVAL MINUTE(parsedDate) % 5 MINUTE) as receivedDate,
servicename,  datavis.health.ipAddr, statusVal, COUNT(statusVal) as NumMessages,
AVG(diskUsagePercent) as avgDiskUsagePercent, MAX(diskUsagePercent) as maxDiskUsagePercent,
AVG(loadAveragePercent) as avgLoadPercent, MAX(loadAveragePercent) as maxLoadPercent FROM datavis.health JOIN datavis.bigmktnetwork ON datavis.bigmktnetwork.ipAddr = datavis.health.ipAddr 
WHERE NOT datavis.bigmktnetwork.typ = 'Workstation'
GROUP BY servicename, datavis.health.ipAddr, receivedDate, statusVal
