package crypt;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class CryptoUtilsTest {
	public static File inputFile;
	static String key;
	static Boolean dec = false, enc = false;
	static int enc_counter = 0;
	public static JFrame frame = new JFrame("Crypt_AES");
	public static JFrame keyframe = new JFrame("Provide a KEY... ");
	public static Image icon = Toolkit.getDefaultToolkit().getImage("favicon.png");

	public static void main(String[] args) throws UnsupportedEncodingException {
		String fileselected = "  ";

		key = " ";

		frame.setResizable(false);
		keyframe.setResizable(false);

		frame.setIconImage(icon);

		JButton encrypt_button = new JButton("Encrypt");
		JButton decrypt_button = new JButton("Decrypt");
		JLabel filelabel = new JLabel(" Selected file:  -");

		JLabel ke = new JLabel(" Specify your 16-char key : ");
		JTextField keyfield = new JTextField();
		keyfield.setColumns(16);

		JButton Ok = new JButton("OK");
		JLabel klabel = new JLabel("Select a file operation to perform: ");
		JLabel alglabel = new JLabel("Algorithm used: ");
		JRadioButton aesradio = new JRadioButton("AES ");

		filelabel.setBounds(8, 70, 500, 10);
		klabel.setBounds(40, 5, 200, 30);
		encrypt_button.setBounds(20, 40, 100, 50);
		decrypt_button.setBounds(150, 40, 100, 50);
		ke.setBounds(15, 10, 190, 20);
		keyfield.setBounds(20, 30, 150, 20);
		Ok.setBounds(200, 20, 60, 30);
		alglabel.setBounds(320, 20, 120, 20);
		aesradio.setBounds(420, 20, 90, 20);

		frame.add(klabel);
		frame.add(encrypt_button);
		frame.add(decrypt_button);

		keyframe.add(keyfield);
		keyframe.add(filelabel);
		keyframe.add(aesradio);
		keyframe.add(alglabel);
		keyframe.add(ke);
		keyframe.add(Ok);

		frame.setBounds(400, 200, 290, 150);
		keyframe.setBounds(300, 300, 500, 150);

		frame.setLayout(null);
		keyframe.setLayout(null);

		frame.setVisible(true);

		JFileChooser fileChooser = new JFileChooser("");

		encrypt_button.getInputMap().put(KeyStroke.getKeyStroke("Enter"), "actionName");

		encrypt_button.getActionMap().put("actionName", new AbstractAction("actionName") {
			public void actionPerformed(ActionEvent evt) {
				System.out.println(evt);
			}
		});

		encrypt_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enc = true;
				dec = false;
				fileChooser.showOpenDialog(null);
				frame.setVisible(false);
				inputFile = fileChooser.getSelectedFile();
				if (inputFile != null) {
					frame.setVisible(false);
					keyframe.setVisible(true);
					filelabel.setText("Selected file from dir: " + inputFile.getAbsolutePath());
					aesradio.setSelected(true);
					keyfield.setFocusable(true);

				} else {
					frame.setVisible(true);
					keyfield.setFocusable(true);
				}

			}
		});
		Ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				key = keyfield.getText().toString();
				if (key.length() == 16) {
					try {
						if (enc)
							CryptoUtils.encrypt(key, inputFile, inputFile);
						else
							CryptoUtils.decrypt(key, inputFile, inputFile);
					} catch (CryptoException e1) {
						e1.printStackTrace();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}

				} else {
					JFrame keyerror = new JFrame("Key invalid");
					keyerror.setResizable(false);
					JLabel error = new JLabel("Key entered is of " + key.length() + ", specify 16 char KEY!");
					JButton k = new JButton("OK");
					error.setBounds(20, 12, 250, 20);
					k.setBounds(90, 40, 100, 30);
					keyerror.setBounds(300, 300, 280, 120);
					keyerror.add(error);
					keyerror.add(k);
					keyerror.setLayout(null);
					keyerror.setVisible(true);
					keyframe.setVisible(false);
					k.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							keyerror.setVisible(false);
							keyframe.setVisible(true);
						}
					});
				}
			}
		});

		decrypt_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				enc = false;
				dec = true;
				fileChooser.showOpenDialog(null);
				inputFile = fileChooser.getSelectedFile();
				if (inputFile != null) {
					frame.setVisible(false);
					keyframe.setVisible(true);
				} else
					frame.setVisible(true);

			}
		});

	}
}
