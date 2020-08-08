package com.paymentinitiation.impelmentaion;


import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.paymentinitiation.model.PaymentDetails;


@Component
public class SignUtil {


  public byte[] convertObjectIntoBytes(PaymentDetails paymentDetails) throws Exception {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(bos);
      out.writeObject(paymentDetails);
      out.flush();
      return bos.toByteArray();
    } catch (Exception ex) {
      if (out != null) {
        out.close();
      }
      bos.close();
      throw new Exception(ex);
    }

  }

  public byte[] message(String data, PrivateKey privateKey) throws Exception {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    try {
      List<byte[]> list = new ArrayList<>();
      list.add(data.getBytes());
      list.add(sign(data, privateKey));
      oos.writeObject(list);
      return bos.toByteArray();
    } catch (Exception ex) {
      bos.close();
      oos.close();
      throw new Exception(ex);
    }


  }

  private byte[] sign(String data, PrivateKey privateKey) throws Exception {
    Signature dsa = Signature.getInstance("SHA1withRSA");
    dsa.initSign(privateKey);
    dsa.update(data.getBytes());
    return dsa.sign();
  }
}
