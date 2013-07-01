'''
Created on 2013-6-18

@author: sijia
'''

from PyQt4 import QtGui,QtCore
from atomicBubble import atomicBubble
from bubbleAnimation import bubbleAnimation
from math import sqrt


bubble1 = atomicBubble(0,0,10,'1st')
bubble1Animation = bubbleAnimation(bubble1)

bubble2 = atomicBubble(0,20,10,'2nd')
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

bubblelist = [bubble1,bubble2]
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
            
#        self.detectCollides()
#assign some animation to the first bubble
#        self.addItem(self.bubble)
#        self.animation = bubbleAnimation(self.bubble)
        
#         self.animation.setbubbleloc(1000000000, QtCore.QPoint(100,-100))
#         self.animation.setbubbleloc(1000000000, QtCore.QPoint(100,100))
#         self.animation.setbubblesize(1000000000, 0.01)
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
        collidesBubble = []
        i=0
        k=1
        for i in range(0,len(bubblelist)-1):       
            for k in range(i+1,len(bubblelist)):
                j=bubblelist[i].collidesWithItem(bubblelist[k])
                if j:
                    collidesBubble.append(bubblelist[i])
                    collidesBubble.append(bubblelist[k])
                    print 'bubble %s collides with bubble %s' % (bubblelist[i].toolTip(),bubblelist[k].toolTip())
        if len(collidesBubble)==0:
            print 'Collides Bubbles is zero.'
        return collidesBubble
    
          
    '''
    Get All bubbles Current Location .
    @return: List of QtCore.QPointF
    '''
            
    def refreshAllBubblesCurrentLoc(self):
        currentbubbleloc=[]
        i=0
        for i in range(0,len(bubblelist)):
            currentbubbleloc.append(bubblelist[i].getCurrentloc())            
        return currentbubbleloc  
    
      
    '''
    Get All bubbles Current Radius .
    @return: List of float
    '''    
    def refreshAllBubblesCurrentRadius(self):
        currentbubbleradius=[]
        i=0
        for i in range(0,len(bubblelist)):
            currentbubbleradius.append(bubblelist[i].getCurrentRadius())   
        return currentbubbleradius
    '''
    Keep all bubble tight.
    '''     
#    def keeptight(self):
        
    '''
    Get an empty space for the third bubble
    @param firstbubble:cloudBubble.atomicBubble
    @param secondbubble:cloudBubble.atomicBubble
    @param thirdBubbleradius: float   
    @return: list of QtCore.QPointF. The third bubble center points 
    '''    
    def insect(self,firstbubble,secondbubble,thirdBubbleradius):
        firstbubbleradius=firstbubble.radius+thirdBubbleradius
        secondbubbleradius=secondbubble.radius+thirdBubbleradius
        d = firstbubble.getCenterDistanceWithOtherBubble(secondbubble)
        if d>=(firstbubbleradius+secondbubbleradius):
            print 'Third bubble size is 0'
        else:
            a = 2.0 * firstbubbleradius * (firstbubble.loc.x() - secondbubble.loc.x());
            b = 2.0 * secondbubbleradius * (firstbubble.loc.y() - secondbubble.loc.y());
            c = secondbubbleradius * secondbubbleradius - firstbubbleradius * firstbubbleradius- self.distance_sqr(firstbubble, secondbubble)
            p = a * a + b * b
            q = -2.0 * a * c
            r = c * c - b * b
            cos_value=[(sqrt(q * q - 4.0 * p * r) - q) / p / 2.0,(-sqrt(q * q - 4.0 * p * r) - q) / p / 2.0]  
            sin_value=[sqrt(1 - cos_value[0] * cos_value[0]),sqrt(1 - cos_value[1] * cos_value[1])]
            insectionPoints1=QtCore.QPointF(firstbubbleradius * cos_value[0] + firstbubble.loc.x(),firstbubbleradius * sin_value[0] + firstbubble.loc.y())
            insectionPoints2=QtCore.QPointF(firstbubbleradius * cos_value[1] + firstbubble.loc.x(),firstbubbleradius * sin_value[1] + firstbubble.loc.y())
            insectionPoints=[insectionPoints1,insectionPoints2]
            return insectionPoints
    
    
    
    def distance_sqr(self,firstbubble,secondbubble):
        return pow(firstbubble.loc.x()-secondbubble.loc.x(), 2)+pow(firstbubble.loc.y()-secondbubble.loc.y(), 2)
    
