package com.example.vp3.JFrames;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateMahasiswaFrame extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField nimField;

    public CreateMahasiswaFrame(MahasiswaFrame parentFrame) {
        JFrame inputFrame = new JFrame("Tambah Mahasiswa");

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        idField = new JTextField();
        nameField = new JTextField();
        nimField = new JTextField();
        JButton saveButton = new JButton("Simpan");

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nama:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("NIM:"));
        inputPanel.add(nimField);
        inputPanel.add(new JLabel());
        inputPanel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String nim = nimField.getText();

                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost/mahasiswaku?" +
                            "user=root&password=");
                    String query = "INSERT INTO mahasiswa (id, nama, nim) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setInt(1, Integer.parseInt(id));
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, nim);
                    preparedStatement.executeUpdate();

                    parentFrame.loadDataFromDatabase();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                inputFrame.dispose();
            }
        });

        inputFrame.getContentPane().add(inputPanel);
        inputFrame.pack();
        inputFrame.setVisible(true);
        inputFrame.setBounds(150, 150, 300, 200);
    }
}
