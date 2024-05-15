from typing import *

# DEFINE CONSTANTS
A_COMMAND = 0
C_COMMAND = 1
L_COMMAND = 2
class Parser:
    
    
    """"
    Reads the input program file   
    """
    def __init__(self, filename:str):
        # given filename, reads the file and saves contents
        with open(filename, 'r') as f:
            self.commands = []
            for line in f.readlines():
                line = line.strip()
                if line.isspace() or len(line) == None:
                    pass
                
            
        self.index = -1 # counter for the next line
        
    """
    Returns if there are more commands in the file
    """
    def hasMoreCommands(self) -> bool:
        if self.index >= len(self.lines):
            return False
        
        else:
            return True
        
    def advance(self) -> None:
        if self.hasMoreCommands():
            self.index += 1
            
    def commandType(self) -> int:
        command = self.inde