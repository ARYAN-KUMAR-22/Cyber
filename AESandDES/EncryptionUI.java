package org.cloudbus.cloudsim.examples.AESandDES;

import javax.swing.*;
import java.awt.*;
import javax.crypto.SecretKey;

public class EncryptionUI extends JFrame {
    private JTextArea inputTextArea, resultTextArea;
    private JButton encryptButton;

    public EncryptionUI() {
        setTitle("AES vs DES Encryption Comparison");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inputTextArea = new JTextArea(5, 40);
        resultTextArea = new JTextArea(10, 40);
        resultTextArea.setEditable(false);
        encryptButton = new JButton("Encrypt & Compare");

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createTitledBorder("Input Message"));
        top.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.add(encryptButton);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createTitledBorder("Results"));
        bottom.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        encryptButton.addActionListener(e -> performEncryption());
    }

    private void performEncryption() {
        try {
            String input = inputTextArea.getText();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a message to encrypt.");
                return;
            }

            SecretKey aesKey = AesUtils.generateAESKey();
            long aesStart = System.nanoTime();
            String aesEncrypted = AesUtils.encryptAES(input, aesKey);
            String aesDecrypted = AesUtils.decryptAES(aesEncrypted, aesKey);
            long aesTime = (System.nanoTime() - aesStart) / 1_000_000;

            SecretKey desKey = DesUtils.generateDESKey();
            long desStart = System.nanoTime();
            String desEncrypted = DesUtils.encryptDES(input, desKey);
            String desDecrypted = DesUtils.decryptDES(desEncrypted, desKey);
            long desTime = (System.nanoTime() - desStart) / 1_000_000;

            resultTextArea.setText("Input Size: " + input.getBytes().length + " bytes\n\n" +
                    "--- AES ---\nEncrypted: " + aesEncrypted + "\nDecrypted: " + aesDecrypted + "\nTime: " + aesTime + " ms\n\n" +
                    "--- DES ---\nEncrypted: " + desEncrypted + "\nDecrypted: " + desDecrypted + "\nTime: " + desTime + " ms\n\n" +
                    (aesTime < desTime ? "AES is faster." : "DES is faster."));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Encryption Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EncryptionUI().setVisible(true));
    }
}
