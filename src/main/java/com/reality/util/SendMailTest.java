package com.reality.util;

import javax.crypto.Cipher;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;


public class SendMailTest {
    public static void main(String[] args) throws Exception {
//        String personal = ""; // sender name
//        String to = "ikutei.ou@goodsun-hd.com"; // send to who
//        String title = "Test"; // title
//        // 内容
//        String html = "<h1>SendMail Test</h1>";
//
//        SendMailTest sendMailTest = new SendMailTest();
//        sendMailTest.submit(personal, to, title, html);

        // 生成 RSA keypair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 暗号化
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String plaintext = "Hello, world!";
        System.out.println("Plain text: " + plaintext);
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes());
        // encode base64
        String base64 = Base64.getEncoder().encodeToString(ciphertext);
        System.out.println("Base 64 Ciphertext: " + base64);

        // decode base64
        ciphertext = Base64.getDecoder().decode(base64);
        // decode
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedText = cipher.doFinal(ciphertext);
        System.out.println("Decrypted text: " + new String(decryptedText));
    }

    public void submit(String personal, String to, String title, String html) throws Exception {

        final String office365 = "ikutei.ou@goodsun-hd.com";
        final String office365Password = "";

        // smpt 設定
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.office365.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        // communication with smtp     ;
        Session session;
        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(office365, office365Password);
                    }
                });

        // Email
        Message message = new MimeMessage(session);

        // sender email address
        InternetAddress ia = new InternetAddress("ikutei.ou@goodsun-hd.com");
        ia.setPersonal(personal);
        message.setFrom(ia);

        // receiver
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
        );

        // Email title
        message.setSubject(title);

        // Email text
        //message.setText("Dear Mail Crawler,\n\n Please do not spam my email!");

        // Email HTML
        message.setContent(html, "text/html;charset=utf-8");

        // send email
        Transport.send(message);

        System.out.println("信件發送成功 !");

    }

}
