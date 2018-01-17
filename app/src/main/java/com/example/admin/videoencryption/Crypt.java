package com.example.admin.videoencryption;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import static com.example.admin.videoencryption.Crypt.decrypt;
import static com.example.admin.videoencryption.Crypt.encrypt;

public class Crypt extends AppCompatActivity{
    //  Original file
    private static final String filePath = "/storage/emulated/0/DCIM/Videos/wildlife.mp4";
    //  Encrypted file
    private static final String outPath = "/storage/emulated/0/DCIM/Videos/encrypt1.mp4";
    //  Encrypted and decrypted files
    private static final String inPath = "/storage/emulated/0/DCIM/Videos/decrypt1.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypt);
        Button encryptButton = (Button) findViewById(R.id.main_encrypt);
        Button DecryptButton = (Button) findViewById(R.id.main_decrypt);
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    MyTask myTask = new MyTask();
                    myTask.execute();
                    Toast.makeText(getApplicationContext(), " Encryption complete ",
                            Toast.LENGTH_SHORT).show();
            }
        });

        DecryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DecryptTask decryptTask = new DecryptTask();
                    decryptTask.execute();
                    Toast.makeText(getApplicationContext(), " finished decrypting ",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Here is Both function for encrypt and decrypt file in Sdcard folder. we
     * can not lock folder but we can encrypt file using AES in Android, it may
     * help you.
     *
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */

    static void encrypt() throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {
        // Here you read the cleartext.
        FileInputStream fis = new FileInputStream(filePath);
        // This stream write the encrypted text. This stream will be wrapped by
        // another stream.
        FileOutputStream fos = new FileOutputStream(outPath);
        // Length is 16 byte
        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(),
                "AES");
        byte[] bt = "MyDifficultPassw".getBytes();
        Log.d("hello", String.valueOf(bt));
        SecretKeySpec blowfish = new SecretKeySpec("MyDifficultPassw1".getBytes(), "Blowfish");
        // Create cipher
        Cipher cipher = Cipher.getInstance("AES");


        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // Wrap the output stream
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        // Write bytes
        int b;
        byte[] d = new byte[8];
        while ((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        // Flush and close streams.
        cos.flush();
        cos.close();
        fis.close();
    }

    static void decrypt() throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {
        FileInputStream fis = new FileInputStream(outPath);
        FileOutputStream fos = new FileOutputStream(inPath);
        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(),
                "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while ((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }
        fos.flush();
        fos.close();
        cis.close();
    }

}
    class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            try {
                encrypt();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    class DecryptTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                decrypt();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }

            return "";
        }
    }