package org.espier.inotes.provider;

import java.util.HashMap;

import org.espier.inotes.db.DatabaseHelper;
import org.espier.inotes.model.NoteCP;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class NoteContentProvider extends ContentProvider {
	private DatabaseHelper databaseHelper;
	private static final UriMatcher uriMatcher;
	private SQLiteDatabase db;

	private static final HashMap<String, String> SEARCH_PROJECTION_MAP;
	public static final int SEARCH = 3;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(NoteCP.AUTOHORITY, NoteCP.TABLE_NOTES, NoteCP.ITEM);
		uriMatcher.addURI(NoteCP.AUTOHORITY, NoteCP.TABLE_NOTES + "/#",
				NoteCP.ITEM_ID);

		uriMatcher.addURI(NoteCP.AUTOHORITY,
				SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH);
		uriMatcher.addURI(NoteCP.AUTOHORITY,
				SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH);
		uriMatcher.addURI(NoteCP.AUTOHORITY,
				SearchManager.SUGGEST_URI_PATH_SHORTCUT, SEARCH);
		uriMatcher.addURI(NoteCP.AUTOHORITY,
				SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", SEARCH);

		SEARCH_PROJECTION_MAP = new HashMap<String, String>();
		SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1,
				NoteCP.NOTE_CONTENT + " AS "
						+ SearchManager.SUGGEST_COLUMN_TEXT_1);
		SEARCH_PROJECTION_MAP.put("_id", NoteCP.NOTE_ID + " AS " + "_id");

	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		databaseHelper = new DatabaseHelper(this.getContext());
		return false;
	}

	@Override
	public int delete(Uri uri, String where, String[] selectionArgs) {
		// TODO Auto-generated method stub
		db = databaseHelper.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case NoteCP.ITEM:
			count = db.delete(NoteCP.TABLE_NOTES, where, selectionArgs);
			break;
		case NoteCP.ITEM_ID:
			String id = uri.getPathSegments().get(1);
			count = db.delete(NoteCP.TABLE_NOTES, NoteCP.NOTE_ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(NoteCP.NOTE_ID = "?") ? "AND("
							+ selectionArgs + ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		case NoteCP.ITEM:
			return NoteCP.CONTENT_TYPE;
		case NoteCP.ITEM_ID:
			return NoteCP.CONTENT_ITEM_TYPE;
		case SEARCH:
			return SearchManager.SUGGEST_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		db = databaseHelper.getWritableDatabase();
		long rowId;
		if (uriMatcher.match(uri) != NoteCP.ITEM) {
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		rowId = db.insert(NoteCP.TABLE_NOTES, NoteCP.NOTE_ID, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(uri, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}
		throw new IllegalArgumentException("Unknown URI" + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] columns, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		System.out.println("provider===query");
		db = databaseHelper.getWritableDatabase();
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {
		case NoteCP.ITEM:
			System.out.println("provider===NoteCP.ITEM");
			cursor = db.query(NoteCP.TABLE_NOTES, columns, selection,
					selectionArgs, null, null, null);

			break;
		case NoteCP.ITEM_ID:
			System.out.println("provider===NoteCP.ITEM_ID");
			String id = uri.getPathSegments().get(1);
			cursor = db.query(NoteCP.TABLE_NOTES, columns, NoteCP.NOTE_ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? "AND(" + selection + ')'
							: ""), selectionArgs, null, null, null);
			break;
		case SEARCH:
			System.out.println("provider===SEARCH");
			String keyWords = uri.getPathSegments().get(1);
			String sql = "select * from " + NoteCP.TABLE_NOTES + " where "
					+ NoteCP.NOTE_CONTENT + " like '%" + keyWords + "%'";
			cursor = db.rawQuery(sql, null);
			break;
		default:
			Log.d("!!!!!!", "Unknown URI" + uri);
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String colums,
			String[] arg3) {
		// TODO Auto-generated method stub
		// db = databaseHelper.getWritableDatabase();
		// int itemId = -1;
		// switch (uriMatcher.match(uri)) {
		// case NoteCP.ITEM:
		//
		// break;
		// case NoteCP.ITEM_ID:
		// // String id = uri.getPathSegments().get(1);
		// itemId = db.update(NoteCP.TABLE_NOTES, values, NoteCP.NOTE_ID + "="
		// + 30, null);
		// break;
		// }
		return 0;
	}

}
