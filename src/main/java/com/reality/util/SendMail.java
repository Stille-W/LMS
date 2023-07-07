package com.reality.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Properties;

@Controller
public class SendMail {

//    protect static final KeyPair keyPair = initKey();

    @GetMapping("/sendMail")
    public String sendMail(String title, String text, Model model) throws NoSuchAlgorithmException {
        model.addAttribute("etitle", title);
        model.addAttribute("text", text);
        return "sendMail";
    }

//    @GetMapping("/sendMail")
//    public String sendMail(String title, String text, Model model) throws NoSuchAlgorithmException {
//        String publicKey = transPublicKey();
//        model.addAttribute("publicKey", publicKey);
//        model.addAttribute("title", title);
//        model.addAttribute("text", text);
//        return "sendmail";
//    }

//    @PostMapping("/sendMail")
//    public String send(String sendTo, String mailAddress, String password, String title, String text, Model model) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
//        String decryptedPassword = new String(splitDecrypt(password, cipher)).replaceAll("[^a-zA-Z0-9`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\\\-]", "");
//
//        try {
//            submit(mailAddress, decryptedPassword, sendTo, title, text);
//        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("stat", "sendFail");
//            return "error";
//        }
//        model.addAttribute("stat", "sendSuccess");
//        return "loading";
//    }

    private void submit(String mailAddress, String password, String to, String title, String text) throws Exception {
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
                        return new PasswordAuthentication(mailAddress, password);
                    }
                });

        // Email
        Message message = new MimeMessage(session);

        // sender email address
        InternetAddress ia = new InternetAddress(mailAddress);
        ia.setPersonal("");
        message.setFrom(ia);

        // receiver
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
        );

        // Email title
        message.setSubject(title);

        // Email text
        message.setText(text);

        // Email HTML
//        message.setContent(html, "text/html;charset=utf-8");

        // send email
        Transport.send(message);

        System.out.println("sending success!");

    }

    private static KeyPair initKey(){
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        generator.initialize(2048);

        return generator.generateKeyPair();
    }

//    private static String transPublicKey() {
//        PublicKey publicKey = keyPair.getPublic();
//
//        return Base64.encodeBase64String(publicKey.getEncoded());
//    }

    private static byte[] splitDecrypt(String password, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
        byte[] decyrptedPwByte = Base64.decodeBase64(password.getBytes(StandardCharsets.UTF_8));
        int length = decyrptedPwByte.length;
        int MAX_BLOCK_SIZE = 256;

        int offSet=0;
        byte[] result = {};
        byte[] cache = {};
        while (length>offSet) {
            if (length - offSet > MAX_BLOCK_SIZE) {
                cache = cipher.doFinal(decyrptedPwByte, offSet, MAX_BLOCK_SIZE);
                offSet += MAX_BLOCK_SIZE;
            } else {
                cache = cipher.doFinal(decyrptedPwByte, offSet, length - offSet);
                offSet = length;
            }
            result = Arrays.copyOf(result, offSet + cache.length);
            System.arraycopy(cache, 0, result, result.length-cache.length, cache.length);
        }
        return result;
    }
}
