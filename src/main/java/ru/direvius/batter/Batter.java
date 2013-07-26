/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.direvius.batter;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.direvius.batter.mbeans.BatterServletController;

/**
 * Server logic. Set server parameters, initialize thread pool, load data.
 * @author direvius
 */
public class Batter{
    private static Logger logger = LoggerFactory.getLogger(Batter.class);
    Server server;
    BatterServlet servlet;
    public Batter(int port){
            
            server = new Server(port);
            
            QueuedThreadPool tp = new QueuedThreadPool(
                    new ArrayBlockingQueue<Runnable>(1000));
            tp.setMaxThreads(512);
            server.setThreadPool(tp);
            
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            
            context.setContextPath("/");
            server.setHandler(context);
            
            servlet = new BatterServlet();
            
            
            context.addServlet(new ServletHolder(servlet), "/*");
            server.setStopAtShutdown(true);
            
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            
            MBeanContainer mbcontainer = new MBeanContainer(mbs);
            server.getContainer().addEventListener(mbcontainer);
            server.addBean(mbcontainer);
            mbcontainer.addBean(Log.getLogger(Batter.class));
            try{
                ObjectName name = new ObjectName("ru.direvius.batter.mbeans:type=BatterServletController");
                mbs.registerMBean(new BatterServletController(servlet), name);
            }
            catch (InstanceAlreadyExistsException ex) {
                java.util.logging.Logger.getLogger(Batter.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (MBeanRegistrationException ex) {
                java.util.logging.Logger.getLogger(Batter.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (NotCompliantMBeanException ex) {
                java.util.logging.Logger.getLogger(Batter.class.getName()).log(Level.SEVERE, null, ex);
            }        
            catch (MalformedObjectNameException ex) {
                java.util.logging.Logger.getLogger(Batter.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (NullPointerException ex) {
                java.util.logging.Logger.getLogger(Batter.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    /**
    * read lines until line started with '--' occured
    */
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
    /**
    * load uri-response dictionary
    */
    public Batter loadStorage(BufferedReader r) throws IOException{
        Map<String, String> result = new HashMap<String, String>();
        while(r.ready()){
            String key = UriUtil.normalizeUri(r.readLine());
            String value = readResponse(r);
            result.put(key,value);
        }
        servlet.setStorage(result);
        return this;
    }
}
