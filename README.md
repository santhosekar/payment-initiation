# Payment -initiation


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Payment-initation service help to  validate the below things:

   - 1.	White listed certificates validation 
   - 2.	Signature validation
   - 3.	Request validation
   
   Note:
   - Mutual SSL has been implemented, so it has exposed in https port.Payment initiation service jks file in resource folder.

### Requirements

-Gradle

-IntelliJ

### Run the application

 -  First gradle build will make common library to be compiled and ready to use.
  - Second, gradle bootRun will make the application to run on port 8443 (note it secure port https)
   

### Testing the application

  - dowload the below payment-initiation-client project 
  - https://github.com/santhosekar/payment-initiation-client.git
  - run the application with above run procedure it will make the application run on 8090 (this also secure port https)
  - 
  
 hit the below url:

https://localhost:8090/payment

(Note:Above the url navigate to simple html page with minimal validation like empty check only because test the behavior of actual  payment -initation service.)
  
