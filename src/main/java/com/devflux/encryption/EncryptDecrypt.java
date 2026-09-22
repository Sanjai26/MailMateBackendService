package com.devflux.encryption;


public class EncryptDecrypt
{

	public static void main(String[] args)
	{

		try
		{

			if (args.length < 2)
			{

				System.out.println("Usage:");

				System.out.println("encryptDecrypt encrypt \"text\"");

				System.out.println("encryptDecrypt decrypt \"encrypted-text\"");

				return;
			}

			String operation = args[0];
			String data = args[1];

			if (operation.equalsIgnoreCase("encrypt"))
			{

				String encrypted = EncryptionUtil.encrypt(data);

				System.out.println(encrypted);

			}
			else if (operation.equalsIgnoreCase("decrypt"))
			{

				String decrypted = EncryptionUtil.decrypt(data);

				System.out.println(decrypted);

			}
			else
			{

				System.out.println("Invalid operation.");
			}

		}
		catch (Exception e)
		{

			System.out.println("Error: " + e.getMessage());
		}
	}
}