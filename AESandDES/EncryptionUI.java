package org.cloudbus.cloudsim.examples;

import javax.swing.*;
import java.awt.*;
import javax.crypto.SecretKey;

public class EncryptionUI extends JFrame {
    private JTextArea inputTextArea, resultTextArea;
    private JButton encryptButton;

    public EncryptionUI() {
        setTitle("AES vs DES Encryption");
        setSize(600, 400);
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
                JOptionPane.showMessageDialog(this, "Enter a message.");
                return;
            }

            int size = input.getBytes().length;

            SecretKey aesKey = AesUtils.generateAESKey();
            long aesStart = System.nanoTime();
            String aesEncrypted = AesUtils.encryptAES(input, aesKey);
            String aesDecrypted = AesUtils.decryptAES(aesEncrypted, aesKey);
            long aesEnd = System.nanoTime();
            long aesTime = (aesEnd - aesStart) / 1_000_000;

            SecretKey desKey = DesUtils.generateDESKey();
            long desStart = System.nanoTime();
            String desEncrypted = DesUtils.encryptDES(input, desKey);
            String desDecrypted = DesUtils.decryptDES(desEncrypted, desKey);
            long desEnd = System.nanoTime();
            long desTime = (desEnd - desStart) / 1_000_000;

            resultTextArea.setText("Input Size: " + size + " bytes\n\n" +
                    "--- AES ---\nEncrypted: " + aesEncrypted + "\nDecrypted: " + aesDecrypted + "\nTime: " + aesTime + " ms\n\n" +
                    "--- DES ---\nEncrypted: " + desEncrypted + "\nDecrypted: " + desDecrypted + "\nTime: " + desTime + " ms\n\n" +
                    (aesTime < desTime ? "AES is faster." : "DES is faster."));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EncryptionUI().setVisible(true));
    }
}
