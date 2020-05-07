package com.example.encrypteddatabase;

import android.telephony.mbms.MbmsErrors;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;



public class SHA256 {
    //Constructor
    public SHA256(){}

    //No need for a main, I should just write a method --> hashPass

    public String hassPass (String unhashedPass) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(unhashedPass.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hashedPass = String.format("%064x", new BigInteger( 1, digest ));
        return hashedPass;
    }
}