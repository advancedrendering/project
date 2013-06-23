import sys, time
from PyQt4 import QtCore, QtGui, QtSql


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
    query.exec_("SELECT COUNT(ID) FROM networkflow;")
    query.next()
    num_rows = query.value(0).toInt()[0]
        
    while (row_counter < num_rows):
        sel_query = "SELECT * FROM datavis.networkflow LIMIT "  +  str(row_counter) + " , " + str(row_counter + step_size) + " ;"
        query.exec_(sel_query)
        iterateQuery(query)
        row_counter += step_size + 1
        print row_counter, "of", num_rows

''' Iterates over a query.'''
def iterateQuery(query):
    #create a new QTable or list here and fill it by the data given in the query
    #Then iterate over filled QTable and bulk insert/ update them in db.
    while (query.next()):
        starttime = query.value(0).toInt()[0] #exact starttime in seconds (unix-systemtime)
        srcIP = query.value(5).toString()[0]
        destIP = query.value(6).toString()[0]
        ipLayerProtocol = query.value(3).toInt()[0]
        totalBytesSrc = query.value(14).toInt()[0]
        totalBytesDest = query.value(15).toInt()[0]
        totalPacketCountSrc = query.value(16).toInt()[0]
        totalPacketCountDest = query.value(17).toInt()[0]
        duration = query.value(11).toInt()[0]
        
        #TODO: Write method which determines the enterprise site from the ip address.
        #TODO: Work with the data i.e. put it in a new datatable
        #Need a check-, insert- and update Method for it.
        #TODO: Write method which determines the exact starttime interval
         
        
        


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