       .data 0x10000480 
Array_A: 
       .word 1,1,1,1,2,2,2,2 
Array_B: 
       .word 3,3,3,3,4,4,4,4 
       .text 
       .globl __start 
__start:
	   li $6, 0                #sum=0 
       li $4, 8                #number of elements
	   li $8, 1				   #N
j_loop: la $2, Array_A
	   la $3, Array_B
loop:  lw $5, 0($2)			   #Array_A
	   lw $7, 0($3)			   #Array_B
       add $6, $6, $5          #sum=sum+Array_A[i]
	   add $6, $6, $7		   #sum=sum+Array_B[i]
       addi $2, $2, 4
	   addi $3, $3, 4
       addi $4, $4, -1 
       bgt $4, $0, loop
	   addi $8, $8, -1
	   bgt $8, $0, j_loop
       .end 