.data 
	source:		.word	3, 1, 4, 1, 5, 9, 0
	dest:		.word	0, 0, 0, 0, 0, 0, 0
	countmsg:	.asciiz	" values copied. "
	
.text

	main: 	
		add	$s0, $0, $ra	# save our return address
		la	$a0, source
		la	$a1, dest
		jal	loop
		li	$v0, 10
		syscall
		
	loop:	
		lw	$v1, 0($a0)	# read next word from source
		addi	$v0, $v0, 1	# increment count words copied
		sw	$v1, 0($a1)	# write to destination
		addi	$a0, $a0, 4	# advance pointer to next source (increase by 4 for words)
		addi	$a1, $a1, 4	# advance pointer to next dest	 (increase by 4 for words)
		bnez	$v1, loop 	# loop if word copied not zero
		
	loopend:
		move	$a0, $v0	# We want to print the count
		li	$v0, 1
		syscall
		la	$a0, countmsg	# We want to print the message
		li	$v0, 4
		syscall			# Print it
		li	$a0, 0x0A	# We want to print '\n'
		li	$v0, 11		
		syscall			# Print it
		jr	$ra		# Return from main. We Stored $ra in $s0