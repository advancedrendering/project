'''
Created on 2013-6-18

@author: sijia
'''


from PyQt4 import QtGui, QtCore 
from math import sqrt

#from numpy.lib.scimath import sqrt



class atomicBubble(QtGui.QGraphicsEllipseItem):
    '''
    Atomic Bubble is used to create a single bubble in cloud bubble.
    '''
    bubblepainter =None

    def __init__(self,x,y,radius,bubblename='empty'):
        QtGui.QGraphicsEllipseItem.__init__(self,x,y,radius,radius)
        self.radius=radius
        self.loc = QtCore.QPointF(x,y)
#        self.painter=QtGui.QPainter()
        self.brush = QtGui.QBrush(QtCore.Qt.black)
        self.color = self.setBubbleColor('good')
        self.setToolTip(bubblename)
        self.setAcceptHoverEvents(True)
        self.nextradius = 10
        self.nextlocation = self.loc
        self.setCacheMode(0)
        

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
        painter.save()
        painter.setBrush(self.color)
        painter.fillPath(self.shape(),self.color)
        painter.restore()
    '''
    Get the shape of bubble
    '''                
    def shape(self):
        p=QtGui.QPainterPath()
        p.addEllipse(self.loc,self.radius,self.radius)
        return p
    

    '''
    detect collides with other bubble
    '''
    def collidesWithItem(self,otherbubblenextlocation,otherbubblenextradius):
        x =self.nextlocation.x()
        y =self.nextlocation.y()
        distance = sqrt(pow((otherbubblenextlocation.x()-x), 2) +pow((otherbubblenextlocation.y()-y), 2))
        if distance >=self.nextradius+otherbubblenextradius:
            return False
        else:
            return True
        
    def detectCollideInNextLocation(self,nextradiuslist,nextlocationlist,seq):
        for i in range (0,seq):
            j=self.collidesWithItem(nextlocationlist[i],nextradiuslist[i])
            if j:
                return True
        return False
        

    def getDistanceWithOtherBubble(self,otherbubble):
        x=self.loc.x()
        y=self.loc.y()
        distance = (sqrt(pow((otherbubble.loc.x()-x), 2) +pow((otherbubble.loc.y()-y), 2)))-self.radius-otherbubble.radius
        return distance
    
    
    def getCenterDistanceWithOtherBubble(self,otherbubble):
        x=self.loc.x()
        y=self.loc.y()
        distance = sqrt(pow((otherbubble.loc.x()-x), 2) +pow((otherbubble.loc.y()-y), 2))
        return distance
    
    def getCurrentloc(self):
        return self.loc
    
    def getCurrentRadius(self):
        return self.radius
    
    def setNextRadius(self,nextradius):
        self.nextradius = nextradius
        
    def setNextLocation(self,nextlocation):
        self.nextlocation = nextlocation
    
    def getNextLocation(self):
        return self.nextlocation
    
    