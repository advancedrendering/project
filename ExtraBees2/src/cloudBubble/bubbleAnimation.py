'''
Created on 2013-6-22

@author: sijia
'''
from PyQt4 import QtCore,QtGui



class bubbleAnimation():
    '''
    classdocs
    '''
 
 
    def __init__(self,newloc,bubble):
        '''
        Constructor
        '''
       
        self.tl = QtCore.QTimeLine(1000000000)
        self.tl.setFrameRange(0,100)
        self.animation = QtGui.QGraphicsItemAnimation()
        self.animation.setItem(bubble)
        self.animation.setTimeLine(self.tl)
        self.animation.setPosAt(0.00000000001,newloc)
        self.animation.setScaleAt(0.00000000001,1.5,1.5)
        
    def start(self): 
        self.tl.start()
        
        