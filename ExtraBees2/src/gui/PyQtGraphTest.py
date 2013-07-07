'''
Created on 06.07.2013

@author: Richard, Maarten
'''

import numpy as np
import pyqtgraph as pg
from PyQt4 import QtGui, QtCore, QtSql
from gui import SlaveClass
from copy import deepcopy
import math

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
        
        self.data = np.zeros(12 * 24 * 7)
        
        self.communicationQuery = QtSql.QSqlQuery()
        self.communicationQuery.prepare("""SELECT Starttime, (SumTotalBytesSrc + SumTotalBytesDest), (SumPacketSrc + SumPacketDest), SumConnections FROM datavis.macro_networkflow WHERE Starttime >= :starttime1
AND Starttime <= DATE_ADD(:starttime2, INTERVAL 7 DAY);""")
        
        self.health_err_com_query = QtSql.QSqlQuery()
        self.health_err_com_query.prepare("""SELECT receivedDate, COUNT(*) FROM datavis.healthserverbyesites WHERE statusVal = 3 GROUP BY receivedDate;""")
                
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
        for i in range(0,len(WEEKDAYS_SHORT)):
            dayLabels.append((i*288,WEEKDAYS_SHORT[i]))
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
        
        self.top_data_plot = self.plotWidgetTop.plot(self.data, pen=(0,0,0,200))
        
        self.regionSelection = pg.LinearRegionItem([200,400])
        self.regionSelection.setBounds([0,self.timeSlots])
        self.regionSelection.setBrush(QtGui.QBrush(QtGui.QColor(100,100,255,50)))
        self.regionSelection.setZValue(-10)
        
        self.plotWidgetTop.addItem(self.regionSelection)
        self.bottom_data_plot = self.plotWidgetBottom.plot(self.data, pen=(0,0,0,200))
        
        self.line = pg.InfiniteLine(angle=90, movable=True,pen=(255,0,0,200))
        self.line.setBounds([0,self.timeSlots])
        
        self.plotWidgetBottom.addItem(self.line)
        
        self.regionSelection.sigRegionChanged.connect(self.updatePlot)
        self.plotWidgetBottom.sigXRangeChanged.connect(self.updateRegion)
        self.line.sigDragged.connect(self.setDateTime)
        
        self.updateData()
        self.updatePlot()
        
        
        
    def updateData(self):
        
        self.plotWidgetTop.removeItem(self.top_data_plot)
        self.plotWidgetBottom.removeItem(self.bottom_data_plot)
        
        self.data = np.zeros(12 * 24 * 7)
        
        loc_datetime = QtCore.QDateTime(self.manager.CW, QtCore.QTime(0,0,0))
        self.communicationQuery.bindValue(":starttime1", loc_datetime)
        self.communicationQuery.bindValue(":starttime2",loc_datetime)
        self.communicationQuery.exec_()
        
        while (self.communicationQuery.next()):
            #print self.communicationQuery.value(1).toDateTime(), self.communicationQuery.value(0).toInt()[0]
            #calc the index of the array using the datetime
            loc_datetime = self.communicationQuery.value(0).toDateTime()
            day_of_week_minutes = (loc_datetime.date().dayOfWeek() - 1) * 24 * 12#* 84 # 7 * 12 
            hour_minutes = loc_datetime.time().hour() * 12
            minute = loc_datetime.time().minute() / 5
            loc_index = day_of_week_minutes + hour_minutes + minute
            if (self.manager.NetMode == self.manager.TOTALBYTES):
                self.data[loc_index] = math.log(int(self.communicationQuery.value(1).toString()) + 1)
            elif (self.manager.NetMode == self.manager.THROUGHPUT):
                self.data[loc_index] = math.log(float(self.communicationQuery.value(1).toString()) / 300.0 + 1)
            elif (self.manager.NetMode == self.manager.NUM_PACKAGES):
                self.data[loc_index] = math.log(float(self.communicationQuery.value(2).toString()) + 1)
            elif (self.manager.NetMode == self.manager.NUM_PACKAGES_PER_SECOND):
                self.data[loc_index] = math.log(float(self.communicationQuery.value(2).toString()) / 300.0 + 1)
            elif (self.manager.NetMode == self.manager.NUM_CONNECTIONS):
                self.data[loc_index] = math.log(float(self.communicationQuery.value(3).toString()) + 1)
        self.top_data_plot = self.plotWidgetTop.plot(self.data,  pen=(0,0,0,200), fillLevel = 0.0,  fillBrush = QtGui.QBrush(QtGui.QColor(200,200,200, 100)))
        self.bottom_data_plot = self.plotWidgetBottom.plot(self.data, pen=(0,0,0,200), fillLevel = 0.0,  fillBrush = QtGui.QBrush(QtGui.QColor(200,200,200, 100)))
        
        
    def updatePlot(self):
        self.plotWidgetBottom.setXRange(*self.regionSelection.getRegion(), padding=0)
        linePos = QtCore.QPointF((self.regionSelection.getRegion()[0]+self.regionSelection.getRegion()[1])/2,0)
        self.line.setPos(linePos)
        tickSpacing = (self.regionSelection.getRegion()[1]-self.regionSelection.getRegion()[0])/10
        if tickSpacing < 1.0:
            tickSpacing = 1
        
        tickLabels = []
        for i in range(int(self.regionSelection.getRegion()[0]),int(self.regionSelection.getRegion()[1]),int(tickSpacing)):
            tickLabels.append((i,self.timeSlotToString(i)))
        self.plotWidgetBottom.getPlotItem().getAxis("bottom").setTicks([tickLabels])
        self.setDateTime()
        
    def updateRegion(self):
        self.regionSelection.setRegion(self.plotWidgetBottom.getViewBox().viewRange()[0])
        self.setDateTime()
        
    def timeSlotToString(self,timeslot):
        day = timeslot/(12*24)
        hour = (timeslot/12)%24
        minute = (timeslot % 12)*5
        return WEEKDAYS_SHORT[day]+" "+str(hour).zfill(2)+":"+str(minute).zfill(2)

    def setLinePos(self):
        dayOfWeek = deepcopy(self.manager.CT.date().dayOfWeek()-1)
        day = dayOfWeek*(24*12)
        hour = deepcopy(self.manager.CT.time().hour() *12)
        minute = deepcopy(self.manager.CT.time().minute()/5)
        pos = int(day)+int(hour)+int(minute)
        self.regionSelection.setRegion([pos-144,pos+144])
    
    def setDateTime(self):
        timeslot = self.line.getPos()[0]
        day = self.manager.CW.day() + int(timeslot/(12*24)) 
        month = self.manager.CW.month()
        year = self.manager.CW.year()
        hour = int((timeslot/12)%24)
        minute = int((timeslot) % 12)*5
#       print self.manager.CT  
        self.manager.CT = QtCore.QDateTime(QtCore.QDate(year, month, day), QtCore.QTime(hour,minute,0))
        self.emit(QtCore.SIGNAL('update'))

