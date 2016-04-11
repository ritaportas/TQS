/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkey.keychain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ico
 */
public class KeyEntryTest {
    private KeyEntry entryA;
    private String s = "test";
    
    public KeyEntryTest() {
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

   
    @Test( expected = IllegalArgumentException.class)
    public void testSetApplicationNameWithNull() {
        entryA.setApplicationName( null);
    }
    
    @Test( expected = IllegalArgumentException.class)
    public void testSetPasswordWithNull(){
        entryA.setPassword(null);
    }
    
    @Test ( expected = IllegalArgumentException.class)
    public void testSetUsernameWithNull(){
        entryA.setUsername(null);
    }

    @Test
    public void testKey() {
        // the key is the application name
        assertEquals("failed to get existing key field", entryA.getApplicationName(), "appx");
    }

    @Test
    public void testFormatAsCsv() {
        String expects = "appx" + KeyEntry.FIELDS_DELIMITER + "xx" + KeyEntry.FIELDS_DELIMITER + "secret@@@";
        assertEquals("failed to format entry to delimited string", entryA.formatAsCsv(), expects);       
    }

    @Test
    public void testToString() {
        String test = "appx\txx\tsecret@@@";
        assertEquals(test, entryA.toString());
    }

    @Test
    public void testParse() {
        String csvTest = "application"+KeyEntry.FIELDS_DELIMITER+"tqs"+KeyEntry.FIELDS_DELIMITER+"tqsapp";
        entryA =  KeyEntry.parse(csvTest);
        KeyEntry ke = new KeyEntry();
        ke.setApplicationName("application");
        ke.setUsername("tqs");
        ke.setPassword("tqsapp");
        
        assertEquals(ke, entryA);
    }

    @Test
    public void testGetApplicationName(){
        entryA.setApplicationName(s);
        assertEquals(s, entryA.getApplicationName());
    }
    
    @Test
    public void testGetPassword(){
        entryA.setPassword(s);
        assertEquals(s, entryA.getPassword());
    }
    
    @Test
    public void testGetUsername(){
        entryA.setUsername(s);
        assertEquals(s, entryA.getUsername());
    }
    
}
