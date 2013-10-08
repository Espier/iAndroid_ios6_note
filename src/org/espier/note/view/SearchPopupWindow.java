package org.espier.note.view;

import org.espier.note.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class SearchPopupWindow extends PopupWindow {
	private LayoutInflater inflater;
	private LinearLayout llSearch;
	private EditText etSearch;

	public SearchPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SearchPopupWindow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		inflater = inflater.from(context);
		llSearch = (LinearLayout) inflater.inflate(R.layout.popup_search, null);
		etSearch = (EditText) llSearch.findViewById(R.id.et_search);
		android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

	}
}
