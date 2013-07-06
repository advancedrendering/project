import sys
import csv
from PyQt4 import QtGui, uic, QtCore, QtSql
from cloudBubble.cloudBubbleScene import cloudBubbleScene
#from scipy.sparse.linalg.dsolve.umfpack.umfpack import updateDictWithVars
from GuiThread import GuiThread

from SlaveClass import SlaveClass

class MyWindow(QtGui.QMainWindow, SlaveClass):
    def __init__(self, parent = None):
        super(MyWindow, self).__init__()
        SlaveClass.__init__(self)
        
        self.ui = uic.loadUi('gui.ui', self)
        self.connect(self.ui.playButton, QtCore.SIGNAL("clicked()"), self.clickedPlay)
        self.connect(self.ui.pauseButton, QtCore.SIGNAL("clicked()"), self.clickedPause)
        self.connect(self.ui.daySlider, QtCore.SIGNAL("valueChanged(int)"), self.dayChanged)
        self.connect(self.ui.timeSlider, QtCore.SIGNAL("valueChanged(int)"), self.timeChanged)
        self.t = GuiThread(1)
        self.connect(self.t, QtCore.SIGNAL('update'),self.loop)
        self.t.start()
         
        self.running = False
        
        
        #ifile = open('../csvfiles/overallTrafficPerTimeSlot.csv')
        #reader = csv.reader(ifile)
        #self.trafficChart = []
        #for row in reader:
        #    self.trafficChart.append(row[1])
        #self.ui.chart.canvas.ax.clear()
        #self.ui.chart.canvas.ax.plot(self.trafficChart)
 
        #self.ui.chart.canvas.draw()

#Add hello world to scene, just for testing
#         site1Scene = QtGui.QGraphicsScene()    
#         site1Path = QtGui.QPainterPath()
#         site1Path.moveTo(30,120)
#         site1Path.cubicTo(80, 0, 50, 50, 80, 80)
#         site1Scene.addPath(site1Path, QtGui.QPen(QtCore.Qt.black), QtGui.QBrush(QtCore.Qt.green));
#         site1Scene.addText("Hello, world!", QtGui.QFont("Times", 15, QtGui.QFont.Bold)); 
#           
        self.site1Scene = cloudBubbleScene()
        self.site2Scene = cloudBubbleScene()
        self.site3Scene = cloudBubbleScene()  
        self.ui.site1GraphicsView.setScene(self.site1Scene)
        self.ui.site2GraphicsView.setScene(self.site2Scene)
        self.ui.site3GraphicsView.setScene(self.site3Scene)
        self.show()
        
    def clickedPlay(self):

#        self.site1Scene.keepTight() 

#        self.site1Scene.animation.setbubbleloc(1000000000, QtCore.QPointF(100,-100))
#        self.site1Scene.animation.setbubblesize(1000000000, 2.0)

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
        
    def timeChanged(self):
        timeSlot = self.ui.timeSlider.value()
        hour = timeSlot/12
        minute = (timeSlot % 12)*5
        string = str(hour).zfill(2)+":"+str(minute).zfill(2)
        self.ui.timeLabel.setText(string)
        self.ui.widget.updateData(self.ui.timeSlider.value(),self.ui.daySlider.value())
        
    def loop(self):
        self.updateGraph()
        if self.running:
            if (self.tabWidget.currentIndex()==1): 
                self.site1Scene.newkeepTight()
            if (self.tabWidget.currentIndex()==2): 
                self.site2Scene.newkeepTight()
            if (self.tabWidget.currentIndex()==3):
                self.site3Scene.newkeepTight()
            self.ui.widget.updateData(self.ui.timeSlider.value(),self.ui.daySlider.value())
            if self.ui.timeSlider.value() == self.ui.timeSlider.maximum():
                self.ui.timeSlider.setValue(self.ui.timeSlider.minimum())
                if self.ui.daySlider.value()== self.ui.daySlider.maximum():
                    self.ui.daySlider.setValue(self.ui.daySlider.minimum())
                else:
                    self.ui.daySlider.setValue(self.ui.daySlider.value() +1)
            else:
                self.ui.timeSlider.setValue(self.ui.timeSlider.value() +1)

    def updateGraph(self):
        self.ui.widget.update()
            
    #def chartClicked(self):
        #self.ui.timeSlider.setValue()
        
if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    window = MyWindow()
    sys.exit(app.exec_())
