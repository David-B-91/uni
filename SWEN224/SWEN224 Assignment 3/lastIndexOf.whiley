function lastIndexOf(int[] items, int item) -> (int r)
ensures r >= 0 ==> items[r] == item
ensures r >= 0 ==> all { i in r+1 .. |items| | items[i] != item }
ensures r < 0 ==> all { i in 0 .. |items| | items[i] != item }:
    // 
    int i = |items|
    while i > 0
    where i <= |items|
    where (items[i] == item) <==> all {j in items[i+1].. |items| | items[j] != item}
    where (i==0 && items[i] != item) ==> all {k in 0..|items| | items[k] != item}:
        i = i - 1
        if items[i] == item:
            return i
    //
    return -1