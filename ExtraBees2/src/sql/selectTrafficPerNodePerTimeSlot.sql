SELECT IF(test.srcESite = 'EnterpriseSite1', test.destESite, test.srcESite) AS dest, SUM(test.traffic) FROM
	(SELECT srcEsite, destESite,SUM(SumTotalBytesSrc + SumTotalBytesDest) AS traffic FROM datavis.macro_networkflow
	WHERE Starttime = '2013-04-01 08:00:00'
		  AND NOT (srcESite = destESite)
	GROUP BY srcESite, destESite
	HAVING srcESite = 'EnterpriseSite1' OR destESite = 'EnterpriseSite1') AS test
GROUP BY dest;