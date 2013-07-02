'''
Created on 2013-6-18

@author: Richard
'''
import math
import random
from PyQt4 import QtGui, QtCore

class NetworkFlow(QtGui.QWidget):
    def __init__(self, parent=None):
        QtGui.QWidget.__init__(self, parent)

        self.startAngle = -90 # random start angle
        self.lineThickness = [2,2,2,2,2,2] # initial
        self.health = [[0,0,0],[1,2,1],[4,1,2],[5,6,7]]
        self.lineThicknessMin = 2
        self.lineThicknessMax = 10
        self.lineHealthMin = 2
        self.lineHealthMax = 10

    def paintEvent(self, event):
        paint = QtGui.QPainter()
        paint.begin(self)
        paint.setRenderHint(QtGui.QPainter.Antialiasing, True)
        paint.setRenderHint(QtGui.QPainter.TextAntialiasing, True)

        color = QtGui.QColor(0, 0, 0)
        color.setNamedColor('#d4d4d4')
        paint.setPen(color)

        w = self.width()
        h = self.height()
        nodeWidth = 0.08*w
        nodeHeight = 0.08*w
        
        nodeNames = ["Internet", "Site 1", "Site 2", "Site 3"]
        '''
        nodePositions =[
                        QtCore.QPointF(0.5*w,0.5*h), #Internet
                        QtCore.QPointF(0.25*w,0.8*h),#Site 1
                        QtCore.QPointF(0.5*w,0.8*h), #Site 2
                        QtCore.QPointF(0.75*w,0.8*h),#Site 3
                        ]
        '''
        paint.translate(w*0.5,h*0.6)
        #self.startAngle = self.startAngle + 0.1
        angle = self.startAngle
        numberOfSites = 3
        angleStep = 360 / numberOfSites 
        radius = h *0.4     
        nodePositions = [QtCore.QPointF(0,0)]
        for i in range(0,numberOfSites):
            point = QtCore.QPointF(radius *math.cos(math.radians(angle)),radius * math.sin(math.radians(angle)))
            nodePositions.append(point)
            angle = angle + angleStep
        
        # quick 'n' dirty implementation >temporarily< 
        paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[0] , QtCore.Qt.SolidLine))
        paint.drawLine(nodePositions[0],nodePositions[1])
        paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[1] , QtCore.Qt.SolidLine))
        paint.drawLine(nodePositions[0],nodePositions[2])
        paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[2] , QtCore.Qt.SolidLine))
        paint.drawLine(nodePositions[0],nodePositions[3])
        paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[3] , QtCore.Qt.SolidLine))
        paint.drawLine(nodePositions[1],nodePositions[2])
        paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[4] , QtCore.Qt.SolidLine))
        paint.drawLine(nodePositions[1],nodePositions[3])
        paint.setPen(QtGui.QPen(QtCore.Qt.black , self.lineThickness[5] , QtCore.Qt.SolidLine))
        paint.drawLine(nodePositions[2],nodePositions[3])
                        
        paint.setPen(QtGui.QColor(0, 0, 0))
        paint.setFont(QtGui.QFont('Verdana', 10))
        paint.setBrush(QtGui.QColor(113,187, 194, 255))
        self.drawRectNode(paint,nodePositions[0],nodeWidth,nodeHeight,nodeNames[0])   
        
        paint.setBrush(QtGui.QColor(153, 217, 234, 255))
        ang = 240
        for i in range(1,4):
            self.drawEllipticNode(paint,nodePositions[i],nodeWidth,nodeHeight,nodeNames[i],self.health[i],ang)
            ang = ang +120

        #paint.setBrush(QtGui.QColor(113,127, 194, 255))
        #self.drawEllipticNode(paint, nodePositions[0], nodeWidth*1.5, nodeHeight*1.5, nodeNames[0])
        
        paint.end()
        
    def updateLineThickness(self):
        for i in range(0,self.lineThickness.__len__()):
            self.lineThickness[i] = random.randint(self.lineThicknessMin,self.lineThicknessMax)
        for i in range(0,self.health.__len__()):
            self.health[i][0] = random.randint(self.lineHealthMin,self.lineHealthMax)
            self.health[i][1] = random.randint(self.lineHealthMin,self.lineHealthMax)
            self.health[i][2] = random.randint(self.lineHealthMin,self.lineHealthMax)
        
    def drawRectNode(self,painter,pos,width,height,name="Blubb"):
        painter.translate(pos.x(),pos.y())
        painter.drawRect(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())
        
        
    def drawEllipticNode(self,painter,pos,width,height,name,health,angle):
        painter.translate(pos.x(),pos.y())
        numberOfHealthStatuses = health.__len__()
        angleStep = 30 
        innerRadius = height *0.8   
        outerRadius = height *0.9
        for i in range(0,numberOfHealthStatuses):
            painter.setPen(QtGui.QPen(QtGui.QColor(113,187, 194, 128) , width*0.25 , QtCore.Qt.SolidLine))
            painter.drawLine(QtCore.QPointF(innerRadius *math.cos(math.radians(angle)),innerRadius * math.sin(math.radians(angle))),
                             QtCore.QPointF((outerRadius+(health[i]*3)) *math.cos(math.radians(angle)),(outerRadius+(health[i]*3)) * math.sin(math.radians(angle))))
                             
            angle = angle + angleStep
        painter.setPen(QtGui.QPen(QtCore.Qt.black , 1 , QtCore.Qt.SolidLine))
        painter.drawEllipse(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())
        
        
        