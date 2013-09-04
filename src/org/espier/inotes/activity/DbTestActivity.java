package org.espier.inotes.activity;

import java.util.Date;
import java.util.List;

import org.espier.inotes.R;
import org.espier.inotes.db.DatabaseHelper;
import org.espier.inotes.model.Note;
import org.espier.inotes.util.TimeUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DbTestActivity extends BaseAcvitity implements OnClickListener{
	private Button btnAdd,btnUpdate,btnQuery,btnDelete;
	private DatabaseHelper databaseHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_test);
		btnAdd=(Button)findViewById(R.id.btn_add);
		btnUpdate=(Button)findViewById(R.id.btn_update);
		btnQuery=(Button)findViewById(R.id.btn_query);
		btnDelete=(Button)findViewById(R.id.btn_delete);
		btnAdd.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnQuery.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		databaseHelper=new DatabaseHelper(this);
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add:
			for (int i = 0; i < 50; i++) {
				String date = TimeUtils.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Date());
				Note note=new Note(i+1, "测试数据测试数据测试数据测试数据测试数据"+i+1,date);
				databaseHelper.insertNote(note);
				System.out.println("添加成功");
			}
			
			break;
		case R.id.btn_update:
			String date1 = TimeUtils.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Date());
			Note note1=new Note(2, "122222222", date1);
			databaseHelper.updateNoteById(note1);
		case R.id.btn_query:
			List<Note> notes=databaseHelper.findAllNotes();
			for (int i = 0; i < notes.size(); i++) {
				System.out.println("content==id"+notes.get(i).getId()+"=="+notes.get(i).getContent()+"==time="+notes.get(i).getCreateTime());
			}
		case R.id.btn_delete:
			databaseHelper.deleteNoteById(2);
		default:
			break;
		}
	}

}
