ideal
p286n
model tiny

codeseg
   org 100h
   jmp start

   ; your data and subroutine here

start:
   mov ax, 4c00h
   int 21h
end