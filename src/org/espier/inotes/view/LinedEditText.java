package org.espier.inotes.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LinedEditText extends EditText {
	private final Rect mRect;
	private final Paint mPaint;
	private final DisplayMetrics dm;

	public LinedEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(0x800000FF);
		dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int count = getLineCount();

		Rect r = mRect;
		Paint paint = mPaint;
		for (int i = 0; i < count; i++) {
			int baseline = getLineBounds(i, r);
//			 System.out.println("baseline==" + baseline + ",left=" + r.left
//			 + ",right=" + r.right);
			// canvas.drawLine(r.left, baseline + 10, r.right, baseline + 10,
			// paint);
			canvas.drawLine(0, baseline + 5, dm.widthPixels, baseline + 5,
					paint);
		}

		super.onDraw(canvas);
	}

	
}
