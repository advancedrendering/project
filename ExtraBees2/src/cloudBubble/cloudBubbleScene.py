'''
Created on 2013-6-18

@author: sijia
'''

from PyQt4 import QtGui,QtCore
from atomicBubble import atomicBubble
from bubbleAnimation import bubbleAnimation
from math import sqrt
from random import randint


#bubblelist = [self.bubble1, self.bubble2, self.bubble3, self.bubble4, bubble5, bubble6]
#bubbleAnimationlist = [self.bubble1Animation, self.bubble2Animation, self.bubble3Animation, self.bubble4Animation, bubble5Animation, bubble6Animation]


class cloudBubbleScene(QtGui.QGraphicsScene):
    '''
    This class is used to specify the bubble scene. 
    '''

    def __init__(self):
        '''
        Constructor
        ''' 
        super(cloudBubbleScene, self).__init__()
#        global bubblelist
        self.nextlocation=[QtCore.QPointF(0,0)] *50
        self.bubble1 = atomicBubble(40,20,10,'1st')
        self.bubble1Animation = bubbleAnimation(self.bubble1)
        
        self.bubble2 = atomicBubble(0,0,10,'2nd')
        self.bubble2Animation = bubbleAnimation(self.bubble2)
        
        self.bubble3 = atomicBubble(13,14,10,'3rd')
        self.bubble3Animation = bubbleAnimation(self.bubble3)
        
        self.bubble4 = atomicBubble(15,0,10)
        self.bubble4Animation = bubbleAnimation(self.bubble4)
        
        self.bubble5 = atomicBubble(0,0,10)
        self.bubble5Animation = bubbleAnimation(self.bubble5)
        
        self.bubble6 = atomicBubble(0,0,10)
        self.bubble6Animation = bubbleAnimation(self.bubble6)
        
        self.bubble7 = atomicBubble(0,0,10)
        self.bubble7Animation = bubbleAnimation(self.bubble7)
        
        self.bubble8 = atomicBubble(0,0,10)
        self.bubble8Animation = bubbleAnimation(self.bubble8)
        
        self.bubble9 = atomicBubble(0,0,10)
        self.bubble9Animation = bubbleAnimation(self.bubble9)
        
        self.bubble10 = atomicBubble(0,0,10)
        self.bubble10Animation = bubbleAnimation(self.bubble10)
        
        self.bubble11 = atomicBubble(0,0,10)
        self.bubble11Animation = bubbleAnimation(self.bubble11)
        
        self.bubble12 = atomicBubble(0,0,10)
        self.bubble12Animation = bubbleAnimation(self.bubble12)
        self.bubble13 = atomicBubble(0,0,10)
        self.bubble13Animation = bubbleAnimation(self.bubble13)
        self.bubble14 = atomicBubble(0,0,10)
        self.bubble14Animation = bubbleAnimation(self.bubble14)
        self.bubble15 = atomicBubble(0,0,10)
        self.bubble15Animation = bubbleAnimation(self.bubble15)
        self.bubble16 = atomicBubble(0,0,10)
        self.bubble16Animation = bubbleAnimation(self.bubble16)
        self.bubble17 = atomicBubble(0,0,10)
        self.bubble17Animation = bubbleAnimation(self.bubble17)
        self.bubble18 = atomicBubble(0,0,10)
        self.bubble18Animation = bubbleAnimation(self.bubble18)
        self.bubble19 = atomicBubble(0,0,10)
        self.bubble19Animation = bubbleAnimation(self.bubble19)
        self.bubble20 = atomicBubble(0,0,10)
        self.bubble20Animation = bubbleAnimation(self.bubble20)
        self.bubble21 = atomicBubble(0,0,10)
        self.bubble21Animation = bubbleAnimation(self.bubble21)
        self.bubble22 = atomicBubble(0,0,10)
        self.bubble22Animation = bubbleAnimation(self.bubble22)
        self.bubble23 = atomicBubble(0,0,10)
        self.bubble23Animation = bubbleAnimation(self.bubble23)
        self.bubble24 = atomicBubble(0,0,10)
        self.bubble24Animation = bubbleAnimation(self.bubble24)
        self.bubble25 = atomicBubble(0,0,10)
        self.bubble25Animation = bubbleAnimation(self.bubble25)
        self.bubble26 = atomicBubble(0,0,10)
        self.bubble26Animation = bubbleAnimation(self.bubble26)
        self.bubble27 = atomicBubble(0,0,10)
        self.bubble27Animation = bubbleAnimation(self.bubble27)
        self.bubble28 = atomicBubble(0,0,10)
        self.bubble28Animation = bubbleAnimation(self.bubble28)
        self.bubble29 = atomicBubble(0,0,10)
        self.bubble29Animation = bubbleAnimation(self.bubble29)
        self.bubble30 = atomicBubble(0,0,10)
        self.bubble30Animation = bubbleAnimation(self.bubble30)
        self.bubble31 = atomicBubble(0,0,10)
        self.bubble31Animation = bubbleAnimation(self.bubble31)
        self.bubble32 = atomicBubble(0,0,10)
        self.bubble32Animation = bubbleAnimation(self.bubble32)
        self.bubble33 = atomicBubble(0,0,10)
        self.bubble33Animation = bubbleAnimation(self.bubble33)
        self.bubble34 = atomicBubble(0,0,10)
        self.bubble34Animation = bubbleAnimation(self.bubble34)
        self.bubble35 = atomicBubble(0,0,10)
        self.bubble35Animation = bubbleAnimation(self.bubble35)
        self.bubble36 = atomicBubble(0,0,10)
        self.bubble36Animation = bubbleAnimation(self.bubble36)
        self.bubble37 = atomicBubble(0,0,10)
        self.bubble37Animation = bubbleAnimation(self.bubble37)
        self.bubble38 = atomicBubble(0,0,10)
        self.bubble38Animation = bubbleAnimation(self.bubble38)
        self.bubble39 = atomicBubble(0,0,10)
        self.bubble39Animation = bubbleAnimation(self.bubble39)
        self.bubble40 = atomicBubble(0,0,10)
        self.bubble40Animation = bubbleAnimation(self.bubble40)
        self.bubble41 = atomicBubble(0,0,10)
        self.bubble41Animation = bubbleAnimation(self.bubble41)
        self.bubble42 = atomicBubble(0,0,10)
        self.bubble42Animation = bubbleAnimation(self.bubble42)
        self.bubble43 = atomicBubble(0,0,10)
        self.bubble43Animation = bubbleAnimation(self.bubble43)
        self.bubble44 = atomicBubble(0,0,10)
        self.bubble44Animation = bubbleAnimation(self.bubble44)
        self.bubble45 = atomicBubble(0,0,10)
        self.bubble45Animation = bubbleAnimation(self.bubble45)
        self.bubble46 = atomicBubble(0,0,10)
        self.bubble46Animation = bubbleAnimation(self.bubble46)
        self.bubble47 = atomicBubble(0,0,10)
        self.bubble47Animation = bubbleAnimation(self.bubble47)
        self.bubble48 = atomicBubble(0,0,10)
        self.bubble48Animation = bubbleAnimation(self.bubble48)
        self.bubble49 = atomicBubble(0,0,10)
        self.bubble49Animation = bubbleAnimation(self.bubble49)
        self.bubble50 = atomicBubble(0,0,10)
        self.bubble50Animation = bubbleAnimation(self.bubble50)
        
        self.bubblelist = [self.bubble1, self.bubble2, self.bubble3, self.bubble4, self.bubble5, self.bubble6, self.bubble7, self.bubble8, self.bubble9, self.bubble10, self.bubble11, self.bubble12, self.bubble13, self.bubble14, self.bubble15, self.bubble16, self.bubble17, self.bubble18, self.bubble19, self.bubble20, self.bubble21, self.bubble22, self.bubble23, self.bubble24, self.bubble25, self.bubble26, self.bubble27, self.bubble28, self.bubble29, self.bubble30, self.bubble31, self.bubble32, self.bubble33, self.bubble34, self.bubble35, self.bubble36, self.bubble37, self.bubble38, self.bubble39, self.bubble40, self.bubble41, self.bubble42, self.bubble43, self.bubble44, self.bubble45, self.bubble46, self.bubble47, self.bubble48, self.bubble49, self.bubble50]
        self.bubbleAnimationlist = [self.bubble1Animation, self.bubble2Animation, self.bubble3Animation, self.bubble4Animation, self.bubble5Animation, self.bubble6Animation, self.bubble7Animation, self.bubble8Animation, self.bubble9Animation, self.bubble10Animation, self.bubble11Animation, self.bubble12Animation, self.bubble13Animation, self.bubble14Animation, self.bubble15Animation, self.bubble16Animation, self.bubble17Animation, self.bubble18Animation, self.bubble19Animation, self.bubble20Animation, self.bubble21Animation, self.bubble22Animation, self.bubble23Animation, self.bubble24Animation, self.bubble25Animation, self.bubble26Animation, self.bubble27Animation, self.bubble28Animation, self.bubble29Animation, self.bubble30Animation, self.bubble31Animation, self.bubble32Animation, self.bubble33Animation, self.bubble34Animation, self.bubble35Animation, self.bubble36Animation, self.bubble37Animation, self.bubble38Animation, self.bubble39Animation, self.bubble40Animation, self.bubble41Animation, self.bubble42Animation, self.bubble43Animation, self.bubble44Animation, self.bubble45Animation, self.bubble46Animation, self.bubble47Animation, self.bubble48Animation, self.bubble49Animation, self.bubble50Animation]

        for eachbubble in self.bubblelist:
            self.addItem(eachbubble)
#        self.addItem(bubblelist[0])

#     def detectCollides(self,radiuslist,locationlist):
#         collidesBubble = []
#         i=0
#         k=1
#         for i in range(0,len(bubblelist)-1):       
#             for k in range(i+1,len(bubblelist)):
#                 j=bubblelist[i].collidesWithItem(bubblelist[k])
#                 if j:
#                     collidesBubble.append(i)
#                     collidesBubble.append(k)
#                     print 'bubble %s collides with bubble %s' % (bubblelist[i].toolTip(),bubblelist[k].toolTip())
#         if len(collidesBubble)==0:
#             print 'Collides Bubbles is zero.'
#         return collidesBubble
    
          
    '''
    Get All bubbles Current Location .
    @return: List of QtCore.QPointF
    '''
            
    def refreshAllBubblesCurrentLoc(self):
        currentbubbleloc=[]
        i=0
        for i in range(0,len(self.bubblelist)):
            currentbubbleloc.append(self.bubblelist[i].getCurrentloc())            
        return currentbubbleloc  
    
      
    '''
    Get All bubbles Current Radius .
    @return: List of float
    '''    
    def refreshAllBubblesCurrentRadius(self):
        currentbubbleradius=[]
        i=0
        for i in range(0,len(self.bubblelist)):
            currentbubbleradius.append(self.bubblelist[i].getCurrentRadius())   
        return currentbubbleradius

    '''
    Keep all bubble tight.
    '''     
    def keepTight(self):
        
        padding =2
        damping = 0.1        
        for i in range(len(self.bubblelist)):
            circle1 = self.bubblelist[i]
            for j in range(i+1, len(self.bubblelist)):
                circle2 = self.bubblelist[j]
                d = self.distance(circle1.loc,circle2.loc)
                r = circle1.radius + circle2.radius + padding
             
        
                if d**2 < r**2 - 0.01:
                    dx = circle2.loc.x() - circle1.loc.x()
                    dy = circle2.loc.y() - circle1.loc.y()
                    vx = (dx / d) * (r-d) * 0.5
                    vy = (dy / d) * (r-d) * 0.5
                    
                    
                    circle1.loc.setX(circle1.loc.x() - vx)
                    circle1.loc.setY(circle1.loc.y() - vy)
                    circle2.loc.setX(circle2.loc.x() + vx)
                    circle2.loc.setY(circle2.loc.y() + vy)

    
    def newkeepTight(self):
#        global bubblelist,bubbleAnimationlist,nextlocation
        padding =2
        fakedata = self.getFakeData()
        fakehealth = self.getFakeHealth()
        subduration = 5000000
        for k in range(10):
            for h in range(len(self.bubblelist)):
                self.bubblelist[h].setNextRadius(fakedata[h])
            for i in range(len(self.bubblelist)):
                circle1 = self.bubblelist[i]
                for j in range(i+1, len(self.bubblelist)):
                    circle2 = self.bubblelist[j]
                    d = self.distance(circle1.nextlocation,circle2.nextlocation)
                    r = circle1.nextradius + circle2.nextradius + padding
                    
                    if d**2 < r**2 - 0.01:
                        if d==0:
                            d=0.1
                        dx = circle2.nextlocation.x() - circle1.nextlocation.x()
                        dy = circle2.nextlocation.y() - circle1.nextlocation.y()
                        vx = (dx / d) * (r-d) * 0.5
                        vy = (dy / d) * (r-d) * 0.5
                        circle1loc = QtCore.QPointF(float(circle1.nextlocation.x() - vx),float(circle1.nextlocation.y() - vy))
                        circle2loc = QtCore.QPointF(float(circle2.nextlocation.x() + vx),float(circle2.nextlocation.y() + vy))
                        circle1.setNextLocation(circle1loc)
                        circle2.setNextLocation(circle2loc)
                        self.nextlocation[i] = circle1loc
                        self.nextlocation[j] = circle2loc
                        
        print len(self.nextlocation)  
        for i in range(len(self.bubbleAnimationlist)):
            self.bubbleAnimationlist[i].setbubblesize(subduration,fakedata[i])
            self.bubbleAnimationlist[i].setbubbleloc(subduration,self.nextlocation[i]) 
            self.bubbleAnimationlist[i].setbubblecolor(fakehealth[i])
 
    def distance(self,firstpoint,secondpoint):
        return sqrt(pow(firstpoint.x()-secondpoint.x(), 2)+pow(firstpoint.y()-secondpoint.y(), 2))
    
    '''
    For testing
    '''
    def getFakeData(self):
        data=[]
        for i in range(0,50):
            x=randint(10,30)
            data.append(x)
        return data
    def getFakeHealth(self):
        health=[]
        for i in range(50):
            x=randint(1,4)
            health.append(x)
        return health
    
