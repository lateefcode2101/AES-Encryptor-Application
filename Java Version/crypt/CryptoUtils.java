package crypt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

		} catch (NoSuchAlgorithmException | IllegalBlockSizeException | IOException ex) {
			System.out.print("printstacktrace= \n");
			ex.printStackTrace();
			throw new CryptoException("Error encrypting/decrypting file " + ex.getMessage() + "\n", ex);

		} catch (InvalidKeyException | BadPaddingException | NoSuchPaddingException ike) {
			System.out.print("Invalid key entered = " + key);
			JFrame invalidframe = new JFrame("Alert! ");
			JLabel alertlabel = new JLabel("Key entered is incorrect! Please enter valid key...");
			JButton ok = new JButton("Ok");
			invalidframe.add(alertlabel);
			invalidframe.add(ok);
			invalidframe.setBounds(20, 30, 150, 150);
			alertlabel.setBounds(20, 30, 100, 100);
			ok.setBounds(60, 60, 60, 30);
			invalidframe.setLayout(null);
			invalidframe.setVisible(true);
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					invalidframe.setVisible(false);

				}
			});

		}
	}
}