'''
Created on 2013-6-18

@author: sijia
'''


from PyQt4 import QtGui, QtCore 


class atomicBubble(QtGui.QGraphicsEllipseItem):
    '''
    Atomic Bubble is used to create a single bubble in cloud bubble.
    '''
    bubblepainter =None

    def __init__(self,x,y,radius,bubblename='empty'):
        QtGui.QGraphicsEllipseItem.__init__(self,x,y,radius,radius)
        self.radius=radius
        self.loc = QtCore.QPoint(x,y)
#        self.painter=QtGui.QPainter()
        self.brush = QtGui.QBrush(QtCore.Qt.black)
        self.color = self.setBubbleColor('good')
        self.setToolTip('Richard Bubble')
        self.setAcceptHoverEvents(True)
        

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
        
        
    '''
    If the bubble is not in top 50 list, the bubble needs to be renamed. 
    @param newname: QString
    '''
    def setBubbleName(self,newname):
        self.setToolTip(newname)
        
        
    '''
    Override the hoverEnterEvent
    Implement that shows the name of bubble when the mouse is hovering on the bubble.
    '''
    def hoverEnterEvent(self,event):
        pos= event.screenPos()
        tooltip =self.toolTip()
        QtGui.QToolTip.showText(pos,tooltip)
    
    
    '''
    Override the hoverLeaveEvent
    Implement that hide the name of bubble when the mouse leaves hovering on the bubble.
    '''
    def hoverLeaveEvent(self, event):
        QtGui.QToolTip.hideText()
        
    '''
    Override the paint
    Draw a circle
    '''    
        
    def paint(self, painter, option, widget):
        painter.setBrush(self.color)
        painter.drawEllipse(self.loc,self.radius,self.radius) 
        print self.color.red()      
        print self.color.green()
        
        
    


        