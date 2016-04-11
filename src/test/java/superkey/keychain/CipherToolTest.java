/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkey.keychain;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rita
 */
public class CipherToolTest {
    
    private KeyChain chain;
    private KeyEntry entryA;
    
    public CipherToolTest() {
    }
    
    @Before
    public void setUp() {
        entryA = new KeyEntry();
        entryA.setApplicationName("appx");
        entryA.setUsername("xx");
        entryA.setPassword("secret@@@");
        
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test //verificar par chave/valor da keyChain
    public void testVerifyKeyValidOfKeyChain() throws IOException {
        
        chain = KeyChain.from(new File("Keychain.txt"), new CipherTool("#wisper"));
        chain.put(entryA);
        
        assertEquals(entryA, chain.find(entryA.key()));
        
    }
    
    @Test (expected = IOException.class)
    public void testVerifyKeyInvalidOfKeyChain() throws IOException {
        chain = KeyChain.from(new File("Keychain.txt"), new CipherTool("#app"));
    }
}
