'''
Created on 03.07.2013

@author: richard
'''
from PyQt4 import QtGui, QtCore
from matplotlib.backends.backend_qt4agg import FigureCanvasQTAgg as FigureCanvas
 
from matplotlib.figure import Figure

class MplCanvas(FigureCanvas):
 
    def __init__(self):
        self.fig = Figure()
        self.fig.patch.set_facecolor('white')
        self.ax = self.fig.add_subplot(111)
        #self.ax.set_frame_on(False)
        self.ax.axes.get_yaxis().set_visible(False)
        self.ax.get_xaxis().tick_bottom()
        self.ax.autoscale(enable = True, axis='y', tight= True)
        self.ax.autoscale(enable = False, axis='x', tight= True)
        self.fig.subplots_adjust(0.005,0.2,0.995,1,0,0)
        self.xdata = 0
        
        #self.ax.spines['right'].set_color('none')
        #self.ax.spines['top'].set_color('none')
        #self.ax.spines['left'].set_color('none')
        
        FigureCanvas.__init__(self, self.fig)
        FigureCanvas.setSizePolicy(self, QtGui.QSizePolicy.Expanding,QtGui.QSizePolicy.Expanding)
        FigureCanvas.updateGeometry(self)
        
    def mousePressEvent(self, event):
        inv = self.ax.transData.inverted()
        self.xdata = int(inv.transform((event.x(),event.y()))[0])
        self.emit(QtCore.SIGNAL('chartClicked'))


class TrafficChartWidget(QtGui.QWidget):
    def __init__(self, parent=None):
        QtGui.QWidget.__init__(self, parent)
        self.canvas = MplCanvas()
        self.vbl = QtGui.QVBoxLayout()
        self.vbl.addWidget(self.canvas)
        self.setLayout(self.vbl)