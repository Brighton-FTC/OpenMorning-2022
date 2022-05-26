# How to add strategies
- This is for steps for the autonomous period
- Place it in `config.json/[team name]/strategies`
- Each item is an object containing an `id` (identifier) and `param` (parameter). Here is a list of functions possible:

id | Description | param
---|-------------|------
fd | Move forwards n metres (backwards = negative n) | n
lt | Turn left n degrees | n
rt | Turn right n degrees | n
sleep | Pause n seconds | n
carousel | Set the carousel to a speed of n, for a length of time inversely proportional to n | n

## Useful strategies:
### Red panic - Both
```json
// Drive to warehouse from central red alliance wall
{
    "id": "fd",
    "param": 0.05
},
{
    "id": "rt",
    "param": 90
},
{
    "id": "fd",
    "param": // distance from warehouse (m)
}
```
### Blue panic - Both
```json
// Drive to warehouse from central blue alliance wall
{
    "id": "fd",
    "param": 0.05
},
{
    "id": "lt",
    "param": 90
},
{
    "id": "fd",
    "param": // distance from warehouse (m)
}
```

### Ducks and Warehouse blue - T1
```json
// Deliver ducks then drive to warehouse from central blue alliance wall
{
    "id": "fd",
    "param": 0.05
},
{
    "id": "lt",
    "param": 90
},
{
    "id": "fd", // Reverse
    "param": -[d]// distance from carousel (m) - as carousel on back left
},
{
    "id": "carousel",
    "param": [s]// TODO: Best carousel speed
},
{
    "id": "fd",
    "param": 3.65
}
```

### Ducks and Warehouse blue - T1
```json
// Deliver ducks then drive to warehouse from central blue alliance wall
{
    "id": "fd",
    "param": 0.05
},
{
    "id": "rt",
    "param": 90
},
{
    "id": "fd", // Reverse
    "param": -[d]// distance from carousel (m) - as carousel on back left
},
{
    "id": "lt",
    "param": 90
},
{
    "id": "carousel",
    "param": [s]// TODO: Best carousel speed
},
{
    "id": "rt",
    "param": 90
},
{
    "id": "fd",
    "param": 3.5
}
```

### Ducks and Warehouse red - T2
```json
// Deliver ducks then drive to warehouse from central blue alliance wall
{
    "id": "fd",
    "param": 0.05
},
{
    "id": "lt",
    "param": 90
},
{
    "id": "fd",
    "param": [d]// distance from carousel (m) - as carousel on back left
},
{
    "id": "carousel",
    "param": [s]// TODO: Best carousel speed
},
{
    "id": "fd",
    "param": -3.5
}
```

### Ducks and Warehouse blue - T2
```json
// Deliver ducks then drive to warehouse from central blue alliance wall
{
    "id": "fd",
    "param": 0.05
},
{
    "id": "rt",
    "param": 90
},
{
    "id": "fd",
    "param": [d]// distance from carousel (m) - as carousel on back left
},
{
    "id": "lt",
    "param": 90
},
{
    "id": "carousel",
    "param": [s]// TODO: Best carousel speed
},
{
    "id": "rt",
    "param": 90
},
{
    "id": "fd",
    "param": -3.5
}
```