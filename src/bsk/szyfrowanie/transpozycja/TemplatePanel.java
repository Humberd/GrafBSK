package bsk.szyfrowanie.transpozycja;

import bsk.exceptions.CipherException;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

public class TemplatePanel extends JPanel {

    private JLabel pageTitle = new JLabel();

    private JLabel keyOutputLabel = new JLabel("Key:");
    private JLabel messageOutputLabel = new JLabel("Message:");
    private JLabel resultOutputLabel = new JLabel("Result:");
    private JLabel messageInputLabel = new JLabel("Message:");
    private JLabel keyInputLabel = new JLabel("Key:");

    private JTextArea keyOutput = new JTextArea();
    private JTextArea messageOutput = new JTextArea();
    private JTextArea resultOutput = new JTextArea();

    private JTextField messageInput = new JTextField("CRYPTOGRAPHY");
    private JTextField keyInput = new JTextField("3");

    private JButton messageInputPasteButton = new JButton("Paste");

    private JButton keyOutputCopyButton = new JButton("Copy");
    private JButton messageOutputCopyButton = new JButton("Copy");
    private JButton resultOutputCopyButton = new JButton("Copy");

    private JButton encryptButton = new JButton("Encrypt");
    private JButton decryptButton = new JButton("Decrypt");

    private JPanel titlePanel = new JPanel();
    private JPanel resultPanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JPanel actionPanel = new JPanel();

    private Cipher cipher;

    public TemplatePanel(Cipher cipher) {
        super();
        this.cipher = cipher;
        pageTitle.setText(cipher.getCipherName());
        setName(cipher.getCipherName());
        messageInput.setText(cipher.getTemplateMessage());
        keyInput.setText(cipher.getTemplateKey());

        addMainPanels();

        attachCopyButton(keyOutputCopyButton, keyOutput);
        attachCopyButton(messageOutputCopyButton, messageOutput);
        attachCopyButton(resultOutputCopyButton, resultOutput);

        attachPasteButon(messageInputPasteButton, messageInput);

        attachActionButtons();
    }

    private void addMainPanels() {
//        titlePanel.setBackground(Color.red);
        setTitlePanel(titlePanel);
//        resultPanel.setBackground(Color.yellow);
        setResultPanel(resultPanel);
//        inputPanel.setBackground(Color.green);
        setInputPanel(inputPanel);
//        actionPanel.setBackground(Color.blue);
        setActionPanel(actionPanel);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 0;
        add(titlePanel, c);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.3;
        c.weighty = 0.3;
        c.ipadx = 100;
        c.ipady = 100;
        c.gridx = 0;
        c.gridy = 1;
        add(resultPanel, c);
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.6;
        c.weighty = 0.6;
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 2;
        add(inputPanel, c);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.4;
        c.weighty = 0.4;
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 3;
        add(actionPanel, c);
    }

    private void setTitlePanel(JPanel titlePanel) {
        GridBagConstraints c = new GridBagConstraints();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(pageTitle, c);
    }

    private void setResultPanel(JPanel resultPanel) {
        GridBagConstraints c = new GridBagConstraints();
        resultPanel.setLayout(new GridBagLayout());
        TitledBorder title = BorderFactory.createTitledBorder("Results");
        title.setTitleJustification(TitledBorder.LEADING);
        resultPanel.setBorder(title);
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.3;
        c.weightx = 0.3;
        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        resultPanel.add(keyOutputLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        resultPanel.add(messageOutputLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        resultPanel.add(resultOutputLabel, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        keyOutput.setEditable(false);
        keyOutput.setLineWrap(true);
        keyOutput.setMargin(new Insets(5, 5, 5, 5));
        resultPanel.add(keyOutput, c);
        c.gridx = 1;
        c.gridy = 1;
        messageOutput.setEditable(false);
        messageOutput.setLineWrap(true);
        messageOutput.setMargin(new Insets(5, 5, 5, 5));
        resultPanel.add(messageOutput, c);
        c.gridx = 1;
        c.gridy = 2;
        resultOutput.setEditable(false);
        resultOutput.setLineWrap(true);
        resultOutput.setMargin(new Insets(5, 5, 5, 5));
        resultPanel.add(resultOutput, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.2;
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 0;
        resultPanel.add(keyOutputCopyButton, c);
        c.gridx = 2;
        c.gridy = 1;
        resultPanel.add(messageOutputCopyButton, c);
        c.gridx = 2;
        c.gridy = 2;
        resultPanel.add(resultOutputCopyButton, c);

    }

    private void setInputPanel(JPanel inputPanel) {
        GridBagConstraints c = new GridBagConstraints();
        inputPanel.setLayout(new GridBagLayout());
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.01;
        c.weightx = 0.01;
        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        inputPanel.add(messageInputLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        inputPanel.add(keyInputLabel, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.9;
        c.weightx = 0.9;
        c.gridx = 1;
        c.gridy = 0;
        inputPanel.add(messageInput, c);
        c.gridx = 1;
        c.gridy = 1;
        inputPanel.add(keyInput, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.01;
        c.weightx = 0.01;
        c.gridx = 2;
        c.gridy = 0;
        inputPanel.add(messageInputPasteButton, c);
    }

    private void setActionPanel(JPanel actionPanel) {
        GridBagConstraints c = new GridBagConstraints();
        actionPanel.setLayout(new GridBagLayout());
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        actionPanel.add(encryptButton, c);
        c.gridx = 1;
        c.gridy = 0;
        actionPanel.add(decryptButton, c);
    }

    public void setCorrectResult(String result) {
        resultOutput.setBackground(new Color(82, 249, 132));
        resultOutput.setText(result);
    }

    public void setErrorResult(String errorResult) {
        resultOutput.setBackground(new Color(255, 111, 111));
        resultOutput.setText(errorResult);
    }

    public void setKeyOutputText(String text) {
        keyOutput.setText(text);
    }

    public void setMessageOutputText(String text) {
        messageOutput.setText(text);
    }

    public void clearInputs() {
        messageInput.setText("");
        keyInput.setText("");
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void attachCopyButton(JButton button, JTextComponent textComponent) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setEnabled(false);

                Thread th = new Thread(new Runnable() {
                    public void run() {
                        copyToClipboard(textComponent.getText());
                    }
                });

                th.start();
                try {
                    th.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TemplatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                button.setEnabled(true);
            }

        });
    }

    private void pasteFromClipboard(JTextComponent textComponent) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = clipboard.getContents(null);
        try {
            String data = (String) t.getTransferData(DataFlavor.stringFlavor);
            textComponent.setText(data);
        } catch (Exception ex) {
            textComponent.setText("Cannot paste from the Clipboard");
        }

    }

    private void attachPasteButon(JButton button, JTextComponent textComponent) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setEnabled(false);

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pasteFromClipboard(textComponent);
                    }
                });
                th.start();
                try {
                    th.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TemplatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                button.setEnabled(true);
            }

        });
    }

    private void attachActionButtons() {
        attachActionButton(encryptButton, new Runnable() {
            @Override
            public void run() {
                String message = getMessageInput().getText() != null ? getMessageInput().getText() : "";
                String key = getKeyInput().getText() != null ? getKeyInput().getText() : "";
                setMessageOutputText(message);
                setKeyOutputText(key);
                try {
                    setCorrectResult(getCipher().encrypt(message, key));
                } catch (CipherException ex) {
                    setErrorResult(ex.getMessage());
                } catch (Exception ex) {
                    setErrorResult("Unknown Exception Error");
                }

            }

        });
        attachActionButton(decryptButton, new Runnable() {
            @Override
            public void run() {
                String message = getMessageInput().getText() != null ? getMessageInput().getText() : "";
                String key = getKeyInput().getText() != null ? getKeyInput().getText() : "";
                setMessageOutputText(message);
                setKeyOutputText(key);
                try {
                    setCorrectResult(getCipher().decrypyt(message, key));
                } catch (CipherException ex) {
                    setErrorResult(ex.getMessage());
                } catch (Exception ex) {
                    setErrorResult("Unknown Exception Error");
                }
            }

        });
    }

    private void attachActionButton(JButton button, Runnable runnable) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActionButtonsEnabled(false);

                Thread th = new Thread(runnable);

                th.start();
                try {
                    th.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TemplatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                setActionButtonsEnabled(true);
            }

        });
    }

    private void setActionButtonsEnabled(boolean enabled) {
        encryptButton.setEnabled(enabled);
        encryptButton.setEnabled(enabled);
    }

    public JTextField getMessageInput() {
        return messageInput;
    }

    public void setMessageInput(JTextField messageInput) {
        this.messageInput = messageInput;
    }

    public JTextField getKeyInput() {
        return keyInput;
    }

    public void setKeyInput(JTextField keyInput) {
        this.keyInput = keyInput;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public void setCipher(Cipher cipher) {
        this.cipher = cipher;
    }

    public JLabel getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(JLabel pageTitle) {
        this.pageTitle = pageTitle;
    }

    public JLabel getKeyOutputLabel() {
        return keyOutputLabel;
    }

    public void setKeyOutputLabel(JLabel keyOutputLabel) {
        this.keyOutputLabel = keyOutputLabel;
    }

    public JLabel getMessageOutputLabel() {
        return messageOutputLabel;
    }

    public void setMessageOutputLabel(JLabel messageOutputLabel) {
        this.messageOutputLabel = messageOutputLabel;
    }

    public JLabel getResultOutputLabel() {
        return resultOutputLabel;
    }

    public void setResultOutputLabel(JLabel resultOutputLabel) {
        this.resultOutputLabel = resultOutputLabel;
    }

    public JLabel getMessageInputLabel() {
        return messageInputLabel;
    }

    public void setMessageInputLabel(JLabel messageInputLabel) {
        this.messageInputLabel = messageInputLabel;
    }

    public JLabel getKeyInputLabel() {
        return keyInputLabel;
    }

    public void setKeyInputLabel(JLabel keyInputLabel) {
        this.keyInputLabel = keyInputLabel;
    }

    public JTextArea getKeyOutput() {
        return keyOutput;
    }

    public void setKeyOutput(JTextArea keyOutput) {
        this.keyOutput = keyOutput;
    }

    public JTextArea getMessageOutput() {
        return messageOutput;
    }

    public void setMessageOutput(JTextArea messageOutput) {
        this.messageOutput = messageOutput;
    }

    public JTextArea getResultOutput() {
        return resultOutput;
    }

    public void setResultOutput(JTextArea resultOutput) {
        this.resultOutput = resultOutput;
    }

    public JButton getMessageInputPasteButton() {
        return messageInputPasteButton;
    }

    public void setMessageInputPasteButton(JButton messageInputPasteButton) {
        this.messageInputPasteButton = messageInputPasteButton;
    }

    public JButton getKeyOutputCopyButton() {
        return keyOutputCopyButton;
    }

    public void setKeyOutputCopyButton(JButton keyOutputCopyButton) {
        this.keyOutputCopyButton = keyOutputCopyButton;
    }

    public JButton getMessageOutputCopyButton() {
        return messageOutputCopyButton;
    }

    public void setMessageOutputCopyButton(JButton messageOutputCopyButton) {
        this.messageOutputCopyButton = messageOutputCopyButton;
    }

    public JButton getResultOutputCopyButton() {
        return resultOutputCopyButton;
    }

    public void setResultOutputCopyButton(JButton resultOutputCopyButton) {
        this.resultOutputCopyButton = resultOutputCopyButton;
    }

    public JButton getEncryptButton() {
        return encryptButton;
    }

    public void setEncryptButton(JButton encryptButton) {
        this.encryptButton = encryptButton;
    }

    public JButton getDecryptButton() {
        return decryptButton;
    }

    public void setDecryptButton(JButton decryptButton) {
        this.decryptButton = decryptButton;
    }
    
    public JPanel getInputPanel() {
        return inputPanel;
    }

}
