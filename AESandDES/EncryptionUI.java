package org.cloudbus.cloudsim.examples.AESandDES;

import javax.swing.*;
import java.awt.*;
import javax.crypto.SecretKey;

public class EncryptionUI extends JFrame {
    private static final long serialVersionUID = 1L;

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
            SecretKey desKey = DesUtils.generateDESKey();

            int iterations = 1000; // run multiple times for stable timing

            // AES timing
            long aesTotalTime = 0;
            String aesEncrypted = null;
            String aesDecrypted = null;
            for (int i = 0; i < iterations; i++) {
                long start = System.nanoTime();
                aesEncrypted = AesUtils.encryptAES(input, aesKey);
                aesDecrypted = AesUtils.decryptAES(aesEncrypted, aesKey);
                long end = System.nanoTime();
                aesTotalTime += (end - start);
            }
            double aesAvgTimeMs = (aesTotalTime / (double) iterations) / 1_000_000.0;

            // DES timing
            long desTotalTime = 0;
            String desEncrypted = null;
            String desDecrypted = null;
            for (int i = 0; i < iterations; i++) {
                long start = System.nanoTime();
                desEncrypted = DesUtils.encryptDES(input, desKey);
                desDecrypted = DesUtils.decryptDES(desEncrypted, desKey);
                long end = System.nanoTime();
                desTotalTime += (end - start);
            }
            double desAvgTimeMs = (desTotalTime / (double) iterations) / 1_000_000.0;

            StringBuilder sb = new StringBuilder();
            sb.append("Input Size: ").append(size).append(" bytes\n\n")
              .append("--- AES ---\n")
              .append("Encrypted: ").append(aesEncrypted).append("\n")
              .append("Decrypted: ").append(aesDecrypted).append("\n")
              .append(String.format("Avg Time: %.3f ms\n\n", aesAvgTimeMs))
              .append("--- DES ---\n")
              .append("Encrypted: ").append(desEncrypted).append("\n")
              .append("Decrypted: ").append(desDecrypted).append("\n")
              .append(String.format("Avg Time: %.3f ms\n\n", desAvgTimeMs))
              .append(aesAvgTimeMs < desAvgTimeMs ? "AES is faster." : "DES is faster.");

            resultTextArea.setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EncryptionUI().setVisible(true));
    }
}
