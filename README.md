# customer-contracts 

#### Created by Bhagyashri Bhutada

This is coding excercise for Homify. This is a Java REST server I created using below technologies:

#### 1] Springboot - Used this because its easy to build web application and its easy to integrate with Spring eco system
#### 2] Maven - Great for managing dependencies and building tool.
#### 3] Jsonpath - For managing JSON objects
#### 4] Java 8 

Also, I have used POJO as in-memory database per mentioned in the task and used MAP to store customer/contracts data. The reason for using MAP is for faster searching of record (complexity O(1)).

## Folder structure:

### 1] com.customer.contracts 
   - It contains controller (with REST endpoints) and main application class.
### 2] com.customer.contracts.models
   - It contains Customer and Contract model POJO's
### 3] com.customer.contracts.repository
   - It contains a in-memory mock database class which has API methods for CRUD operations and are consumed via controller methods.

The application has below REST endpoints as mentioned in the coding challenge but with few error corrections:

## 1] POST <Host>/customerservice/customer 
   This API is used to create customer:
   POST body as below:
     curl -H "Content-Type: application/json" -X POST -d '{"email":<email>, "fullName":"name"}' http://localhost:8080/customerservice/customer
     
   Response as below:
    {
        "email": <email>,
        "fllName": <fullName>,
        "id" : <record Id>
    }
    
    Error handling:
    1] Throws ad Request 400 error if any of the input fields are missing
    
 ## 2] POST <Host>/customerservice/contract
     This API is used to create a contract and associate it to a particular customer
    an example POST body is as below:
    curl -H "Content-Type: application/json" -X POST -d '{"customerId":<customerId>, "monthlyRevenue": 22.5, "startDate":"2017-05-01T16:05:07.765Z", "type":"loanType"}' http://localhost:8080/customerservice/contract
    
    RESPONSE body
     {"id": <contract Id>, "customerId":<customerId>, "monthlyRevenue": 22.5, "startDate":"2017-05-01T16:05:07.765Z", "type":"loanType"}
     
     Eror Handling:
     1] If "customerId" does not exist then HTTP error response is sent along with message "Customer ID does not exist"
    2] Throws ad Request 400 error if any of the input fields are missing
     
     
 ## 3] GET <host>/customerservice/customer/{customerId}    
      This API is to get customer by Id along with all its contracts:
        RESPONSE body: 
      {
        "email":"<email>",
        "fullName":"<name>",
        "id":1493659094391,
        "contracts":[
          {
            "customerId":<id>,
            "id":<id1>,
            "monthlyRevenue":<revenue>,
            "startDate":<startDate>,
            "type":"<type>"
          },
          {
            "customerId":<id>,
            "id":<id2>,
            "monthlyRevenue":<revenue>,
            "startDate":<startDate>,
            "type":"<type>"
          }
        ]
      }
     Error Handling:
     1] If "customerId" does not exist then HTTP response 400 is sent along with message "Customer ID does not exist"
     
## 4] GET <host>/customerservice/contractrevenue/customer/{customerId}
    This API returns sum of all contract revenues of the given customer Id:
    RESPONSE body: <number>
    
    Error handling:
    1] If "customerId" does not exist then HTTP response 400 is sent along with message "Customer ID does not exist"
     
## 5] Get <host>/customerservice/contractrevenue/{type}
     This API returns sum of all contract revenues of given type across customers:
     RESPONSE body: <Number>
     
     
     
     
