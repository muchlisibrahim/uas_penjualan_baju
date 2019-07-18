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


public class formTransaksi extends javax.swing.JFrame {

    private DefaultTableModel model;
    
    String noJual, kdProduk, nmProduk, xtotal;
    int hrg_jual, jml;
    double total, bayar, kembali, sTotal;
    
    /**
     * Creates new form formTransaksi
     */
    public formTransaksi() {
        initComponents();
        
        model = new DefaultTableModel();
        
        tblTransaksi.setModel(model);
        model.addColumn("NO JUAL");
        model.addColumn("KODE PRODUK");
        model.addColumn("NAMA PRODUK");
        model.addColumn("HARGA JUAL");
        model.addColumn("QTY");
        model.addColumn("TOTAL");
        model.addColumn("BAYAR");
        model.addColumn("KEMBALI");
        
        getDataTransaksi();
    }
    
    
    public void dataProduk(){   
        try{
            //tes KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel produk
            String sql = "SELECT * FROM tbl_produk WHERE kd_produk = '"+ KdProduk.getText() +"'";
            ResultSet res = stat.executeQuery(sql);
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               NmProduk.setText(res.getString("nm_produk"));
               Hrg.setText(res.getString("hrg_jual"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    public void getDataTransaksi(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi KoneksiDB dan kirimkan query ke database
        try{
            //tes KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM tbl_transaksi";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[8];
                obj[0]=res.getString("no_jual");
                obj[1]=res.getString("kd_produk");
                obj[2]=res.getString("nm_produk");
                obj[3]=res.getString("hrg_jual");
                obj[4]=res.getString("qty");
                obj[5]=res.getString("total");
                obj[6]=res.getString("bayar");
                obj[6]=res.getString("kembali");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    public void masukTabel(){
        try{
            String noJual=NoJual.getText();
            String kdProduk=KdProduk.getText();
            String nmProduk=NmProduk.getText();
            double hrg=Double.parseDouble(Hrg.getText());
            int jml=Integer.parseInt(Jml.getText());
            sTotal = hrg*jml;
            total = total + sTotal;
            xtotal=Double.toString(total);
            lblTotal.setText(xtotal);
            model.addRow(new Object[]{noJual,kdProduk,nmProduk,hrg,jml,sTotal});
        }
        catch(Exception e){
            System.out.println("Error : "+e);
        }
    }
    
    public void simpanDataTransaksi(){
        loadDataTransaksi();
        
        //proses perhitungan uang kembalian
        bayar = Double.parseDouble(Bayar.getText());
        kembali = bayar - total;
        String xkembali=Double.toString(kembali);
        lblKembali.setText(xkembali);
       
        //uji KoneksiDB dan eksekusi perintah
        try{
            //test KoneksiDB
            Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO tbl_transaksi(no_jual,kd_produk, nm_produk, hrg_jual, qty, total, bayar, kembali)"
                            + "VALUES('"+ NoJual.getText() +"','"+ KdProduk.getText() +"','"+ NmProduk.getText() +"','"+ Hrg.getText() +"','"+ Jml.getText() +"','"+ total +"', '"+ Bayar.getText() +"', '"+ kembali +"')";
            PreparedStatement p = (PreparedStatement) KoneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void dataSelect(){
        //deklarasi variabel
        int i = tblTransaksi.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        NoJual.setText(""+model.getValueAt(i, 0));
        KdProduk.setText(""+model.getValueAt(i,1));
        NmProduk.setText(""+model.getValueAt(i,2));
        Hrg.setText(""+model.getValueAt(i,3));
        Jml.setText(""+model.getValueAt(i,4));
        lblTotal.setText(""+model.getValueAt(i,5));     
    }
    public void hapusDataTransaksi(){
        //panggil fungsi ambil data
        loadDataTransaksi(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ noJual +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji KoneksiDB
            try{
                //buka KoneksiDB ke database
                Statement stat = (Statement) KoneksiDB.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM tbl_transaksi WHERE no_jual='"+ noJual +"'";
                PreparedStatement p =(PreparedStatement)KoneksiDB.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                //fungsi ambil data
                getDataTransaksi();
                
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

        jLabel1 = new javax.swing.JLabel();
        NoJual = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        KdProduk = new javax.swing.JTextField();
        label2 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        NmProduk = new javax.swing.JTextField();
        label = new javax.swing.JLabel();
        Bayar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Hrg = new javax.swing.JTextField();
        label1 = new javax.swing.JLabel();
        lblKembali = new javax.swing.JTextField();
        tblSimpan = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        cmdKeranjang = new javax.swing.JButton();
        Jml = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        tblKeluar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 255));
        jLabel1.setText("TRANSAKSI PENJUALAN BAJU");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        NoJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoJualActionPerformed(evt);
            }
        });
        getContentPane().add(NoJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 460, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 204));
        jLabel2.setText("NOMOR PENJUALAN");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 204));
        jLabel3.setText("KODE PRODUK");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        KdProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdProdukActionPerformed(evt);
            }
        });
        KdProduk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                KdProdukKeyReleased(evt);
            }
        });
        getContentPane().add(KdProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 210, -1));

        label2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 0, 204));
        label2.setText("TOTAL BAYAR");
        getContentPane().add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, -1, -1));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 150, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 204));
        jLabel4.setText("NAMA PRODUK");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        NmProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmProdukActionPerformed(evt);
            }
        });
        getContentPane().add(NmProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 210, -1));

        label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label.setForeground(new java.awt.Color(255, 0, 204));
        label.setText("BAYAR");
        getContentPane().add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, -1, -1));
        getContentPane().add(Bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, 150, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 204));
        jLabel5.setText("HARGA");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));
        getContentPane().add(Hrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 210, -1));

        label1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 0, 204));
        label1.setText("KEMBALIAN");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, -1, -1));
        getContentPane().add(lblKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 150, -1));

        tblSimpan.setText("SIMPAN");
        tblSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblSimpanActionPerformed(evt);
            }
        });
        getContentPane().add(tblSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 70, -1));

        hapus.setText("HAPUS");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        getContentPane().add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 70, -1));

        cmdKeranjang.setText("BELI");
        cmdKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeranjangActionPerformed(evt);
            }
        });
        getContentPane().add(cmdKeranjang, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 70, -1));

        Jml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmlActionPerformed(evt);
            }
        });
        getContentPane().add(Jml, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 210, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 204));
        jLabel6.setText("JUMLAH");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseReleased(evt);
            }
        });
        tblTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblTransaksiKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblTransaksi);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 580, 110));

        tblKeluar.setText("KELUAR");
        tblKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKeluarMouseClicked(evt);
            }
        });
        tblKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(tblKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 330, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon("E:\\#Kuliah\\Smt 4\\Pemrograman Open Source\\kontroversi-toko-fesyen-tersohor-cuma-jual-baju-ukuran-kecil-141030v.jpg")); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void KdProdukKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdProdukKeyReleased
        //memanggil fungsi data produk
        dataProduk();
    }//GEN-LAST:event_KdProdukKeyReleased

    private void cmdKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeranjangActionPerformed
        //memanggil fungsi masuk tabel sementara
        masukTabel();
    }//GEN-LAST:event_cmdKeranjangActionPerformed

    private void tblSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblSimpanActionPerformed
        //memanggil fungsi simpan data transaksi
        simpanDataTransaksi();
    }//GEN-LAST:event_tblSimpanActionPerformed

    private void NmProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmProdukActionPerformed

    private void JmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JmlActionPerformed

    private void tblKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKeluarActionPerformed

    private void tblKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKeluarMouseClicked
        // TODO add your handling code here:
        this.dispose();
         new menuKasir().setVisible(true);
    }//GEN-LAST:event_tblKeluarMouseClicked

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        hapusDataTransaksi();
    }//GEN-LAST:event_hapusActionPerformed

    private void tblTransaksiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblTransaksiKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblTransaksiKeyReleased

    private void tblTransaksiMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseReleased
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tblTransaksiMouseReleased

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tblTransaksiMouseClicked

    private void NoJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoJualActionPerformed

    private void KdProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdProdukActionPerformed

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
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Bayar;
    private javax.swing.JTextField Hrg;
    private javax.swing.JTextField Jml;
    private javax.swing.JTextField KdProduk;
    private javax.swing.JTextField NmProduk;
    private javax.swing.JTextField NoJual;
    private javax.swing.JButton cmdKeranjang;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JTextField lblKembali;
    private javax.swing.JTextField lblTotal;
    private javax.swing.JButton tblKeluar;
    private javax.swing.JButton tblSimpan;
    private javax.swing.JTable tblTransaksi;
    // End of variables declaration//GEN-END:variables

    private void loadDataTransaksi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    private void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
