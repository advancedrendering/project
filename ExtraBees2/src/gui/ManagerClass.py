'''
Created on 2013-7-7

@author: Richard, Maarten
'''

from PyQt4 import QtCore, QtSql

def singleton(cls):
    if not cls._instance:
        cls._instance = cls()

    return cls._instance


class ManagerClass(object):
    _instance = None
    def __init__(self):    
        self.CT = QtCore.QDateTime(QtCore.QDate(2013, 4, 1), QtCore.QTime(8,40,0))
        self.CW = QtCore.QDate(2013,4,1)
        self.INTERVAL = 0
        self.DB = QtSql.QSqlDatabase.addDatabase("QMYSQL")
        self.DB.setDatabaseName("datavis")
        self.DB.setUserName("datavis")    
        self.DB.setPassword("DataVis")
        self.DB.open()
        
        self.TOTALBYTES = 0
        self.THROUGHPUT = 1
        self.NUM_PACKAGES = 2
        self.NUM_PACKAGES_PER_SECOND = 3 
        self.NUM_CONNECTIONS = 4
        self.NUM_ERRORS = 5
        self.NUM_WARNINGS = 6
        self.NUM_SERVER_NOT_AVAILABLE = 7 
        
        #self.NetMode = self.TOTALBYTES
        self.NetMode = self.NUM_CONNECTIONS
        
    
        