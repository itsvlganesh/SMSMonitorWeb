package com.amg.servicemgmt.common;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JaspytPasswordEncryptor {
	
	public static void main(String[] args) {
		
		String jaspytEncryptionKey = "12345678";		
		String jaspytAlgorithm = "PBEWithMD5AndDES";
		String passwordToEncrypt = "KG&sas2sv";
		
		System.out.println("Password to encrypt: "+passwordToEncrypt);
		
		String encryptedPassword = getEncryptedString(jaspytEncryptionKey, jaspytAlgorithm, passwordToEncrypt);		
		System.out.println("Encrypted password: "+ encryptedPassword);
		String decryptedPassword = getDecryptedString(jaspytEncryptionKey, jaspytAlgorithm, encryptedPassword);
		System.out.println("Decrypted password: "+decryptedPassword);
		
		
	}

	/*
	 * Java services use the following:
	 * jaspytEncryptionKey:javahonk_key                 
	 * jaspytAlgorithm:PBEWithMD5AndDES
	 */
	
	public static String getEncryptedString(
			String jaspytEncryptionKey,
			String jaspytAlgorithm,
			String passwordToEncrypt){
		
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(jaspytEncryptionKey);                     
		encryptor.setAlgorithm(jaspytAlgorithm);
		String encryptText = encryptor.encrypt(passwordToEncrypt);
		return encryptText;
	}
	
	public static String getDecryptedString(
			String jaspytEncryptionKey,
			String jaspytAlgorithm,
			String passwordToDecrypt){
		
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(jaspytEncryptionKey);                     
		encryptor.setAlgorithm(jaspytAlgorithm);
		String decryptText = encryptor.decrypt(passwordToDecrypt);
		return decryptText;
	}
}
