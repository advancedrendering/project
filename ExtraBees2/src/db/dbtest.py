import sys, copy
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


def processNetworkflowRawData(db, step_size = 1000):
    #create new query object
    query = QtSql.QSqlQuery()
    row_counter = 0
#    query.exec_("SELECT COUNT(ID) FROM networkflow;")
#    query.next()
#    num_rows = query.value(0).toInt()[0]

    loc_dict = {}
        
    while (row_counter < 2000):#< num_rows):
        sel_query = "SELECT * FROM datavis.networkflow LIMIT "  +  str(row_counter) + " , " + str(row_counter + step_size) + " ;"
        query.exec_(sel_query)
        iterateQuery(query, loc_dict)
        row_counter += step_size + 1
#        print row_counter, "of", num_rows

''' Iterates over a query.'''
def iterateQuery(query, dict):
    #create a new QTable or list here and fill it by the data given in the query
    #Then iterate over filled QTable and bulk insert/ update them in db.
    while (query.next()):
        startdt = query.value(2).toDateTime()
        interval_start = determineIntervalStart(startdt) #note startdt is changed because passes only reference
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
        
        #TODO: WHAT to do if have connection with duration longer than 5 minutes
        
        
        #check with primary key whether entry already exists in dict
        key = (interval_start, srcES, destES, ipLayerProtocol)
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
            
        print "Interval Start", interval_start
        print "SRC:", srcIP,  srcES, "DEST:", destIP, destES
        print "ipLayerProtocol", ipLayerProtocol
        
        #TODO: Write method which determines the enterprise site from the ip address.
        #TODO: Work with the data i.e. put it in a new datatable
        #Need a check-, insert- and update Method for it.
        #TODO: Write method which determines the exact starttime interval
    print dict
         
        
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
    
if __name__ == "__main__":

    app = QtGui.QApplication(sys.argv)
 
    db = createConnection(dbname = "datavis", dbuser = "datavis", dbpasswd = "DataVis")
    
    db.open()
    print "Opened connection to database"

#    query = QtSql.QSqlQuery()
#    query.exec_("SELECT * FROM datavis.networkflow LIMIT 0, 1000;") 
#
#    while(query.next()):
#        #auf die einzelnen spalten zugreifen via index und dann zu richtigen datentypen casten.
#        print query.value(2).toString() #print the dates


    processNetworkflowRawData(db)
    db.close()
    print "Closed connection to database"
    
    raw_input("Press any key to exit...")