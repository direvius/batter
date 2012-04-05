/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.direvius.batter.mbeans;

/**
 *
 * @author direvius
 */
public interface BatterServletControllerMBean {
    public void setDelay(long delay);
    public long getDelay();
    public void setJitter(long jitter);
    public long getJitter();
}
