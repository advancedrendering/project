'''
Created on 2013-6-22

@author: sijia
'''
from PyQt4 import QtCore,QtGui



class bubbleAnimation():
    '''
    This class is used to implement animation of bubble
    '''
 
 
    def __init__(self,bubble):
        '''
        @param bubble:The bubble which needs to display animation 
        '''      
        self.tl4loc = QtCore.QTimeLine()
        self.tl4size = QtCore.QTimeLine()
        self.tl4color = QtCore.QTimeLine()
        self.bubble = bubble
        
        
        '''
        Move the bubble in a smooth way to the new location
        '''
    def setbubbleloc (self,duration,location):
        self.tl4loc.setDuration(duration)
        self.tl4loc.setFrameRange(0,100)
        self.changeLoc = QtGui.QGraphicsItemAnimation()
        self.changeLoc.setItem(self.bubble)
        self.changeLoc.setTimeLine(self.tl4loc)
        self.changeLoc.setPosAt(0.00000000001,location)
    
        '''
        Resize the bubble to visual the traffic load of each workstation
        '''
    def setbubblesize(self,duration,scale):
        self.tl4size.setDuration(duration)
        self.tl4size.setFrameRange(0,100)
        self.changeSize = QtGui.QGraphicsItemAnimation()
        self.changeSize.setItem(self.bubble)
        self.changeSize.setTimeLine(self.tl4loc)
        self.changeSize.setScaleAt(0.00000000001,scale,scale)
        if self.bubble.radius==0:
            self.bubble.__del__()
        
        '''
        Smoothly change the bubble color
        @param duration:unsigned int
        @param health:QtGui.QColor
        @todo: optimize the setbubblecolor after discussing
        '''
    def setbubblecolor(self,health):
        self.bubble.color = health
        '''
        green = self.bubble.color.green()
        red = self.bubble.color.red()
        blue = self.bubble.color.blue()
       
        for i in range(0,abs(redchange)):
            red = abs(red +(redchange/abs(redchange)))
            for k in range(0,abs(greenchange)):
                green = abs(green +(greenchange/abs(greenchange)))
                self.bubble.color = QtGui.QColor(red,green,0)
         '''
        
        
        '''start all timelines'''
    def start(self): 
        self.tl4loc.start()
        self.tl4size.start()
        
        
        '''stop all timelines'''
    def stop(self):
        self.tl4loc.stop()
        self.tl4size.stop()
        
        