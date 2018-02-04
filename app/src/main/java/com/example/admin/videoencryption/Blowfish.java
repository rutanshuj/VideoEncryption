package com.example.admin.videoencryption;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

 public class Blowfish extends AppCompatActivity {

    private static final String ALGORITHM = "Blowfish";
    private static String keyString = "DesireSecretKey";

    public static void encrypt(File inputFile, File outputFile)
            throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile);
        System.out.println("File encrypted successfully!");
    }

    public static void decrypt(File inputFile, File outputFile)
            throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile);
        System.out.println("File decrypted successfully!");
    }

    private static void doCrypto(int cipherMode, File inputFile,
                                 File outputFile) throws Exception {

        Key secretKey = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(cipherMode, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();

    }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

             File inputFile = new File("/storage/emulated/0/DCIM/Videos/wildlife.mp4");
             File encryptedFile = new File( "/storage/emulated/0/DCIM/Videos/encrypt.mp4");

             File decryptedFile = new File( "/storage/emulated/0/DCIM/Videos/decrypt.mp4");

             try {
                 Blowfish.encrypt(inputFile, encryptedFile);
                 Blowfish.decrypt(encryptedFile, decryptedFile);
             } catch (Exception e) {
                 e.printStackTrace();
             }

     }
}
