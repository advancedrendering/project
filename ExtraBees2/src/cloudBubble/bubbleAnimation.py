'''
Created on 2013-6-22

@author: sijia
'''
from PyQt4 import QtCore,QtGui


class bubbleAnimation(QtCore.QObject):
    '''
    This class is used to implement animation of bubble
    '''
 
 
    def __init__(self,bubble):
        '''
        @param bubble:The bubble which needs to display animation 
        '''      
        QtCore.QObject.__init__(self)
        self.tl4color = QtCore.QTimeLine()
        self.bubble = bubble
        self.radius = self.bubble.radius
        self.location = self.bubble.loc
        
        
        
        '''
        Move the bubble in a smooth way to the new location
        '''
    def setbubbleloc (self,duration,location):
        tl4loc = QtCore.QTimeLine()
        tl4loc.setDuration(duration)
        tl4loc.setFrameRange(0,100)
        self.changeLoc = QtGui.QGraphicsItemAnimation()
        self.changeLoc.setItem(self.bubble)
        self.changeLoc.setTimeLine(tl4loc)
        tl4loc.start()
        self.changeLoc.setPosAt(0.00000000001,location)
        self.location = location
        self.connect(tl4loc,QtCore.SIGNAL('finished()'),self.updateloc)
            
    
        '''
        Resize the bubble to visual the traffic load of each workstation
        '''
    def setbubblesize(self,duration,scale):
        tl4size = QtCore.QTimeLine()
        tl4size.setDuration(duration)
        tl4size.setFrameRange(0,100)
        tl4size.start()
        self.changeSize = QtGui.QGraphicsItemAnimation()
        self.changeSize.setItem(self.bubble)
        self.changeSize.setTimeLine(tl4size)
        self.changeSize.setScaleAt(0.00000000001,scale,scale)
        self.radius = self.radius*scale
        self.connect(tl4size,QtCore.SIGNAL('finished()'),self.updateradius)    
#        self.bubble.radius=self.bubble.radius*scale
        
        
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
        
        
        '''stop all timelines'''
    def stop(self):

        
        '''
        Everytime after using animation, we have to use update()
        '''
        
    def updateloc(self):
        self.bubble.loc = self.location
    def updateradius(self):
        self.bubble.radius = self.radius
        

        
        