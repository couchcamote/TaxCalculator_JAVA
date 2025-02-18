- I prioritized the calculation GetTollFee , IsTollFreeDate
- It is faster to debug my fix if I can test it directly without need to setup an HTTP endpoint first
- I had extracted the the data (dates)


- Bugs found
-- Setting of intervalStart at the end of each loop
-- 


Plans, put these data in an external source instead.

| Time        | Amount |
| ----------- | :----: |
| 06:00–06:29 | SEK 8  |
| 06:30–06:59 | SEK 13 |
| 07:00–07:59 | SEK 18 |
| 08:00–08:29 | SEK 13 |
| 08:30–14:59 | SEK 8  |
| 15:00–15:29 | SEK 13 |
| 15:30–16:59 | SEK 18 |
| 17:00–17:59 | SEK 13 |
| 18:00–18:29 | SEK 8  |
| 18:30–05:59 | SEK 0  |

{
    "Gothenburg": {
        "timeRanges": [
            {"start": "06:00", "end": "06:29", "amount": 8},
            {"start": "06:30", "end": "06:59", "amount": 13},
            {"start": "07:00", "end": "07:59", "amount": 18},
            {"start": "08:00", "end": "08:29", "amount": 13},
            {"start": "08:30", "end": "14:59", "amount": 8},
            {"start": "15:00", "end": "15:29", "amount": 13},
            {"start": "15:30", "end": "16:59", "amount": 18},
            {"start": "17:00", "end": "17:59", "amount": 13},
            {"start": "18:00", "end": "18:29", "amount": 8}
        ],
        "maxDailyTax": 60,
        "exemptVehicles": ["Motorcycle", "Tractor", "Emergency", "Diplomat", "Foreign", "Military"]
    }
}

- Holiday is fixed for 2013 only
