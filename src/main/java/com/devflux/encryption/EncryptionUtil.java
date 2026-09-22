package com.devflux.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil
{

	private static final String ALGORITHM = "AES/GCM/NoPadding";

	private static final byte[]	SECRET_KEY	= "aGWD8Au8oLVsijPHAVWo1LxrWfZ24cze".getBytes(StandardCharsets.UTF_8);

	private static final int	IV_LENGTH	= 12;

	private static final int	TAG_LENGTH	= 128;

	public static String encrypt(String plainText) throws Exception
	{

		// Generate random IV
		byte[] iv = new byte[IV_LENGTH];

		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);

		SecretKeySpec key = new SecretKeySpec(SECRET_KEY, "AES");

		Cipher cipher = Cipher.getInstance(ALGORITHM);

		GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);

		cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

		byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

		// Combine IV + encrypted data
		byte[] result = new byte[IV_LENGTH + encrypted.length];

		System.arraycopy(iv, 0, result, 0, IV_LENGTH);

		System.arraycopy(encrypted, 0, result, IV_LENGTH, encrypted.length);

		return Base64.getEncoder().encodeToString(result);
	}

	public static String decrypt(String encryptedText) throws Exception
	{

		byte[] combined = Base64.getDecoder().decode(encryptedText);

		// Extract IV
		byte[] iv = new byte[IV_LENGTH];

		System.arraycopy(combined, 0, iv, 0, IV_LENGTH);

		// Extract encrypted data
		byte[] encrypted = new byte[combined.length - IV_LENGTH];

		System.arraycopy(combined, IV_LENGTH, encrypted, 0, encrypted.length);

		SecretKeySpec key = new SecretKeySpec(SECRET_KEY, "AES");

		Cipher cipher = Cipher.getInstance(ALGORITHM);

		GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);

		cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

		byte[] decrypted = cipher.doFinal(encrypted);

		return new String(decrypted, StandardCharsets.UTF_8);
	}
}