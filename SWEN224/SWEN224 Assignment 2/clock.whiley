type Time is { int hours, int minutes } 
// There are exactly 24 hours in a day, and 60 minutes in an hour
where hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60

function tick(Time t) -> (Time r):
    if t.hours==23 && t.minutes == 59:
        return {hours: 0, minutes:0}
    else if t.minutes == 59:
        return{hours: t.hours+1, minutes: 0}
    else:
        return{hours: t.hours, minutes: t.minutes+1}
