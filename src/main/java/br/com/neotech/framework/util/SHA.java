package br.com.neotech.framework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.log4j.Log4j;

@Log4j
public final class SHA {

    private SHA() {
    }

    public static String valueSHA192(String valor) {
        try {
            return valueOf("SHA-192", valor);
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
            return null;
        }
    }

    public static String valueSHA512(String valor) {
        try {
            return valueOf("SHA-512", valor);
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
            return null;
        }
    }

    public static String valueOf(String valor) {
        try {
            return valueOf(null, valor);
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
            return null;
        }
    }

    public static String valueOf(String algoritmo, String valor) throws NoSuchAlgorithmException {

        String alg = algoritmo == null ? "SHA-256" : algoritmo;

        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            BigInteger hash = new BigInteger(1, md.digest(valor.getBytes("UTF-8")));
            return hash.toString(16);
        } catch(UnsupportedEncodingException ex) {
            log.error(ex);
            return null;
        }

    }
}
