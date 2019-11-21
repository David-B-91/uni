function remove (int[] items, int index) -> (int[] r)
requires |items| != 0
requires index >= 0 && index <= |items|
ensures |r| == |items| -1
ensures all {i in 0.. index-1 | items[i] == r[i]}
ensures all {i in index..|r| | items[i-1] == r[i]}:
    //
    
    assert |items| > 0
    assert index >= 0 && index <= |items|
    
    int rSize = |items| - 1
    int[] result = [0; rSize]
    int i = 0
    while i < index
    where i >= 0
    where |result| == |items| - 1
    where |items| > 0
    where i < |items|
    where i <= |result|:
        result[i] = items[i]
        i = i + 1
        
    i = index + 1
    
    while i <= |result|
    where |result| == |items| - 1 
    where |items| > 0
    where i < |items|:
        result[i] = items[i+1]
    //
    
    assert |result| == |items| - 1
    
    
    return result