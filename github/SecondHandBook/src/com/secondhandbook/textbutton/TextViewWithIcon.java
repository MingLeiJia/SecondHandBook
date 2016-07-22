package com.secondhandbook.textbutton;
/**
 * @author MingLei Jia
 */

import com.secondhandbook.aty.R;
import com.secondhandbook.textbutton.TextWithIconGroup.OnItemClickListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TextViewWithIcon extends ImageView {

	private static final float SCALE = 0.9f;
	private int height;

	private Paint paint;

	private String text = "";

	private Bitmap icon;
	private Bitmap selectIcon;

	private int iconHeight = (int) TypedValue
			.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
					.getDisplayMetrics());
	
	private float textSize = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

	private Rect rect;

	private float spacing = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
	private Matrix matrix;
	private boolean init = true;
	private boolean checked;
	private int paddingBottom;
	private int paddingTop;
	
	private TextWithIconGroup parentView;

	public TextViewWithIcon(Context context, AttributeSet attrs) {
		super(context, attrs);

		setClickable(true);

		paint = new Paint();
		paint.setAntiAlias(true);

		rect = new Rect();
		matrix = new Matrix();
		matrix.postScale(SCALE, SCALE);
		
		BitmapDrawable background = (BitmapDrawable) getBackground();
		icon = background.getBitmap();
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.textwithicon);
		Drawable drawable = typedArray.getDrawable(R.styleable.textwithicon_selected);
		setBackgroundDrawable(drawable);
		if (drawable!=null) {
			BitmapDrawable background1 = (BitmapDrawable) getBackground();
			selectIcon = background1.getBitmap();
		}
		typedArray.recycle();
		setBackgroundColor(Color.TRANSPARENT);

		text = (String) getTag();

		paddingTop = getPaddingTop();
		paddingBottom = getPaddingBottom();
	}

	public TextViewWithIcon(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, 0);
		paint.setTextSize(textSize);
		paint.getTextBounds(text, 0, text.length(), rect);
		iconHeight = (int) (icon.getHeight() * SCALE);
		height = (int) (iconHeight + spacing + rect.height()) + paddingTop
				+ paddingBottom;
		setMeasuredDimension(widthMeasureSpec, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (icon == null) {
			return;
		}
		if (init) {
			matrix.postTranslate((getWidth() - iconHeight) / 2, paddingTop);
			init = false;
		}
		if (checked) {
			Matrix m = new Matrix(matrix);
			m.postScale(0.5f, 0.5f);
			m.postTranslate((getWidth() - iconHeight) / 4, paddingTop / 2);
			canvas.drawBitmap(selectIcon, m, null);
			//System.out.println("##############"+m.toString());
			//paint.setColor(0xFFFF7B00);
			paint.setColor(0xFF99CCFF);
		} else {
			canvas.drawBitmap(icon, matrix, null);
			paint.setColor(0xFF999999);
			//System.out.println("***************"+matrix.toString());
		}

		canvas.drawText(text, (getWidth() - rect.width()) / 2, (height / 2)
				+ spacing + rect.height(), paint);
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		invalidate();
	}
	
	public void setParentView(TextWithIconGroup group){
		parentView = group;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			checked = true;
			setBackgroundColor(Color.argb(66, 77, 99, 120));
			invalidate();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			setBackgroundColor(Color.TRANSPARENT);
			invalidate();
			if (parentView == null) {
				return true;
			}
			parentView.setCurrentItem(this);
			OnItemClickListener listener = parentView.getItemClickListener();
			if (listener != null) {
				listener.onItemClick(this);
			}
		}
		return true;
	}
}
