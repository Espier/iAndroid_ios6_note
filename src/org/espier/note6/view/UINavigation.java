package org.espier.note6.view;


import org.espier.note.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UINavigation extends LinearLayout {
	private final TextView tvLeft,tvRight,tvTitle;
	public UINavigation(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater=LayoutInflater.from(context);
		View view=inflater.inflate(R.layout.ui_navigation, null);
		tvLeft=(TextView)findViewById(R.id.tv_left);
		tvTitle=(TextView)findViewById(R.id.tv_title);
		tvRight=(TextView)findViewById(R.id.tv_right);
		android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
				);
		
        this.addView(view, params);
	}
	
	public void setTvLeftBg(int resId) {
		if (tvLeft != null) {
			tvLeft.setBackgroundResource(resId);
		}
	}
	public void setTvLeftText(int resId) {
		if (tvLeft!=null) {
			tvLeft.setText(resId);
		}
	}
	public void setTvTitleTextRes(int resId) {
		if (tvTitle!=null) {
			tvTitle.setText(resId);
		}
	}
	public void setTvTitleText(String title) {
		if (tvTitle!=null) {
			tvTitle.setText(title);
		}
	}
	public void setTvRightBg(int resId) {
		if (tvLeft != null) {
			tvLeft.setBackgroundResource(resId);
		}
	}
	public void setTvRightText(int resId) {
		if (tvLeft!=null) {
			tvLeft.setText(resId);
		}
	}
}
