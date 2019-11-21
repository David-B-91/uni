constant Jan is 1
constant Feb is 2
constant Mar is 3
constant Apr is 4
constant May is 5
constant Jun is 6
constant Jul is 7
constant Aug is 8
constant Sep is 9
constant Oct is 10
constant Nov is 11
constant Dec is 12

function getDate() -> (int day, int month, int year)
ensures month == 2 ==> (0 < day && day <= 29)
ensures month == 9||month == 4||month == 6||month == 11 ==> (0<day&& day<=30)
ensures month == 1||month == 3||month == 5||month == 7||month == 8||month == 10||month == 12 ==> (0<day&&day<=31):
    return 17, Sep, 2015
