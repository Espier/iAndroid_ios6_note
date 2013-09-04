package org.espier.inotes.activity;

import java.util.ArrayList;
import java.util.List;

import org.espier.inotes.R;
import org.espier.inotes.adapter.NoteAdapter;
import org.espier.inotes.db.DatabaseHelper;
import org.espier.inotes.model.Note;
import org.espier.inotes.view.MyListView;
import org.espier.inotes.view.MyListView.OnRefreshListener;
import org.espier.inotes.view.UINavigation;

import android.R.integer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.content.DialogInterface;

public class NoteListActivity extends BaseAcvitity {
	private UINavigation navigation;
	private TextView tvRight,tvLeft;
	private MyListView listView;
	private DatabaseHelper databaseHelper;
	private NoteAdapter adapter;
	private List<Note> items;
	private boolean isNull=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_list);
		navigation=(UINavigation)findViewById(R.id.navigation);
		tvRight=(TextView)navigation.findViewById(R.id.tv_right);
		tvLeft=(TextView)navigation.findViewById(R.id.tv_left);
		tvLeft.setVisibility(View.GONE);
		tvRight.setText("    +    ");
		
		listView = (MyListView) findViewById(R.id.listView);
		tvRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(NoteListActivity.this,EditNoteActivity.class);
				startActivity(intent);
			}
		});
		listView.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		}
		
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		items=getData();
		int count=0;
		if (!isNull) {
			count=items.size();
		}
		if (!isNull) {
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(NoteListActivity.this,EditNoteActivity.class);
						intent.putExtra("note", items.get(position-1));
						intent.putParcelableArrayListExtra("notes",(ArrayList<? extends Parcelable>) items);
						intent.putExtra("index", position);
						startActivity(intent);
					}
					
				});
				listView.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view,
							final int position, long id) {
						// TODO Auto-generated method stub
						CharSequence[] alertItems = {"删除","取消"};
						AlertDialog.Builder builder=new AlertDialog.Builder(NoteListActivity.this);
						builder.setItems(alertItems,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								if (which==0) {
									System.out.println("postion=="+position);
									System.out.println("id=="+items.get(position-1).getId());
									boolean delete=databaseHelper.deleteNoteById(items.get(position-1).getId());
//									System.out.println("position=="+position+","+"delete=="+delete);
									items=getData();
									adapter=new NoteAdapter(NoteListActivity.this, items,isNull);
									listView.setAdapter(adapter);
								}else if(which==1){
									
								}
							}
						});
						builder.setTitle("确定要删除吗？");
						builder.show();
		                return false;
					}
					
				});
				
			
		}
		navigation.setTvTitleText("Notes("+count+")");
		adapter=new NoteAdapter(this, items,isNull);
		listView.setAdapter(adapter);
	}

	public List<Note> getData(){
		List<Note> items=new ArrayList<Note>();
		databaseHelper=new DatabaseHelper(this);
		items=databaseHelper.findAllNotes();
		System.out.println("items===size=="+items.size());
		if (items.size()==0||items==null) {
			isNull=true;
			for (int i = 0; i < 10; i++) {
				Note note=null;
				if (i==2) {
					note=new Note(3, "无备忘录", "");
				}else {
					note=new Note(i, "", "");
				}
				items.add(note);
			}
		}else {
			isNull=false;
		}
		
		return items;
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			items=getData();
			adapter.notifyDataSetChanged();
		}

	}
	
}
