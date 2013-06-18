'''
Created on 2013-6-18

@author: sijia
'''

from PyQt4 import QtGui
from cloudBubble.atomicBubble import atomicBubble

class cloudBubbleScene(QtGui.QGraphicsScene):
    '''
    classdocs
    '''


    def __init__(self):
        '''
        Constructor
        '''
        super(cloudBubbleScene, self).__init__()
#       firstBubble = atomicBubble(10,10,10)
        firstBubble = QtGui.QGraphicsEllipseItem(10.0,10.0,10.0,10.0)
        #firstBubble.
# firstBubble.
        self.addItem(firstBubble)
        
        
        
        