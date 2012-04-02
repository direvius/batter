/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.direvius.batter;

import junit.framework.TestCase;

/**
 *
 * @author direvius
 */
public class UriUtilTest extends TestCase {
    
    public UriUtilTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of normalizeUri method, of class UriUtil.
     */
    public void testNormalizeUri() {
        System.out.println("normalizeUri");
        String uri = "/hello/world?hi=1&allow=yes";
        String expResult = "/hello/world?allow=yes&hi=1&";
        String result = UriUtil.normalizeUri(uri);
        assertEquals(expResult, result);
    }
}
