'''
Created on 2013-6-18

@author: Richard
'''
from PyQt4 import QtGui, QtCore

class NetworkFlow(QtGui.QWidget):
    def __init__(self, parent=None):
        QtGui.QWidget.__init__(self, parent)

        self.setGeometry(300, 300, 350, 280)
        self.setWindowTitle('Colors')

    def paintEvent(self, event):
        paint = QtGui.QPainter()
        paint.begin(self)

        color = QtGui.QColor(0, 0, 0)
        color.setNamedColor('#d4d4d4')
        paint.setPen(color)

        w = self.width()
        h = self.height()
        nodePositions =[
                        QtCore.QPointF(0.5*w,0.5*h), #Router
                        QtCore.QPointF(0.25*w,0.8*h),#Site 1
                        QtCore.QPointF(0.5*w,0.8*h), #Site 2
                        QtCore.QPointF(0.75*w,0.8*h),#Site 3
                        QtCore.QPointF(0.5*w,0.2*h) #Internet
                        ]
        nodeNames = ["Router", "Site 1", "Site 2", "Site 3", "Internet"]
        
        paint.setPen(QtGui.QPen(QtCore.Qt.black , 5 , QtCore.Qt.SolidLine))
        paint.drawLine(nodePositions[0],nodePositions[1])
        paint.drawLine(nodePositions[0],nodePositions[2])
        paint.drawLine(nodePositions[0],nodePositions[3])
        paint.drawLine(nodePositions[0],nodePositions[4])
        paint.drawLine(nodePositions[1],nodePositions[2])
        paint.drawLine(nodePositions[2],nodePositions[3])
        
        # very dirty, but for now it should be oke ;)
        paint.translate(w/2,h/2*1.3)
        paint.drawArc( -w*0.3, -h*0.3, w*0.3*2, h*0.3*2, 220*16, 100*16)
        paint.translate(-w/2,-h/2*1.3)

        
        nodeWidth = 0.1*w
        nodeHeight = 0.1*h
        
        
        paint.setPen(QtGui.QColor(0, 0, 0))
        paint.setFont(QtGui.QFont('Verdana', 10))
        paint.setBrush(QtGui.QColor(113,187, 194, 255))
        self.drawRectNode(paint,nodePositions[0],nodeWidth,nodeHeight,nodeNames[0])   
        
        paint.setBrush(QtGui.QColor(153, 217, 234, 255))
        for i in range(1,4):
            self.drawRectNode(paint,nodePositions[i],nodeWidth,nodeHeight,nodeNames[i])

        paint.setBrush(QtGui.QColor(113,127, 194, 255))
        self.drawEllipticNode(paint, nodePositions[4], nodeWidth*1.5, nodeHeight*1.5, nodeNames[4])
        
        paint.end()
        
    def drawRectNode(self,painter,pos,width,height,name="Blubb"):
        painter.translate(pos.x(),pos.y())
        painter.drawRect(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())
        
        
    def drawEllipticNode(self,painter,pos,width,height,name="Blubb"):
        painter.translate(pos.x(),pos.y())
        painter.drawEllipse(-width/2, -height/2, width, height)
        painter.drawText(-width/2, -height/2, width, height,QtCore.Qt.AlignCenter,name)
        painter.translate(-pos.x(),-pos.y())
        