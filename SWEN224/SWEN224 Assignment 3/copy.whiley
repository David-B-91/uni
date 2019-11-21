function copy (int[] src, int sStart, int[] dest, int dStart, int len) -> (int[] r)
requires sStart > 0 && sStart <= |src| && dStart > 0 && dStart <= |dest| && len >= 0 
ensures all{i in sStart.. sStart + len, j in dStart.. dStart + len | src[i] == dest[j]}:

    int i = 0
    
    while i < len
    where i >= 0
    where dStart + i <= |dest|
    where sStart + i <= |src|: 
        dest[dStart + i] = src[sStart + i]
    
    return dest