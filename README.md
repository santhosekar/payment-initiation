# Payment -initiation


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Payment-initiation service help to  validate the below things:

   - 1.	White listed certificates validation 
   - 2.	Signature validation
   - 3.	Request validation
   
  

### Requirements

-Gradle

-IntelliJ

### Run the application

 -  First gradle build will make common library to be compiled and ready to use.
  - Second, gradle bootRun will make the application to run on port 8080
   

### Testing the application

  - Via postman:
  
  example: 
  -  X-Request-Id - 29318e25-cebd-498c-888a-f77672f66449
  
   -   Signature-Certificate: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlDeXpDQ0FiT2dBd0lCQWdJRUh4eXhhREFOQmdrcWhraUc5dzBCQVFzRkFEQVdNUlF3RWdZRFZRUURFd3RUDQpZVzVrWW05NExWUlFVREFlRncweU1EQTRNVE14TlRJNU5ERmFGdzB5TVRBNE1UTXhOVEk1TkRGYU1CWXhGREFTDQpCZ05WQkFNVEMxTmhibVJpYjNndFZGQlFNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDDQpBUUVBcytEWmZnUG9Ec3RZY1Q0dndZeGhPa0FNQVhudDhsMWR6RTVZSTFqRmpyMCtHT05ycUxTVXE3cGFEa0JCDQo5d1RMbU9uZjlJdXVSek1GalI5aS9NUG5GK3UyU0Z5UW9lcnMxaVArTXErbFN5TXNBWCtrQk9BeEhLc00ycC84DQoveGdIcFhXSDFHNStPaWpTUGZFZmxOdFJGK25ITERFTVVsM2JSUzBmeEhsZjl3TEkxeGlLUWZ6bVA1eldENHM0DQp6djlnZ2JuQWdyWGNvbG8weForRDJEYlFrNms2ZERrQTR6OTFNS1Z5Sk9CRmZrWE0zLzhBalVTS0J3a3RUeWNyDQpVRGozWlBsRnhQMi8wNU5oenRFY2JjNk5Xc1plUG9NT3FqTkdwenNheGMrNTFZQ0Q3U3A2OHVNUEd2UjExNUwzDQphb3IwK1Flb1BkLzdiMzBLOVBWcnd2aldtd0lEQVFBQm95RXdIekFkQmdOVkhRNEVGZ1FVSUpXTnlOOFI1UDlHDQpxbU5XNzZuSnhOMnZJM1F3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQUZ0cGpJMWk1K1VvZDJmNnhqK01NQlM3DQpLeUwyd0ZtSE5lYmI3UGhxbFoxbHoydEV6aEkya0VCa3U1SEpha2Y0UTdzNUN6cnNqelkrOWxwcXFGUVVtT0FJDQpMNlFqTHAzQVNhbG14U2dPUlh0M1NNNmJXS2FEbHF4d2lBSHFzUUJqTnpVZUNNako5SHFHRHBPMEdxTHNSa3hnDQpVRUliNVpMRXdFTCtGYkZpdUtSZ3ljRjFTS3o0dis4bXZ0aFUrNVU2ZmJjTTU4UmZYMk9laVZ0YytOTEJUbGgvDQp4bU9wV3FWa0xNWWhHRFZCejVZVklqYnFMM3BRTUI0UUNyNTA1UjFrbmFaY0hQVVV0Zkdjbk16L25Db0FPVWlZDQpRbkY2VWliQVZwNWVjN0JpeERrV0dZa3lVdzFmT3gzTHk0RmhSeWtLTXdKc2NDcW1jNUFtWlFDSU1WNHRLU1U9DQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tDQo=
  
-  Signature:
   rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAACdwQAAAACdXIAAltCrPMX+AYIVOACAAB4cAAAABYxMjMtMTIzLTQ1NixbQkA1YTEwNDExdXEAfgACAAABAGgIKnaNm0A0ANX3jWqFL4VqCHDeua/NjENvwwdzJ4JC13rUV4Uaz09m3IjWM7/oi2g1iTCQodqtinhExi4daSUYZw5tts+IfZ0beUfetKbrBdzqE2XYeoW7L5kTffrSCZDA6ATJ36ky109UyzjsatCISIXpoG/+HZUIed+ss3mLfHEJCuRBFK4IJeJw7ygeKrmg09cBfj49YHdhtM/2gcmf5mTPWAGth2AgF5daNVbE4HnMb8JuNw+s9cScq/WunLzEei4yfMOymz8FaAi15T6DCE1Fr32O0GEersMp+EsM/y7WZX+9MBxSMT7o+Hy8bcKYm4cOe0zRn1T0ijV7ZQl4
   
 - request body:
    {"debtorIBAN":"NL02RABO0000001555","creditorIBAN":"NL94ABNA1008270121","amount":"1.00"}
  
  
  
