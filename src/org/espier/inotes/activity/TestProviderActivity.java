package org.espier.inotes.activity;

import java.util.Date;

import org.espier.inotes.db.DatabaseHelper;
import org.espier.inotes.model.NoteCP;
import org.espier.inotes.util.TimeUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class TestProviderActivity extends Activity {
	/** Called when the activity is first created. */
	private DatabaseHelper databaseHelper;
	private ContentResolver contentResolver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		setContentView(tv);
		// setContentView(R.layout.main);
		// 先对数据库进行添加数据
		// 通过contentResolver进行查找

		contentResolver = this.getContentResolver();
		System.out.println("1111" + contentResolver);

		// System.out.println("2222" + cursor);

		int item = contentResolver.delete(NoteCP.CONTENT_URI, NoteCP.NOTE_ID
				+ "=?", new String[] { "21" });
		System.out.println("3333=" + item);
		Cursor cursor = contentResolver.query(NoteCP.CONTENT_URI, new String[] {
				NoteCP.NOTE_ID, NoteCP.NOTE_CONTENT, NoteCP.NOTE_CREATE_TIME },
				null, null, null);
		while (cursor.moveToNext()) {
			System.out.println("note:"
					+ cursor.getString(cursor.getColumnIndex(NoteCP.NOTE_ID))
					+ " "
					+ cursor.getString(cursor
							.getColumnIndex(NoteCP.NOTE_CONTENT))
					+ " "
					+ cursor.getString(cursor
							.getColumnIndex(NoteCP.NOTE_CREATE_TIME)) + " ");
		}
		String date = TimeUtils.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				new Date());
		ContentValues values = new ContentValues();
		values.put(NoteCP.NOTE_ID, 30);
		values.put(NoteCP.NOTE_CONTENT, "发斯蒂芬斯蒂芬");
		values.put(NoteCP.NOTE_CREATE_TIME, date);
		contentResolver.update(NoteCP.CONTENT_URI, values, null, null);
		startManagingCursor(cursor); // 查找后关闭游标
		Cursor cursor1 = contentResolver.query(NoteCP.CONTENT_URI,
				new String[] { NoteCP.NOTE_ID, NoteCP.NOTE_CONTENT,
						NoteCP.NOTE_CREATE_TIME }, null, null, null);
		while (cursor1.moveToNext()) {
			System.out.println("note111:"
					+ cursor1.getString(cursor1.getColumnIndex(NoteCP.NOTE_ID))
					+ " "
					+ cursor1.getString(cursor1
							.getColumnIndex(NoteCP.NOTE_CONTENT))
					+ " "
					+ cursor1.getString(cursor1
							.getColumnIndex(NoteCP.NOTE_CREATE_TIME)) + " ");
		}
		startManagingCursor(cursor1);
	}
}
