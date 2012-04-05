/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.direvius.batter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author direvius
 */
public class BatterServlet extends HttpServlet{
    
    private static Logger logger = LoggerFactory.getLogger(BatterServlet.class);
    
    protected Map<String, String> storage;
    private long delay = 0;
    private long jitter = 30;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        StringBuilder sb = new StringBuilder();
        
                sb
                .append(req.getRequestURI());
        String query = req.getQueryString();
        if(query!=null)
                sb.append('?')
                .append(query);
        String requestString = UriUtil.normalizeUri(sb.toString());
        if(storage != null && storage.containsKey(requestString)){
            
            resp.setContentType("text/xml");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(storage.get(requestString));
            if(delay>0){
                try {
                    //Continuation continuation = ContinuationSupport.getContinuation(req);
                    //continuation.setTimeout(delay);//delay+(long)(Math.random()*jitter));
                    //continuation.suspend();
                    Thread.sleep(delay+(long)(Math.random()*jitter));
                }
                catch (InterruptedException ex) {
                    logger.warn(ex.getLocalizedMessage());
                }
            }
            logger.info("200\tSent response");
        } else if(requestString.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html");
            sb = new StringBuilder();
            sb.append("<html><body>");
            for(String key: storage.keySet()){
                sb.append("<a href='").append(key).append("'>")
                        .append(key).append("</a><br/>");
            }
            sb.append("</body></html>");
            resp.getWriter().println(sb);
        } else {
            resp.sendError(404, "Could not find content for: "+requestString);
            logger.warn("404\tCould not find content for:\t{}", requestString);
        }
    }
    public void setStorage(Map<String, String> storage){
        this.storage = Collections.unmodifiableMap(storage);
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setJitter(long jitter) {
        this.jitter = jitter;
    }

    public long getJitter() {
        return jitter;
    }
}
