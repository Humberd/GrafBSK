package bsk.szyfrowanie.strumieniowe;

import bsk.exceptions.CipherException;
import bsk.file.extensions.BIN.BinaryFileReader;
import bsk.szyfrowanie.transpozycja.TemplatePanel;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

public class StreamTemplatePanel extends JPanel {

    private JLabel pageTitle = new JLabel();

    private JLabel polynomialOutputLabel = new JLabel("Polynomial:");
    private JLabel seedOutputLabel = new JLabel("Seed:");
    private JLabel messageOutputLabel = new JLabel("Message:");
    private JLabel lengthOutputLabel = new JLabel("Length:");
    private JLabel resultOutputLabel = new JLabel("Result:");
    private JLabel polynomialInputLabel = new JLabel("Polynomial:");
    private JLabel seedInputLabel = new JLabel("Seed:");
    private JLabel messageInputLabel = new JLabel("Message:");
    private JLabel lengthInputLabel = new JLabel("Length:");

//    private JLabel keyOutputLabel = new JLabel("Key:");
//    private JLabel messageOutputLabel = new JLabel("Message:");
////    private JLabel resultOutputLabel = new JLabel("Result:");
//    private JLabel messageInputLabel = new JLabel("Message:");
//    private JLabel keyInputLabel = new JLabel("Key:");
    private JTextArea polynomialOutput = new JTextArea();
    private JTextArea seedOutput = new JTextArea();
    private JTextArea messageOutput = new JTextArea();
    private JTextArea lengthOutput = new JTextArea();
    private JTextArea resultOutput = new JTextArea();

//    private JTextArea keyOutput = new JTextArea();
//    private JTextArea messageOutput = new JTextArea();
//    private JTextArea resultOutput = new JTextArea();
    private JTextField polynomialInput = new JTextField("");
    private JTextField seedInput = new JTextField("");
    private JTextField messageInput = new JTextField("");
    private JTextField lengthInput = new JTextField("");

//    private JTextField messageInput = new JTextField("");
//    private JTextField keyInput = new JTextField("");
    private JButton polynomialInputPasteButton = new JButton("Paste");
    private JButton seedInputPasteButton = new JButton("Paste");
    private JButton messageInputPasteButton = new JButton("Paste");
    private JButton lengthInputPasteButton = new JButton("Paste");

//    private JButton messageInputPasteButton = new JButton("Paste");
    private JButton polynomialOutputCopyButton = new JButton("Copy");
    private JButton seedOutputCopyButton = new JButton("Copy");
    private JButton messageOutputCopyButton = new JButton("Copy");
    private JButton lengthOutputCopyButton = new JButton("Copy");
    private JButton resultOutputCopyButton = new JButton("Copy");

//    private JButton keyOutputCopyButton = new JButton("Copy");
//    private JButton messageOutputCopyButton = new JButton("Copy");
//    private JButton resultOutputCopyButton = new JButton("Copy");
    private JButton encryptButton = new JButton("Encrypt");
    private JButton decryptButton = new JButton("Decrypt");
    private JButton importMessageButton = new JButton("Import Message");
    private JButton exportResultButton = new JButton("Export Result");
    private JButton compareFilesButton = new JButton("Compare Files");

    private JPanel titlePanel = new JPanel();
    private JPanel resultPanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JPanel actionPanel = new JPanel();

    private String resultOutputString;
    private String messageOutputString;

    private StreamCipher cipher;

    public StreamTemplatePanel(StreamCipher cipher) {
        super();
        this.cipher = cipher;
        pageTitle.setText(cipher.getCipherName());
        setName(cipher.getCipherName());
        polynomialInput.setText(cipher.getTemplatePolynomial());
        seedInput.setText(cipher.getTemplateSeed());
        messageInput.setText(cipher.getTemplateMessage());
        lengthInput.setText(cipher.getTemplateLength());

        addMainPanels();

        attachCopyButton(polynomialOutputCopyButton, polynomialOutput);
        attachCopyButton(seedOutputCopyButton, seedOutput);
        attachCopyButton(messageOutputCopyButton, messageOutput, new Runnable() {
            @Override
            public void run() {
                copyToClipboard(messageOutputString);
            }
        });
        attachCopyButton(lengthOutputCopyButton, lengthOutput);
        attachCopyButton(resultOutputCopyButton, resultOutput, new Runnable() {
            @Override
            public void run() {
                copyToClipboard(resultOutputString);
            }
        });

        attachPasteButton(polynomialInputPasteButton, polynomialInput);
        attachPasteButton(seedInputPasteButton, seedInput);
        attachPasteButton(messageInputPasteButton, messageInput);
        attachPasteButton(lengthInputPasteButton, lengthInput);

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
        resultPanel.add(polynomialOutputLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        resultPanel.add(seedOutputLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        resultPanel.add(messageOutputLabel, c);
        c.gridx = 0;
        c.gridy = 3;
        resultPanel.add(lengthOutputLabel, c);
        c.gridx = 0;
        c.gridy = 4;
        resultPanel.add(resultOutputLabel, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        polynomialOutput.setEditable(false);
        polynomialOutput.setLineWrap(true);
        polynomialOutput.setMargin(new Insets(5, 5, 5, 5));
        resultPanel.add(polynomialOutput, c);
        c.gridx = 1;
        c.gridy = 1;
        seedOutput.setEditable(false);
        seedOutput.setLineWrap(true);
        seedOutput.setMargin(new Insets(5, 5, 5, 5));
        resultPanel.add(seedOutput, c);
        c.gridx = 1;
        c.gridy = 2;
        messageOutput.setEditable(false);
        messageOutput.setLineWrap(true);
        messageOutput.setMargin(new Insets(5, 5, 5, 5));
        resultPanel.add(messageOutput, c);
        c.gridx = 1;
        c.gridy = 3;
        lengthOutput.setEditable(false);
        lengthOutput.setLineWrap(true);
        lengthOutput.setMargin(new Insets(5, 5, 5, 5));
        resultPanel.add(lengthOutput, c);
        c.gridx = 1;
        c.gridy = 4;
        resultOutput.setEditable(false);
        resultOutput.setLineWrap(true);
        resultOutput.setMargin(new Insets(5, 5, 5, 5));
//        JScrollPane scrollPane = new JScrollPane(resultOutput);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setMaximumSize(new Dimension(400, 300));
//        scrollPane.setWheelScrollingEnabled(true);
        resultPanel.add(resultOutput, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.2;
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 0;
        resultPanel.add(polynomialOutputCopyButton, c);
        c.gridx = 2;
        c.gridy = 1;
        resultPanel.add(seedOutputCopyButton, c);
        c.gridx = 2;
        c.gridy = 2;
        resultPanel.add(messageOutputCopyButton, c);
        c.gridx = 2;
        c.gridy = 3;
        resultPanel.add(lengthOutputCopyButton, c);
        c.gridx = 2;
        c.gridy = 4;
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
        inputPanel.add(polynomialInputLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        inputPanel.add(seedInputLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        inputPanel.add(messageInputLabel, c);
        c.gridx = 0;
        c.gridy = 3;
        inputPanel.add(lengthInputLabel, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.9;
        c.weightx = 0.9;
        c.gridx = 1;
        c.gridy = 0;
        inputPanel.add(polynomialInput, c);
        c.gridx = 1;
        c.gridy = 1;
        inputPanel.add(seedInput, c);
        c.gridx = 1;
        c.gridy = 2;
        inputPanel.add(messageInput, c);
        c.gridx = 1;
        c.gridy = 3;
        inputPanel.add(lengthInput, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.01;
        c.weightx = 0.01;
        c.gridx = 2;
        c.gridy = 0;
        inputPanel.add(polynomialInputPasteButton, c);
        c.gridx = 2;
        c.gridy = 1;
        inputPanel.add(seedInputPasteButton, c);
        c.gridx = 2;
        c.gridy = 2;
        inputPanel.add(messageInputPasteButton, c);
        c.gridx = 2;
        c.gridy = 3;
        inputPanel.add(lengthInputPasteButton, c);
    }

    private void setActionPanel(JPanel actionPanel) {
        GridBagConstraints c = new GridBagConstraints();
        actionPanel.setLayout(new GridBagLayout());
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(5, 10, 5, 10);

        JPanel filePanel = new JPanel();
        filePanel.add(importMessageButton);
        filePanel.add(exportResultButton);
        filePanel.add(compareFilesButton);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        actionPanel.add(filePanel, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        actionPanel.add(encryptButton, c);
        c.gridx = 1;
        c.gridy = 1;
        actionPanel.add(decryptButton, c);
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void attachCopyButton(JButton button, JTextComponent textComponent, Runnable runnable) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setEnabled(false);

                Thread th = new Thread(runnable);

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

    private void attachCopyButton(JButton button, JTextComponent textComponent) {
        attachCopyButton(button, textComponent, new Runnable() {
            @Override
            public void run() {
                copyToClipboard(textComponent.getText());
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

    private void attachPasteButton(JButton button, JTextComponent textComponent) {
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

    public void setCorrectResult(String result) {
        resultOutput.setBackground(new Color(82, 249, 132));
        String printResult = result;
        resultOutputString = result;
        if (result.length() > 250) {
            printResult = result.substring(0, 250) + "...";
        }
        resultOutput.setText(printResult);
    }

    public void setMessageOutputString(String message) {
        messageOutputString = message;
        String printMessage = message;
        if (message.length() > 50) {
            printMessage = message.substring(0, 50) + "...";
        }
        messageOutput.setText(printMessage);
    }

    public void setErrorResult(String errorResult) {
        resultOutput.setBackground(new Color(255, 111, 111));
        resultOutput.setText(errorResult);
    }

    private void setActionButtonsEnabled(boolean enabled) {
        encryptButton.setEnabled(enabled);
        encryptButton.setEnabled(enabled);
    }

    private void attachActionButtons() {
        attachActionButton(encryptButton, new Runnable() {
            @Override
            public void run() {
                String polynomial = polynomialInput.getText() != null ? polynomialInput.getText() : "";
                String seed = seedInput.getText() != null ? seedInput.getText() : "";
                String key = messageInput.getText() != null ? messageInput.getText() : "";
                String length = lengthInput.getText() != null ? lengthInput.getText() : "";
                polynomialOutput.setText(polynomial);
                seedOutput.setText(seed);
                //strasznie duzo czasu zabiera
                setMessageOutputString(key);
                lengthOutput.setText(length);

                try {
                    setCorrectResult(cipher.encrypt(polynomial, seed, key, length));
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
                String polynomial = polynomialInput.getText() != null ? polynomialInput.getText() : "";
                String seed = seedInput.getText() != null ? seedInput.getText() : "";
                String key = messageInput.getText() != null ? messageInput.getText() : "";
//                String length = lengthInput.getText() != null ? lengthInput.getText() : "";
                polynomialOutput.setText(polynomial);
                seedOutput.setText(seed);
                setMessageOutputString(key);
//                lengthOutput.setText(length);
                try {
                    setCorrectResult(cipher.decrypt(polynomial, seed, key));
                } catch (CipherException ex) {
                    setErrorResult(ex.getMessage());
                } catch (Exception ex) {
                    setErrorResult("Unknown Exception Error");
                }
            }
        });

        attachFileButton(importMessageButton, new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.bin", "bin");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setPreferredSize(new Dimension(500, 500));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String message = BinaryFileReader.read(fileChooser.getSelectedFile().getAbsolutePath());
                    messageInput.setText(message);
                }
            }
        });
        attachFileButton(exportResultButton, new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.bin", "bin");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setPreferredSize(new Dimension(500, 500));
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    BinaryFileReader.save(fileChooser.getSelectedFile().getAbsolutePath() + ".bin", resultOutputString);
                }
            }
        });

        attachFileButton(compareFilesButton, new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.bin", "bin");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setPreferredSize(new Dimension(500, 500));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    boolean theSame = BinaryFileReader.compareFiles(fileChooser.getSelectedFiles());

                    if (theSame) {
                        JOptionPane.showMessageDialog(StreamTemplatePanel.this, "Selected files are equal", "Files compare", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(StreamTemplatePanel.this, "Selected files are different", "Files compare", JOptionPane.ERROR_MESSAGE);
                    }

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

    private void attachFileButton(JButton button, Runnable runnable) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runnable.run();
            }
        });
    }
}
