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
#        self.connect(tl4loc,QtCore.SIGNAL("finished()"),self.updateloc)
        tl4loc.setFrameRange(30,100)
        self.changeLoc = QtGui.QGraphicsItemAnimation()
        self.changeLoc.setItem(self.bubble)
        self.changeLoc.setTimeLine(tl4loc)
        tl4loc.start()
        self.changeLoc.setPosAt(0.00000001,location)
        self.location = location
        
            
    
        '''
        Resize the bubble to visual the traffic load of each workstation
        '''
    def setbubblesize(self,duration,radius):
        
        tl4size = QtCore.QTimeLine()
#        self.connect(tl4size,QtCore.SIGNAL("finished()"),self.updateradius) 
        tl4size.setDuration(duration)
        tl4size.setFrameRange(30,100)
        tl4size.start()
        self.changeSize = QtGui.QGraphicsItemAnimation()
        self.changeSize.setItem(self.bubble)
        self.changeSize.setTimeLine(tl4size)
#         if self.bubble.radius>=radius:
#             self.changeSize.setShearAt(0.00000000001,self.bubble.radius/radius,self.bubble.radius/radius)
#         else:
        self.changeSize.setScaleAt(0.00000001,radius/self.bubble.radius,radius/self.bubble.radius)
        self.radius = radius
           
        
        
        '''
        Smoothly change the bubble color
        @param duration:unsigned int
        @param health:QtGui.QColor
        @todo: optimize the setbubblecolor after discussing
        '''
    def setbubblecolor(self,health):
        if health==1:
            self.bubble.color = QtGui.QColor(0,255,0)
        elif health==2:
            self.bubble.color = QtGui.QColor(255,153,0)   
        elif health==3:
            self.bubble.color = QtGui.QColor(255,0,0)
        elif health==4:
            self.bubble.color = QtGui.QColor(153,204,255)

        
        
        '''stop all timelines'''
    def stop(self):

        
        '''
        Everytime after using animation, we have to use update()
        '''
        
    def updateloc(self):
        self.bubble.loc = self.location
    def updateradius(self):
        self.bubble.setBubbleSize(self.radius)
#        self.bubble.radius = self.radius
        

        
        