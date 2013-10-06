package org.espier.inotes.activity;

import org.espier.inotes.R;
import org.espier.inotes.db.DatabaseHelper;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class TestProviderActivity extends Activity {
	/** Called when the activity is first created. */
	private DatabaseHelper databaseHelper;
	private ContentResolver contentResolver;
	private Button btnTest;
	private View view;
	private PopupWindow mPopupWindow;
	private LinearLayout llSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_activity);
		btnTest = (Button) findViewById(R.id.btn_test);
		llSearch = (LinearLayout) findViewById(R.id.ll_container);
		btnTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initPopupWindow(R.layout.popup_search);
				showPopupWindow(llSearch);
			}

		});

		// setContentView(R.layout.main);
		// 先对数据库进行添加数据
		// 通过contentResolver进行查找

		// contentResolver = this.getContentResolver();
		// System.out.println("1111" + contentResolver);
		//
		// // System.out.println("2222" + cursor);
		//
		// int item = contentResolver.delete(NoteCP.CONTENT_URI, NoteCP.NOTE_ID
		// + "=?", new String[] { "21" });
		// System.out.println("3333=" + item);
		// Cursor cursor = contentResolver.query(NoteCP.CONTENT_URI, new
		// String[] {
		// NoteCP.NOTE_ID, NoteCP.NOTE_CONTENT, NoteCP.NOTE_CREATE_TIME },
		// null, null, null);
		// while (cursor.moveToNext()) {
		// System.out.println("note:"
		// + cursor.getString(cursor.getColumnIndex(NoteCP.NOTE_ID))
		// + " "
		// + cursor.getString(cursor
		// .getColumnIndex(NoteCP.NOTE_CONTENT))
		// + " "
		// + cursor.getString(cursor
		// .getColumnIndex(NoteCP.NOTE_CREATE_TIME)) + " ");
		// }
		// String date = TimeUtils.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss",
		// new Date());
		// ContentValues values = new ContentValues();
		// values.put(NoteCP.NOTE_ID, 30);
		// values.put(NoteCP.NOTE_CONTENT, "发斯蒂芬斯蒂芬");
		// values.put(NoteCP.NOTE_CREATE_TIME, date);
		// contentResolver.update(NoteCP.CONTENT_URI, values, null, null);
		// startManagingCursor(cursor); // 查找后关闭游标
		// Cursor cursor1 = contentResolver.query(NoteCP.CONTENT_URI,
		// new String[] { NoteCP.NOTE_ID, NoteCP.NOTE_CONTENT,
		// NoteCP.NOTE_CREATE_TIME }, null, null, null);
		// while (cursor1.moveToNext()) {
		// System.out.println("note111:"
		// + cursor1.getString(cursor1.getColumnIndex(NoteCP.NOTE_ID))
		// + " "
		// + cursor1.getString(cursor1
		// .getColumnIndex(NoteCP.NOTE_CONTENT))
		// + " "
		// + cursor1.getString(cursor1
		// .getColumnIndex(NoteCP.NOTE_CREATE_TIME)) + " ");
		// }
		// startManagingCursor(cursor1);
	}

	private void initPopupWindow(int resId) {
		LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		view = mLayoutInflater.inflate(resId, null);

		mPopupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		// mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.bg_body));
		ColorDrawable dw = new ColorDrawable(-00000);
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setOutsideTouchable(true);

		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view, 0, 0);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
			// mPopupWindow.show
		}
	}
}
