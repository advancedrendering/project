'''
Created on 06.07.2013

@author: richard
'''
import numpy as np
import pyqtgraph as pg
from PyQt4 import QtGui, QtCore
from gui import SlaveClass

WEEKDAYS = ["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"]
WEEKDAYS_SHORT = ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"]

class PyQtGraphTest(QtGui.QFrame,SlaveClass):
    def __init__(self, parent=None):
        QtGui.QWidget.__init__(self, parent)
        SlaveClass.__init__(self)
        self.setAutoFillBackground(True)
        p = self.palette()
        p.setColor(self.backgroundRole(), QtGui.QColor(255,255,255,255))
        self.setPalette(p)
        
        pg.setConfigOption('background', 'w')
        pg.setConfigOption('foreground', 'k')
        
        self.layout = QtGui.QVBoxLayout()
        self.layout.setMargin(2)
        self.setLayout(self.layout)
        self.timeSlots = 12*24*7
        self.plotWidgetTop = pg.PlotWidget(name="week")
        self.plotWidgetTop.getPlotItem().hideAxis("bottom")
        self.plotWidgetTop.getPlotItem().showAxis("top")
        self.plotWidgetBottom = pg.PlotWidget(name="detail")
        dayLabels = []
        for i in range(0,len(WEEKDAYS)):
            dayLabels.append((i*288,WEEKDAYS[i]))
        timeLabels = []
        for i in range(0,self.timeSlots):
            hour = (i/12)%24
            minute = (i % 12)*5
            string = str(hour).zfill(2)+":"+str(minute).zfill(2)
            timeLabels.append((i,string))

        self.plotWidgetTop.getPlotItem().getAxis("top").setTicks([dayLabels])
        #self.plotWidgetBottom.getPlotItem().getAxis("bottom").setTicks([timeLabels])
        
        self.plotWidgetTop.getPlotItem().hideAxis("left")
        self.plotWidgetBottom.getPlotItem().hideAxis("left")
        
        self.layout.addWidget(self.plotWidgetTop)
        self.layout.addWidget(self.plotWidgetBottom)
        
        # sample data
        x2 = np.linspace(0, self.timeSlots, self.timeSlots)
        data2 = np.sin(x2)
        #self.numberOfTimeSlots 
        
        self.plotWidgetTop.plot(data2, pen=(0,0,0,200))
        
        self.regionSelection = pg.LinearRegionItem([200,400])
        self.regionSelection.setBounds([0,self.timeSlots])
        self.regionSelection.setBrush(QtGui.QBrush(QtGui.QColor(100,100,255,50)))
        self.regionSelection.setZValue(-10)
        
        self.plotWidgetTop.addItem(self.regionSelection)
        self.plotWidgetBottom.plot(data2, pen=(0,0,0,200))
        
        self.line = pg.InfiniteLine(angle=90, movable=True)
        self.line.setPen(pen=(0,0,0,200))
        self.line.setBounds([0,self.timeSlots])
        
        self.plotWidgetBottom.addItem(self.line)
        
        self.regionSelection.sigRegionChanged.connect(self.updatePlot)
        self.plotWidgetBottom.sigXRangeChanged.connect(self.updateRegion)
        self.line.sigPositionChangeFinished.connect(self.setTimeSlot)
        
        self.updatePlot()
        
    def updatePlot(self):
        self.plotWidgetBottom.setXRange(*self.regionSelection.getRegion(), padding=0)
        linePos = QtCore.QPointF((self.regionSelection.getRegion()[0]+self.regionSelection.getRegion()[1])/2,0)
        self.line.setPos(linePos)
        tickSpacing = (self.regionSelection.getRegion()[1]-self.regionSelection.getRegion()[0])/10
        
        tickLabels = []
        for i in range(int(self.regionSelection.getRegion()[0]),int(self.regionSelection.getRegion()[1]),int(tickSpacing)):
            tickLabels.append((i,self.timeSlotToString(i)))
        self.plotWidgetBottom.getPlotItem().getAxis("bottom").setTicks([tickLabels])
        self.setTimeSlot()
        
    def updateRegion(self):
        self.regionSelection.setRegion(self.plotWidgetBottom.getViewBox().viewRange()[0])
        
    def timeSlotToString(self,timeslot):
        day = timeslot/(12*24)
        hour = (timeslot/12)%24
        minute = (timeslot % 12)*5
        return WEEKDAYS[day]+" "+str(hour).zfill(2)+":"+str(minute).zfill(2)
    
    def setTimeSlot(self):
        timeslot = self.line.getPos()[0]
        day = self.manager.CW.day() +(timeslot/(12*24)) 
        month = self.manager.CW.month()
        year = self.manager.CW.year()
        hour = (timeslot/12)%24
        minute = (int(timeslot) % 12)*5
        print self.manager.CT  
        print year, month, day, hour, minute
        self.manager.CT = QtCore.QDateTime(QtCore.QDate(year, month, day), QtCore.QTime(hour,minute,0))
        print self.manager.CT        

