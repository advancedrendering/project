SET SQL_SAFE_UPDATES=0; #disable save update necessary because updating without where-clause.
UPDATE datavis.networkflow
SET
parsedDate = DATE_SUB(DATE_FORMAT(parsedDate, "%Y-%m-%d %H:%i:00"), INTERVAL MINUTE(parsedDate) % 5 MINUTE);
SET SQL_SAFE_UPDATES=1;

