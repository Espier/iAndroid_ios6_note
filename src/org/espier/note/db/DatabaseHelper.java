package org.espier.note.db;

import java.util.ArrayList;
import java.util.List;

import org.espier.note.model.Note;
import org.espier.note.model.NoteCP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	
	public DatabaseHelper(Context context, String name, 
			int version) {
		super(context, name, null, version);
	}
	public DatabaseHelper(Context context, int version) {
		super(context, NoteCP.DB_NAME, null, version);
	}
	public DatabaseHelper(Context context){
		super(context, NoteCP.DB_NAME, null, NoteCP.DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(NoteCP.SQL_CREATE_NOTES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+NoteCP.TABLE_NOTES);  
        onCreate(db); 
	}
	
	public List<Note> findAllNotes(){
		SQLiteDatabase db=this.getReadableDatabase();
		List<Note> notes=new ArrayList<Note>();
		Cursor cursor=db.query(NoteCP.TABLE_NOTES, null,null, null, null, null, "note_create_time desc");
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			int id=cursor.getInt(cursor.getColumnIndex("note_id"));
			String content=cursor.getString(cursor.getColumnIndex("note_content"));
			String createTime=cursor.getString(cursor.getColumnIndex("note_create_time"));
			Note note=new Note(id, content, createTime);
			notes.add(note);
		}
		cursor.close();
		return notes;
	}
	
	public boolean deleteNoteById(int noteId){
		SQLiteDatabase db=this.getWritableDatabase();
		boolean isDelete=false;
		int flag=db.delete(NoteCP.TABLE_NOTES, "note_id=?", new String[]{noteId+""});
		if (flag!=-1) {
			isDelete=true;
		}
		db.close();
		return isDelete;
	}
	
	public boolean insertNote(Note note){
		SQLiteDatabase db=this.getWritableDatabase();
		boolean isInsert=false;
		ContentValues cv=new ContentValues();
		cv.put("note_content", note.getContent());
		cv.put("note_create_time", note.getCreateTime());
		long note_id=db.insert(NoteCP.TABLE_NOTES, "note_id", cv);
		if (note_id!=-1) {
			isInsert=true;
		}
		db.close();
		return isInsert;
	}
	
	public boolean updateNoteById(Note note){
		SQLiteDatabase db=this.getWritableDatabase();
		boolean isUpdate=false;
		ContentValues cv=new ContentValues();
		cv.put("note_id", note.getId());
		cv.put("note_content", note.getContent());
		cv.put("note_create_time", note.getCreateTime());
		int count=db.update(NoteCP.TABLE_NOTES, cv, "note_id=?", new String[]{note.getId()+""});
		if (count>0) {
			isUpdate=true;
		}
		db.close();
		return isUpdate;
	}

	public List<Note> searchNotes(String keyWords){
		SQLiteDatabase db=this.getReadableDatabase();
		String sql = "select * from " + NoteCP.TABLE_NOTES + " where "
				+ NoteCP.NOTE_CONTENT + " like '%" + keyWords + "%'";
		Cursor cursor = db.rawQuery(sql, null);
		List<Note> notes = new ArrayList<Note>();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("note_id"));
			String content = cursor.getString(cursor
					.getColumnIndex("note_content"));
			String createTime = cursor.getString(cursor
					.getColumnIndex("note_create_time"));
			Note note = new Note(id, content, createTime);
			notes.add(note);
		}
		cursor.close();
		return notes;

	}
}