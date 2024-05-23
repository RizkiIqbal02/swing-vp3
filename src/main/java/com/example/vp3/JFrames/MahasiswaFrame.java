package com.example.vp3.JFrames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MahasiswaFrame {
    private DefaultTableModel tableModel;
    private JTable table;

    public MahasiswaFrame() {
        JFrame jFrame = new JFrame("MahasiswaKu");
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nama");
        tableModel.addColumn("NIM");

        loadDataFromDatabase();

        table = new JTable(tableModel);
        JScrollPane pane = new JScrollPane(table);

        JButton button = new JButton("Tambah Mahasiswa");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateMahasiswaFrame(MahasiswaFrame.this).setVisible(true);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.NORTH);
        panel.add(pane, BorderLayout.CENTER);

        jFrame.getContentPane().add(panel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setBounds(100, 100, 400, 300);
    }

    public void loadDataFromDatabase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/mahasiswaku?" +
                    "user=root&password=");
            String query = "SELECT * FROM mahasiswa";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            // Menghapus semua baris yang ada di model tabel
            tableModel.setRowCount(0);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                String nim = rs.getString("nim");
                tableModel.addRow(new Object[]{id, nama, nim});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
