function swap(int x, int y) -> (int a, int b)
    requires x != y
	ensures a!=x && b != y:
    return y, x
