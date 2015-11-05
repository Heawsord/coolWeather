package com.coolweather.app.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;

public class HttpUtil {
	public static final int TIMEOUT=8000;

	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable(){
			@Override
			public void run(){
				HttpURLConnection conn=null;
				
				try{
					
					URL url=new URL(address);
					conn=(HttpURLConnection)url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(TIMEOUT);
					conn.setReadTimeout(TIMEOUT);
					InputStream in=conn.getInputStream();
					BufferedReader bufr=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line=null;
					
					while((line=bufr.readLine())!=null){
						response.append(line);
					}
					if(listener!=null){
						listener.onFinish(response.toString());
					}
				} catch(Exception e){
					if(listener!=null){
						listener.onError(e);
					}
				} finally{
					if(conn!=null){
						conn.disconnect();
					}
				}
			}
		}).start();
	}
}
