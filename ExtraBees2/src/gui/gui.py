import sys
from PyQt4 import QtGui, uic, QtCore 
from cloudBubble.cloudBubbleScene import cloudBubbleScene
from scipy.sparse.linalg.dsolve.umfpack.umfpack import updateDictWithVars



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
        self.site1Scene = cloudBubbleScene()   
        self.ui.site1GraphicsView.setScene(self.site1Scene)
        self.ui.site1GraphicsView.show()
        self.ui.site2GraphicsView.setScene(self.site1Scene)
        self.ui.site2GraphicsView.show()
        self.ui.site3GraphicsView.setScene(self.site1Scene)
        self.ui.site3GraphicsView.show()
        self.show()
    def clickedPlay(self):
        self.site1Scene.animation.setbubbleloc(1000000000, QtCore.QPointF(100,-100))
        print "Play"

        
    def clickedPause(self):

        print "pause"


        
if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    window = MyWindow()
    sys.exit(app.exec_())
