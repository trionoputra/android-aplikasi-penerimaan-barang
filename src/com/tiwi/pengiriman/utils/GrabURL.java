package com.tiwi.pengiriman.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;


public class GrabURL extends AsyncTask<String, String, String>
{	 
	public static ArrayList<String> cookies = new ArrayList<String>();
	private HttpClient httpclient;
	HttpResponse response;
	private grabListener _grablistener;
	private int timeout = 120000;
	
	public GrabURL(int timeout)
	{
		this.timeout = timeout;
	}
	
	public GrabURL()
	{
		
	}

	public interface grabListener
	{
		public void onCompleted(String result);
		public void onReady();
		public void onCancel();
	}
	
	public void setgrablistener(grabListener gl)
	{
		_grablistener = gl;
	}

	@Override
	protected String doInBackground(String... param) {
		// TODO Auto-generated method stub
		String responseText = "";
        try 
		{	
        	httpclient = new DefaultHttpClient();
        	
    		HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, this.timeout);
			HttpConnectionParams.setSoTimeout(httpParameters, this.timeout);
			httpclient = new DefaultHttpClient(httpParameters);	
			
        	HttpPost httpost = new HttpPost(param[0]);
        	
        	if (param.length == 2)
        	{
        		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
        	    nameValuePairs.add(new BasicNameValuePair("data", param[1]));
        	    httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        	}
        	
        	response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
				
			responseText = EntityUtils.toString(entity);
			
			return responseText;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			responseText = "noinet";
		}
		
		catch (ConnectTimeoutException e) {
			responseText = "timeout";
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			responseText = "noinet";
		} 
        
		return responseText; 
	}
	
	@Override
	public void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if ( _grablistener != null )
			_grablistener.onCompleted(result);
		
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		_grablistener.onReady();
	}
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		_grablistener.onCancel();
	}
}


