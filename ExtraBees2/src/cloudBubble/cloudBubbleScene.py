'''
Created on 2013-6-18

@author: sijia
'''

from PyQt4 import QtGui,QtCore
from atomicBubble import atomicBubble
from bubbleAnimation import bubbleAnimation


bubble1 = atomicBubble(0,0,10,'1st')
bubble1Animation = bubbleAnimation(bubble1)

bubble2 = atomicBubble(-20,-20,10,'2nd')
bubble2Animation = bubbleAnimation(bubble2)

bubble3 = atomicBubble(-30,-30,10,'3rd')
bubble3Animation = bubbleAnimation(bubble3)

bubble4 = atomicBubble(20,20,10)
bubble4Animation = bubbleAnimation(bubble4)

bubble5 = atomicBubble(30,30,10)
bubble5Animation = bubbleAnimation(bubble5)

bubble6 = atomicBubble(0,0,10)
bubble6Animation = bubbleAnimation(bubble6)

bubble7 = atomicBubble(0,0,10)
bubble7Animation = bubbleAnimation(bubble7)

bubble8 = atomicBubble(0,0,10)
bubble8Animation = bubbleAnimation(bubble8)

bubble9 = atomicBubble(0,0,10)
bubble9Animation = bubbleAnimation(bubble9)

bubblelist = [bubble1,bubble2,bubble3]
bubbleAnimationlist = [bubble1Animation,bubble2Animation,bubble3Animation]



bubbledict = {'bubble1':'1st','bubble2':'2nd','bubble3':'3rd','bubble4':'4th'}
bubbleAnimationdict = {'bubble1Animation':'bubble1',
                       'bubble2Animation':'bubble2','bubble3Animation':'bubble3','bubble4Animation':'bubble4'}
bubblenumber = [x for x in range(1,4)]
bubblenamelist = ["1st","2nd","3rd"]
class cloudBubbleScene(QtGui.QGraphicsScene):
    '''
    This class is used to specify the bubble scene. 
    '''

    def __init__(self):
        '''
        Constructor
        ''' 
        super(cloudBubbleScene, self).__init__()
        global bubblelist
#add first bubble into the scene        
        self.bubble = atomicBubble(0,0,10)
        for eachbubble in bubblelist:
            self.addItem(eachbubble)
            
        self.detectCollides()
#assign some animation to the first bubble
#        self.animation = bubbleAnimation(self.bubble)
#        self.animation.setbubbleloc(1000000000, QtCore.QPoint(100,-100))
#        self.animation.setbubbleloc(1000000000, QtCore.QPoint(100,100))
#        self.animation.setbubblesize(1000000000, 0.01)        
#        self.addAllBubble()
        '''
        Add new bubbles to the scene
        '''
    def addAllBubble(self):
        global bubbledict
        i=0
        for i in range(0,3):
            print 'bubble'+str(i)
#            'bubble'+str(i)=atomicBubble(5+i,5+i,5)
#            self.addItem(self.('bubble'+str(i)))
#            print 'key=%s, value=' % (bubbledict[i])
#            i=i+4

    def detectCollides(self):
        i=0
        k=1
        for i in range(0,len(bubblelist)-1):       
            for k in range(0,len(bubblelist)):
                j=bubblelist[i].collidesWithItem(bubblelist[k])
                print j
            
        
    

    

            
        
        
        
        