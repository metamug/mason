/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath.extract.strategy;

/**
 *
 * @author pc
 */
public interface ExtractStrategy {
    public String extract(String path, Object target);
}
