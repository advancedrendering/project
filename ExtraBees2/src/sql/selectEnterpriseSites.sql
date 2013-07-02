SELECT ID, 
CASE
	WHEN SUBSTRING(firstSeenSrcIP,1, 6) LIKE '172.10' THEN 'EnterpriseSite1'
	WHEN SUBSTRING(firstSeenSrcIP,1, 6) LIKE '172.20' THEN 'EnterpriseSite2' 
	WHEN SUBSTRING(firstSeenSrcIP,1, 6) LIKE '172.30' THEN 'EnterpriseSite3'
	WHEN SUBSTRING(firstSeenSrcIP,1, 3) LIKE '10.' THEN 'INTERNET'
	ELSE 'Other'
END as 'SrcIP',
CASE
	WHEN SUBSTRING(firstSeenDestIP,1, 6) LIKE '172.10' THEN 'EnterpriseSite1'
	WHEN SUBSTRING(firstSeenDestIP,1, 6) LIKE '172.20' THEN 'EnterpriseSite2' 
	WHEN SUBSTRING(firstSeenDestIP,1, 6) LIKE '172.30' THEN 'EnterpriseSite3'
	WHEN SUBSTRING(firstSeenDestIP,1, 3) LIKE '10.' THEN 'INTERNET'
	ELSE 'Other'
END as 'DestIP'
FROM datavis.networkflow;