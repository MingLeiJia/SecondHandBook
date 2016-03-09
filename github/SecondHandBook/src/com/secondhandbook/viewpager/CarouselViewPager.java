/**
 * @author MingLei Jia
 */
package com.secondhandbook.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import com.secondhandbook.util.ImageHelper;


public class CarouselViewPager extends ViewPager {

    private Context context;

    private PagerAdapter mPagerAdapter;

    private boolean isTouch = false;

    private int count = 0;

    private Handler handler = new Handler();

    private boolean state = false;

    private boolean isSliding;

    private List<String> mData;

    public CarouselViewPager(Context context) {
        this(context, null);
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isSliding = true;
        this.context = context;

        mPagerAdapter = new MyPagerAdapter();

        setAdapter(mPagerAdapter);

    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<String> data) {
        this.mData = data;
        mPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 开始轮播
     */
    public void start() {
        state = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (state) {
                    handler.post(new MyRunnable());
                    SystemClock.sleep(2 * 1000);
                }
            }
        });
    }

    /**
     * 停止轮播
     */
    public void stop() {
        state = false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isSliding)
            return false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouch = false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isSliding)
            return false;
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @param position
     * @return
     */
    public View getView(int position) {
        ImageView iv = new ImageView(context);
        ImageHelper.getInstance().displayImage(mData.get(position), iv);

        return iv; 
        
    }

    /**
     * 轮播的任务
     */
    class MyRunnable implements Runnable {

        @Override
        public void run() {
            if (isTouch)
                return;

            int current = getCurrentItem() + 1;

            if (current >= mPagerAdapter.getCount()) {
                current = 0;
            }

            setCurrentItem(current);
        }
    }

    /**
     * 设置滑动
     *
     * @param isSliding   
     */
    public void setSliding(boolean isSliding) {
        this.isSliding = isSliding;
    }


    /**
     * 适配�?
     */
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getView(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
