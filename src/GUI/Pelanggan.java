package GUI;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Pelanggan extends javax.swing.JInternalFrame {

    /**
     * Creates new form Pelanggan
     */
    Connection conn;
    ResultSet rs;
    Statement st;
    String sql, namaPelanggan, JK, noTelp, almt,tglLahir;
    String idPelanggan;

    public Pelanggan() {
        initComponents();
        //Menghilangkan header JinternalFrame
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        
        tampilPelanggan();
    }
    
    private String idPelanggan(){
        String idPel = "";
        String rpad = "";
        int no= 0;
        try {
            conn = Koneksi.getKoneksi(); 
            st = conn.createStatement();
            
//            String angka = "PL-0004";
            sql = "select max(substr(id_pelanggan, 4, 8)) as id_pelanggan from m_pelanggan";
            
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                idPel = rs.getString("id_pelanggan");
                
                if (idPel == null) {
                    no = no + 1;
                }else{
                    no = Integer.parseInt(idPel) + 1;
                }
                rpad = "select lpad('"+no+"', 4, '0') as id from dual";
                rs = st.executeQuery(rpad);
                rs.next();
                String id = rs.getString("id");
                idPel = "PL-"+ id;
            }    

            } catch (Exception e) {
                   JOptionPane.showMessageDialog(null,e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        return idPel;
    
    }
    
    private void tambahPelanggan(){
        conn = Koneksi.getKoneksi();
        idPelanggan = String.valueOf(tfIdPelanggan.getText());
        namaPelanggan = String.valueOf(tfNamaPelanggan.getText());
        if (rbLaki.isSelected()){
                JK = "L";
            } else if (rbPerempuan.isSelected()) {
                JK = "P";
        }

        noTelp = String.valueOf(tfNoPonsel.getText());
        almt = String.valueOf(taAlamat.getText());

        SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
        tglLahir = sdf.format(dcTglLahir.getDate());


            try {
                sql = "INSERT INTO m_pelanggan "
                        + "VALUES "
                        + "('"+idPelanggan+"','"+namaPelanggan+"', '"+JK+"', '"+noTelp+"','"+almt+"','"+tglLahir+"')";

                st=conn.createStatement();
                st.execute(sql);
                JOptionPane.showMessageDialog(this, "Data Pelanggan Berhasil Disimpan");
                tampilPelanggan();
                bersihkanIsiData();
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    private void ubahPelanggan(){
        conn = Koneksi.getKoneksi();
        idPelanggan = tfIdPelanggan.getText();
        namaPelanggan = tfNamaPelanggan.getText();
        JK = null;
        if (rbLaki.isSelected()) {
            JK = "L";
        }else if(rbPerempuan.isSelected()){
            JK = "P";
        }
        noTelp = tfNoPonsel.getText();
        almt = taAlamat.getText();
        
        SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
        tglLahir = sdf.format(dcTglLahir.getDate());

        try {
            sql = "UPDATE m_pelanggan set "
                    + "nama_pelanggan = '"+namaPelanggan+"',"
                    + "jenis_kelamin = '"+JK+"',"
                    + "no_telp = '"+noTelp+"',"
                    + "alamat = '"+almt+"',"
                    + "tgl_lahir = '"+tglLahir+"'"
                    + "where id_pelanggan = '"+idPelanggan+"'";

            st = conn.createStatement();
            st.execute(sql);
            System.out.println(tglLahir);
            JOptionPane.showMessageDialog(this, "Data Pelanggan Berhasil Diubah");
        } catch (Exception e) {
            System.out.println(e);
        }

        tampilPelanggan();
        bersihkanIsiData();


    }
    
    private void bersihkanIsiData(){
        tfIdPelanggan.setText(idPelanggan());
        tfNamaPelanggan.setText("");
        rbLaki.setSelected(true);
        tfNoPonsel.setText("");
        taAlamat.setText("");
        dcTglLahir.setDate(null);
    }

    private void tampilPelanggan(){
        conn = Koneksi.getKoneksi();
        DefaultTableModel data = new DefaultTableModel();
        data.addColumn ("id_pelanggan");
        data.addColumn ("nama_pelanggan");
        data.addColumn ("jenis_kelamin");
        data.addColumn ("no_telp");
        data.addColumn ("alamat");
        data.addColumn ("tgl_lahir");

        try{
            st=conn.createStatement();
            rs=st.executeQuery("select * from m_pelanggan");
            while (rs.next())
                data.addRow(new Object[]{
                    rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6)
                });
            tbPelanggan.setModel(data);
        } catch (Exception e){
            System.out.println(e);
        }

        tbPelanggan.setDefaultEditor(Object.class, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgJK = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfIdPelanggan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfNamaPelanggan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfNoPonsel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnSimpanLayanan = new javax.swing.JButton();
        btnUbahLayanan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dcTglLahir = new com.toedter.calendar.JDateChooser();
        rbLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taAlamat = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPelanggan = new javax.swing.JTable();

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(733, 648));
        setMinimumSize(new java.awt.Dimension(733, 648));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pelanggan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 16))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel1.setText("Id Pelanggan");

        tfIdPelanggan.setName("tfId"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel2.setText("Nama Pelanggan");

        tfNamaPelanggan.setName("tfNamaPelanggan"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel3.setText("Jenis Kelamin");

        tfNoPonsel.setName("tfPonsel"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel4.setText("No Ponsel");

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
        jLabel5.setText("Alamat");

        jLabel6.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel6.setText("Tgl.Lahir");

        dcTglLahir.setDateFormatString("yyyy-MM-dd");
        dcTglLahir.setName("dcLahir"); // NOI18N

        bgJK.add(rbLaki);
        rbLaki.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        rbLaki.setText("Laki-Laki");
        rbLaki.setName("rbLaki"); // NOI18N

        bgJK.add(rbPerempuan);
        rbPerempuan.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        rbPerempuan.setText("Perempuan");
        rbPerempuan.setName("rbPerempuan"); // NOI18N

        taAlamat.setColumns(20);
        taAlamat.setRows(5);
        taAlamat.setName("taAlamat"); // NOI18N
        jScrollPane2.setViewportView(taAlamat);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(tfIdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfNamaPelanggan)
                                    .addComponent(rbLaki, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rbPerempuan, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNoPonsel)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(dcTglLahir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpanLayanan)
                        .addGap(39, 39, 39)
                        .addComponent(btnUbahLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(tfIdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(tfNoPonsel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(rbLaki))
                        .addGap(10, 10, 10)
                        .addComponent(rbPerempuan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel6))
                            .addComponent(dcTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanLayanan)
                    .addComponent(btnUbahLayanan))
                .addGap(16, 16, 16))
        );

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPelangganMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPelanggan);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        btnUbahLayanan.setEnabled(false);
        rbLaki.setSelected(true);
        tfIdPelanggan.setEditable(false);
        tfIdPelanggan.setText(idPelanggan());
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnSimpanLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanLayananActionPerformed
        // TODO add your handling code here:
        tambahPelanggan();

    }//GEN-LAST:event_btnSimpanLayananActionPerformed

    private void tbPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPelangganMouseClicked
        // TODO add your handling code here:
        int index = tbPelanggan.rowAtPoint(evt.getPoint());
        if (evt.getClickCount() == 1) {
            tfIdPelanggan.setText(tbPelanggan.getValueAt(index, 0).toString());
            tfNamaPelanggan.setText(tbPelanggan.getValueAt(index, 1).toString());
            if ("L".equals(tbPelanggan.getValueAt(index, 2).toString())) {
                rbLaki.setSelected(true);
            }else{
                rbPerempuan.setSelected(true);
            }

            tfNoPonsel.setText(tbPelanggan.getValueAt(index, 3).toString());
            taAlamat.setText(tbPelanggan.getValueAt(index, 4).toString());

            try {
                String tgl = tbPelanggan.getValueAt(index, 5).toString();
                Date tgl_lahir = new SimpleDateFormat("yyyy-MM-dd").parse(tgl);
                dcTglLahir.setDate(tgl_lahir);
            } catch (Exception e) {
                System.out.println(e);
            }



        }
        tfIdPelanggan.setEditable(false);
        btnSimpanLayanan.setEnabled(false);
        btnUbahLayanan.setEnabled(true);

    }//GEN-LAST:event_tbPelangganMouseClicked

    private void btnUbahLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahLayananActionPerformed
        // TODO add your handling code here:
        ubahPelanggan();
        tfIdPelanggan.setEditable(false);
        btnSimpanLayanan.setEnabled(true);
        btnUbahLayanan.setEnabled(false);
    }//GEN-LAST:event_btnUbahLayananActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgJK;
    private javax.swing.JButton btnSimpanLayanan;
    private javax.swing.JButton btnUbahLayanan;
    private com.toedter.calendar.JDateChooser dcTglLahir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTextArea taAlamat;
    private javax.swing.JTable tbPelanggan;
    private javax.swing.JTextField tfIdPelanggan;
    private javax.swing.JTextField tfNamaPelanggan;
    private javax.swing.JTextField tfNoPonsel;
    // End of variables declaration//GEN-END:variables
}
