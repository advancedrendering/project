'''
Created on 06.07.2013

@author: richard
'''
import numpy as np
import pyqtgraph as pg
from PyQt4 import QtGui, QtCore

WEEKDAYS = ["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"]

class PyQtGraphTest(QtGui.QWidget):
    def __init__(self, parent=None):
        QtGui.QWidget.__init__(self, parent)
        
        pg.setConfigOption('background', 'w')
        pg.setConfigOption('foreground', 'k')
        
        self.layout = QtGui.QVBoxLayout()
        self.setLayout(self.layout)
        timeSlots = 12*24*7
        self.plotWidgetTop = pg.PlotWidget(name="week")
        self.plotWidgetBottom = pg.PlotWidget(name="detail")
        dayLabels= []
        for i in range(0,len(WEEKDAYS)):
            dayLabels.append((i*288,WEEKDAYS[i]))
        
        self.plotWidgetTop.getPlotItem().getAxis("bottom").setTicks([dayLabels])
        
        self.plotWidgetTop.getPlotItem().hideAxis("left")
        self.plotWidgetBottom.getPlotItem().hideAxis("left")
        
        self.layout.addWidget(self.plotWidgetTop)
        self.layout.addWidget(self.plotWidgetBottom)
        
        # sample data
        x2 = np.linspace(0, timeSlots, timeSlots)
        data2 = np.sin(x2)
        #self.numberOfTimeSlots 
        
        self.plotWidgetTop.plot(data2, pen=(0,0,0,200))
        
        self.regionSelection = pg.LinearRegionItem([200,400])
        self.regionSelection.setBounds([0,timeSlots])
        self.regionSelection.setBrush(QtGui.QBrush(QtGui.QColor(100,100,255,50)))
        self.regionSelection.setZValue(-10)
        
        self.plotWidgetTop.addItem(self.regionSelection)
        self.plotWidgetBottom.plot(data2, pen=(0,0,0,200))
        
        self.line = pg.InfiniteLine(angle=90, movable=True)
        self.line.setPen(pen=(0,0,0,200))
        self.line.setBounds([0,timeSlots])
        
        self.plotWidgetBottom.addItem(self.line)
        
        self.regionSelection.sigRegionChanged.connect(self.updatePlot)
        self.plotWidgetBottom.sigXRangeChanged.connect(self.updateRegion)
        
        self.updatePlot()
        
    def updatePlot(self):
        self.plotWidgetBottom.setXRange(*self.regionSelection.getRegion(), padding=0)
        linePos = QtCore.QPointF((self.regionSelection.getRegion()[0]+self.regionSelection.getRegion()[1])/2,0)
        self.line.setPos(linePos)
        
    def updateRegion(self):
        self.regionSelection.setRegion(self.plotWidgetBottom.getViewBox().viewRange()[0])

