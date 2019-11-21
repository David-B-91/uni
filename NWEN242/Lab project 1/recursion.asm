.data
	endl:	.asciiz "\n"
.text

	main:	addi	$a0, $zero, 32
		#TODO: jump to F1, making sure we return to the right address
		jal	F1
		li	$2, 10		#exit
		syscall
		
	# F1 takes one parameter in $a0
	F1:	
		#TODO: handle the base case of the recursion
		#TODO: set the right parameters for the recursion
		#TODO: store relevant registers before jump
		#TODO: perform recursive call
		#TODO: reset data after return from recursion
		
		addi	$sp, $sp, -8	# adjust stack for 2 items
		sw	$ra, 4($sp)	# save the return addres
		sw	$a0, 0($sp)	# save the argument n
		
		slti	$t0, $a0, 0
		addi	$a0, $a0, -1
		beqz	$t0, F1
		
		#????
		
		lw	$a0, 0($sp)
		lw	$ra, 4($sp)
		addi	$sp, $sp, 8
		jr	$ra
				
		li	$2, 1		# print parameter, it's already in $a0
		syscall
		move	$t0, $a0	# copy parameter
		la	$a0, endl	# print end of line
		li	$2, 4
		syscall
		#
		
		