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


def iterateQuery(query):
    while (query.next()):
#        starttime = 
#        srcIP = 
#        destIP = 
#        ipLayerProtocol = 
#        totalBytesSrc = 
#        totalBytesDest =
#        duration =
        #TODO: Write method which determines the enterprise site from the ip address.
        #TODO: Work with the data i.e. put it in a new datatable
        #Need a check-, insert- and update Method for it.
         
        
        


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