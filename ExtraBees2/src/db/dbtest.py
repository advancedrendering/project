import sys, math
from PyQt4 import QtCore, QtGui, QtSql

INTERVAL_START_TIME = 0
SRC_ES = 1
DEST_ES = 2
PROTOCOL = 3
SUM_BYTES_SRC = 4
SUM_BYTES_DEST = 5
SUM_PACKET_SRC = 6
SUM_PACKET_DEST = 7
SUM_DURATION = 8
CONNECTION_COUNT = 9 


FIVE_MINUTES_IN_SECONDS = 300

def createTestConnection():
    db = QtSql.QSqlDatabase.addDatabase("QMYSQL")
    db.setDatabaseName("datavis")
    db.setUserName("datavis")    
    db.setPassword("DataVis")
    db.open()
    print "Opened connection to database"

    query = QtSql.QSqlQuery()
    query.exec_("SELECT * FROM datavis.networkflow LIMIT 0, 1000;") 

    while(query.next()):
        #auf die einzelnen spalten zugreifen via index und dann zu richtigen datentypen casten.
        print query.value(2).toString() #print the dates

    db.close()
    print "Closed connection to database"
    return True

''' Create a connection to a table in a database '''
def createConnection(dbname, dbuser, dbpasswd, dbtype = "QMYSQL"):
    db = QtSql.QSqlDatabase.addDatabase(dbtype)
    db.setDatabaseName(dbname)
    db.setUserName(dbuser)    
    db.setPassword(dbpasswd)
    return db


def processNetworkflowRawData(db, step_size = 100000):
    db.open()
    #create new query object
    query = QtSql.QSqlQuery()
    row_counter = 0
    query.exec_("SELECT COUNT(ID) FROM networkflow;")
    query.next()
    num_rows = query.value(0).toInt()[0]

    loc_dict = {}
    
#    num_rows = 2000    
    
    while (row_counter < num_rows):
        sel_query = "SELECT * FROM datavis.networkflow LIMIT "  +  str(row_counter) + " , " + str(row_counter + step_size) + " ;"
        query.exec_(sel_query)
        iterateQuery(query, loc_dict)
        row_counter += step_size + 1
        print row_counter, "of", num_rows
    saveDictToDB(loc_dict)
    db.close()
    


def filterLongEntries(db):
    query = QtSql.QSqlQuery()
    #get entries which are longer than five minutes
    query.exec_("SELECT * FROM datavis.networkflow WHERE durationSeconds > 300;")
    
    loc_insertquery = QtSql.QSqlQuery()
    loc_insertquery.prepare("INSERT INTO datavis.networkflow (TimeSeconds, parsedDate, dateTimeStr, ipLayerProtocol, ipLayerProtocolCode, firstSeenSrcIP, firstSeenDestIP, firstSeenSrcPort, firstSeenDestPort, moreFragments, contFragments, durationSeconds, firstSeenSrcPayloadBytes, firstSeenDestPayloadBytes, firstSeenSrcTotalBytes, firstSeenDestTotalBytes, firstSeenSrcPacketCount, firstSeenDestPacketCount, recordForceOut) VALUES ( :TimeSeconds, :parsedDate, :dateTimeStr, :ipLayerProtocol, :ipLayerProtocolCode, :firstSeenSrcIP, :firstSeenDestIP, :firstSeenSrcPort, :firstSeenDestPort, :moreFragments, :contFragments, :durationSeconds, :firstSeenSrcPayloadBytes, :firstSeenDestPayloadBytes, :firstSeenSrcTotalBytes, :firstSeenDestTotalBytes, :firstSeenSrcPacketCount, :firstSeenDestPacketCount, :recordForceOut);")
    
    loc_updatequery = QtSql.QSqlQuery()
    loc_updatequery.prepare("UPDATE datavis.networkflow SET durationSeconds = :durationSeconds WHERE ID = :id;")
    
    counter = 0
    
    #create a new QTable or list here and fill it by the data given in the query
    #Then iterate over filled QTable and bulk insert/ update them in db.
    while (query.next()):
        id = query.value(0).toInt()[0]
        timeseconds = query.value(1).toDouble()
        startdt = query.value(2).toDateTime()
        dateTimeStr = query.value(3).toString()
        ipLayerProtocol = query.value(4).toInt()[0]
        ipLayerProtocolCode = query.value(5).toString()
        srcIP = query.value(6).toString()
        destIP = query.value(7).toString()
        srcPort = query.value(8).toInt()[0]
        destPort = query.value(9).toInt()[0]
        moreFragments = query.value(10).toString()
        contFragments = query.value(11).toString()
        duration = query.value(12).toInt()[0]    
        srcPayload = query.value(13).toInt()[0]
        destPayload = query.value(14).toInt()[0]
        totalBytesSrc = query.value(15).toInt()[0]
        totalBytesDest = query.value(16).toInt()[0]
        totalPacketCountSrc = query.value(17).toInt()[0]
        totalPacketCountDest = query.value(18).toInt()[0]
        recordForceOut = query.value(19).toString()

        #check whether connection is longer than five minutes.        
        #NOT YET TESTED
        num_five_min = int(math.ceil(float(duration) / float(FIVE_MINUTES_IN_SECONDS)))
        loc_totalBytesSrc = round(totalBytesSrc / num_five_min)
        loc_totalBytesDest = round(totalBytesDest / num_five_min)
        loc_totalPacketCountSrc = round(totalPacketCountSrc / num_five_min)
        loc_totalPacketCountDest = round(totalPacketCountDest / num_five_min)
        loc_srcPayload = round(srcPayload / num_five_min)
        loc_destPayload = round(destPayload / num_five_min)
                
        loc_duration = FIVE_MINUTES_IN_SECONDS
        
        loc_interval_start = startdt
        
        print counter, "of", query.size()
        counter += 1
        #now divide traffic equally.
        for i in xrange(num_five_min):
            if (i == 0):
                loc_updatequery.bindValue(":id", id)
                loc_updatequery.bindValue(":durationSeconds", loc_duration)
                loc_updatequery.exec_()
            else:                        
                #determine duration (if is last iteration than calculate rest duration
                if i == (num_five_min - 1):
                    loc_duration = duration % 5            
            
                #add to database    
                loc_insertquery.bindValue(":TimeSeconds", timeseconds)
                loc_insertquery.bindValue(":parsedDate", loc_interval_start)
                loc_insertquery.bindValue(":dateTimteStr", dateTimeStr)
                loc_insertquery.bindValue(":ipLayerProtocol", ipLayerProtocol)
                loc_insertquery.bindValue(":ipLayerProtocolCode", ipLayerProtocolCode)
                loc_insertquery.bindValue(":firstSeenSrcIP", srcIP)
                loc_insertquery.bindValue(":firstSeenDestIP", destIP)
                loc_insertquery.bindValue(":firstSeenSrcPort", srcPort)
                loc_insertquery.bindValue(":firstSeenDestPort", destPort)
                loc_insertquery.bindValue(":moreFragments", moreFragments)
                loc_insertquery.bindValue(":contFragments", contFragments)
                loc_insertquery.bindValue(":durationSeconds", loc_duration)
                loc_insertquery.bindValue(":firstSeenSrcPayloadBytes", loc_srcPayload)
                loc_insertquery.bindValue(":firstSeenDestPayloadBytes", loc_destPayload)
                loc_insertquery.bindValue(":firstSeenSrcTotalBytes", loc_totalBytesSrc)
                loc_insertquery.bindValue(":firstSeenDestTotalBytes", loc_totalBytesDest)
                loc_insertquery.bindValue(":firstSeenSrcPacketCount", loc_totalPacketCountSrc)
                loc_insertquery.bindValue(":firstSeenDestPacketCount", loc_totalPacketCountDest)
                loc_insertquery.bindValue(":recordForceOut", recordForceOut)
                loc_insertquery.exec_()
            #(create whole new object just to be sure nothing is changed by unexpected references)
            #increment new interval by five minutes
            loc_interval_start = QtCore.QDateTime(loc_interval_start).addSecs(FIVE_MINUTES_IN_SECONDS)
''' Iterates over a query.'''
def iterateQuery(query, dict):
    #create a new QTable or list here and fill it by the data given in the query
    #Then iterate over filled QTable and bulk insert/ update them in db.
    while (query.next()):
        startdt = query.value(2).toDateTime()
        interval_start = determineIntervalStart(startdt) #note startdt is changed because passes only reference
        unix_interval_start = interval_start.toMSecsSinceEpoch() #calculate unix time is used as key for dictionary because QDateTime is problematic since hash is computed by objects itself.
        srcIP = query.value(6).toString()
        destIP = query.value(7).toString()
        ipLayerProtocol = query.value(4).toInt()[0]
        totalBytesSrc = query.value(15).toInt()[0]
        totalBytesDest = query.value(16).toInt()[0]
        totalPacketCountSrc = query.value(17).toInt()[0]
        totalPacketCountDest = query.value(18).toInt()[0]
        duration = query.value(12).toInt()[0]
        
        srcES = determineNetworkEntity(srcIP)
        destES = determineNetworkEntity(destIP)
        
        
        
        #check whether connection is longer than five minutes.        
        if duration > FIVE_MINUTES_IN_SECONDS:
            #NOT YET TESTED
            num_five_min = int(duration / FIVE_MINUTES_IN_SECONDS)
            loc_totalBytesSrc = round(totalBytesSrc / num_five_min)
            loc_totalBytesDest = round(totalBytesDest / num_five_min)
            loc_totalPacketCountSrc = round(totalPacketCountSrc / num_five_min)
            loc_totalPacketCountDest = round(totalPacketCountDest / num_five_min)
            
            #create new interval_start 
            loc_interval_start = interval_start 
            
            loc_duration = FIVE_MINUTES_IN_SECONDS
            
            #now divide traffic equally.
            for i in xrange(num_five_min):
                #determine duration (if is last iteration than calculate rest duration
                if i == (num_five_min - 1):
                    loc_duration = duration % 5
                    
                
                unix_interval_start = loc_interval_start.toMSecsSinceEpoch()
                #check with primary key whether entry already exists in dict
                key = (unix_interval_start, srcES, destES, ipLayerProtocol)
                if not (key in dict):
                    #create entry
                    #use list instead of tuple because can change list and tuple cannot be changed
                    entry = [loc_interval_start, srcES, destES, ipLayerProtocol, loc_totalBytesSrc, loc_totalBytesDest, loc_totalPacketCountSrc, loc_totalPacketCountDest, loc_duration, 1]
                    #put entry in dict
                    dict[key] = entry
                else:
                    #have to update the already existing entry i.e. increment counters
                    dict[key][SUM_BYTES_SRC] += loc_totalBytesSrc
                    dict[key][SUM_BYTES_DEST] += loc_totalBytesDest
                    dict[key][SUM_PACKET_SRC] += loc_totalPacketCountSrc
                    dict[key][SUM_PACKET_DEST] += loc_totalPacketCountDest
                    dict[key][SUM_DURATION] += loc_duration
                    dict[key][CONNECTION_COUNT] += 1 #increment number of connection
                    
                #(create whole new object just to be sure nothing is changed by unexpected references)
                loc_interval_start = QtCore.QDateTime(loc_interval_start)
                #increment new interval by five minutes
                loc_interval_start.addSecs(FIVE_MINUTES_IN_SECONDS)
        else:
            #check with primary key whether entry already exists in dict
            key = (unix_interval_start, srcES, destES, ipLayerProtocol)
            if not (key in dict):
                #create entry
                #use list instead of tuple because can change list and tuple cannot be changed
                entry = [interval_start, srcES, destES, ipLayerProtocol, totalBytesSrc, totalBytesDest, totalPacketCountSrc, totalPacketCountDest, duration, 1]
                #put entry in dict
                dict[key] = entry
            else:
                #have to update the already existing entry i.e. increment counters
                dict[key][SUM_BYTES_SRC] += totalBytesSrc
                dict[key][SUM_BYTES_DEST] += totalBytesDest
                dict[key][SUM_PACKET_SRC] += totalPacketCountSrc
                dict[key][SUM_PACKET_DEST] += totalPacketCountDest
                dict[key][SUM_DURATION] += duration
                dict[key][CONNECTION_COUNT] += 1 #increment number of connecton
                
#            print "Interval Start", interval_start
#            print "SRC:", srcIP,  srcES, "DEST:", destIP, destES
#            print "ipLayerProtocol", ipLayerProtocol
        
         
        
def determineNetworkEntity(ip):
    if ip[0:6] == '172.10':
        return 'Enterprise Site 1'
    elif ip[0:6] == '172.20':
        return 'Enterprise Site 2'
    elif ip[0:6] == '172.30':
        return 'Enterprise Site 3'
    elif ip[0:3] == '10.':
        return 'Internet'
    else:
        return 'Other'


# @note: startdt is an in/out variable i.e. startdt is changed during execution of the method.
def determineIntervalStart(startdt):
    old_time = startdt.time()
    #do an integer division and multiply to remove rest i.e. round to five minute intervals
    loc_times_5_min = old_time.minute() / 5
    #multiply again by five to get rounded minutes
    loc_minute = loc_times_5_min * 5
    #create new time
    new_time = QtCore.QTime(old_time.hour(), loc_minute, 0,0)
    startdt.setTime(new_time)
    return startdt

def saveDictToDB(dict):
    #create connection and open it
#    loc_db = createConnection(dbname = "macro_networkflow", dbuser = "datavis", dbpasswd = "DataVis")
    #create query
    query = QtSql.QSqlQuery()
    keys = dict.keys()
    query.prepare("INSERT INTO datavis.macro_networkflow (StarttimeSeconds, srcESite, destESite, ipLayerProtocol, SumTotalBytesSrc, SumTotalBytesDest, SumPacketSrc, SumPacketDest, SumDuration, SumConnections) VALUES(:StarttimeSeconds, :srcESite, :destESite, :ipLayerProtocol, :SumTotalBytesSrc, :SumTotalBytesDest, :SumPacketSrc, :SumPacketDest, :SumDuration, :SumConnections);")
    for key in keys:
        query.bindValue(":StarttimeSeconds", dict[key][INTERVAL_START_TIME])
        query.bindValue(":srcESite", dict[key][SRC_ES])
        query.bindValue(":destESite", dict[key][DEST_ES])
        query.bindValue(":ipLayerProtocol", dict[key][PROTOCOL])
        query.bindValue(":SumTotalBytesSrc", dict[key][SUM_BYTES_SRC])
        query.bindValue(":SumTotalBytesDest", dict[key][SUM_BYTES_DEST])
        query.bindValue(":SumPacketSrc", dict[key][SUM_PACKET_SRC])
        query.bindValue(":SumPacketDest", dict[key][SUM_PACKET_DEST])
        query.bindValue(":SumDuration", dict[key][SUM_DURATION])
        query.bindValue(":SumConnections", dict[key][CONNECTION_COUNT])
        query.exec_()
        
    
if __name__ == "__main__":

    app = QtGui.QApplication(sys.argv)
 
    db = createConnection(dbname = "datavis", dbuser = "datavis", dbpasswd = "DataVis")
    
    db.open()
    
    filterLongEntries(db)
    
    db.close()
    
    
    
#    print "Opened connection to database"

#    query = QtSql.QSqlQuery()
#    query.exec_("SELECT * FROM datavis.networkflow LIMIT 0, 1000;") 
#
#    while(query.next()):
#        #auf die einzelnen spalten zugreifen via index und dann zu richtigen datentypen casten.
#        print query.value(2).toString() #print the dates


#    processNetworkflowRawData(db)
#    db.close()
#    print "Closed connection to database"
    
    raw_input("Press any key to exit...")