Assignment one 
part 1a 

> lineToWords :: String -> [String]
> lineToWords str = if str' == "" then [] else word : lineToWords rest
>    where str' = dropWhile isSpace str (word, rest) = break isSpace str'