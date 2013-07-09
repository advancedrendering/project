'''
Created on 2013-7-7

@author: Richard, Maarten
'''

import sys
from PyQt4 import QtGui, uic, QtCore, QtSql
import cloudBubble
from cloudBubble import cloudBubbleScene
#from scipy.sparse.linalg.dsolve.umfpack.umfpack import updateDictWithVars
from GuiThread import GuiThread
from copy import deepcopy
from SlaveClass import SlaveClass

class MyWindow(QtGui.QMainWindow, SlaveClass):
    def __init__(self, parent = None):
        super(MyWindow, self).__init__()
        SlaveClass.__init__(self)
        
        self.ui = uic.loadUi('gui.ui', self)
        self.t = GuiThread(1)
        self.connect(self.t, QtCore.SIGNAL('update'),self.loop)
        self.connect(self.ui.chart, QtCore.SIGNAL('update'),self.loop)
        self.connect(self.ui.dateTimeEdit, QtCore.SIGNAL('dateTimeChanged(QDateTime)'),self.dateTimeChanged)
        self.connect(self.ui.widget, QtCore.SIGNAL("mouseOverSiteChanged"), self.loop)
        self.t.start()
        comboBoxStrings = ["total Bytes","throughput","#Packages","#Packages/s","#Connections","#Errors","#Warnings","#Server n.a."]
        self.ui.comboBox.addItems(comboBoxStrings)
        self.connect(self.ui.comboBox, QtCore.SIGNAL("currentIndexChanged(int)"),self.comboBoxIndexChanged)
        self.connect(self.ui.widget, QtCore.SIGNAL("siteDoubleClicked"),self.siteDoubleClicked)
         
#Add hello world to scene, just for testing
#         site1Scene = QtGui.QGraphicsScene()    
#         site1Path = QtGui.QPainterPath()
#         site1Path.moveTo(30,120)
#         site1Path.cubicTo(80, 0, 50, 50, 80, 80)
#         site1Scene.addPath(site1Path, QtGui.QPen(QtCore.Qt.black), QtGui.QBrush(QtCore.Qt.green));
#         site1Scene.addText("Hello, world!", QtGui.QFont("Times", 15, QtGui.QFont.Bold)); 
#           
        self.site1Scene = cloudBubbleScene(1)
        self.site2Scene = cloudBubbleScene(2)
        self.site3Scene = cloudBubbleScene(3)  
        self.ui.site1GraphicsView.setScene(self.site1Scene)
        self.ui.site2GraphicsView.setScene(self.site2Scene)
        self.ui.site3GraphicsView.setScene(self.site3Scene)
        self.show()
        
    def loop(self):
        self.ui.dateTimeEdit.blockSignals(True)
        self.ui.dateTimeEdit.setDateTime(self.manager.CT)
        self.ui.dateTimeEdit.blockSignals(False)
        self.updateGraph()
        if (self.ui.tabWidget.currentIndex()==1): 
            self.site1Scene.newkeepTight()
        if (self.ui.tabWidget.currentIndex()==2): 
            self.site2Scene.newkeepTight()
        if (self.ui.tabWidget.currentIndex()==3):
            self.site3Scene.newkeepTight()
                
    def updateGraph(self):
        self.ui.widget.update()
        
    def dateTimeChanged(self):
        date = self.ui.dateTimeEdit.dateTime().date()
        dayOfWeek = (date.dayOfWeek())
        old_week_num = self.manager.CW.weekNumber()
        self.manager.CW = date.addDays(-dayOfWeek+1)
        if self.manager.CW.weekNumber() != old_week_num:
            self.ui.chart.updateData()
        self.manager.CT = deepcopy(self.ui.dateTimeEdit.dateTime())
        self.ui.chart.setLinePos()
    
    def siteDoubleClicked(self,name):
        if name == "Site 1":
            self.ui.tabWidget.setCurrentIndex(1)
        if name == "Site 2":
            self.ui.tabWidget.setCurrentIndex(2)
        if name == "Site 3":
            self.ui.tabWidget.setCurrentIndex(3)
        
    def comboBoxIndexChanged(self,index):
        self.manager.NetMode = index
        self.ui.chart.updateData()
    #def chartClicked(self):
        #self.ui.timeSlider.setValue()
        
if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    window = MyWindow()
    sys.exit(app.exec_())
