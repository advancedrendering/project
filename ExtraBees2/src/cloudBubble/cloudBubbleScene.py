'''
Created on 2013-6-18

@author: sijia
'''

from PyQt4 import QtGui,QtCore
from atomicBubble import atomicBubble
from bubbleAnimation import bubbleAnimation

class cloudBubbleScene(QtGui.QGraphicsScene):
    '''
    classdocs
    '''


    def __init__(self):
        '''
        Constructor
        '''
        super(cloudBubbleScene, self).__init__()
        self.bubble = atomicBubble(100.0,5.0,10)
#            bubble.paint()
        self.addItem(self.bubble)
        self.animation = bubbleAnimation(QtCore.QPoint(100,-100), self.bubble)
#       self.bubbleAnimation(QtCore.QPoint(100,-100), self.bubble)
        '''
        firstBubble = atomicBubble(10.0,10.0,10.0)
        self.addItem(firstBubble)
        '''
    def addBubble(self,numberofworkstation):
#        for i in range(10,numberofworkstation):
        self.bubble = atomicBubble(100.0,5.0,10)
#            bubble.paint()
        self.addItem(self.bubble)
        
#     def bubbleAnimation(self,newloc,bubble):
#         tl = QtCore.QTimeLine(1000000000)
#         tl.setFrameRange(0,100)
#         animation = QtGui.QGraphicsItemAnimation()
#         animation.setItem(bubble)
#         animation.setTimeLine(tl)
#         animation.setPosAt(0.00000000001,newloc)
#         animation.setScaleAt(0.00000000001,1.5,1.5)
#         tl.start()       
#    
            
        
        
        
        