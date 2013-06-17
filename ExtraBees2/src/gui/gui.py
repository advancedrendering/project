import sys
from PyQt4 import QtGui, uic, QtCore


class MyWindow(QtGui.QMainWindow):
    def __init__(self, parent = None):
        super(MyWindow, self).__init__(parent)
        self.ui = uic.loadUi('gui.ui', self)
        self.connect(self.ui.pushButton, QtCore.SIGNAL("clicked()"), self.clicked)
        
        
        self.show()
    
    def clicked(self):
        print "ok"

if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    window = MyWindow()
    sys.exit(app.exec_())
