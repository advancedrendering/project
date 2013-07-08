LOAD DATA LOCAL INFILE 'C:\\Users\\lisijia\\git\\project\\ExtraBees2\\src\\csvfiles\\site3totalflow.csv'
INTO TABLE site3networkflow
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
(`Starttime`,`IP`,`Trafficload`)
