/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import java.awt.Window;
import java.awt.Dimension;
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
public class LoginTest {
    FrameFixture window;
    
    public LoginTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        window = new FrameFixture(new Login());
        Login login = new Login();
        Dimension ukuranLogin = new Dimension(login.getSize());
        window.show(ukuranLogin);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Login.
     */
    @Test
    public void testMain() {

        System.out.println("----Username Kosong---");
        window.textBox("tfPass").setText("123");
        window.button("btnSubmit").click();
        window.optionPane().requireMessage("Username atau password anda salah");
        window.optionPane().okButton().click();
        
        window.textBox("tfUser").setText("");
        window.textBox("tfPass").setText("");
        
        System.out.println("----Password Kosong---");
        window.textBox("tfUser").setText("admin");
        window.button("btnSubmit").click();
        window.optionPane().requireMessage("Username atau password anda salah");
        window.optionPane().okButton().click();
        
        window.textBox("tfUser").setText("");
        window.textBox("tfPass").setText("");
        
        System.out.println("=====Login Gagal=====");
        window.textBox("tfUser").enterText("admin");
        window.textBox("tfPass").enterText("1234");
        window.button("btnSubmit").click();
        window.optionPane().requireMessage("Username atau password anda salah");
        window.optionPane().okButton().click();
        
        window.textBox("tfUser").setText("");
        window.textBox("tfPass").setText("");
//     
        System.out.println("=====Login Berhasil=====");
        window.textBox("tfUser").enterText("admin");
        window.textBox("tfPass").enterText("123");
        window.button("btnSubmit").click();
        window.optionPane().requireMessage("Login Berhasil");
        window.optionPane().okButton().click();
 
        // TODO review the generated test code and remove the default call to fail.

    }
    
}
