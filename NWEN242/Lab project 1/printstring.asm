# NWEN 242 Lab 1
# Program: printstring 
# Author: 

.data
	str: 	.asciiz  "David Burrell, 19911004"	#replace with your name and birthday
.text 


	la	$a0, str	# load the address of the string
	addi	$t0, $a0, 0	# starting address of array
	#TODO: find length of string, it's zero terminated, so loop until a zero
	addi 	$t1, $a0, 21	# initialize loop counter to array end position (replace with your str length)
loop:	lb	$a0, 0($t0)	# load a single character
	li	$v0, 11		# specify print character service
	syscall			# print 
	addi	$t0, $t0, 1	# add 1 to the loop index
	blt	$t0, $t1, loop 	# continue if not at string length
	li	$v0, 10		# system call for exit
	syscall			# we are out of here.
