package br.com.neotech.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.log4j.Log4j;

import org.apache.commons.codec.binary.Base64;

import br.com.neotech.framework.excecao.CriptoException;

@Log4j
public final class AES {

    private String encryptionKey;

    public AES(String encryptionKey) throws CriptoException {

        if(encryptionKey == null || encryptionKey.length() != 16) {
            int len = encryptionKey == null ? 0 : encryptionKey.length();
            throw new CriptoException("encriptionKey MUST HAVE 16 bytes (actual " + len + " bytes)");
        }

        this.encryptionKey = encryptionKey;
    }

    public String encrypt(String plainText) throws CriptoException {

        try {

            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.encodeBase64String(encryptedBytes);

        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);

        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);
        }

    }

    public String decrypt(String encrypted) throws CriptoException {

        try {

            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(plainBytes);

        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);

        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);
        }

    }

    private Cipher getCipher(int cipherMode) throws CriptoException {

        try {

            String encryptionAlgorithm = "AES";
            SecretKeySpec keySpecification = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
            Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
            cipher.init(cipherMode, keySpecification);
            return cipher;

        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);

        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);

        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);

        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            throw new CriptoException(e);
        }
    }

}
