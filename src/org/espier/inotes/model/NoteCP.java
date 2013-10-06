package org.espier.inotes.model;

import android.app.SearchManager;
import android.net.Uri;

public class NoteCP {
	public static final String DB_NAME = "espier_notes.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_NOTES = "notes";

	public static String NOTE_ID = "note_id";
	public static final String NOTE_CONTENT = "note_content";
	public static final String NOTE_CREATE_TIME = "note_create_time";

	public static final String SQL_CREATE_NOTES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NOTES + " (" + NOTE_ID
			+ " INTEGER PRIMARY KEY autoincrement," + NOTE_CONTENT
			+ " VARCHAR," + NOTE_CREATE_TIME + " VARCHAR" + ");";

	public static final String AUTOHORITY = "org.espier.inotes";
	public static final int ITEM = 1;
	public static final int ITEM_ID = 2;

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"
			+ TABLE_NOTES;
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/"
			+ TABLE_NOTES;

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY
			+ "/" + TABLE_NOTES);

	// 将被容暴露给系统
	public static final Uri SEARCH_URI = Uri.parse("content://" + AUTOHORITY
			+ "/" + SearchManager.SUGGEST_URI_PATH_QUERY);

}
