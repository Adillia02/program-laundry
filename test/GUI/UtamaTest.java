/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import org.fest.swing.core.MouseButton;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adel
 */
public class UtamaTest {
    FrameFixture window;
    
    public UtamaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        window = new FrameFixture(new Utama());
        Utama utama = new Utama();
        Dimension Pelanggan = new Dimension(utama.getSize());
        window.show(Pelanggan);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Utama.
     */
    @Test
    public void testMain() {
        window.panel("pBeranda").click();
        System.out.println("=====Beranda=====");
//        window.panel("pJenisLayanan").click();
//        window.panel("pPelanggan").click();
//        window.panel("pTransaksi").click();
//        window.panel("pLaporan").click();
//        
    }
    
    @After
    public void clean() {
        window.cleanUp();
    }
    
    @Test
    public void tambahJenisLayanan(){
        System.out.println("====Tambah Jenis Layanan====");
        window.panel("pJenisLayanan").click();
        window.textBox("tfJenis").enterText("Cuci Kering");
        window.comboBox("cbSatuan").selectItem("Kg");
        window.textBox("tfWaktu").enterText("2");
        window.textBox("tfHarga").enterText("20000");
        window.button("btnSimpan").click();
        window.optionPane().requireMessage("Data Jenis Layanan Berhasil Disimpan");
        window.optionPane().okButton().click();
        
        System.out.println("====Ubah Jenis Layanan====");
        window.robot.moveMouse(800, 550);
        window.robot.pressMouse(MouseButton.LEFT_BUTTON);
        window.robot.releaseMouse(MouseButton.LEFT_BUTTON);
        window.robot.enterText("2001-01-21");
        window.textBox("tfJenis").setText("");
        window.textBox("tfJenis").enterText("Cuci Seprai");
        window.comboBox("cbSatuan").selectItem("Kg");
        window.textBox("tfWaktu").setText("");
        window.textBox("tfWaktu").enterText("3");
        window.textBox("tfHarga").setText("");
        window.textBox("tfHarga").enterText("35000");
        window.button("btnUbah").click();
        window.optionPane().requireMessage("Data Jenis Layanan Berhasil Ditambahkan");
        window.optionPane().okButton().click();

    }
    
    @After
    public void clean2() {
        window.cleanUp();
    }
    
    @Test
    public void Pelanggan(){
        System.out.println("====Tambah Pelanggan====");
        window.panel("pPelanggan").click();
        window.textBox("tfNamaPelanggan").enterText("Amiroh Adillia");
        window.radioButton("rbLaki").click();
        window.textBox("tfPonsel").enterText("087512345678");
        window.textBox("taAlamat").enterText("Surabaya");
        window.robot.moveMouse(800, 360);
        window.robot.pressMouse(MouseButton.LEFT_BUTTON);
        window.robot.releaseMouse(MouseButton.LEFT_BUTTON);
        window.robot.enterText("2001-01-21");

        window.button("btnSimpan").click();
        window.optionPane().requireMessage("Data Pelanggan Berhasil Disimpan");
        window.optionPane().okButton().click();
        
        System.out.println("====Ubah Pelanggan====");
        window.robot.moveMouse(800, 500);
        window.robot.pressMouse(MouseButton.LEFT_BUTTON);
        window.robot.releaseMouse(MouseButton.LEFT_BUTTON);
        window.textBox("tfNamaPelanggan").setText("");
        window.textBox("tfNamaPelanggan").enterText("Aliftyan Meirsyah Prasetyo");
        window.radioButton("rbPerempuan").click();
        window.textBox("tfPonsel").setText("");
        window.textBox("tfPonsel").enterText("087512345678");
        window.textBox("taAlamat").setText("");
        window.textBox("taAlamat").enterText("Gresik");
        
        window.button("btnUbah").click();
        window.optionPane().requireMessage("Data Pelanggan Berhasil Diubah");
        window.optionPane().okButton().click();
//
//    
    }
//    
    @After
    public void clean3() {
        window.cleanUp();
    }
    
    @Test
    public void tambahTransaksi(){
        System.out.println("====Tambah Transaksi dengan Diskon====");
        window.panel("pTransaksi").click();
        window.comboBox("cbPelanggan").selectItem("Aliftyan Meirsyah Prasetyo");
        window.comboBox("cbLayanan").selectItem("Cuci Kering");
        window.textBox("tfJumlah").enterText("2");
        window.button("btnCek").click();
        window.optionPane().requireMessage("Anda Sedang Berulang Tahun Bulan Ini (: Selamat Ulang Tahun !!!");
        window.optionPane().okButton().click();
        window.textBox("tfUang").enterText("40000");

        window.button("btnTambah").click();
        window.optionPane().requireMessage("Data Transaksi Berhasil Ditambahkan");
        window.optionPane().okButton().click();
        
        window.textBox("tfJumlah").setText("");
        window.textBox("tfUang").setText("");
//        
        System.out.println("====Tambah Transaksi tanpa Diskon====");
//        window.panel("pTransaksi").click();
        window.comboBox("cbPelanggan").selectItem("Ina Susanti");
        window.comboBox("cbLayanan").selectItem("Cuci Kering");
        window.textBox("tfJumlah").enterText("2");
        window.button("btnCek").click();
        window.textBox("tfUang").enterText("40000");
//
        window.button("btnTambah").click();        
        window.optionPane().requireMessage("Data Transaksi Berhasil Ditambahkan");
        window.optionPane().okButton().click();
    }
    
}
