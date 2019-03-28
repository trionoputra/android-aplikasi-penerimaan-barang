package com.tiwi.pengiriman.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public final class Helper
{
	private static ContextWrapper instance;
	private static SharedPreferences pref;
	
	public static String SERVER_URL = "http://localhost/pengiriman/index.php/api/mobile/";
	public static void initialize(Context base)
	{
		instance = new ContextWrapper(base);
		pref = instance.getSharedPreferences("com.tiwi", Context.MODE_PRIVATE);
		
	}
	public static void write(String key, String value)
	{
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String read(String key)
	{
		return Helper.read(key, null);
	}
	
	public static String read(String key, String defValue)
	{
		return pref.getString(key, defValue);
	}
	
	public static void clear()
	{
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	public static void clear(String key)
	{
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public static Context getContext()
	{
		return instance.getBaseContext();
	}
	
	public static int DipToInt(int value)
	{
		return (int)(instance.getResources().getDisplayMetrics().density * value);
	}
	
}

