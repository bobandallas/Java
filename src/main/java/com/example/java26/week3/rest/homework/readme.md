# 1. Student Build with JPA

### Get All
#### Request Url : /student
#### Request method : GET
#### Request Body : null
#### Response Body : 
```
[
    {
        "id": 4,
        "name": "B",
        "age": 121
    },
    {
        "id": 6,
        "name": "fasfs",
        "age": 21
    },
    {
        "id": 7,
        "name": "faadsfasfasds",
        "age": 3232
    },
    {
        "id": 8,
        "name": "apple",
        "age": 32
    },
    {
        "id": 5,
        "name": "banana",
        "age": 32
    }
]
```


### Get One
#### Request Url : /student/{id}
#### Request method : GET
#### Request Body : null
#### Response Body : 
```
{
    "id": 7,
    "name": "faadsfasfasds",
    "age": 3232
}
```


### Add One
#### Request Url : /student
#### Request method : PUT
#### Request Body : 
```
    {
        "name": "tbtb",
        "age": 32,
        "vegetarianism": true
    }
```
#### Response Body : 
```
success

```


### Delete One
#### Request Url : /student/{id}
#### Request method : DELETE
#### Request Body : null
#### Response Body : 
```
success

```

### Update One
#### Request Url : /student/{id}
#### Request method : POST
#### Request Body : 
```
    {
        "name": "tbtb",
        "age": 32,
        "vegetarianism": true
    }
```
#### Response Body : 
```
success

```




# 2. Teacher Build with Hibernate

### Get All
#### Request Url : /teacher
#### Request method : GET
#### Request Body : null
#### Response Body : 
```
[
    {
        "name": "Paul",
        "age": 32,
        "gender": "male"
    }
]
```


### Get One
#### Request Url : /teacher/{id}
#### Request method : GET
#### Request Body : null
#### Response Body : 
```
{
    "name": "Paul",
    "age": 32,
    "gender": "male"
}
```


### Add One
#### Request Url : /teacher
#### Request method : PUT
#### Request Body : 
```
    {
        "name": "tbtb",
        "age": 32,
        "gender": "male"
    }
```
#### Response Body : 
```
success

```


### Delete One
#### Request Url : /teacher/{id}
#### Request method : DELETE
#### Request Body : null
#### Response Body : 
```
success

```

### Update One
#### Request Url : /teacher/{id}
#### Request method : POST
#### Request Body : 
```
    {
        "name": "coco",
        "age": 32,
        "vegetarianism": true
    }
```
#### Response Body : 
```
success

```
