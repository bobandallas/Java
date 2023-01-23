# 1. User

### Get All
#### Request Url : /user
#### Request method : GET
#### Response Code : 200
#### Response Body : 
```
{
    "data": [
        {
            "firstName": "A",
            "lastName": "B",
            "middleName": "C",
            "dob": "01/10/10"
        },
        {
            "firstName": "A",
            "lastName": "B",
            "middleName": "C",
            "dob": "01/10/10"
        }
    ]
}
```




### Add One
#### Request Url : /user
#### Request method : POST
#### Response Code : 201 / 500
#### Request Body : 
```

    {
        "provider" : {
            "firstName" : "A",
            "lastName" : "B",
            "middleName" : "C",
            "dob" : "01/10/10"
         }
    }
```




