package com.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;  


public class JspToHtml {
	
	public static String getCode(String httpUrl ) { //������һ�������http�������ĵ�ַ
			String htmlCode="";//���巵�صľ������
			InputStream in;  
			try {
				URL url = new URL(httpUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/4.0");
				connection.connect();
				in = connection.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
				BufferedReader reader = new BufferedReader(inputStreamReader);  
				String currentLine = "";
				while((currentLine = reader.readLine()) != null ){ 
					htmlCode += currentLine + "\n";
				}
				reader.close();
				inputStreamReader.close();
				in.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return htmlCode;
		}
	}



