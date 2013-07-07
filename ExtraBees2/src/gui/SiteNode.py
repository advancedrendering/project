'''
Created on 03.07.2013

@author: Richard, Maarten
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
        self.communicationQuery.prepare("""SELECT AVG(avgDiskUsagePercent), MAX(maxDiskUsagePercent), AVG(avgLoadPercent), MAX(maxLoadPercent) FROM datavis.healthserverbyesites 
        WHERE  ESite = :esite AND receivedDate = :currentTime;""")
        
        self.health_com_query = QtSql.QSqlQuery()
        self.health_com_query.prepare("""SELECT statusVal, COUNT(*) FROM datavis.healthserverbyesites WHERE receivedDate = :receivedDate AND ESite = :esite GROUP BY statusVal;""")
                
        #define some constantss
        self.avgDiskUsagePercent = 0
        self.maxDiskUsagePercent = 1
        self.avgLoadPercent = 2
        self.maxLoadPercent = 3
        
        self.isOver = False
        self.isSelected = False
        
        
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
        loc_text = self.name
        if self.isOver == True or self.isSelected == True:
            self.communicationQuery.bindValue(":currentTime", self.manager.CT)
            self.communicationQuery.bindValue(":esite", self.dbname)
            self.communicationQuery.exec_()
                    
            #construct new health list
            loc_health = [0,0,0,0]
            loc_health_labels = ["avg disk", "max disk", "avg cpu", "max cpu"]
            while(self.communicationQuery.next()):
                loc_health[0] = self.communicationQuery.value(self.avgDiskUsagePercent).toInt()[0]
                loc_health[1] = self.communicationQuery.value(self.maxDiskUsagePercent).toInt()[0]
                loc_health[2] = self.communicationQuery.value(self.avgLoadPercent).toInt()[0]
                loc_health[3] = self.communicationQuery.value(self.maxLoadPercent).toInt()[0]
            
#             print self.name, loc_health
            self.drawBars(painter, self.pos, 10, self.h, self.name, loc_health, loc_health_labels, 135)

            #get health info.
            
            num_info_messages = 0
            num_warn_messages = 0
            num_err_messages = 0
            num_conn_lost_messages = 0
            
            self.health_com_query.bindValue(":receivedTime", self.manager.CT)
            self.health_com_query.bindValue(":esite", self.dbname)
            self.health_com_query.exec_()
            while(self.health_com_query.next()):
                statusVal = self.health_com_query.value(0).toInt()[0]
                if statusVal == 1:
                    num_info_messages = self.health_com_query.value(1).toInt()[0]
                elif statusVal == 2:
                    num_warn_messages = self.health_com_query.value(1).toInt()[0]
                elif statusVal == 3:
                    num_err_messages = self.health_com_query.value(1).toInt()[0]
                elif statusVal == 4:
                    num_conn_lost_messages = self.health_com_query.value(1).toInt()[0]
            
            loc_brush = None
            if num_err_messages > 0:
                loc_brush = QtGui.QBrush(QtGui.QColor(255, 0, 0, 255))
            elif num_conn_lost_messages > 0:
                loc_brush = QtGui.QBrush(QtGui.QColor(255, 165, 0, 255))
            elif num_warn_messages > 0:
                loc_brush = QtGui.QBrush(QtGui.QColor(255, 255, 0, 255))
            else:
                loc_brush = QtGui.QBrush(QtGui.QColor(153, 217, 234, 255))
            
            painter.setBrush(loc_brush)
            painter.setPen(QtGui.QPen(QtGui.QColor(0,0,0)))
            painter.drawRoundedRect(self.pos.x() + 20, self.pos.y() + 15, 210, 40, 2.0, 2.0)
            painter.drawText(QtCore.QPoint(self.pos.x() + 30, self.pos.y() + 30), "No. Info: " + str(num_info_messages) + ", No. Warn: " + str(num_warn_messages))
            painter.drawText(QtCore.QPointF(self.pos.x() + 30, self.pos.y() + 50), "No. Err: " + str(num_err_messages) + " , No. ConnLost: " + str(num_conn_lost_messages))
                
            #No. Info: " + str(num_info_messages) + "No. Warn: " + str(num_warn_messages) + "\n No. Err: " + str(num_err_messages) + "\n No. ConnLost: " + str(num_conn_lost_messages)
                
                
        self.drawEllipticNode(painter, self.pos, self.w, self.h,self.name)
    
    def drawBars(self, painter, pos, width, height, name, health, health_labels, angle):
        painter.translate(pos.x(),pos.y())
        numberOfHealthStatuses = len(health)
        angleStep = 30 
        innerRadius = height *0.8   
#         outerRadius = height *0.9
        save_brush = painter.brush()
        for i in range(0, numberOfHealthStatuses):
#             painter.setPen(QtGui.QPen(QtGui.QColor(113,187, 194, 128) , width*0.25 , QtCore.Qt.SolidLine))
            loc_color_brush = QtGui.QColor((health[i] / 100.0) * 255.0,((100 - health[i]) / 100.0) * 255.0,0, 220.0)
            loc_color_pen = QtGui.QColor((health[i] / 100.0) * 255.0,((100 - health[i]) / 100.0) * 255.0,0)
            painter.setPen(QtGui.QPen(QtGui.QColor(loc_color_pen) , 1 , QtCore.Qt.SolidLine))
            painter.setBrush(QtGui.QColor(loc_color_brush))
            if name != "Internet" and health[i] != 0:
#                 painter.drawLine(QtCore.QPointF(innerRadius *math.cos(math.radians(angle)),innerRadius * math.sin(math.radians(angle))),
#                              QtCore.QPointF((innerRadius+(health[i] * 0.5)) * math.cos(math.radians(angle)),(innerRadius+(health[i] * 0.5)) * math.sin(math.radians(angle))))
                painter.translate(QtCore.QPointF(innerRadius *math.cos(math.radians(angle)),innerRadius * math.sin(math.radians(angle))))
                painter.rotate(angle)
                poly = QtGui.QPolygonF([QtCore.QPointF(0.0,  width), QtCore.QPointF(health[i] * 0.75,  width), QtCore.QPointF(health[i] * 0.75, - width), QtCore.QPointF(0,- width)])
                painter.drawPolygon(poly)
                painter.rotate(180)
                painter.setPen(QtGui.QPen(QtGui.QColor(70,70,70, 150) , 1 , QtCore.Qt.SolidLine))
                painter.font().setPointSize(300)
                painter.drawText(- health[i] *0.75  * 0.5 - 12, 4, str(health[i]) + "%")
                painter.drawText(-130, 4, health_labels[i])
                painter.rotate(-angle + 180)
                painter.translate(QtCore.QPointF(-innerRadius *math.cos(math.radians(angle)),-innerRadius * math.sin(math.radians(angle))))
            elif name != "Internet" and health[i] == 0:
                painter.translate(QtCore.QPointF(innerRadius *math.cos(math.radians(angle)),innerRadius * math.sin(math.radians(angle))))
                painter.rotate(angle)
                poly = QtGui.QPolygonF([QtCore.QPointF(0.0,  width), QtCore.QPointF(1,  width), QtCore.QPointF(1, - width), QtCore.QPointF(0,- width)])
                painter.drawPolygon(poly)
                painter.rotate(180)
                painter.setPen(QtGui.QPen(QtGui.QColor(70,70,70, 150) , 1 , QtCore.Qt.SolidLine))
                painter.font().setPointSize(300)
                painter.drawText(- 1 * 0.5 - 12, 4, str(0) + "%")
                painter.drawText(-135, 4, health_labels[i])
                painter.rotate(-angle + 180)
                painter.translate(QtCore.QPointF(-innerRadius *math.cos(math.radians(angle)),-innerRadius * math.sin(math.radians(angle))))
            angle = angle + angleStep
        painter.translate(-pos.x(),-pos.y())
        #reset brush
        painter.setBrush(save_brush)
    
    def drawEllipticNode(self,painter,pos,width,height,name ):
        painter.translate(pos.x(),pos.y())
        linewidht = 1
        if (self.isOver or self.isSelected) and self.name != "Internet":
            linewidht = 2
            font = painter.font()
            font.setBold(True)
            painter.setFont(font)
        painter.setPen(QtGui.QPen(QtCore.Qt.black , linewidht , QtCore.Qt.SolidLine))
        painter.drawEllipse(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())
        font = painter.font()
        font.setBold(False)
        painter.setFont(font)
        painter.setPen(QtGui.QPen(QtCore.Qt.black , 1 , QtCore.Qt.SolidLine))
        

    def drawRectNode(self,painter,pos,width,height,name="Blubb"):
        painter.translate(pos.x(),pos.y())
        painter.drawRect(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())