package com.redislabs.ingest.pubsub;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectionUtils {
	private static List<String> hosts;
	
	public static void setHosts(String hostsFile) throws Exception{
		hosts = new ArrayList<String>();
		
			
			String line = "";
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(hostsFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                hosts.add(line);
            }   

            // Always close files.
            bufferedReader.close();         
       
	}
	
	public static String getHost(){
		Random rand = new Random();
		int  n = rand.nextInt(hosts.size()-1);
		String host = hosts.get(n);
		return host;
	}
}
