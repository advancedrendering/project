INSERT INTO `datavis`.`HealthServerByESites`
(`receivedDate`,
`ESite`,
`statusVal`,
`numMessages`,
`avgDiskUsagePercent`,
  `maxDiskUsagePercent`,
   `avgLoadPercent`,
	`maxLoadPercent`)
SELECT receivedDate, 
CASE
	WHEN SUBSTRING(HealthServer.ipAddr,1, 6) LIKE '172.10' THEN 'EnterpriseSite1'
	WHEN SUBSTRING(HealthServer.ipAddr,1, 6) LIKE '172.20' THEN 'EnterpriseSite2' 
	WHEN SUBSTRING(HealthServer.ipAddr,1, 6) LIKE '172.30' THEN 'EnterpriseSite3'
	WHEN SUBSTRING(HealthServer.ipAddr,1, 3) LIKE '10.' THEN 'INTERNET'
	ELSE 'Other'
END as ESite, statusVal, SUM(numMessages) as numMessages,
AVG(avgDiskUsagePercent) as avgDiskUsagePercent, MAX(maxDiskUsagePercent) as maxDiskUsagePercent,
AVG(avgLoadPercent) as avgLoadPercent, MAX(maxLoadPercent) as maxLoadPercent FROM datavis.HealthServer 
JOIN bigmktnetwork ON HealthServer.ipAddr = bigmktnetwork.ipAddr
#WHERE bigmktnetwork.typ = 'HTTP'
GROUP BY receivedDate, ESite, statusVal;
