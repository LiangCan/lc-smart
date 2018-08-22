package com.sykj.uusmart.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class VivoSHA256Util {

    public static String encrytSHA256(String content, String secret) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF8"),
                    "HmacSHA256");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            byte[] digest = mac.doFinal(content.getBytes("UTF-8"));
            return Base64.encodeBase64URLSafeString(digest);
        } catch (Exception e) {
            throw new RuntimeCryptoException("加密异常");
        }
    }
}
