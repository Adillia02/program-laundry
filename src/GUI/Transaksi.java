package GUI;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
public class Transaksi extends javax.swing.JInternalFrame {

    /**
     * Creates new form Transaksi
     */
        Connection conn;
        ResultSet rs, rs1;
        Statement st, st1;
        HashMap <String, String> Dept = new HashMap <String, String>();
        String sql, sql1, idTransaksi, idPelanggan, idJenisLayanan, nomDiskon, nomTanpaDiskon;
        int nom, satuanHarga;

    public Transaksi() {
        initComponents();
        //Menghilangkan header JinternalFrame
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        
        tampilData();
        tampilComboPelanggan();
        tampilComboJenisLayanan();
//        lihatSatuan();
        satuanLayanan();
        tampilData();
        System.out.println(idTransaksi());
        
    }
    
    private String idTransaksi(){
        String idTrans = "";
        String rpad = "";
        int no= 0;
        try {
            conn = Koneksi.getKoneksi(); 
            st = conn.createStatement();
            
//            String angka = "#TRO-0004";
            sql = "select max(substr(id_transaksi, 6, 9)) as id_transaksi from transaksi";
            
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                idTrans = rs.getString("id_transaksi");
                
                if (idTrans == null) {
                    no = no + 1;
                }else{
                    no = Integer.parseInt(idTrans) + 1;
                }
                rpad = "select lpad('"+no+"', 4, '0') as id from dual";
                rs = st.executeQuery(rpad);
                rs.next();
                String id = rs.getString("id");
                idTrans = "#TRO-"+ id;
            }    

            } catch (Exception e) {
                   JOptionPane.showMessageDialog(null,e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        return idTrans;
    
    }
    
    private void c(){
        String bulanLhr = "";
        String bulanIni = "";
        int jumlah = 0;
        Double total = 0.0;
        conn = Koneksi.getKoneksi();
        try {
            sql = "select harga from m_jenis_layanan where id_jenis_layanan = '"+cbJenisLayanan.getSelectedItem()+"'";
            st = conn.createStatement();
            rs1 = st.executeQuery(sql);
            while (rs1.next()) {                
                satuanHarga = rs1.getInt(1);     
            }
            System.out.println(satuanHarga);
            
            sql = "select month(tgl_lahir) as bulan from m_pelanggan where id_pelanggan = '"+cbPelanggan.getSelectedItem()+"'"; 
            System.out.println(sql);
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                bulanLhr = rs.getString("bulan");
                System.out.println(bulanLhr);
            }
            
            sql = "select month(curdate()) as bulanIni from dual";
            rs = st.executeQuery(sql);
            while(rs.next()){
                bulanIni = rs.getString("bulanIni");
                System.out.println("bulan ini"+ bulanIni);
            }
            
            sql = "select count(*) as jumlah from transaksi where id_pelanggan = '"+cbPelanggan.getSelectedItem()+"'";
            rs = st.executeQuery(sql);
            while (rs.next()) {                
                jumlah = rs.getInt("jumlah");
            }
            
            Double berat = Double.parseDouble(tfBerat.getText());
            if (bulanLhr.equals(bulanIni) ) {
                if(jumlah >= 2){
                    System.out.println("Pemesanan = "+jumlah);
//                    JOptionPane.showMessageDialog(this, "Bulan Sama");
                    System.out.println("bulan sama"+ bulanLhr + ""+ bulanIni);

                    Double diskon = 0.7;
                    total = (satuanHarga*berat)*diskon;
                    
                }else{
                    total = satuanHarga*berat;
                    
                }
            }else{
                total = satuanHarga*berat;
            }
            
            lTotalHarga.setText(total.toString());
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void tambahTran(String id_Transaksi, String id_Pelanggan, String idJenis_Layanan, int berat, int nominal){        
        String bulanLhr = "";
        String bulanIni = "";
        int jumlah = 0;
        conn = Koneksi.getKoneksi();
        try {
            sql = "select harga from m_jenis_layanan where id_jenis_layanan = '"+idJenis_Layanan+"'";
            st = conn.createStatement();
            rs1 = st.executeQuery(sql);
            while (rs1.next()) {                
                satuanHarga = rs1.getInt(1);     
            }
            System.out.println(satuanHarga);
            
            sql = "select month(tgl_lahir) as bulan from m_pelanggan where id_pelanggan = '"+id_Pelanggan+"'"; 
            System.out.println(sql);
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                bulanLhr = rs.getString("bulan");
                System.out.println(bulanLhr);
            }
            
            sql = "select month(curdate()) as bulanIni from dual";
            rs = st.executeQuery(sql);
            while(rs.next()){
                bulanIni = rs.getString("bulanIni");
                System.out.println("bulan ini"+ bulanIni);
            }
            
            sql = "select count(*) as jumlah from transaksi where id_pelanggan = '"+id_Pelanggan+"'";
            rs = st.executeQuery(sql);
            while (rs.next()) {                
                jumlah = rs.getInt("jumlah");
            }
            
            Double berat1 = Double.parseDouble(tfBerat.getText());
            nomTanpaDiskon = String.valueOf(satuanHarga*berat1);
            if (bulanLhr.equals(bulanIni) ) {
                if(jumlah >= 2){
                    System.out.println("Pemesanan = "+jumlah);
//                    JOptionPane.showMessageDialog(this, "Bulan Sama");
                    System.out.println("bulan sama"+ bulanLhr + ""+ bulanIni);

                    Double diskon = 0.7;
                    
                    nomDiskon = String.valueOf((satuanHarga*berat1)*diskon);

                    sql1 = "insert INTO transaksi values('"
                        + id_Transaksi +"', '"+ id_Pelanggan +"', '"+idJenis_Layanan+"', curdate(),"+berat+", "+(satuanHarga*berat1)*diskon+", "+nomDiskon+")";
                    
                    lTotalHarga.setText(nomDiskon);
                }else{
                    System.out.println("Pesanan masih kurang "+ jumlah);
                     sql1 = "insert INTO transaksi values('"
                    + id_Transaksi +"', '"+ id_Pelanggan +"', '"+idJenis_Layanan+"', curdate(),"+berat+", "+satuanHarga*berat+", "+nomTanpaDiskon+")";
                     lTotalHarga.setText(nomTanpaDiskon);
                }
            }else{
                sql1 = "insert INTO transaksi values('"
                    + id_Transaksi +"', '"+ id_Pelanggan +"', '"+idJenis_Layanan+"', curdate(),"+berat+", "+satuanHarga*berat+", "+nomTanpaDiskon+")";
                lTotalHarga.setText(nomTanpaDiskon);
//                JOptionPane.showMessageDialog(this, "Bulan tidak sama");
            }
            
            st = conn.createStatement();
            st.execute(sql1);
            
            
            
            JOptionPane.showMessageDialog(this, "Data Transaksi Berhasil Ditambahkan"); 
            tampilData();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    
    }
    
    //Function tampilId untuk semua combobox
    private String idCombobox(String tabel, String idKolom, String namaKolom, String namaCombo){
        String result  = "";
        try {
            conn = Koneksi.getKoneksi(); 
            st = conn.createStatement();
            
            
            sql = "select "+idKolom+" from "+tabel+" where "+namaKolom+" = '"+namaCombo+"'";
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                result = rs.getString(idKolom);
            }    

            } catch (Exception e) {
                   JOptionPane.showMessageDialog(null,e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        return result;
    }
     
     //function Satuan Layanan
    private String satuanLayanan(){
        String satuan = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT satuan FROM m_jenis_layanan WHERE nama_jenis_layanan = '" +cbJenisLayanan.getSelectedItem().toString() +"'");
            
            if (rs == null) {
                
            }else{
                while(rs.next()){
                    satuan = rs.getString("satuan");
                }
            }
        } catch (Exception e) {
//            System.out.println(e);
        }
        return satuan;
    }
    
    private void bersihkanIsiData(){
        tfIdTransaksi.setText(idTransaksi());
        tfBerat.setText("");
        lHarga.setText("");
        lSatuan.setText("");
        lSatuan1.setText("/ ");
        lTotalHarga.setText("Rp 0 ");
        tfNominal.setText("Rp 0 ");
        lKembalian.setText("Rp 0 ");
    }
    
    private void tampilData(){
        conn = Koneksi.getKoneksi();
        DefaultTableModel data = new DefaultTableModel();
        data.addColumn ("id_transaksi");
        data.addColumn ("id_pelanggan");
        data.addColumn ("id_jenis_layanan");
        data.addColumn ("tgl_bayar");
        data.addColumn ("jumlah");
        data.addColumn ("total_bayar");

        try{
            st=conn.createStatement();
            rs=st.executeQuery("select * from transaksi");
            while (rs.next())
                data.addRow(new Object[]{
                    rs.getString(1), 
                    rs.getString(2),
                    rs.getString(3), 
                    rs.getString(4),
                    rs.getString(5), 
                    "Rp "+ rs.getString(6)
                });
            tbTransaksi.setModel(data);
        } catch (Exception e){
            System.out.println(e);
        }

        tbTransaksi.setDefaultEditor(Object.class, null);
    }
    
    private void tampilComboPelanggan(){
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from m_pelanggan order by id_pelanggan asc");
            
            cbPelanggan.removeAllItems();
            while(rs.next()){
                String Id = rs.getString("nama_pelanggan");
                cbPelanggan.addItem(Id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void tampilComboJenisLayanan(){
        try {
            st1 = conn.createStatement();
            rs1 = st1.executeQuery("select * from m_jenis_layanan order by id_jenis_layanan asc");
            
            cbJenisLayanan.removeAllItems();
            while(rs1.next()){
                String Id = rs1.getString("nama_jenis_layanan");
                cbJenisLayanan.addItem(Id);
            }
        } catch (Exception e) {
            System.out.println(e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfIdTransaksi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfTglBayar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        BtnCekTotal = new javax.swing.JButton();
        cbPelanggan = new javax.swing.JComboBox<>();
        cbJenisLayanan = new javax.swing.JComboBox<>();
        tfBerat = new javax.swing.JTextField();
        lSatuan = new javax.swing.JLabel();
        btnMasukkan = new javax.swing.JButton();
        tfNominal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lHarga = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lSatuan1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lTotalHarga = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lKembalian = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTransaksi = new javax.swing.JTable();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 16))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel1.setText("id Transaksi");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel2.setText("Pelanggan");

        jLabel3.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel3.setText("Jenis Layanan");

        tfTglBayar.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel4.setText("Tgl.Bayar");

        BtnCekTotal.setText("Cek");
        BtnCekTotal.setName("btnCek"); // NOI18N
        BtnCekTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnCekTotalMouseClicked(evt);
            }
        });

        cbPelanggan.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        cbPelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbPelanggan.setName("cbPelanggan"); // NOI18N

        cbJenisLayanan.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        cbJenisLayanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbJenisLayanan.setName("cbLayanan"); // NOI18N
        cbJenisLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJenisLayananActionPerformed(evt);
            }
        });

        tfBerat.setName("tfJumlah"); // NOI18N

        lSatuan.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lSatuan.setText("Satuan");

        btnMasukkan.setText("Simpan");
        btnMasukkan.setName("btnTambah"); // NOI18N
        btnMasukkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasukkanActionPerformed(evt);
            }
        });

        tfNominal.setFont(new java.awt.Font("Roboto Light", 0, 20)); // NOI18N
        tfNominal.setName("tfUang"); // NOI18N
        tfNominal.setPreferredSize(new java.awt.Dimension(6, 45));
        tfNominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfNominalKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N
        jLabel5.setText("Jumlah");

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 16)); // NOI18N
        jLabel6.setText("Uang");

        lHarga.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel10.setText("Rp");

        lSatuan1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        lSatuan1.setText("/");

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 16)); // NOI18N
        jLabel7.setText("Total Harga");

        lTotalHarga.setFont(new java.awt.Font("Roboto Light", 0, 20)); // NOI18N
        lTotalHarga.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Roboto Light", 1, 16)); // NOI18N
        jLabel8.setText("Kembalian");

        lKembalian.setFont(new java.awt.Font("Roboto Light", 0, 20)); // NOI18N
        lKembalian.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tfBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(lSatuan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(BtnCekTotal))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbPelanggan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbJenisLayanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfIdTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)
                                        .addComponent(lHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lSatuan1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(tfNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(lKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(lTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(tfTglBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMasukkan)
                        .addGap(104, 104, 104))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfTglBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(198, 198, 198)
                        .addComponent(btnMasukkan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(cbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbJenisLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lSatuan)
                                    .addComponent(jLabel5)
                                    .addComponent(BtnCekTotal)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lSatuan1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(39, 39, 39))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(lHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(44, 44, 44))))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel8)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfNominal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lKembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(15, 15, 15))
        );

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbTransaksi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        tfTglBayar.setEditable(false);
        tfIdTransaksi.setText(idTransaksi());
        tfIdTransaksi.setEditable(false);
        lHarga.setText("0");
//        lSatuan.setText("Satuan");
        try {
            conn = Koneksi.getKoneksi();
            sql = "select DATE_FORMAT(SYSDATE(), '%Y-%m-%d')";
            st=conn.createStatement();
            rs = st.executeQuery(sql);
            
            rs.next();
            tfTglBayar.setText(rs.getString(1));
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void cbJenisLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJenisLayananActionPerformed
        // TODO add your handling code here:
        int harga = 0;
        try{
            conn = Koneksi.getKoneksi(); 
            st = conn.createStatement(); 
            sql = "select harga from m_jenis_layanan where nama_jenis_layanan='"+cbJenisLayanan.getSelectedItem()+"'";
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                harga = rs.getInt("harga");
                lHarga.setText(rs.getString("harga"));
//                lSatuan.setText(rs.getString("satuan"));
            }            
            } catch (Exception e) {
                   JOptionPane.showMessageDialog(null,e, "Error", JOptionPane.ERROR_MESSAGE);
            }
//        lHarga.setText(String.valueOf("Rp "+satuanHarga+ " /"+satuanLayanan()));
           lSatuan.setText(satuanLayanan());
           lSatuan1.setText("/ "+satuanLayanan());
    }//GEN-LAST:event_cbJenisLayananActionPerformed

    private void btnMasukkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasukkanActionPerformed
        String id_transaksi = tfIdTransaksi.getText();
        String pelanggan = idCombobox("m_pelanggan", "id_pelanggan", "nama_pelanggan", cbPelanggan.getSelectedItem().toString());
        String layanan = idCombobox("m_jenis_layanan", "id_jenis_layanan", "nama_jenis_layanan", cbJenisLayanan.getSelectedItem().toString());
        int berat = Integer.parseInt(tfBerat.getText());
        nom = Integer.parseInt(tfNominal.getText());
        
        tambahTran(id_transaksi, pelanggan, layanan, berat, nom);
        bersihkanIsiData();
    }//GEN-LAST:event_btnMasukkanActionPerformed

    private void tfNominalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNominalKeyReleased
        // TODO add your handling code here:
        if(!tfNominal.getText().equals("") && !lTotalHarga.getText().equals("")){
            int uang = Integer.parseInt(tfNominal.getText());
            double totHarga = Double.parseDouble(lTotalHarga.getText());
            
            if(uang>=totHarga){
                lKembalian.setText(String.valueOf(uang - totHarga));
            }else{
                lKembalian.setText("Uang Kurang");
            }
           

        }else{
            lKembalian.setText("error");
        }
        
    }//GEN-LAST:event_tfNominalKeyReleased

    private void BtnCekTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnCekTotalMouseClicked
        // TODO add your handling code here:
        String pelanggan = idCombobox("m_pelanggan", "id_pelanggan", "nama_pelanggan", cbPelanggan.getSelectedItem().toString());
        String layanan = idCombobox("m_jenis_layanan", "id_jenis_layanan", "nama_jenis_layanan", cbJenisLayanan.getSelectedItem().toString());
        String bulanLhr = "";
        String bulanIni = "";
        int jumlah = 0;
        Double total, diskon = 0.0;
        conn = Koneksi.getKoneksi();
        try {
            sql = "select harga from m_jenis_layanan where id_jenis_layanan = '"+layanan+"'";
            st = conn.createStatement();
            rs1 = st.executeQuery(sql);
            while (rs1.next()) {                
                satuanHarga = rs1.getInt(1);     
            }
            System.out.println(satuanHarga);
            
            sql = "select month(tgl_lahir) as bulan from m_pelanggan where id_pelanggan = '"+pelanggan+"'"; 
//            System.out.println(sql);
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                bulanLhr = rs.getString("bulan");
                System.out.println(bulanLhr); 
            }
            
            sql = "select month(curdate()) as bulanIni from dual";
            rs = st.executeQuery(sql);
            while(rs.next()){
                bulanIni = rs.getString("bulanIni");
                System.out.println("bulan ini"+ bulanIni);
            }
            
            sql = "select count(*) as jumlah from transaksi where id_pelanggan = '"+pelanggan+"'";
            rs = st.executeQuery(sql);
            while (rs.next()) {                
                jumlah = rs.getInt("jumlah");
            }
            
            int berat = Integer.parseInt(tfBerat.getText());
            if (bulanLhr.equals(bulanIni) ) {
                if(jumlah >= 2){
                    System.out.println("Pemesanan = "+jumlah);
                    JOptionPane.showMessageDialog(this, "Anda Sedang Berulang Tahun Bulan Ini (: Selamat Ulang Tahun !!!");
                    System.out.println("bulan sama"+ bulanLhr + ""+ bulanIni);

                    diskon = 0.7;
                    total = (satuanHarga*berat)*diskon;
                    
                    
                }else{
                    total = Double.parseDouble(String.valueOf(satuanHarga*berat));
                    
                }
            }else{
                total = Double.parseDouble(String.valueOf(satuanHarga*berat));
            }
            
            lTotalHarga.setText(total.toString());
//            System.out.println(satuanHarga);
//            System.out.println(berat);
//            System.out.println("Harga sebelum dis"+(satuanHarga*berat));
//            System.out.println((satuanHarga*berat)*diskon);
//            System.out.println(total);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_BtnCekTotalMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCekTotal;
    private javax.swing.JButton btnMasukkan;
    private javax.swing.JComboBox<String> cbJenisLayanan;
    private javax.swing.JComboBox<String> cbPelanggan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lHarga;
    private javax.swing.JLabel lKembalian;
    private javax.swing.JLabel lSatuan;
    private javax.swing.JLabel lSatuan1;
    private javax.swing.JLabel lTotalHarga;
    private javax.swing.JTable tbTransaksi;
    private javax.swing.JTextField tfBerat;
    private javax.swing.JTextField tfIdTransaksi;
    private javax.swing.JTextField tfNominal;
    private javax.swing.JTextField tfTglBayar;
    // End of variables declaration//GEN-END:variables
}
