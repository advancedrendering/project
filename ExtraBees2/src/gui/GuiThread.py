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
        self.running = False
        self.time = seconds
    
    def run(self):
        while True:
            self.emit(QtCore.SIGNAL('updateGraph'))
            if self.running:
                self.emit(QtCore.SIGNAL('seconds_passed'))
            time.sleep(self.time)
    
    def play(self):
        self.running = True
    
    def pause(self):
        self.running = False