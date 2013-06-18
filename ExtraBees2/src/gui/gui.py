import sys
from PyQt4 import QtGui, uic, QtCore 
from cloudBubble.cloudBubbleScene import cloudBubbleScene



class MyWindow(QtGui.QMainWindow):
    def __init__(self, parent = None):
        super(MyWindow, self).__init__()
        self.ui = uic.loadUi('gui.ui', self)
        self.connect(self.ui.playButton, QtCore.SIGNAL("clicked()"), self.clickedPlay)
        self.connect(self.ui.pauseButton, QtCore.SIGNAL("clicked()"), self.clickedPause)
         
#Add hello world to scene, just for testing
#         site1Scene = QtGui.QGraphicsScene()    
#         site1Path = QtGui.QPainterPath()
#         site1Path.moveTo(30,120)
#         site1Path.cubicTo(80, 0, 50, 50, 80, 80)
#         site1Scene.addPath(site1Path, QtGui.QPen(QtCore.Qt.black), QtGui.QBrush(QtCore.Qt.green));
#         site1Scene.addText("Hello, world!", QtGui.QFont("Times", 15, QtGui.QFont.Bold)); 
#           
        site1Scene = cloudBubbleScene()
#         site1Path = QtGui.QPainterPath()
#         site1Path.moveTo(30,120)
#         site1Path.cubicTo(80, 0, 50, 50, 80, 80)
#         site1Scene.addPath(site1Path, QtGui.QPen(QtCore.Qt.black), QtGui.QBrush(QtCore.Qt.green));
#         site1Scene.addText("Hello, world!", QtGui.QFont("Times", 15, QtGui.QFont.Bold));     
        self.ui.site1GraphicsView.setScene(site1Scene)
        self.ui.site1GraphicsView.show()
        self.ui.site2GraphicsView.setScene(site1Scene)
        self.ui.site2GraphicsView.show()
        self.ui.site3GraphicsView.setScene(site1Scene)
        self.ui.site3GraphicsView.show()

#         self.onebubble=atomicBubble(3,1,255);
#         l = QtGui.QVBoxLayout(self.site1GraphicView);
#         l.addWidget(self.onebubble);
#         self.site1GraphicView.setFocus()
#         self.setCentralWidget(self.site1GraphicView)
        self.show()
    def clickedPlay(self):
        print "Play"
    
    def clickedPause(self):
        print "pause"


        
if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    window = MyWindow()
    sys.exit(app.exec_())
