'''
Created on 2013-July-7th

@author: lisijia
'''

import math
from PyQt4 import QtGui, QtCore, QtSql
from locale import str
from SlaveClass import SlaveClass

class cloudbubbledata(SlaveClass):
    '''
    classdocs
    '''


    def __init__(self,sitenumber,parent = None):
        '''
        Constructor
        '''
        SlaveClass.__init__(self)  
        self.sitenumber = sitenumber
#         self.communicationQuery =QtSql.QSqlQuery()
#         self.healthQuery = QtSql.QSqlQuery()
#         self.trafficdict = {}
#         self.averageValue=self.getAverageValue()
#         self.basenumber = self.averageValue**(1.0/20) 
#         self.healthdict = {}
#         self.prepareSiteQuery()
#         self.communicationQuery.prepare("Select * from networkflow.site1networkflow where Starttime=:time Order By Trafficload LIMIT 50 ")

        '''
        @param sitenumber: Should be 1, 2 or 3 to specify the site number
        '''
        
    def prepareSiteQuery(self):
        trafficquery = "Select * from networkflow.site"+str(self.sitenumber)+"networkflow where Starttime=:time Order By Trafficload desc LIMIT 50 "
        site1healthquery = "Select * from networkflow.site"+str(self.sitenumber)+"health where Starttime=:time"
        self.communicationQuery.prepare(trafficquery)
        self.healthQuery.prepare(site1healthquery)
        
        
    def updateSiteData(self):
        self.trafficdict.clear()
        self.healthdict.clear()
#         hour = timeSlot/12
#         minute = (timeSlot % 12)*5
#         timeString = str(hour).zfill(2)+":"+str(minute).zfill(2)+":00"
# #        timeDate = "2013-04-"+str(day).zfill(2)+" "+timeString
# 
#        timeDate = "2013-04-01 08:45:00"

        self.communicationQuery.bindValue(":time", self.manager.CT)
        self.communicationQuery.exec_()
        while(self.communicationQuery.next()):
            radius = self.transToRadius(int(self.communicationQuery.value(2).toString()))

            self.trafficdict[self.communicationQuery.value(1).toString()] = float(radius)
            
        self.healthQuery.bindValue(":time", self.manager.CT)
        self.healthQuery.exec_()
        while(self.healthQuery.next()):
            healthip = self.healthQuery.value(1).toString()
            if self.trafficdict.has_key(healthip):
                self.healthdict[self.healthQuery.value(1).toString()]= int(self.healthQuery.value(2).toString())

    
    def transToRadius(self,trafficload):
        radius=0
        if trafficload==0:
            return radius
        else:
            radius = math.log(trafficload,self.basenumber)        
        if radius <10:
            radius = 10
        elif radius >30:
            radius =30
        return radius
    
    def getAverageValue(self):
        averageQuery = QtSql.QSqlQuery()
        query = "Select avg(Trafficload) from networkflow.site"+str(self.sitenumber)+"networkflow"
        averageQuery.prepare(query)
        averageQuery.exec_()
        while(averageQuery.next()):
            averageValue = float(averageQuery.value(0).toString())
            return averageValue
        
    
        
            
            
        
        