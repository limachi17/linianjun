package com.moxian.com.login.util;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.moxian.common.login.util.HMacMD5;
import com.moxian.common.login.util.HashGenerator;

public class HMacMD5Test {

    @Test
    public void test() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String input = "11";
        String encryptKey = HashGenerator.generateEncryptKey(input);
        String originalToken = "a7d9496ab0522f492401b1e9915822e7";//HashGenerator.generateToken(input);
        
        String encryptToken = HMacMD5.encrypt(originalToken, encryptKey);
        
        System.out.print("encrypt Token:" + encryptToken);
        
    }

}
