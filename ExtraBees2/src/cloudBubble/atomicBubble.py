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
            return QtGui.QColor(0,255,0)
        elif health == 'bad':
            return QtGui.QColor(255,0,0)
        else:
            return QtCore.Qt.black
        
#     def setBubbleLoc(self,duration,newloc,bubble):
#         self.tl.setDuration(duration)
#         self.tl.setFrameRange(0,100)
#         self.animation = QtGui.QGraphicsItemAnimation()
#         self.animation.setItem(bubble)
#         self.animation.setPosAt(0.00000000001,newloc)
#                       
#      

        
        
    def paint(self, painter, option, widget):
        painter.setBrush(self.color)
        painter.drawEllipse(self.loc,self.radius,self.radius) 
        print self.color.red()      
        print self.color.green()


        