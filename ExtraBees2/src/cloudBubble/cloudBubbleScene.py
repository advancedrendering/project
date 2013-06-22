'''
Created on 2013-6-18

@author: sijia
'''

from PyQt4 import QtGui,QtCore
from atomicBubble import atomicBubble
from bubbleAnimation import bubbleAnimation

class cloudBubbleScene(QtGui.QGraphicsScene):
    '''
    This class is used to specify the bubble scene. 
    '''


    def __init__(self):
        '''
        Constructor
        '''
        super(cloudBubbleScene, self).__init__()
#add first bubble into the scene        
        self.bubble = atomicBubble(100.0,5.0,10)
        self.addItem(self.bubble)
#assign some animation to the first bubble
        self.animation = bubbleAnimation(self.bubble)
        self.animation.setbubbleloc(1000000000, QtCore.QPoint(100,-100))
        self.animation.setbubblesize(1000000000, 1.5)
        '''
        Add new bubbles to the scene
        '''
    def addBubble(self,numberofworkstation):
#        for i in range(10,numberofworkstation):
        self.bubble = atomicBubble(100.0,5.0,10)
        self.addItem(self.bubble)
    

            
        
        
        
        