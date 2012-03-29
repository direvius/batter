package ru.direvius.batter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * java -Xms16048m -Xmx16048m -XX:+UseConcMarkSweepGC -XX:+PrintGC -server -Dlogback.configurationFile=./logback.xml -jar ./batter-1.5-SNAPSHOT.jar ppo.log 80
 *
 */
public class App 
{
    private static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        if(args.length<1){
            System.out.println("Usage: pass input file as a first parameter and port as a second parameter (default 8080)");
            System.exit(1);
        }
        int port = 8080;
        if(args.length==2){
            port = Integer.parseInt(args[1]);
        }
        Batter b = new Batter(port);
        try {
            b.loadStorage(new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),"UTF-8")));
            System.out.print("File loaded. Starting server on "+port+"... ");
            try {
                b.start();
                System.out.println("done");
                b.join();
            } catch (Exception ex) {
                logger.error("Could not start server.", ex);
            } finally {
                try {
                    b.stop();
                } catch (Exception ex) {
                    logger.error("Could not stop server.", ex);
                }
            }
        } catch (IOException ex) {
            logger.error("Could not load storage from file");
        }
    }
}
