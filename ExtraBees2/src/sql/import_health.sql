LOAD DATA LOCAL INFILE 'PATH\\TO\\FILE'
INTO TABLE health
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\r'
IGNORE 1 LINES
(@dummy, @hostname, @servicename, @dummy, @statusVal, @dummy,
@ipAddr, @diskUsagePercent, @pageFileUsagePercent, @numProcs, @loadAveragePercent,
@physicalMemoryUsagePercent, @dummy, parsedDate)
SET hostname = IF(@hostname = NULL, 'moep', @hostname),
servicename = IF(@servicename = NULL, 'moep', @servicename),
statusVal = IF(@statusVAl = '', NULL, @statusVal),
ipAddr = IF(@ipAddr = NULL, 'moep', @ipAddr),
diskUsagePercent = IF(@diskUsagePercent = NULL, 0, @diskUsagePercent),
pageFileUsagePercent = IF(@pageFileUsage = NULL, 0, @pageFileUsage),
numProcs = IF(@numProcs = NULL, 0, @numProcs),
loadAveragePercent = IF(@loadAveragePercent = NULL, 0, @loadAveragePercent),
physicalMemoryUsagePercent = IF(@physicalMemoryUsagePercent = NULL, 0, @physicalMemoryUsagePercent);

