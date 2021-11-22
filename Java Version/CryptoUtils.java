package cry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils extends CryptoUtilsTest {
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";

	public static void encrypt(String key, File inputFile, File outputFile)
			throws CryptoException, MalformedURLException, IOException, URISyntaxException {
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}

	public static void decrypt(String key, File inputFile, File outputFile)
			throws CryptoException, MalformedURLException, IOException, URISyntaxException {
		doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}

	private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile)
			throws CryptoException, MalformedURLException, IOException, URISyntaxException {
		try {
			System.out.println("in doCrypto try1 = " + key.length());
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);

			System.out.println("in doCrypto try2");

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			System.out.println("in doCrypto try3" + cipherMode);

			cipher.init(cipherMode, secretKey);
			System.out.println("in doCrypto try4 " + cipherMode);

			FileInputStream inputStream = new FileInputStream(inputFile);
			System.out.println("in doCrypto try5 " + cipherMode);

			byte[] inputBytes = new byte[(int) inputFile.length()];
			System.out.println("in doCrypto try6" + inputBytes + " " + cipherMode);

			inputStream.read(inputBytes);
			System.out.println("in doCrypto try7 " + cipherMode);

			byte[] outputBytes = cipher.doFinal(inputBytes);
			System.out.println("in doCrypto try8 " + cipherMode);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			System.out.println("in doCrypto try9 " + cipherMode + " " + outputStream);

			outputStream.write(outputBytes);
			System.out.println("in doCrypto try10 " + cipherMode);

			inputStream.close();
			System.out.println("in doCrypto try11 " + cipherMode);

			outputStream.close();
			System.out.println("in doCrypto try12");

			keyframe.setVisible(false);
			frame.setVisible(true);

		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException ex) {
			throw new CryptoException("Error encrypting/decrypting file " + ex.getMessage(), ex);
		}
	}
}