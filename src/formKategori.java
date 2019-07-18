/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SHIFT Comp
 */

//memanggil library/api yang diperlukan
import Koneksi.KoneksiDB;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class formKategori extends javax.swing.JFrame {
    
     //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String kdKategori, nmKategori;
    /**
     * Creates new form formKategori
     */
    public formKategori() {
        initComponents();
        //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tblKategori.setModel(model);
        model.addColumn("KODE KATEGORI");
        model.addColumn("NAMA KATEGORI");
        
        //fungsi ambil data
        getDataKategori();
    }

    //fungsi membaca data kategori
    public void getDataKategori(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi KoneksiDB dan kirimkan query ke database
        try{
            //tes KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel kategori        
            String sql = "SELECT * FROM tbl_kategori";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[2];
                obj[0]=res.getString("kd_kategori");
                obj[1]=res.getString("nm_kategori");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        KdKategori = new javax.swing.JTextField();
        NmKategori = new javax.swing.JTextField();
        simpan = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        ubah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        keluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKategori = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 204));
        jLabel1.setText("MASTER DATA KATEGORI");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 11, -1, -1));

        jLabel2.setText("KODE KATEGORI");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, -1));

        jLabel3.setText("NAMA KATEGORI");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        KdKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdKategoriActionPerformed(evt);
            }
        });
        getContentPane().add(KdKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 207, -1));
        getContentPane().add(NmKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 207, -1));

        simpan.setText("SIMPAN");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });
        getContentPane().add(simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        reset.setText("RESET");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, -1));

        ubah.setText("UBAH");
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });
        getContentPane().add(ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, -1, -1));

        hapus.setText("HAPUS");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        getContentPane().add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, -1, -1));

        keluar.setText("KELUAR");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });
        getContentPane().add(keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        tblKategori.setModel(new javax.swing.table.DefaultTableModel(
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
        tblKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKategoriMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKategori);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 138, 370, 94));

        jLabel4.setIcon(new javax.swing.ImageIcon("E:\\#Kuliah\\Smt 4\\Pemrograman Open Source\\Desain-Lemari-Pakaian-Modern-Untuk-Desain-Interior-Modern-modern-wardrobe-design-ideas.jpg")); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //membaca data 
    public void loadDataKategori(){
        kdKategori = KdKategori.getText();
        nmKategori = NmKategori.getText();
    }
    
    //memilih data saat baris pada tabel dipilih dengan mouse
    public void dataSelect(){
        //deklarasi variabel
        int i = tblKategori.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        KdKategori.setText(""+model.getValueAt(i,0));
        NmKategori.setText(""+model.getValueAt(i,1));
    }
     
    //mengosongkan form
    public void reset(){
        kdKategori = "";
        nmKategori = "";
               
        KdKategori.setText(kdKategori);
        NmKategori.setText(nmKategori);
    }
    
    public void simpanDataKategori(){
        //panggil fungsi load data
        loadDataKategori();
        
        //uji KoneksiDB dan eksekusi perintah
        try{
            //test KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO tbl_kategori(kd_kategori, nm_kategori)" + "VALUES('"+ kdKategori +"','"+ nmKategori +"')";
            PreparedStatement p = (PreparedStatement) KoneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataKategori();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void perbaruiDataKategori(){
        //fungsi load data kategori
        loadDataKategori();
        
        try{
            //uji KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //kirim perintah sql
            String sql = "UPDATE tbl_kategori SET nm_kategori = '"+ nmKategori +"' WHERE kd_kategori = '"+ kdKategori +"'";
            PreparedStatement p =(PreparedStatement)KoneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataKategori();
            
            //kosongkan data
            reset();
            JOptionPane.showMessageDialog(null, "PERUBAHAN DATA BERHASIL");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    private void ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahActionPerformed
        // TODO add your handling code here:
        perbaruiDataKategori();
    }//GEN-LAST:event_ubahActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_resetActionPerformed

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new menuAdmin().setVisible(true);
    }//GEN-LAST:event_keluarActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        // TODO add your handling code here:
        simpanDataKategori();
    }//GEN-LAST:event_simpanActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
         //panggil fungsi ambil data
        loadDataKategori(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ kdKategori +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji KoneksiDB
            try{
                //buka KoneksiDB ke database
                Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM tbl_kategori WHERE kd_kategori='"+ kdKategori +"'";
                PreparedStatement p =(PreparedStatement)KoneksiDB.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                //fungsi ambil data
                getDataKategori();
                
                //fungsi reset data
                reset();
                JOptionPane.showMessageDialog(null, "KENANGAN BERSAMANYA BERHASIL DIHAPUS");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
        }
    }//GEN-LAST:event_hapusActionPerformed

    private void tblKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKategoriMouseClicked
        // TODO add your handling code here:
        
        //memanggil fungsi memilih data
        dataSelect();
    }//GEN-LAST:event_tblKategoriMouseClicked

    private void KdKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKategoriActionPerformed

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
            java.util.logging.Logger.getLogger(formKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formKategori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField KdKategori;
    private javax.swing.JTextField NmKategori;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keluar;
    private javax.swing.JButton reset;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tblKategori;
    private javax.swing.JButton ubah;
    // End of variables declaration//GEN-END:variables
}
