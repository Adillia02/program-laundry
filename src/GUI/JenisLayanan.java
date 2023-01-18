package GUI;


import java.sql.*;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adel
 */
public class JenisLayanan extends javax.swing.JInternalFrame {
    
    Connection conn;
    ResultSet rs;
    Statement st;
    String sql, namaLayanan, satuan, idLayanan;
    int estimasiWaktu, harga;

    /**
     * Creates new form JenisLayanan
     */
    public JenisLayanan() {
        initComponents();
        //Menghilangkan header JinternalFrame
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        
        tampilLayanan();
        
    }
    
    private String idLayanan(){
        String idLayan = "";
        String rpad = "";
        int no= 0;
        try {
            conn = Koneksi.getKoneksi(); 
            st = conn.createStatement();
            
            String angka = "LYN-0001";
            sql = "select max(substr(id_jenis_layanan, 5, 8)) as id_layanan from m_jenis_layanan";  
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                idLayan = rs.getString("id_layanan");
                
                if (idLayan == null) {
                    no = no + 1;
                }else{
                    no = Integer.parseInt(idLayan) + 1;
                }
                rpad = "select lpad('"+no+"', 4, '0') as id from dual";
                rs = st.executeQuery(rpad);
                rs.next();
                String id = rs.getString("id");
                idLayan = "LYN-"+ id;
                
            }    

            } catch (Exception e) {
                   JOptionPane.showMessageDialog(null,e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        return idLayan;
    
    }
    
    private void tambahLayanan(){
        conn = Koneksi.getKoneksi();
        idLayanan = tfIdLayanan.getText();
        namaLayanan = String.valueOf(tfNamaLayanan.getText());
        satuan = String.valueOf(cbSatuan.getSelectedItem());
        harga = Integer.parseInt(tfHargaLayanan.getText());
        estimasiWaktu = Integer.parseInt(tfEstimasiLayanan.getText());
                
        
            try {
                sql = "INSERT INTO m_jenis_layanan values"
                        + "('"+ idLayanan +"', '"+ namaLayanan +"', '"+satuan+"','"+ estimasiWaktu +"', '"+harga+"')";
                
                st=conn.createStatement();
                st.execute(sql);
                JOptionPane.showMessageDialog(this, "Data Jenis Layanan Berhasil Disimpan");
                tampilLayanan();
                bersihIsiForm();
            } catch (Exception e) {
                System.out.println(e);
            }
    
    }
    
    private void ubahLayanan(){
        conn = Koneksi.getKoneksi();
        idLayanan = tfIdLayanan.getText();
        namaLayanan = tfNamaLayanan.getText();
        satuan = String.valueOf(cbSatuan.getSelectedItem());
        estimasiWaktu = Integer.parseInt(tfEstimasiLayanan.getText());
        harga = Integer.parseInt(tfHargaLayanan.getText());
        
        try {
            sql = "Update m_jenis_layanan set "
                    + "nama_jenis_layanan = '"+namaLayanan+"',"
                    + "satuan = '"+satuan+"',"
                    + "estimasi_waktu = '"+estimasiWaktu+"',"
                    + "harga = '"+harga+"'"
                    + "where id_jenis_layanan = '"+idLayanan+"'";
            
            st = conn.createStatement();
            st.execute(sql);
            JOptionPane.showMessageDialog(this, "Data Jenis Layanan Berhasil Ditambahkan");
            tampilLayanan();
            bersihIsiForm();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    private void bersihIsiForm(){
        tfIdLayanan.setText(idLayanan());
        tfNamaLayanan.setText("");
        cbSatuan.setSelectedItem(null);
        tfEstimasiLayanan.setText("");
        tfHargaLayanan.setText("");
    }
    
    private void tampilLayanan(){
        conn = Koneksi.getKoneksi();
        DefaultTableModel data = new DefaultTableModel();
        data.addColumn ("id_jenis_layanan");
        data.addColumn ("nama_jenis_layanan");
        data.addColumn ("satuan");
        data.addColumn ("estimasi_waktu");
        data.addColumn ("harga");
        
        try{    
            st=conn.createStatement();
            rs=st.executeQuery("select * from m_jenis_layanan");
            while (rs.next())
                data.addRow(new Object[]{
                    rs.getString(1), 
                    rs.getString(2),
                    rs.getString(3), 
                    rs.getString(4),
                    rs.getString(5)
                });
            tbLayanan.setModel(data);
        } catch (Exception e){
            System.out.println(e);
        }
        tbLayanan.setDefaultEditor(Object.class, null);
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfIdLayanan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfNamaLayanan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfEstimasiLayanan = new javax.swing.JTextField();
        tfHargaLayanan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnSimpanLayanan = new javax.swing.JButton();
        btnUbahLayanan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbSatuan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbLayanan = new javax.swing.JTable();

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(38, 45));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jenis Layanan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 16))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel1.setText("Id Jenis Layanan");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel2.setText("Nama Jenis Layanan");

        tfNamaLayanan.setName("tfJenis"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel3.setText("Estimasi Waktu");

        tfEstimasiLayanan.setName("tfWaktu"); // NOI18N

        tfHargaLayanan.setName("tfHarga"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel4.setText("Harga");

        btnSimpanLayanan.setText("Simpan");
        btnSimpanLayanan.setName("btnSimpan"); // NOI18N
        btnSimpanLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanLayananActionPerformed(evt);
            }
        });

        btnUbahLayanan.setText("Ubah");
        btnUbahLayanan.setName("btnUbah"); // NOI18N
        btnUbahLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahLayananActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel5.setText("Hari");

        jLabel6.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel6.setText("Satuan");

        cbSatuan.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        cbSatuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pcs", "Kg" }));
        cbSatuan.setName("cbSatuan"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(tfEstimasiLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(tfIdLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(tfHargaLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbSatuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfNamaLayanan, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSimpanLayanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUbahLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfIdLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfNamaLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSimpanLayanan)
                        .addGap(29, 29, 29)
                        .addComponent(btnUbahLayanan)))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfEstimasiLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfHargaLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbLayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbLayanan.setName("tbLayanan"); // NOI18N
        tbLayanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLayananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbLayanan);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        btnUbahLayanan.setEnabled(false);
        tfIdLayanan.setText(idLayanan());
        tfIdLayanan.setEditable(false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnSimpanLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanLayananActionPerformed
        // TODO add your handling code here:
        tambahLayanan();
    }//GEN-LAST:event_btnSimpanLayananActionPerformed

    private void tbLayananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLayananMouseClicked
        // TODO add your handling code here:
        int index = tbLayanan.rowAtPoint(evt.getPoint());
        if (evt.getClickCount() == 1) {
            tfIdLayanan.setText(tbLayanan.getValueAt(index, 0).toString());
            tfNamaLayanan.setText(tbLayanan.getValueAt(index, 1).toString());
            cbSatuan.setSelectedItem(tbLayanan.getValueAt(index, 2));
            tfEstimasiLayanan.setText(tbLayanan.getValueAt(index, 3).toString());
            tfHargaLayanan.setText(tbLayanan.getValueAt(index, 4).toString());
        }
        tfIdLayanan.setEditable(false);
        btnSimpanLayanan.setEnabled(false);
        btnUbahLayanan.setEnabled(true);
    }//GEN-LAST:event_tbLayananMouseClicked

    private void btnUbahLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahLayananActionPerformed
        // TODO add your handling code here:
        ubahLayanan();
        tfIdLayanan.setEditable(true);
        btnSimpanLayanan.setEnabled(true);
        btnUbahLayanan.setEnabled(false);
        
    }//GEN-LAST:event_btnUbahLayananActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpanLayanan;
    private javax.swing.JButton btnUbahLayanan;
    private javax.swing.JComboBox<String> cbSatuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbLayanan;
    private javax.swing.JTextField tfEstimasiLayanan;
    private javax.swing.JTextField tfHargaLayanan;
    private javax.swing.JTextField tfIdLayanan;
    private javax.swing.JTextField tfNamaLayanan;
    // End of variables declaration//GEN-END:variables
}
