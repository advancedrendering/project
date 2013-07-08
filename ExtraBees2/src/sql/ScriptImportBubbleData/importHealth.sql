LOAD DATA LOCAL INFILE 'C:\\Users\\lisijia\\git\\project\\ExtraBees2\\src\\csvfiles\\site3health.csv'
INTO TABLE site3health
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
(`Starttime`,`IP`,`Health`)
