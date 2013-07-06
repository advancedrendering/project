'''
Created on 03.07.2013

@author: richard
'''
import math
from PyQt4 import QtGui, QtCore, QtSql

from SlaveClass import SlaveClass

class SiteNode(SlaveClass):
    
    def __init__(self,pos,xCenter,yCenter,nodeWidth,nodeHeight,name,health, dbname):
        SlaveClass.__init__(self)
        self.pos = pos
        self.xC = xCenter
        self.yC = yCenter
        self.w = nodeWidth
        self.h = nodeHeight
        self.name = name
        self.health = health
        self.dbname = dbname
        
        self.communicationQuery = QtSql.QSqlQuery()
        
        #define some constantss
        self.avgDiskUsagePercent = 0
        self.maxDiskUsagePercent = 1
        self.avgLoadPercent = 2
        self.maxLoadPercent = 3
        
    def mouseOver(self,event):
        xDistance = abs(self.pos.x()-(event.x()-self.xC))
        yDistance = abs(self.pos.y()-(event.y()-self.yC))
        if(xDistance < (self.w*0.5) and yDistance < (self.h*0.5)):
            return True
        else:
            return False

    def setPos(self,newPos):
        self.pos.setX(newPos.x())
        self.pos.setY(newPos.y())    

    def draw(self,painter):
        painter.setBrush(QtGui.QColor(153, 217, 234, 255))

        self.communicationQuery.prepare("""SELECT ESite, AVG(avgDiskUsagePercent), MAX(maxDiskUsagePercent), AVG(avgLoadPercent), MAX(maxLoadPercent) FROM datavis.healthserverbyesites 
        WHERE  ESite = :esite AND receivedDate = :currentTime AND receivedDate <= DATE_ADD( :currentTime2, INTERVAL :interval MINUTE) ;""")
        self.communicationQuery.bindValue(":currentTime", self.manager.CT)
        self.communicationQuery.bindValue(":currentTime2", self.manager.CT)
        self.communicationQuery.bindValue(":interval", self.manager.INTERVAL)
        self.communicationQuery.bindValue(":esite", self.dbname)
        self.communicationQuery.exec_()
                
        #construct new health list
        loc_health = []

        while(self.communicationQuery.next()):
            loc_health.append(self.communicationQuery.value(self.avgDiskUsagePercent).toInt()[0])
            loc_health.append(self.communicationQuery.value(self.maxDiskUsagePercent).toInt()[0])
            loc_health.append(self.communicationQuery.value(self.avgLoadPercent).toInt()[0])
            loc_health.append(self.communicationQuery.value(self.maxLoadPercent).toInt()[0])
        
        print loc_health
        self.drawEllipticNode(painter, self.pos, self.w, self.h, self.name, loc_health, 120)
        

    def drawEllipticNode(self,painter,pos,width,height,name,health,angle):
        painter.translate(pos.x(),pos.y())
        numberOfHealthStatuses = len(health)
        angleStep = 30 
        innerRadius = height *0.8   
        outerRadius = height *0.9
        for i in range(0, numberOfHealthStatuses):
            painter.setPen(QtGui.QPen(QtGui.QColor(113,187, 194, 128) , width*0.25 , QtCore.Qt.SolidLine))
            if name != "Internet" and health[i] != 0:
                painter.drawLine(QtCore.QPointF(innerRadius *math.cos(math.radians(angle)),innerRadius * math.sin(math.radians(angle))),
                             QtCore.QPointF((outerRadius+(health[i] / 100.0)) * math.cos(math.radians(angle)),(outerRadius+(health[i] / 100.0)) * math.sin(math.radians(angle))))
            angle = angle + angleStep
        painter.setPen(QtGui.QPen(QtCore.Qt.black , 1 , QtCore.Qt.SolidLine))
        painter.drawEllipse(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())

    def drawRectNode(self,painter,pos,width,height,name="Blubb"):
        painter.translate(pos.x(),pos.y())
        painter.drawRect(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())