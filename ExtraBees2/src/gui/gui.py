import sys
import csv
import random
from PyQt4 import QtGui, uic, QtCore 
from cloudBubble.cloudBubbleScene import cloudBubbleScene
from cloudBubble.cloudBubbleScene import bubblelist
from cloudBubble.cloudBubbleScene import bubbleAnimationlist
#from scipy.sparse.linalg.dsolve.umfpack.umfpack import updateDictWithVars
from cloudBubble.atomicBubble import atomicBubble
from GuiThread import GuiThread

class MyWindow(QtGui.QMainWindow):
    def __init__(self, parent = None):
        super(MyWindow, self).__init__()
        self.ui = uic.loadUi('gui.ui', self)
        self.connect(self.ui.playButton, QtCore.SIGNAL("clicked()"), self.clickedPlay)
        self.connect(self.ui.pauseButton, QtCore.SIGNAL("clicked()"), self.clickedPause)
        self.connect(self.ui.daySlider, QtCore.SIGNAL("valueChanged(int)"), self.dayChanged)
        self.connect(self.ui.timeSlider, QtCore.SIGNAL("valueChanged(int)"), self.timeChanged)
        self.t = GuiThread(0.1)
        self.connect(self.t, QtCore.SIGNAL('update'),self.loop)
        self.t.start()
        
        self.running = False
        
        ifile = open('../csvfiles/overallTrafficPerTimeSlot.csv')
        reader = csv.reader(ifile)
        self.trafficChart = []
        for row in reader:
            self.trafficChart.append(row[1])
        self.ui.chart.canvas.ax.clear()
        self.ui.chart.canvas.ax.plot(self.trafficChart)

        self.ui.chart.canvas.draw()

         
#Add hello world to scene, just for testing
#         site1Scene = QtGui.QGraphicsScene()    
#         site1Path = QtGui.QPainterPath()
#         site1Path.moveTo(30,120)
#         site1Path.cubicTo(80, 0, 50, 50, 80, 80)
#         site1Scene.addPath(site1Path, QtGui.QPen(QtCore.Qt.black), QtGui.QBrush(QtCore.Qt.green));
#         site1Scene.addText("Hello, world!", QtGui.QFont("Times", 15, QtGui.QFont.Bold)); 
#           
        self.site1Scene = cloudBubbleScene()   
        self.ui.site1GraphicsView.setScene(self.site1Scene)
        self.ui.site1GraphicsView.show()
        self.ui.site2GraphicsView.setScene(self.site1Scene)
        self.ui.site2GraphicsView.show()
        self.ui.site3GraphicsView.setScene(self.site1Scene)
        self.ui.site3GraphicsView.show()
        self.show()
        
    def clickedPlay(self):
#        self.site1Scene.keepTight() 
#        self.site1Scene.animation.setbubbleloc(1000000000, QtCore.QPointF(100,-100))
#        self.site1Scene.animation.setbubblesize(1000000000, 2.0)
        global bubblelist,bubbleAnimationlist
        self.site1Scene.locatefirstandsecondbubble(10,14)
        insectionPoints=self.site1Scene.insect(bubblelist[0],bubblelist[1],17)
        print len(insectionPoints)
        if len(insectionPoints)>=1:
            bubbleAnimationlist[2].setbubbleloc(1000000000,insectionPoints[0])
            bubbleAnimationlist[2].setbubblesize(1000000000,17/bubblelist[2].radius)
        print "Play"
        self.running = True

        
    def clickedPause(self):
        print "Pause"
        self.running = False
    
    def dayChanged(self):
        day = self.ui.daySlider.value()
        string = "Day "+ str(day).zfill(2)
        self.ui.dayLabel.setText(string)
        self.ui.widget.updateData(self.ui.timeSlider.value(),self.ui.daySlider.value())
        self.updateChart(self.ui.timeSlider.value())
        
    def timeChanged(self):
        timeSlot = self.ui.timeSlider.value()
        hour = timeSlot/12
        minute = (timeSlot % 12)*5
        string = str(hour).zfill(2)+":"+str(minute).zfill(2)
        self.ui.timeLabel.setText(string)
        self.ui.widget.updateData(self.ui.timeSlider.value(),self.ui.daySlider.value())
        self.updateChart(self.ui.timeSlider.value())
        
    def loop(self):
        self.updateGraph()
        if self.running:
            self.updateChart(self.ui.timeSlider.value())
            self.ui.widget.updateData(self.ui.timeSlider.value(),self.ui.daySlider.value())
            if self.ui.timeSlider.value() == self.ui.timeSlider.maximum():
                self.ui.timeSlider.setValue(self.ui.timeSlider.minimum())
                if self.ui.daySlider.value()== self.ui.daySlider.maximum():
                    self.ui.daySlider.setValue(self.ui.daySlider.minimum())
                else:
                    self.ui.daySlider.setValue(self.ui.daySlider.value() +1)
            else:
                self.ui.timeSlider.setValue(self.ui.timeSlider.value() +1)
        else:
            self.updateChart(self.ui.chart.canvas.xdata) 
    
    def updateGraph(self):
        self.ui.widget.update()
    
    def updateChart(self,value):
        self.ui.chart.canvas.ax.clear()
        self.ui.chart.canvas.ax.plot(self.trafficChart)
        self.ui.chart.canvas.ax.vlines(value,0,1100000000)
        self.ui.chart.canvas.draw()
        
        
    def addAllBubbleIntoScene(self):
        global bubblelist
        i=0
        for i in range(0,50):
            self.site1Scene.addItem(bubblelist[i])

        
if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    window = MyWindow()
    sys.exit(app.exec_())
