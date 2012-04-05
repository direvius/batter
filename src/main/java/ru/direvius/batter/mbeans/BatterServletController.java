/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.direvius.batter.mbeans;

import ru.direvius.batter.BatterServlet;

/**
 *
 * @author direvius
 */
public class BatterServletController implements BatterServletControllerMBean{
    private final BatterServlet servlet;

    public BatterServletController(BatterServlet servlet){
        this.servlet = servlet;
    }
    public void setDelay(long delay) {
        servlet.setDelay(delay);
    }

    public long getDelay() {
        return servlet.getDelay();
    }

    public void setJitter(long jitter) {
        servlet.setJitter(jitter);
    }

    public long getJitter() {
        return servlet.getJitter();
    }
    
}
