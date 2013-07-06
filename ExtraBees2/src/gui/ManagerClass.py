
from PyQt4 import QtCore, QtSql

def singleton(cls):
    if not cls._instance:
        cls._instance = cls()

    return cls._instance


class ManagerClass(object):
    _instance = None
    def __init__(self):    
        self.CT = QtCore.QDateTime(QtCore.QDate(2013, 4, 1), QtCore.QTime(8,40,0))
        self.INTERVAL = 0
        self.DB = QtSql.QSqlDatabase.addDatabase("QMYSQL")
        self.DB.setDatabaseName("datavis")
        self.DB.setUserName("datavis")    
        self.DB.setPassword("DataVis")
        self.DB.open()
        