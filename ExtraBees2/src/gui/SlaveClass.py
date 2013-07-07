'''
Created on 2013-7-7

@author: Richard, Maarten
'''

from ManagerClass import singleton, ManagerClass

class SlaveClass(object):
    def __init__(self):
        self.manager = singleton(ManagerClass)