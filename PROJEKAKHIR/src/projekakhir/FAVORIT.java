/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projekakhir;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author I MASTER ZEN
 */
public class FAVORIT extends javax.swing.JFrame {
        Connection conn;
        private DefaultTableModel resepfavorit;


    /**
     * Creates new form FAVORIT
     */
    public FAVORIT() {
        initComponents();
        
        conn = koneksi.getConnection();
        initTableModels();
        loadtampilfavorit();
        
        favorittabel.setCellSelectionEnabled(false); 
        favorittabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        favorittabel.setRowSelectionAllowed(true); 
        
        favorittabel.setDefaultEditor(Object.class, null);
        
        favorittabel.setModel(resepfavorit);


    }
    private void initTableModels(){
        resepfavorit = new DefaultTableModel(new String[]{"ID", "NAMA MAKANAN", " ASAL DAERAH", "BAHAN", "LANGKAH-LANGKAH"},0);
        favorittabel.setModel(resepfavorit);
    }
    
    private void loadtampilfavorit(){
         if (resepfavorit == null) {
        JOptionPane.showMessageDialog(this, "Model tabel belum diinisialisasi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    resepfavorit.setRowCount(0); // Membersihkan data di tabel favorit
    
    try {
        String sql = "SELECT f.favorite_id, r.nama_makanan, r.asal_daerah, r.bahan, r.langkah_langkah " +
             "FROM favorites AS f " +
             "JOIN resepbaru AS r ON f.id_resep = r.id_resep"; 
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            // Tambahkan data ke tabel favorit
            resepfavorit.addRow(new Object[]{
                rs.getInt("favorite_id"), 
                rs.getString("nama_makanan"), 
                rs.getString("asal_daerah"), 
                rs.getString("bahan"), 
                rs.getString("langkah_langkah")
            });
        }
    } catch (SQLException e) {
        System.out.println("Error Load Data Favorites: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data favorit.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    public class PDFExporter {

    public void exportSelectedRowToPDF(JTable table, String filePath) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Silakan pilih satu baris dari tabel terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        exportToPDF(tableModel, selectedRow, filePath);

        openPDF(filePath);
    }
    private void exportToPDF(DefaultTableModel tableModel, int rowIndex, String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Phrase("Data Resep Terpilih\n\n"));

            // Membuat tabel PDF dengan jumlah kolom sesuai tabel
            PdfPTable table = new PdfPTable(tableModel.getColumnCount());

            // Header tabel
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                table.addCell(new PdfPCell(new Phrase(tableModel.getColumnName(i))));
            }

            // Data baris yang dipilih
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                table.addCell(new PdfPCell(new Phrase(tableModel.getValueAt(rowIndex, col).toString())));
            }

            document.add(table);
            JOptionPane.showMessageDialog(null, "PDF berhasil dibuat di: " + filePath);
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error dalam membuat PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    public void openPDF(String filePath) {
        try {
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(null, "Desktop tidak didukung. Tidak bisa membuka PDF.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "File PDF tidak ditemukan di: " + filePath);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat membuka PDF: " + e.getMessage());
        }
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        CETAK = new javax.swing.JButton();
        KEMBALI = new javax.swing.JButton();
        HAPUS = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        favorittabel = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(51, 0, 51));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        CETAK.setText("CETAK");
        CETAK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CETAKActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(49, 64, 28, 0);
        jPanel2.add(CETAK, gridBagConstraints);

        KEMBALI.setText("KEMBALI");
        KEMBALI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KEMBALIActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(49, 18, 28, 0);
        jPanel2.add(KEMBALI, gridBagConstraints);

        HAPUS.setText("HAPUS");
        HAPUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HAPUSActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(49, 18, 28, 387);
        jPanel2.add(HAPUS, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        favorittabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(favorittabel);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Microsoft Uighur", 3, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FAVORIT");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CETAKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CETAKActionPerformed
        // TODO add your handling code here:
         String outputPath = "Data Resep.pdf";
        PDFExporter exporter = new PDFExporter();
        exporter.exportSelectedRowToPDF(favorittabel, outputPath);
    }//GEN-LAST:event_CETAKActionPerformed

    private void KEMBALIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KEMBALIActionPerformed
        // TODO add your handling code here:
        popupmkn b = new popupmkn();
            b.setVisible(true);
            dispose();
    }//GEN-LAST:event_KEMBALIActionPerformed

    private void HAPUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAPUSActionPerformed

        DefaultTableModel model = (DefaultTableModel) favorittabel.getModel();
    int selectedRow = favorittabel.getSelectedRow();
    
    if (selectedRow != -1) {
        int favoriteId = (int) model.getValueAt(selectedRow, 0);  
        String deleteSql = "DELETE FROM favorites WHERE favorite_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setInt(1, favoriteId);  
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus dari List favorit.");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error menghapus data dari favorites: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Silakan pilih baris yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_HAPUSActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FAVORIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FAVORIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FAVORIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FAVORIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FAVORIT().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CETAK;
    private javax.swing.JButton HAPUS;
    private javax.swing.JButton KEMBALI;
    private javax.swing.JTable favorittabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
