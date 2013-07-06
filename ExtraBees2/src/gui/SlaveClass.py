
from ManagerClass import singleton, ManagerClass

class SlaveClass(object):
    def __init__(self):
        self.manager = singleton(ManagerClass)