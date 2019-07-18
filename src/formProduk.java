/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SHIFT Comp
 */

import Koneksi.KoneksiDB;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class formProduk extends javax.swing.JFrame {

    //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String kdProduk, kdKategori, nmProduk, satuan;
    int hrg_beli, hrg_jual, stok;
    /**
     * Creates new form formProduk
     */
    public formProduk() {
        initComponents();
        //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tblProduk.setModel(model);
        model.addColumn("KODE PRODUK");
        model.addColumn("KODE KATEGORI");
        model.addColumn("NAMA PRODUK");
        model.addColumn("HARGA BELI");
        model.addColumn("HARGA JUAL");
        model.addColumn("STOK");
        model.addColumn("SATUAN");
        
        //fungsi ambil data
        getDataProduk();
    }

    
     //fungsi membaca data kategori
    public void getDataProduk(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi KoneksiDB dan kirimkan query ke database
        try{
            //tes KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM tbl_produk";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[7];
                obj[0]=res.getString("kd_produk");
                obj[1]=res.getString("kd_kategori");
                obj[2]=res.getString("nm_produk");
                obj[3]=res.getString("hrg_beli");
                obj[4]=res.getString("hrg_jual");
                obj[5]=res.getString("stok");
                obj[6]=res.getString("satuan");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void loadDataProduk(){
        //mengambil data dari textbox dan menyimpan nilainya pada variabel
        kdProduk = KdProduk.getText();
        kdKategori = KdKategori.getText();
        nmProduk = NmProduk.getText();
        hrg_beli = Integer.parseInt(HBeli.getText());
        hrg_jual = Integer.parseInt(HJual.getText());
        stok = Integer.parseInt(Stok.getText());
        satuan = Satuan.getText(); 
    }
    
    public void dataSelect(){
        //deklarasi variabel
        int i = tblProduk.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        KdProduk.setText(""+model.getValueAt(i,0));
        KdKategori.setText(""+model.getValueAt(i,1));
        NmProduk.setText(""+model.getValueAt(i,2));
        HBeli.setText(""+model.getValueAt(i,3));
        HJual.setText(""+model.getValueAt(i,4));
        Stok.setText(""+model.getValueAt(i,5));
        Satuan.setText(""+model.getValueAt(i,6));
    }
    
    public void reset(){
        kdProduk = "";
        kdKategori = "";
        nmProduk = "";
        hrg_beli = 0;
        hrg_jual = 0;
        stok = 0;
        satuan = "";
        
        KdProduk.setText(kdProduk);
        KdKategori.setText(kdKategori);
        NmProduk.setText(nmProduk);
        HBeli.setText("");
        HJual.setText("");
        Stok.setText("");
        Satuan.setText(satuan);
    }
    
    public void simpanDataProduk(){
         //panggil fungsi load data
        loadDataProduk();
        
        //uji KoneksiDB dan eksekusi perintah
        try{
            //test KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO tbl_produk(kd_produk, kd_kategori, nm_produk, hrg_beli, hrg_jual, stok, satuan)"
                            + "VALUES('"+ kdProduk +"','"+ kdKategori +"','"+ nmProduk +"','"+ hrg_beli +"','"+ hrg_jual +"','"+ stok +"', '"+ satuan +"')";
            PreparedStatement p = (PreparedStatement) KoneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataProduk();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void rubahDataProduk(){
          //panggil fungsi load data
        loadDataProduk();
        
        //uji KoneksiDB dan eksekusi perintah
        try{
            //test KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String sql  =   "UPDATE tbl_produk SET kd_kategori = '"+ kdKategori +"',"
                            + "nm_produk  = '"+ nmProduk +"',"
                            + "hrg_beli  = '"+ hrg_beli +"',"
                            + "hrg_jual  = '"+ hrg_jual +"',"  
                            + "stok  = '"+ stok +"',"
                            + "satuan  = '"+ satuan +"'"
                            + "WHERE kd_produk = '" + kdProduk +"'";
            PreparedStatement p = (PreparedStatement) KoneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataProduk();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void hapusDataProduk(){
        //panggil fungsi ambil data
        loadDataProduk(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ kdProduk +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji KoneksiDB
            try{
                //buka KoneksiDB ke database
                Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM tbl_produk WHERE kd_produk='"+ kdProduk +"'";
                PreparedStatement p =(PreparedStatement)KoneksiDB.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                //fungsi ambil data
                getDataProduk();
                
                //fungsi reset data
                reset();
                JOptionPane.showMessageDialog(null, "DATA YANG DIPILIH BERHASIL DIHAPUS");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        KdProduk = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        KdKategori = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        NmProduk = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        HBeli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        HJual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Stok = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Satuan = new javax.swing.JTextField();
        cmdSimpan = new javax.swing.JButton();
        cmdReset = new javax.swing.JButton();
        cmdRubah = new javax.swing.JButton();
        cmdHapus = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduk = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 153));
        jLabel1.setText("MASTER DATA PRODUK");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 300, -1));

        jLabel2.setText("KODE PRODUK");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));
        getContentPane().add(KdProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 280, -1));

        jLabel3.setText("KODE KATEGORI");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));
        getContentPane().add(KdKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 280, -1));

        jLabel4.setText("NAMA PRODUK");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        NmProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmProdukActionPerformed(evt);
            }
        });
        getContentPane().add(NmProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 280, -1));

        jLabel5.setText("HARGA BELI");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        HBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HBeliActionPerformed(evt);
            }
        });
        getContentPane().add(HBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 280, -1));

        jLabel6.setText("HARGA JUAL");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        HJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HJualActionPerformed(evt);
            }
        });
        getContentPane().add(HJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 280, -1));

        jLabel7.setText("STOK");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        Stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StokActionPerformed(evt);
            }
        });
        getContentPane().add(Stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 280, -1));

        jLabel9.setText("SATUAN");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, -1, -1));

        Satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SatuanActionPerformed(evt);
            }
        });
        getContentPane().add(Satuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 280, -1));

        cmdSimpan.setText("SIMPAN");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });
        getContentPane().add(cmdSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, -1));

        cmdReset.setText("RESET");
        cmdReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResetActionPerformed(evt);
            }
        });
        getContentPane().add(cmdReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, -1, -1));

        cmdRubah.setText("RUBAH");
        cmdRubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRubahActionPerformed(evt);
            }
        });
        getContentPane().add(cmdRubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, -1, -1));

        cmdHapus.setText("HAPUS");
        cmdHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusActionPerformed(evt);
            }
        });
        getContentPane().add(cmdHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, -1, -1));

        cmdKeluar.setText("KELUAR");
        cmdKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdKeluarMouseClicked(evt);
            }
        });
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(cmdKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, -1, -1));

        tblProduk.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdukMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProduk);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 480, 90));

        jLabel8.setIcon(new javax.swing.ImageIcon("E:\\#Kuliah\\Smt 4\\Pemrograman Open Source\\Desain-Lemari-Pakaian-Modern-Untuk-Desain-Interior-Modern-modern-wardrobe-design-ideas.jpg")); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NmProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmProdukActionPerformed

    private void cmdKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdKeluarMouseClicked
        // TODO add your handling code here:
         this.dispose();
         new menuAdmin().setVisible(true);
    }//GEN-LAST:event_cmdKeluarMouseClicked

    private void cmdResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_cmdResetActionPerformed

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:
        simpanDataProduk();
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdRubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRubahActionPerformed
        // TODO add your handling code here:
        rubahDataProduk();
    }//GEN-LAST:event_cmdRubahActionPerformed

    private void cmdHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusActionPerformed
        // TODO add your handling code here:
        hapusDataProduk();
    }//GEN-LAST:event_cmdHapusActionPerformed

    private void tblProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdukMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tblProdukMouseClicked

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void HJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HJualActionPerformed

    private void HBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HBeliActionPerformed

    private void SatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SatuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SatuanActionPerformed

    private void StokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StokActionPerformed

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
            java.util.logging.Logger.getLogger(formProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formProduk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField HBeli;
    private javax.swing.JTextField HJual;
    private javax.swing.JTextField KdKategori;
    private javax.swing.JTextField KdProduk;
    private javax.swing.JTextField NmProduk;
    private javax.swing.JTextField Satuan;
    private javax.swing.JTextField Stok;
    private javax.swing.JButton cmdHapus;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdReset;
    private javax.swing.JButton cmdRubah;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblProduk;
    // End of variables declaration//GEN-END:variables
}
