/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.direvius.batter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author direvius
 */
public class Batter{
    private static Logger logger = LoggerFactory.getLogger(App.class);
    Server server;
    BatterServlet servlet;
    public Batter(int port){
        server = new Server(port);
                
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        
        context.setContextPath("/");
        server.setHandler(context);
        
        servlet = new BatterServlet();
        
        context.addServlet(new ServletHolder(servlet), "/*");
        server.setStopAtShutdown(true);
    }
    public Batter start() throws Exception{
        server.start();
        return this;
    }
    public Batter stop() throws Exception{
        server.stop();
        return this;
    }
    public Batter join() throws InterruptedException{
        server.join();
        return this;
    }
    private String readResponse(BufferedReader r) throws IOException{
        StringBuilder sb = new StringBuilder();
        while(r.ready()){
            String line = r.readLine();
            if(line.startsWith("--")){
                break;
            }else{
                sb.append(line);
            }
        }
        return sb.toString();
    }
    public Batter loadStorage(BufferedReader r) throws IOException{
        Map<String, String> result = new HashMap<String, String>();
        while(r.ready()){
            String key = r.readLine();
            String value = readResponse(r);
            result.put(key,value);
        }
        servlet.setStorage(result);
        return this;
    }
}
