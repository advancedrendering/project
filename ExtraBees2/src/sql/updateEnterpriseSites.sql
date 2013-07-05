SET SQL_SAFE_UPDATES=0; #disable save update necessary because updating without where-clause.
UPDATE datavis.networkflow
SET
firstSeenSrcIP = CASE
	WHEN SUBSTRING(firstSeenSrcIP,1, 6) LIKE '172.10' THEN 'EnterpriseSite1'
	WHEN SUBSTRING(firstSeenSrcIP,1, 6) LIKE '172.20' THEN 'EnterpriseSite2' 
	WHEN SUBSTRING(firstSeenSrcIP,1, 6) LIKE '172.30' THEN 'EnterpriseSite3'
	WHEN SUBSTRING(firstSeenSrcIP,1, 3) LIKE '10.' THEN 'INTERNET'
	ELSE 'Other'
END,
firstSeenDestIP = CASE
	WHEN SUBSTRING(firstSeenDestIP,1, 6) LIKE '172.10' THEN 'EnterpriseSite1'
	WHEN SUBSTRING(firstSeenDestIP,1, 6) LIKE '172.20' THEN 'EnterpriseSite2' 
	WHEN SUBSTRING(firstSeenDestIP,1, 6) LIKE '172.30' THEN 'EnterpriseSite3'
	WHEN SUBSTRING(firstSeenDestIP,1, 3) LIKE '10.' THEN 'INTERNET'
	ELSE 'Other'
END;
SET SQL_SAFE_UPDATES=1; #enable save updates again.
