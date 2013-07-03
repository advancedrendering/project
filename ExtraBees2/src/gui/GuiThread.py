'''
Created on 2013-6-30

Thread which sends every @param seconds: a custom signal 'seconds_passed'. So it's basically used for timing stuff.

@author: Richard
'''

import time
from PyQt4 import QtCore

class GuiThread(QtCore.QThread):
    def __init__(self,seconds):
        QtCore.QThread.__init__(self)
        self.time = seconds
    
    def run(self):
        while True:
            self.emit(QtCore.SIGNAL('update'))
            time.sleep(self.time)
