package com.reality.util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class SendMailTest {
	
	private static final KeyPair keyPair = initKey();
	
	public static void main(String[] args) throws Exception {
		
		String password = "Phoebe832";
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] ciphertext = cipher.doFinal(password.getBytes());
        // encode base64
        password = java.util.Base64.getEncoder().encodeToString(ciphertext);

        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        String decryptedPassword = new String(splitDecrypt(password, cipher)).replaceAll("\\W","");
        SendMailTest sendMail = new SendMailTest();
        sendMail.submit("ikutei.ou@goodsun-hd.com", decryptedPassword, "ikutei.ou@goodsun-hd.com", "title", "text");
        System.out.println("success!");
		
		
		
		
//		String personal = ""; // sender name
//		String to = "ikutei.ou@goodsun-hd.com"; // send to who
//		String title = "Test"; // title
//		// 内容
//		String html = "<h1>SendMail Test</h1>";
//
//		SendMailTest sendMailTest = new SendMailTest();
//		sendMailTest.submit(personal, to, title, html);
	}
	
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
        ia.setPersonal("name");
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

    private static String transPublicKey() {
        PublicKey publicKey = keyPair.getPublic();

        return Base64.encodeBase64String(publicKey.getEncoded());
    }

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

//	public void submit(String personal, String to, String title, String html) throws Exception {
//
//		final String office365 = "ikutei.ou@goodsun-hd.com";
//		final String office365Password = "";
//
//		// smpt 設定
//		Properties prop = new Properties();
//		prop.put("mail.smtp.host", "smtp.office365.com");
//		prop.put("mail.smtp.port", "587");
//		prop.put("mail.smtp.auth", "true");
//		prop.put("mail.smtp.starttls.enable", "true"); // TLS
//
//		// communication with smtp ;
//		Session session;
//		session = Session.getInstance(prop, new javax.mail.Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(office365, office365Password);
//			}
//		});
//
//		// Email
//		Message message = new MimeMessage(session);
//
//		// sender email address
//		InternetAddress ia = new InternetAddress("ikutei.ou@goodsun-hd.com");
//		ia.setPersonal(personal);
//		message.setFrom(ia);
//
//		// receiver
//		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//		// Email title
//		message.setSubject(title);
//
//		// Email text
//		// message.setText("Dear Mail Crawler,\n\n Please do not spam my email!");
//
//		// Email HTML
//		message.setContent(html, "text/html;charset=utf-8");
//
//		// send email
//		Transport.send(message);
//
//		System.out.println("信件發送成功 !");
//
//	}

}
