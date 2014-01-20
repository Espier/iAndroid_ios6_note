package org.espier.note.view;


import org.espier.note.R;
import org.espier.note.activity.NoteListActivity;
import org.espier.note.db.DatabaseHelper;
import org.espier.note.model.Note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MenuPopupWindow extends PopupWindow {
	private LayoutInflater inflater;
	private Note note;
	private Context context;
	private DatabaseHelper dbHelper;
	public MenuPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MenuPopupWindow(Context context,Note note) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init(context);
	}

	private void init(final Context context) {
		dbHelper = new DatabaseHelper(context);
		inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.popup_menu, null);
		TextView tvDelete = (TextView) view.findViewById(R.id.tv_delete);
		TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
		tvDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbHelper.deleteNoteById(note.getId());
				Intent intent = new Intent(context, NoteListActivity.class);
				((Activity) context).setResult(1, intent);
				((Activity) context).finish();
			}
		});
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		this.setOutsideTouchable(true);
	}
}
