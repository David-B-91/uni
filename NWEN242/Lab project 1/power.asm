#Power

.data

	prompt: .asciiz "Enter the number: "
	mesg: 	.asciiz "When two is raised to that power, the answer is: "
	endl:	.asciiz "\n"
	
.text

	main:	la 	$a0, prompt		# print the prompt
		li 	$2, 4
		syscall
		
		li 	$2, 5		# read an interger from the user
		syscall			# into $v0
				
		addi	$t1, $t1, 1	# t1 = 1
		move 	$t2, $v0	# t2 = v0
		
	power: 	# your code to compute the power goes here
		# the number is in $v0, you should put the result into $t1
		
		beqz	$t2, output	# while (t2 != 0) {
		addi 	$t2, $t2, -1	#   t2 = t2-1
		sll 	$t1, $t1, 1	#   t1 = t1*2
		j 	power		# }
		
	output:	la $a0, mesg		# print some text
		li $2, 4
		syscall
		
		li $2, 1 		# print the answer
		move $a0, $t1
		syscall
		
		la $a0, endl		# print return
		li $2, 4
		syscall
		
		li $2, 10		# let's get out of here.
		syscall