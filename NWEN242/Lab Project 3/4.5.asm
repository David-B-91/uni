        .data 0x10000000
Base:
        .word 0, 10, 20
        .text
        .globl __start
__start:
        la $9, Base
        addi $3, $0, 3
        addi $4, $0, 4
        addi $6, $0, 3           # number of loops
loop:
        addi $5, $5, 1           # i++
        lw $8, 4($9)
        lw $2, 8($9)
        add $8, $8, $3
        sw $8, 36($9)
        add $2, $2, $4
        sw $2, 40($9)
        addi $3, $3, 1
        addi $4, $4, 1
        bne $5, $6, loop
.end