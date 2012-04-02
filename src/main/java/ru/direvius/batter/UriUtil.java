/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.direvius.batter;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author direvius
 */
public class UriUtil {
    public static String normalizeUri(String uri){
        StringBuilder sb = new StringBuilder();
        String [] tokens = uri.split("[?]");
        sb.append(tokens[0]);
        if(tokens.length>1){
            String [] params = tokens[1].split("&");
            sb.append("?");
            Collections.sort(Arrays.asList(params));
            for(String param: params){
                sb.append(param)
                        .append("&");
            }
        }
        return sb.toString();
    }
}
