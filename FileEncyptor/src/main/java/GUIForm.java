import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class GUIForm {

    private JPanel rootPanel;
    private JTextField failPath;
    private JButton selectButton;
    private JButton actionButton;
    private JProgressBar progressBar;
    private File selectedFile;

    private boolean encryptorFileSelected = false;

    private String decryptAction = "Расшифровать";
    private String encryptAction = "Зашифровать";

    public GUIForm() {

        selectButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.showOpenDialog(rootPanel);
                selectedFile = chooser.getSelectedFile();
                onFileSelect();
            }
        });

        actionButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    return;
                }

                String password = showPassword();
                if (password == null || password.length() == 0) {
                    showWarning("Пароль не указан!");
                    return;
                }

                progressBar.setVisible(true);
                if (encryptorFileSelected) {
                    decryptFile(password);
                } else {
                    encryptFile(password);
                }
            }
        });
    }

    private String showPassword() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Введите пароль: ");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"Ok","Cancel"};
        int option = JOptionPane.showOptionDialog(
                null, panel,"Ввод пароля", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);

        if (option != 0) return null;

        return new String(pass.getPassword());
    }

    public  JPanel getRootPanel() {
        return rootPanel;
    }

    public void setButtonsEnabled(boolean enabled) {
        selectButton.setEnabled(enabled);
        actionButton.setEnabled(enabled);
    }

    private void  encryptFile(String password) {
        EncryptorThread thread = new EncryptorThread(this);
        thread.setFile(selectedFile);
        thread.setPassword(password);
        thread.start();
    }

    private  void decryptFile(String password) {
        DecryptorThread thread = new DecryptorThread(this);
        thread.setFile(selectedFile);
        thread.setPassword(password);
        thread.start();
    }

    private  void onFileSelect() {

        if (selectedFile == null) {
            failPath.setText("");
            actionButton.setVisible(false);
            return;
        }
        failPath.setText(selectedFile.getAbsolutePath());
        try {
            ZipFile zipFile = new ZipFile(selectedFile);
            encryptorFileSelected = zipFile.isValidZipFile() && zipFile.isEncrypted();
            actionButton.setText(encryptorFileSelected ? decryptAction : encryptAction);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        actionButton.setVisible(true);
    }

    public void showWarning(String message) {
        progressBar.setVisible(false);
        JOptionPane.showMessageDialog(
                rootPanel, message,"Ошибка", JOptionPane.WARNING_MESSAGE);
    }

    public void showFinished() {
        progressBar.setVisible(false);
        JOptionPane.showMessageDialog(
                rootPanel, encryptorFileSelected ?
                        "Расшифровка завершена" : "Шифрование завершено",
                "Завершено", JOptionPane.INFORMATION_MESSAGE);
    }
}
