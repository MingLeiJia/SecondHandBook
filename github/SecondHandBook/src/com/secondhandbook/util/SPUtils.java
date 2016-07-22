package com.secondhandbook.util;
/**
 * @author MingLei Jia
 */
import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

	private static final String FILE_NAME = "share_data";
	
	/**
	 * 保存数据
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void setParam(Context context, String key, Object object)
	{
		String type = object.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		
		if("String".equals(type))
		{
			editor.putString(key, (String)object);
		}
		else if("Integer".equals(type))			
		{
			editor.putInt(key, (Integer)object);
		}
		else if("Boolean".equals(type))
		{
			editor.putBoolean(key, (Boolean)object);
		}
		else if("Float".equals(type))
		{
			editor.putFloat(key, (Float)object);
		}
		else if("Long".equals(type))
		{
			editor.putLong(key, (Long)object);
		}
		
		editor.commit();
	}
	
	public static Object getParam(Context context, String key, Object defaultObject)
	{
		String type = defaultObject.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		
		if("String".equals(type))
		{
			return sp.getString(key, (String)defaultObject);
		}
		else if("Integer".equals(type))			
		{
			return sp.getInt(key, (Integer)defaultObject);
		}
		else if("Boolean".equals(type))
		{
			return sp.getBoolean(key, (Boolean)defaultObject);
		}
		else if("Float".equals(type))
		{
			return sp.getFloat(key, (Float)defaultObject);
		}
		else if("Long".equals(type))
		{
			return sp.getLong(key, (Long)defaultObject);
		}
		return null;
	}
	/**
	 * 移除某一个Key对应的值
	 * @param context
	 * @param key
	 */
		public static void remove(Context context, String key)
		{
			SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.remove(key);
			editor.commit();
		}
		/**
		 * 清空所有数据
		 * @param context
		 */
		public static void clear(Context context)
		{
			SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.clear();
			editor.commit();
		}
		
		public static boolean contains(Context context, String key)
		{
			SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			return sp.contains(key);
		}
}
