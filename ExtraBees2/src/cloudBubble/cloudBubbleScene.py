'''
Created on 2013-6-18

@author: sijia
'''

from PyQt4 import QtGui,QtCore
from atomicBubble import atomicBubble
from bubbleAnimation import bubbleAnimation
from math import sqrt
from random import randint


bubble1 = atomicBubble(40,20,10,'1st')
bubble1Animation = bubbleAnimation(bubble1)

bubble2 = atomicBubble(0,0,10,'2nd')
bubble2Animation = bubbleAnimation(bubble2)

bubble3 = atomicBubble(13,14,10,'3rd')
bubble3Animation = bubbleAnimation(bubble3)

bubble4 = atomicBubble(15,0,10)
bubble4Animation = bubbleAnimation(bubble4)

bubble5 = atomicBubble(0,0,10)
bubble5Animation = bubbleAnimation(bubble5)

bubble6 = atomicBubble(0,0,10)
bubble6Animation = bubbleAnimation(bubble6)

bubble7 = atomicBubble(0,0,10)
bubble7Animation = bubbleAnimation(bubble7)

bubble8 = atomicBubble(0,0,10)
bubble8Animation = bubbleAnimation(bubble8)

bubble9 = atomicBubble(0,0,10)
bubble9Animation = bubbleAnimation(bubble9)

bubble10 = atomicBubble(0,0,10)
bubble10Animation = bubbleAnimation(bubble10)

bubble11 = atomicBubble(0,0,10)
bubble11Animation = bubbleAnimation(bubble11)

bubble12 = atomicBubble(0,0,10)
bubble12Animation = bubbleAnimation(bubble12)
bubble13 = atomicBubble(0,0,10)
bubble13Animation = bubbleAnimation(bubble13)
bubble14 = atomicBubble(0,0,10)
bubble14Animation = bubbleAnimation(bubble14)
bubble15 = atomicBubble(0,0,10)
bubble15Animation = bubbleAnimation(bubble15)
bubble16 = atomicBubble(0,0,10)
bubble16Animation = bubbleAnimation(bubble16)
bubble17 = atomicBubble(0,0,10)
bubble17Animation = bubbleAnimation(bubble17)
bubble18 = atomicBubble(0,0,10)
bubble18Animation = bubbleAnimation(bubble18)
bubble19 = atomicBubble(0,0,10)
bubble19Animation = bubbleAnimation(bubble19)
bubble20 = atomicBubble(0,0,10)
bubble20Animation = bubbleAnimation(bubble20)
bubble21 = atomicBubble(0,0,10)
bubble21Animation = bubbleAnimation(bubble21)
bubble22 = atomicBubble(0,0,10)
bubble22Animation = bubbleAnimation(bubble22)
bubble23 = atomicBubble(0,0,10)
bubble23Animation = bubbleAnimation(bubble23)
bubble24 = atomicBubble(0,0,10)
bubble24Animation = bubbleAnimation(bubble24)
bubble25 = atomicBubble(0,0,10)
bubble25Animation = bubbleAnimation(bubble25)
bubble26 = atomicBubble(0,0,10)
bubble26Animation = bubbleAnimation(bubble26)
bubble27 = atomicBubble(0,0,10)
bubble27Animation = bubbleAnimation(bubble27)
bubble28 = atomicBubble(0,0,10)
bubble28Animation = bubbleAnimation(bubble28)
bubble29 = atomicBubble(0,0,10)
bubble29Animation = bubbleAnimation(bubble29)
bubble30 = atomicBubble(0,0,10)
bubble30Animation = bubbleAnimation(bubble30)
bubble31 = atomicBubble(0,0,10)
bubble31Animation = bubbleAnimation(bubble31)
bubble32 = atomicBubble(0,0,10)
bubble32Animation = bubbleAnimation(bubble32)
bubble33 = atomicBubble(0,0,10)
bubble33Animation = bubbleAnimation(bubble33)
bubble34 = atomicBubble(0,0,10)
bubble34Animation = bubbleAnimation(bubble34)
bubble35 = atomicBubble(0,0,10)
bubble35Animation = bubbleAnimation(bubble35)
bubble36 = atomicBubble(0,0,10)
bubble36Animation = bubbleAnimation(bubble36)
bubble37 = atomicBubble(0,0,10)
bubble37Animation = bubbleAnimation(bubble37)
bubble38 = atomicBubble(0,0,10)
bubble38Animation = bubbleAnimation(bubble38)
bubble39 = atomicBubble(0,0,10)
bubble39Animation = bubbleAnimation(bubble39)
bubble40 = atomicBubble(0,0,10)
bubble40Animation = bubbleAnimation(bubble40)
bubble41 = atomicBubble(0,0,10)
bubble41Animation = bubbleAnimation(bubble41)
bubble42 = atomicBubble(0,0,10)
bubble42Animation = bubbleAnimation(bubble42)
bubble43 = atomicBubble(0,0,10)
bubble43Animation = bubbleAnimation(bubble43)
bubble44 = atomicBubble(0,0,10)
bubble44Animation = bubbleAnimation(bubble44)
bubble45 = atomicBubble(0,0,10)
bubble45Animation = bubbleAnimation(bubble45)
bubble46 = atomicBubble(0,0,10)
bubble46Animation = bubbleAnimation(bubble46)
bubble47 = atomicBubble(0,0,10)
bubble47Animation = bubbleAnimation(bubble47)
bubble48 = atomicBubble(0,0,10)
bubble48Animation = bubbleAnimation(bubble48)
bubble49 = atomicBubble(0,0,10)
bubble49Animation = bubbleAnimation(bubble49)
bubble50 = atomicBubble(0,0,10)
bubble50Animation = bubbleAnimation(bubble50)




bubblelist = [bubble1, bubble2, bubble3, bubble4, bubble5, bubble6, bubble7, bubble8, bubble9, bubble10, bubble11, bubble12, bubble13, bubble14, bubble15, bubble16, bubble17, bubble18, bubble19, bubble20, bubble21, bubble22, bubble23, bubble24, bubble25, bubble26, bubble27, bubble28, bubble29, bubble30, bubble31, bubble32, bubble33, bubble34, bubble35, bubble36, bubble37, bubble38, bubble39, bubble40, bubble41, bubble42, bubble43, bubble44, bubble45, bubble46, bubble47, bubble48, bubble49, bubble50]
bubbleAnimationlist = [bubble1Animation, bubble2Animation, bubble3Animation, bubble4Animation, bubble5Animation, bubble6Animation, bubble7Animation, bubble8Animation, bubble9Animation, bubble10Animation, bubble11Animation, bubble12Animation, bubble13Animation, bubble14Animation, bubble15Animation, bubble16Animation, bubble17Animation, bubble18Animation, bubble19Animation, bubble20Animation, bubble21Animation, bubble22Animation, bubble23Animation, bubble24Animation, bubble25Animation, bubble26Animation, bubble27Animation, bubble28Animation, bubble29Animation, bubble30Animation, bubble31Animation, bubble32Animation, bubble33Animation, bubble34Animation, bubble35Animation, bubble36Animation, bubble37Animation, bubble38Animation, bubble39Animation, bubble40Animation, bubble41Animation, bubble42Animation, bubble43Animation, bubble44Animation, bubble45Animation, bubble46Animation, bubble47Animation, bubble48Animation, bubble49Animation, bubble50Animation]
#bubblelist = [bubble1, bubble2, bubble3, bubble4, bubble5, bubble6]
#bubbleAnimationlist = [bubble1Animation, bubble2Animation, bubble3Animation, bubble4Animation, bubble5Animation, bubble6Animation]
nextlocation=[QtCore.QPointF(0,0)] *50

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

        for eachbubble in bubblelist:
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
    def keepTight(self):
        global bubblelist
        padding =2
        damping = 0.1        
        for i in range(len(bubblelist)):
            circle1 = bubblelist[i]
            for j in range(i+1, len(bubblelist)):
                circle2 = bubblelist[j]
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
        global bubblelist,bubbleAnimationlist,nextlocation
        padding =2
        fakedata = self.getFakeData()
        
        subduration = 5000000
        for k in range(10):
            for h in range(len(bubblelist)):
                bubblelist[h].setNextRadius(fakedata[h])
            for i in range(len(bubblelist)):
                circle1 = bubblelist[i]
                for j in range(i+1, len(bubblelist)):
                    circle2 = bubblelist[j]
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
                        nextlocation[i] = circle1loc
                        nextlocation[j] = circle2loc
                        
        print len(nextlocation)  
        for i in range(len(bubbleAnimationlist)):
            bubbleAnimationlist[i].setbubblesize(subduration,fakedata[i])
            bubbleAnimationlist[i].setbubbleloc(subduration,nextlocation[i]) 
 
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
    
    def locatefirstandsecondbubble(self,firstbubblenextradius,secondbubblenextradius):
        global bubble1
        global bubble2
        bubble1.setNextLocation(QtCore.QPointF(0,0))
        bubble2.setNextLocation(QtCore.QPointF(firstbubblenextradius+secondbubblenextradius,firstbubblenextradius+secondbubblenextradius))
    
    def setAllBubblesNextRadius(self,radiuslist):
        global bubblelist
        for i in range(0,50):
            bubblelist[i].setNextRadius(radiuslist[i])