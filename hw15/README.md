## Homework 15

### Spring Integration app for Skynet Laboratory

#### Request examples:

POST to order constructing terminators

```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"order":[{"type":"T_1000","quantity":1},{"type":"T_800","quantity":3}]}' \
  http://localhost:8080/terminator
  
Response: {"orderId":"f3ff0fe3-2c98-4cd6-a61f-04022de31938"}
```

GET to find constructed terminators by order id

```
curl --request GET http://localhost:8080/terminator/f3ff0fe3-2c98-4cd6-a61f-04022de31938
```