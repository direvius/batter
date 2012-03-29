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
        String requestString = sb.toString();
        if(storage != null && storage.containsKey(requestString)){
            resp.getWriter().print(storage.get(requestString));
            logger.info("200\tSent response");
        } else if(requestString.equals("/")) {
            sb = new StringBuilder();
            sb.append("<html><body>");
            for(String key: storage.keySet()){
                sb.append("<a href='").append(key).append("'>")
                        .append(key).append("</a><br/>");
            }
            sb.append("</body></html>");
            resp.getWriter().print(sb);
            resp.flushBuffer();
        } else {
            resp.sendError(404, "Could not find content for: "+requestString);
            logger.warn("404\tCould not find content for:\t{}", requestString);
        }
    }
    public void setStorage(Map<String, String> storage){
        this.storage = Collections.unmodifiableMap(storage);
    }
}
