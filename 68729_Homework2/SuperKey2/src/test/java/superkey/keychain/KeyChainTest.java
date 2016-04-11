/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkey.keychain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.function.Consumer;
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
public class KeyChainTest {

    public KeyChainTest() {
    }

    private KeyChain chain;
    private KeyEntry entryA, entryB;
    //private Iterator<KeyEntry> entryIteratable;

    @Before
    public void setUp() {
        entryA = new KeyEntry();
        entryA.setApplicationName("appx");
        entryA.setUsername("xx");
        entryA.setPassword("secret@@@");

        entryB = new KeyEntry();
        entryB.setApplicationName("applicationx");
        entryB.setUsername("xxapp");
        entryB.setPassword("secret@@@app");

        try {
            chain = KeyChain.from(new File("Keychain.txt"), new CipherTool("#wisper"));
            chain.put(entryA);
            chain.put(entryB);
        } catch (IOException ex) {
            Logger.getLogger(KeyChainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() throws FileNotFoundException {
        File f = new File("FileTest.txt");
        PrintWriter pw = new PrintWriter(f);
        pw.println("");
        pw.close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test //ver se a entrada e igual a que inseri, retorna o que e inserido
    public void testPut() {
        KeyEntry entry = new KeyEntry();
        entry.setApplicationName("application");
        entry.setUsername("tqs");
        entry.setPassword("tqsapp");

        chain.put(entry);
        assertEquals(entry, chain.find(entry.key()));
    }

    @Test //retorna null a uma chave que nao existe
    public void testFind() {
        assertEquals(null, chain.find("aaaaaaaa"));
    }

    @Test //persistir informacao
    public void testSave() {
        //garantir que este KeyChain escreve na file vazia    
        KeyChain kChain;
        KeyChain kc;
        try {
            kChain = KeyChain.from(new File("FileTest.txt"), new CipherTool("#wisper"));
            kChain.put(entryA);
            kChain.save();
            
            //garantir que ele le bem a file anterior
            kc = KeyChain.from(new File("FileTest.txt"), new CipherTool("#wisper"));
            assertEquals(kc.find(entryA.key()), entryA);      
        } catch (IOException ex) {
            Logger.getLogger(KeyChainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test //realizar um put e ver se ele constra no allEntry
    public void testAllEntries() {
        Iterable<KeyEntry> allEntries = chain.allEntries();
        Iterator iterator = allEntries.iterator();

        boolean allEntryA = false;
        boolean allEntryB = false;

        while (iterator.hasNext()) {
            KeyEntry keyEntry = (KeyEntry) iterator.next();
            if (keyEntry == entryA) {
                allEntryA = true;
            }
            if (keyEntry == entryB) {
                allEntryB = true;
            }
        }

        assertEquals(allEntryA, true);
        assertEquals(allEntryB, true);
    }

    @Test //garantir que as entradas estao ordenadas
    public void testSortedEntries() {
        Iterable<KeyEntry> sortedEntries = chain.sortedEntries();
        KeyEntry prev = null;
        KeyEntryComparator keyComp = new KeyEntryComparator();
        boolean comp = true;
        for (KeyEntry elem : sortedEntries) {
            if (prev != null && keyComp.compare(prev, elem) > 0) {
                comp = false;
            }
            prev = elem;
        }

        assertEquals(comp, true);
    }

    @Test
    public void testToString() {
        String test = "pp\t++\tso5b2ur7pv05v91mui\n"
                + "1\t1\tvv6p8s4kddncnna64k\n"
                + "applicationx\txxapp\tsecret@@@app\n"
                + "ua2\tico2\tpassx\n"
                + "appx\txx\tsecret@@@\n"
                + "ua\tico\t6rb5rv3n267cjoe5od\n"
                + "zbd\t123\tudhb30ebgtfppks9gb\n"
                + "b1\tb1\tcnr4d9j3q2r1utth1d\n";

        //System.err.println(chain.toString());
        assertEquals(test, chain.toString());
    }

}
