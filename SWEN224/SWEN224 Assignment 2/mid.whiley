function mid(int x,int y, int z) -> (int r)
requires x != y && y != z && x != z
ensures (r == x && r != y && r != z) || (r == y && r != x && r != z) || (r == z && r != y && r != x):
    if x < y && y < z:
        return y
    else if x < z && z < y:
        return z
    else:
        return x
