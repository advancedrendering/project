'''
Created on 2013-6-18

@author: sijia
'''



from PyQt4.uic.Compiler.qtproxies import QtGui
#from twisted.words.test.test_basesupport import self

class atomicBubble(QtGui.QGraphicsEllipseItem):
    '''
    Atomic Bubble is used to create a single bubble in cloud bubble.
    '''
    

    def __init__(self,x,y,w,h,parent=None,scene=None):
        QtGui.QGraphicsEllipseItem.__init__(x,y,w,h,parent=None,scene=None)
#         super(atomicBubble, self).__init__(self)
        