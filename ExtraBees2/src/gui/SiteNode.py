'''
Created on 03.07.2013

@author: richard
'''
import math
from PyQt4 import QtGui, QtCore

class SiteNode():
    def __init__(self,pos,xCenter,yCenter,nodeWidth,nodeHeight,name,health):
        self.pos = pos
        self.xC = xCenter
        self.yC = yCenter
        self.w = nodeWidth
        self.h = nodeHeight
        self.name = name
        self.health = health
        
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
        self.drawEllipticNode(painter, self.pos, self.w, self.h, self.name, self.health, 120)
        

    def drawEllipticNode(self,painter,pos,width,height,name,health,angle):
       painter.translate(pos.x(),pos.y())
       numberOfHealthStatuses = health.__len__()
       angleStep = 30 
       innerRadius = height *0.8   
       outerRadius = height *0.9
       for i in range(0,numberOfHealthStatuses):
           painter.setPen(QtGui.QPen(QtGui.QColor(113,187, 194, 128) , width*0.25 , QtCore.Qt.SolidLine))
           if name != "Internet":
               painter.drawLine(QtCore.QPointF(innerRadius *math.cos(math.radians(angle)),innerRadius * math.sin(math.radians(angle))),
                            QtCore.QPointF((outerRadius+(health[i]*3)) *math.cos(math.radians(angle)),(outerRadius+(health[i]*3)) * math.sin(math.radians(angle))))
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