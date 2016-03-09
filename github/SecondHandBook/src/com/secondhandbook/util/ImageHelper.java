package com.secondhandbook.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

public class ImageHelper {
	
	private static ImageHelper mInstance = new ImageHelper();
	
	private Handler handler = new Handler();
	
	public static ImageHelper getInstance(){
		return mInstance;
	}
	
	private ImageHelper(){};
	
	public void displayImage(final String bmurl,final ImageView iv){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				displayImageSync(bmurl, iv);
			}
		}).start();
	}

	public void displayImageSync(String bmurl,final ImageView iv)
	{
		Bitmap bm=null;
		InputStream is =null;
		BufferedInputStream bis=null;
		try{
			URL  url=new URL(bmurl);
			URLConnection connection=url.openConnection();
			bis=new BufferedInputStream(connection.getInputStream());
			bm= BitmapFactory.decodeStream(bis);
			final Bitmap bm1 = bm;
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					iv.setImageBitmap(bm1);
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				if(bis!=null)
					bis.close();
				if (is!=null)
					is.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}


}
