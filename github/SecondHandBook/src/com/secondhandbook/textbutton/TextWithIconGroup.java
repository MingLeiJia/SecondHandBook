package com.secondhandbook.textbutton;
/**
 * @author MingLei Jia
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class TextWithIconGroup extends ViewGroup {
	
	private OnItemClickListener itemClickListener;
	
	private TextViewWithIcon currentItem;

	public TextWithIconGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextWithIconGroup(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		View view = getChildAt(0);
		view.measure(widthMeasureSpec, heightMeasureSpec);
		setCurrentItem((TextViewWithIcon) view);
		setMeasuredDimension(widthMeasureSpec, view.getMeasuredHeight());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		int width = r / count;
		View view = null;
		for (int i = 0; i < count; i++) {
			view = getChildAt(i);
			if (view instanceof TextViewWithIcon) {
				TextViewWithIcon withIcon = (TextViewWithIcon)view;
				withIcon.setParentView(this);
			}
			view.measure(0, 0);
			int bufWidth = width * i;
			view.layout(bufWidth, 0, bufWidth + width, view.getMeasuredHeight());
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			currentItem.setChecked(false);
		}
		return false;
	}
	public void setCurrentItem(TextViewWithIcon currentItem){
		this.currentItem = currentItem;
		currentItem.setChecked(true);
	}
	public void setItemClickListener(OnItemClickListener listener){
		itemClickListener = listener;
	}
	public OnItemClickListener getItemClickListener(){
		return itemClickListener;
	}
	public interface OnItemClickListener{
		void onItemClick(TextViewWithIcon view);
	}
}
