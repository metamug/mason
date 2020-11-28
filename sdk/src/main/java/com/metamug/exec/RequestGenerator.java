/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.exec;

import com.metamug.entity.Request;
import java.io.BufferedReader;
import java.util.Map;

/**
 *
 * @author D3ep4k
 */
public interface RequestGenerator {

    public Request generate(Map<String, String> headers, BufferedReader reader);

}
