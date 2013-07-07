'''
Created on 2013-6-18

@author: Richard
'''
import math
from SiteNode import SiteNode
from PyQt4 import QtGui, QtCore, QtSql
from gui import SlaveClass

CENTER_X_SCALE = 0.5
CENTER_Y_SCALE = 0.6
NODE_WIDTH_SCALE = 0.08
NODE_HEIGHT_SCALE = 0.08

class NetworkFlow(QtGui.QWidget, SlaveClass):
    def __init__(self, parent=None):
        QtGui.QWidget.__init__(self, parent)
        SlaveClass.__init__(self)        
        self.lineThickness = [0,0,0,0,0,0] # initial
        self.health = [[0,0,0],[1,2,1],[4,1,2],[5,6,7]]
        
        self.communicationQuery = QtSql.QSqlQuery()
        self.communicationQuery.prepare("Select if(test.srcESite = :site, test.destESite, test.srcESite) as dest, sum(test.traffic) from (SELECT srcEsite, destESite,SUM(SumTotalBytesSrc + SumTotalBytesDest) as traffic FROM datavis.macro_networkflow WHERE Starttime = :time AND NOT (srcESite = destESite) GROUP BY srcESite, destESite HAVING srcESite = :site OR destESite = :site) as test group by dest;")
        
        self.numberOfSites = 3        
        self.startAngle = -90
        self.angleStep = 360 / self.numberOfSites 
             
        
        self.nodeList = []
        self.initNodes(784,439)

    def initNodes(self,width,height):
        nodeNames = ["Internet", "Site 1", "Site 2", "Site 3"]
        nodeDBName = ["INTERNET", "EnterpriseSite1", "EnterpriseSite2", "EnterpriseSite3"]
        nodeWidth = NODE_WIDTH_SCALE*width
        nodeHeight = NODE_HEIGHT_SCALE*width
        
        nodeRadius = height *0.4     
        nodePositions = [QtCore.QPoint(0,0)]
        for i in range(0,len(nodeNames)):
            point = QtCore.QPointF(nodeRadius *math.cos(math.radians(self.startAngle)),nodeRadius * math.sin(math.radians(self.startAngle)))
            nodePositions.append(point)
            self.startAngle = self.startAngle + self.angleStep
            node = SiteNode(nodePositions[i],width*CENTER_X_SCALE,height*CENTER_Y_SCALE,nodeWidth,nodeHeight,nodeNames[i],self.health[i], nodeDBName[i])
            self.nodeList.append(node)
        self.startAngle = -90
            
    def updateNodes(self,width,height):
        nodeRadius = height *0.4
        for i in range(0,len(self.nodeList)):
            if not (i == 0):
                point = QtCore.QPointF(nodeRadius *math.cos(math.radians(self.startAngle)),nodeRadius * math.sin(math.radians(self.startAngle)))
                self.nodeList[i].pos = point
            self.nodeList[i].w = width*NODE_WIDTH_SCALE
            self.nodeList[i].h = width*NODE_HEIGHT_SCALE
            self.startAngle = self.startAngle + self.angleStep
        self.startAngle = -90
        
    def paintEvent(self, event):
        self.updateData()
        self.updateNodes(self.width(),self.height())
        paint = QtGui.QPainter()
        paint.begin(self)
        paint.setRenderHint(QtGui.QPainter.Antialiasing, True)
        paint.setRenderHint(QtGui.QPainter.TextAntialiasing, True)

        color = QtGui.QColor(0, 0, 0)
        color.setNamedColor('#d4d4d4')
        paint.setPen(color)

        paint.translate(self.width()*CENTER_X_SCALE,self.height()*CENTER_Y_SCALE)
                        
        paint.setPen(QtGui.QColor(0, 0, 0))
        paint.setFont(QtGui.QFont('Verdana', 10))
        paint.setBrush(QtGui.QColor(113,187, 194, 255))
        
        self.drawEdges(paint)
        
        for n in self.nodeList:
            n.draw(paint)

        paint.end()
        
    def updateData(self):
                
        self.communicationQuery.bindValue(":time", self.manager.CT)
        #print timeDate
        self.lineThickness = [0,0,0,0,0,0]
        self.communicationQuery.bindValue(":site", "INTERNET")
        self.communicationQuery.exec_()
        while(self.communicationQuery.next()):
            if(self.communicationQuery.value(0) == "EnterpriseSite1"):
                self.lineThickness[0] = self.lineThickness[0] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "EnterpriseSite2"):
                self.lineThickness[1] = self.lineThickness[1] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "EnterpriseSite3"):
                self.lineThickness[2] = self.lineThickness[2] + int(self.communicationQuery.value(1).toString());
                
        self.communicationQuery.bindValue(":site", "EnterpriseSite1")
        self.communicationQuery.exec_()
        while(self.communicationQuery.next()):
            if(self.communicationQuery.value(0) == "INTERNET"):
                self.lineThickness[0] = self.lineThickness[0] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "EnterpriseSite2"):
                self.lineThickness[3] = self.lineThickness[3] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "EnterpriseSite3"):
                self.lineThickness[4] = self.lineThickness[4] + int(self.communicationQuery.value(1).toString());
                
        self.communicationQuery.bindValue(":site", "EnterpriseSite2")
        self.communicationQuery.exec_()
        while(self.communicationQuery.next()):
            if(self.communicationQuery.value(0) == "EnterpriseSite1"):
                self.lineThickness[3] = self.lineThickness[3] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "INTERNET"):
                self.lineThickness[1] = self.lineThickness[1] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "EnterpriseSite3"):
                self.lineThickness[5] = self.lineThickness[5] + int(self.communicationQuery.value(1).toString());
                
        self.communicationQuery.bindValue(":site", "EnterpriseSite3")
        self.communicationQuery.exec_()
        while(self.communicationQuery.next()):
            if(self.communicationQuery.value(0) == "EnterpriseSite1"):
                self.lineThickness[4] = self.lineThickness[4] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "EnterpriseSite2"):
                self.lineThickness[5] = self.lineThickness[5] + int(self.communicationQuery.value(1).toString());
            if(self.communicationQuery.value(0) == "INTERNET"):
                self.lineThickness[2] = self.lineThickness[2] + int(self.communicationQuery.value(1).toString());
        
        #print self.lineThickness
        for i in range(0,len(self.lineThickness)):
            self.lineThickness[i] = math.log(self.lineThickness[i]+1) 
        #print self.lineThickness  
     
                
        '''
        for i in range(0,self.health.__len__()):
            self.health[i][0] = random.randint(self.lineHealthMin,self.lineHealthMax)
            self.health[i][1] = random.randint(self.lineHealthMin,self.lineHealthMax)
            self.health[i][2] = random.randint(self.lineHealthMin,self.lineHealthMax)
        '''

    def drawEdges(self,paint):
        # quick 'n' dirty implementation >temporarily< 
        if (self.lineThickness[0] != 0):
            paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[0] , QtCore.Qt.SolidLine))
            paint.drawLine(self.nodeList[0].pos,self.nodeList[1].pos)
        if (self.lineThickness[1] != 0):
            paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[1] , QtCore.Qt.SolidLine))
            paint.drawLine(self.nodeList[0].pos,self.nodeList[2].pos)
        if (self.lineThickness[2] != 0):
            paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[2] , QtCore.Qt.SolidLine))
            paint.drawLine(self.nodeList[0].pos,self.nodeList[3].pos)
        if (self.lineThickness[3] != 0):
            paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[3] , QtCore.Qt.SolidLine))
            paint.drawLine(self.nodeList[1].pos,self.nodeList[2].pos)
        if (self.lineThickness[4] != 0):
            paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[4] , QtCore.Qt.SolidLine))
            paint.drawLine(self.nodeList[1].pos,self.nodeList[3].pos)
        if (self.lineThickness[5] != 0):
            paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[5] , QtCore.Qt.SolidLine))
            paint.drawLine(self.nodeList[2].pos,self.nodeList[3].pos)
       
    def drawRectNode(self,painter,pos,width,height,name="Blubb"):
        painter.translate(pos.x(),pos.y())
        painter.drawRect(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())
               
    def mouseReleaseEvent(self,event):
        self.clickedOnNode(event)
       
    def clickedOnNode(self,event):
        for n in self.nodeList:
            xDistance = abs(n.pos.x()-(event.x()-(self.width()*CENTER_X_SCALE)))
            yDistance = abs(n.pos.y()-(event.y()-(self.height()*CENTER_Y_SCALE)))
            if(xDistance < (n.w*0.5) and yDistance < (n.h*0.5)):
                #newPoint = QtCore.QPoint(event.x()-(self.width()*CENTER_X_SCALE),event.y()-(self.height()*CENTER_Y_SCALE))
                #n.setPos(newPoint)
                print "Hit " + n.name