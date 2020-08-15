# Payment -initiation


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Payment-initiation service help to  validate the below things:

   - 1.	White listed certificates validation 
   - 2.	Signature validation
   - 3.	Request validation
   
  

### Requirements

-Gradle

-IntelliJ

-Java8

### Run the application

 -  First gradle build will make common library to be compiled and ready to use.
  - Second, gradle bootRun will make the application to run on port 8080
   

### Testing the application

  - Via postman:
  
  URL:
  
 -  POST: localhost:8080/initiate-payment
  
  example: 
  -  X-Request-Id - 29318e25-cebd-498c-888a-f77672f66449
  
   -   Signature-Certificate: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlDeXpDQ0FiT2dBd0lCQWdJRUh4eXhhREFOQmdrcWhraUc5dzBCQVFzRkFEQVdNUlF3RWdZRFZRUURFd3RUDQpZVzVrWW05NExWUlFVREFlRncweU1EQTRNVE14TlRJNU5ERmFGdzB5TVRBNE1UTXhOVEk1TkRGYU1CWXhGREFTDQpCZ05WQkFNVEMxTmhibVJpYjNndFZGQlFNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDDQpBUUVBcytEWmZnUG9Ec3RZY1Q0dndZeGhPa0FNQVhudDhsMWR6RTVZSTFqRmpyMCtHT05ycUxTVXE3cGFEa0JCDQo5d1RMbU9uZjlJdXVSek1GalI5aS9NUG5GK3UyU0Z5UW9lcnMxaVArTXErbFN5TXNBWCtrQk9BeEhLc00ycC84DQoveGdIcFhXSDFHNStPaWpTUGZFZmxOdFJGK25ITERFTVVsM2JSUzBmeEhsZjl3TEkxeGlLUWZ6bVA1eldENHM0DQp6djlnZ2JuQWdyWGNvbG8weForRDJEYlFrNms2ZERrQTR6OTFNS1Z5Sk9CRmZrWE0zLzhBalVTS0J3a3RUeWNyDQpVRGozWlBsRnhQMi8wNU5oenRFY2JjNk5Xc1plUG9NT3FqTkdwenNheGMrNTFZQ0Q3U3A2OHVNUEd2UjExNUwzDQphb3IwK1Flb1BkLzdiMzBLOVBWcnd2aldtd0lEQVFBQm95RXdIekFkQmdOVkhRNEVGZ1FVSUpXTnlOOFI1UDlHDQpxbU5XNzZuSnhOMnZJM1F3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQUZ0cGpJMWk1K1VvZDJmNnhqK01NQlM3DQpLeUwyd0ZtSE5lYmI3UGhxbFoxbHoydEV6aEkya0VCa3U1SEpha2Y0UTdzNUN6cnNqelkrOWxwcXFGUVVtT0FJDQpMNlFqTHAzQVNhbG14U2dPUlh0M1NNNmJXS2FEbHF4d2lBSHFzUUJqTnpVZUNNako5SHFHRHBPMEdxTHNSa3hnDQpVRUliNVpMRXdFTCtGYkZpdUtSZ3ljRjFTS3o0dis4bXZ0aFUrNVU2ZmJjTTU4UmZYMk9laVZ0YytOTEJUbGgvDQp4bU9wV3FWa0xNWWhHRFZCejVZVklqYnFMM3BRTUI0UUNyNTA1UjFrbmFaY0hQVVV0Zkdjbk16L25Db0FPVWlZDQpRbkY2VWliQVZwNWVjN0JpeERrV0dZa3lVdzFmT3gzTHk0RmhSeWtLTXdKc2NDcW1jNUFtWlFDSU1WNHRLU1U9DQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tDQo=
  
-  Signature:
   rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAACdwQAAAACdXIAAltCrPMX+AYIVOACAAB4cAAAAGQyOTMxOGUyNS1jZWJkLTQ5OGMtODg4YS1mNzc2NzJmNjY0NDk1OTcwY2I1MmYwNDMyZWI2NzBmMDM5YTAzMDI0OGYxMmU0MTkxOTE1NzViMjMwNTllOWEzMWQ3YWFlOTY0NDJhdXEAfgACAAABAHDSHFonyq8b9GF9nO2sK9NxfjkAnazw2QUQiNO/2i3R7bJdteM1pfO5RlkbIs6DaG7cHWJnQVmW7BpBey1s2ixo3QvqgAZ8RQ31TF9mJZcfHz8tMbQj7BF3e/Dr36WKz1jtV+Xa7kJtLO2Thlhjk5S2ca7wkvrYZhs9ecC6Xw9Rqk8m+5ws1sdhqHGRFhwiKeeowP/0wd7VzV09j3aVMZgcnTfws/urlAX6zuMM6hyumK1ENGTSpfDSJY8xNvJ6lPJ7dRlgpyQ3KG/8c0mLuNxkn61jWRMPxglT7bbzAvkAlq0hyt6zD4dH/QrBjz3NxqI89psfXRMM65iPaC3AulV4
   
 - request body:
    {"debtorIBAN":"NL02RABO0000001555","creditorIBAN":"NL94ABNA1008270121","amount":"1","currency":"EUR"}
  
  ## Behind the logic

```python
----------------- Decode signature--------------------------------
  byte[] byteSignature = Base64.getDecoder().decode(signature);
        objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteSignature));
        certificateList = (List<byte[]>) objectInputStream.readObject();
------------------------- Verify signature -------------------
verifySignature(certificateList.get(0), certificateList.get(1), publicKey)


---------  Compare the hash with request body-------

    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] payLoadByte = md.digest(convertObjectIntoBytes(paymentDetails));
    String paymentHex = paymentId + bytesToHex(payLoadByte);
    if (paymentHex.equalsIgnoreCase(encryptedHash)) {
      return true;
    } else {
      throw new InvalidCertificateException();
    }

refer: CertificateValidationImplTest class.



```

  
  
