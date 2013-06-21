'''
Created on 2013-6-18

@author: sijia
'''


from PyQt4 import QtGui, uic, QtCore 


class atomicBubble(QtGui.QGraphicsEllipseItem):
    '''
    Atomic Bubble is used to create a single bubble in cloud bubble.
    '''
    bubblepainter =None

    def __init__(self,x,y,radius):
        QtGui.QGraphicsEllipseItem.__init__(self,x,y,radius,radius)
        self.radius=radius
        self.loc = QtCore.QPoint(x,y)
#        self.painter=QtGui.QPainter()
        self.brush = QtGui.QBrush(QtCore.Qt.black)
        self.color = self.setBubbleColor('good')

 #       self.dr()
    '''
    set Bubble size based on the traffic load
    '''
    def setBubbleSize(self,trafficload):
        self.radius = trafficload
        
    '''
    setBubbleColor is used to set the bubble color and update the bubble color in dynamic
    '''
    def setBubbleColor(self,health):
        if health == 'good':
            return QtCore.Qt.green
        elif health == 'bad':
            return QtCore.Qt.red
        else:
            return QtCore.Qt.black
        
               
     

        
        
    def paint(self, painter, option, widget):
        painter.setBrush(self.color)
        painter.drawEllipse(self.loc,self.radius,self.radius)        
        


        