package org.wangfy.dev.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Cryptography {
	protected final Log logger = LogFactory.getLog(this.getClass());

	public static final String ALGORITHM_MD5 = "MD5";

	public static final String ALGORITHM_SHA_1 = "SHA-1";

	public static final String ALGORITHM_DSA = "DSA";

	public static final String ALGORITHM_DH = "DH";
	
	public static final String ALGORITHM_DES = "DES";

	private String msg = "Hello, wangfy!";

	// ===============================================================
	/**
	 * 
	 */
	public void showProviders() {
		Provider[] csps = Security.getProviders();
		for (int i = 0; i < csps.length; i++) {
			this.logger.info(csps[i]);
		}
	}

	/**
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public void showMD5() throws NoSuchAlgorithmException, UnsupportedEncodingException {

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(msg.getBytes());
			byte[] value = md5.digest();
			this.logger.info(value);

	}

	/**
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	public void showSHA1() throws NoSuchAlgorithmException {

			MessageDigest sha1 = MessageDigest.getInstance(ALGORITHM_SHA_1);
			sha1.update(msg.getBytes());
			byte[] value = sha1.digest();
			this.logger.info(value);

	}

	/**
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	public void showDSA() throws NoSuchAlgorithmException {
		// 1024-bit Digital Signature Algorithm(DSA) key pairs

			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM_DSA);
			keyGen.initialize(1024);
			KeyPair keypair = keyGen.genKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();

	}

	public void showDH() throws NoSuchAlgorithmException {

			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM_DH);
			keyGen.initialize(576);
			KeyPair keypair = keyGen.genKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();

	}

	public void showSignature() throws Exception{
		//

			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM_DSA);
			keyGen.initialize(1024);
			KeyPair keypair = keyGen.genKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();
			//

			byte[] data = msg.getBytes();
			Signature dsig = Signature.getInstance(privateKey.getAlgorithm());
			dsig.initSign(privateKey);
			dsig.update(data);
			byte[] value = dsig.sign();
			this.logger.info(value);
	}
	
	public void showDES() throws Exception{

			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM_DES);
			keyGen.init(56);
			Key key = keyGen.generateKey();
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] enbytes = cipher.doFinal(msg.getBytes());
			String enctyptMsg = new String(enbytes);
			this.logger.info("enctyptMsg: "+enctyptMsg);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] debytes = cipher.doFinal(enbytes);
			String dectyptMsg = new String(debytes);
			this.logger.info("dectyptMsg: " + dectyptMsg);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Cryptography ctyprography = new Cryptography();
		ctyprography.showProviders();
		ctyprography.showMD5();
//		ctyprography.showSHA1();
//		ctyprography.showDSA();
//		ctyprography.showDH();
//		ctyprography.showSignature();
//		ctyprography.showDES();
	}

}
