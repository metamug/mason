/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath;

import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class MPathTest {
    
    @Test
    public void objectNameTest(){
        String mpath = "$['xreq'].body.arr[1].title";
        
        MPathUtil.getVarFromPath(mpath);
    }
    
}
