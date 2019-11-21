addi $2, $0, 3
nop
sw   $2, 0($0) 
addi $3, $0, 2 
addi $9, $0, 12
nop 
sw   $9, 12($0) 
uncon: 
add  $1, $2, $3 
lw   $5, 12($0)
sw   $1, 0($0) 
addi $4, $0, 100
nop
slt  $6, $1, $4 
nop
beq  $6, $0, fin
nop
add  $2, $2, $2 
add  $3, $3, $3 
lw   $8, 0($0)
nop
nop
beq  $8, $0, fin
lw   $7, 0($5)
nop
nop
add  $7, $3, $7 
beq  $0, $0, uncon
nop
fin: 
END 