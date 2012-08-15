package org.wangfy.dev.web.httpclient;
import java.io.IOException;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import org.apache.commons.httpclient.HttpClient;

/**
 *
 * @author hp
 */
public class HttpClientPerformance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MultiThreadedHttpConnectionManager connectionManger = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
    	HttpClientParams httpClientParams = new HttpClientParams();
        params.setMaxTotalConnections(4000);
        params.setDefaultMaxConnectionsPerHost(4);
        params.setConnectionTimeout(5000); //5 sec
        params.setSoTimeout(5000);
        connectionManger.setParams(params);
        final HttpClient http = new HttpClient(httpClientParams,connectionManger);
        Thread pts[] = new Thread[50];
        for (int i=0;i<pts.length;i++) {
            pts[i] = new Thread(new Runnable(){

                public void run() {
                    int stat = 0;
                    long st = System.currentTimeMillis();
                    GetMethod get = new GetMethod("http://www.163.com");
                    try {
                        stat = http.executeMethod(get);
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        get.releaseConnection();
                    }
                    long et = System.currentTimeMillis();
                    System.out.println("statusCode:"+stat+"  execute time:" + (et-st) );
                   
                }
            });
        }
       
        for(int i=0;i<pts.length;i++) {
            pts[i].start();
        }
        MultiThreadedHttpConnectionManager.shutdownAll();

    }
}
