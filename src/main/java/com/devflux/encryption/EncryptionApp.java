package com.devflux.encryption;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class EncryptionApp extends JFrame {

	private static final long serialVersionUID = -811932428936629429L;
	private JTextArea inputArea;
    private JTextArea outputArea;

    public EncryptionApp() {
        setTitle("MailMate Encryption Tool");
        setSize(550, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel: Input & Output text areas
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setBorder(BorderFactory.createTitledBorder("Input Text"));

        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder("Output Result"));

        centerPanel.add(inputScroll);
        centerPanel.add(outputScroll);

        // Center Action Panel: Buttons layout matching your image
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton encryptBtn = new JButton("ENCRYPT");
        JButton decryptBtn = new JButton("DECRYPT");

        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);

        // Add components to Frame
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Listeners
        encryptBtn.addActionListener(e -> {
            try {
                String text = inputArea.getText();
                if (text.isEmpty()) return;
                String encrypted = EncryptionUtil.encrypt(text);
                outputArea.setText(encrypted);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Encryption Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        decryptBtn.addActionListener(e -> {
            try {
                String text = inputArea.getText().trim();
                if (text.isEmpty()) return;
                String decrypted = EncryptionUtil.decrypt(text);
                outputArea.setText(decrypted);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid encrypted text or corrupt key.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        // Set native system look and feel for Windows aesthetic
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new EncryptionApp().setVisible(true));
    }
}