package com.secondhandbook.viewpager;
/**
 * @author MingLei Jia
 */
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

import com.secondhandbook.aty.R;
import com.secondhandbook.util.ImageHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MyViewPager implements OnPageChangeListener{

	private ImageView[] mDots;
	private String[] mImageRes;
	private Context mContext;

	private ImageView[][] mImageViews;


	private int newWidth;
	private int padding;
	private int width;

	private ViewPagerAdapter mViewPagerAdp;

	private ViewPager mViewpager;

	private final long delay = 3 * 1000;

	private final int AUTO = 0;
	public MyViewPager(Context context, String[] url, LinearLayout layoutDots, ViewPager mViewpager)
	{
		mContext = context;

		width = mContext.getResources().getDisplayMetrics().widthPixels;

		newWidth = (int) (divideWidth(width, 1080, 6) * 17);

		padding = (int) (divideWidth(width, 1080, 6) * 9); 

		this.mViewpager = mViewpager;

		mImageRes = url;


		mViewpager.setOnPageChangeListener(this);

		initDots(layoutDots);
		initViewPager();
	}

	public Bitmap receiveImageSync(String bmurl)
	{
		Bitmap bm=null;
		InputStream is =null;
		BufferedInputStream bis=null;
		try{
			URL  url=new URL(bmurl);
			URLConnection connection=url.openConnection();
			bis=new BufferedInputStream(connection.getInputStream());
			bm= BitmapFactory.decodeStream(bis);
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

		return bm;
	}
	public void initDots(LinearLayout layoutDots) {

		mDots = new ImageView[mImageRes.length];

		for (int i = 0; i < mImageRes.length; i++) {

			ImageView iv = new ImageView(mContext);

			LayoutParams lp = new LayoutParams(newWidth, newWidth);

			lp.leftMargin = padding;

			lp.rightMargin = padding;

			lp.topMargin = padding;

			lp.bottomMargin = padding;

			iv.setLayoutParams(lp);

			iv.setImageResource(R.drawable.dot_normal);

			layoutDots.addView(iv);

			mDots[i] = iv;

		}

		mDots[0].setImageResource(R.drawable.dot_selected);
	}

	/**
	 * @author SheXiaoHeng
	 * 
	 * 
	 * @param screenWidth
	 *            手机屏幕的宽度
	 * 
	 * @param picWidth
	 *            原始图片所用分辨率的宽度
	 * 
	 * @param retainValue
	 *            保留小数位
	 * 
	 * @return 手机屏幕分辨率与原始图片分辨率的宽度比
	 * 
	 * */
	public double divideWidth(int screenWidth, int picWidth, int retainValue) {
		BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
		BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
		return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}
	/**
	 * @author
	 * 
	 *         初始化ViewPager
	 * 
	 * */
	public void initViewPager() {

		mImageViews = new ImageView[2][];

		mImageViews[0] = new ImageView[mImageRes.length];

		mImageViews[1] = new ImageView[mImageRes.length];


		

		for (int i = 0; i < mImageViews.length; i++) {

			for (int j = 0; j < mImageViews[i].length; j++) {

				ImageView iv = new ImageView(mContext);

				ImageHelper.getInstance().displayImage(mImageRes[j], iv);

				mImageViews[i][j] = iv; 

			}

		}



		mViewPagerAdp = new ViewPagerAdapter(mImageViews, mImageRes);

		mViewpager.setAdapter(mViewPagerAdp);

		mViewpager.setCurrentItem(mImageRes.length * 50);

		mHandler.sendEmptyMessageDelayed(AUTO, delay);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case AUTO:

				int index = mViewpager.getCurrentItem();

				mViewpager.setCurrentItem(index + 1);

				mHandler.sendEmptyMessageDelayed(AUTO, delay);

				break;

			default:
				break;
			}

		};
	};

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		setCurrentDot(position % mImageRes.length);
	}
	/**
	 * @author
	 * 
	 *         设置ViewPager当前的底部小点
	 * 
	 * 
	 * */
	private void setCurrentDot(int currentPosition) {

		for (int i = 0; i < mDots.length; i++) {

			if (i == currentPosition) {

				mDots[i].setImageResource(R.drawable.dot_selected);

			} else {

				mDots[i].setImageResource(R.drawable.dot_normal);

			}
		}
	}
}
