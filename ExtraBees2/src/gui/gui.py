import sys
from PyQt4 import QtGui, uic, QtCore, QtSql
from cloudBubble.cloudBubbleScene import cloudBubbleScene
#from scipy.sparse.linalg.dsolve.umfpack.umfpack import updateDictWithVars
from GuiThread import GuiThread
from copy import deepcopy
from SlaveClass import SlaveClass

class MyWindow(QtGui.QMainWindow, SlaveClass):
    def __init__(self, parent = None):
        super(MyWindow, self).__init__()
        SlaveClass.__init__(self)
        
        self.ui = uic.loadUi('gui.ui', self)
        self.connect(self.ui.playPauseButton, QtCore.SIGNAL("clicked()"), self.clickedPlayPause)
        self.t = GuiThread(1)
        self.connect(self.t, QtCore.SIGNAL('update'),self.loop)
        self.connect(self.ui.chart, QtCore.SIGNAL('update'),self.loop)
        self.connect(self.ui.dateTimeEdit, QtCore.SIGNAL('dateTimeChanged(QDateTime)'),self.dateTimeChanged)
        self.connect(self.ui.widget, QtCore.SIGNAL("mouseOverSiteChanged"), self.loop)
        self.t.start()
         
        self.running = False
        
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
        
    def clickedPlayPause(self):

#        self.site1Scene.keepTight() 

#        self.site1Scene.animation.setbubbleloc(1000000000, QtCore.QPointF(100,-100))
#        self.site1Scene.animation.setbubblesize(1000000000, 2.0)

        if self.running:
            self.running = False
            self.ui.playPauseButton.setText("Play")
            print "Pause"
        else:
            self.running = True
            self.ui.playPauseButton.setText("Pause")
            print "Play"
        self.ui.update()

        
    def clickedPause(self):
        print "Pause"
        self.running = False
        
    def loop(self):
        self.ui.dateTimeEdit.blockSignals(True)
        self.ui.dateTimeEdit.setDateTime(self.manager.CT)
        self.ui.dateTimeEdit.blockSignals(False)
        self.updateGraph()
        if self.running:     
            if (self.tabWidget.currentIndex()==1): 
                self.site1Scene.newkeepTight()
            if (self.tabWidget.currentIndex()==2): 
                self.site2Scene.newkeepTight()
            if (self.tabWidget.currentIndex()==3):
                self.site3Scene.newkeepTight()
                
    def updateGraph(self):
        self.ui.widget.update()
        
    def dateTimeChanged(self):
        date = self.ui.dateTimeEdit.dateTime().date()
        dayOfWeek = (date.dayOfWeek())
        date2 = date.addDays(-dayOfWeek+1)
        self.manager.CW = date2
        self.manager.CT = deepcopy(self.ui.dateTimeEdit.dateTime())
        self.ui.chart.setLinePos()
    #def chartClicked(self):
        #self.ui.timeSlider.setValue()
        
if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    window = MyWindow()
    sys.exit(app.exec_())
