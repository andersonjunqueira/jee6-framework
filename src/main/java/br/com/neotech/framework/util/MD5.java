package br.com.neotech.framework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.log4j.Log4j;

@Log4j
public final class MD5 {

    private MD5() {
    }

    public static String valueOf(String valor) {

        try {
            String sen = "";
            MessageDigest md = null;

            md = MessageDigest.getInstance("MD5");

            BigInteger hash = new BigInteger(1, md.digest(valor.getBytes("UTF-8")));
            sen = hash.toString(16);

            return sen;

        } catch(UnsupportedEncodingException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

}
