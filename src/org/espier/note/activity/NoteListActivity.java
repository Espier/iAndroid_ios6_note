package org.espier.note.activity;

import java.util.ArrayList;
import java.util.List;

import org.espier.note.R;
import org.espier.note.adapter.NoteAdapter;
import org.espier.note.db.DatabaseHelper;
import org.espier.note.model.Note;
import org.espier.note.view.MyListView;
import org.espier.note.view.MyListView.OnRefreshListener;
import org.espier.note.view.UINavigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NoteListActivity extends BaseAcvitity {
	private UINavigation navigation;
	private TextView tvRight, tvLeft, tvTitle;
	public MyListView listView;
	private DatabaseHelper databaseHelper;
	public NoteAdapter adapter, dialogAdapter;
	private List<Note> items;
	private boolean isNull = false;
	private EditText etSearch, etDialogSearch;
	public Dialog baseDialog;
	public LayoutInflater mInflater;
	private ListView lvDialog;
	private int count = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_list);
		navigation = (UINavigation) findViewById(R.id.navigation);
		tvRight = (TextView) navigation.findViewById(R.id.tv_right);
		tvLeft = (TextView) navigation.findViewById(R.id.tv_left);
		tvTitle = (TextView) navigation.findViewById(R.id.tv_title);
		tvTitle.setVisibility(View.VISIBLE);
		tvLeft.setVisibility(View.GONE);
		tvRight.setText("    +    ");

		listView = (MyListView) findViewById(R.id.listView);
		etSearch = (EditText) findViewById(R.id.et_search);
		etSearch.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean hasfocus) {
				// TODO Auto-generated method stub
				if (hasfocus) {
					etSearch.setVisibility(View.INVISIBLE);
					navigation.setVisibility(View.GONE);
					baseDialog = getDialog(NoteListActivity.this);
					etDialogSearch.setHint("Search");
					etDialogSearch.findFocus();
					etDialogSearch.addTextChangedListener(new TextWatcher() {

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub


						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int before, int count) {
							// TODO Auto-generated method stub

						}

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub
							lvDialog.setBackgroundResource(R.drawable.bg_body);
							String keyWords = etDialogSearch.getText()
									.toString();
							List<Note> notes = databaseHelper
									.searchNotes(keyWords);
							for (int i = 0; i < notes.size(); i++) {
								System.out.println(notes.get(i).toString());
								dialogAdapter = new NoteAdapter(
										NoteListActivity.this, notes, false);
								lvDialog.setAdapter(dialogAdapter);
							}

						}
					});

				}

			}
		});

		tvRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NoteListActivity.this,
						EditNoteActivity.class);
				startActivity(intent);
			}
		});
		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub

			}

		});

		// detector = new GestureDetector(this, new MyGuestureListener(this));

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		items = getData();
		if (null == items || items.size() == 0) {
			isNull = true;
		}
		tvTitle.setText("Notes(" + count + ")");
		System.out.println("mmmmmm");
		adapter = new NoteAdapter(this, items, isNull);
		listView.setAdapter(adapter);

	}

	public List<Note> getData() {
		List<Note> items = new ArrayList<Note>();
		databaseHelper = new DatabaseHelper(this);
		items = databaseHelper.findAllNotes();
		count = items.size();
		System.out.println("items===size==" + items.size());
		if (items.size() == 0 || items == null) {
			isNull = true;
			for (int i = 0; i < 10; i++) {
				Note note = null;
				if (i == 2) {
					note = new Note(3, "无备忘录", "");
				} else {
					note = new Note(i, "", "");
				}
				items.add(note);
			}
		} else {
			isNull = false;
		}

		return items;
	}
	public Dialog getDialog(Context context) {
		if (mInflater == null) {
			mInflater = LayoutInflater.from(context);
		}
		if (baseDialog != null)
			baseDialog = null;
		baseDialog = new Dialog(context, R.style.Transparent);
		baseDialog.setCanceledOnTouchOutside(true);
		// dialog.
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout mLayout = (LinearLayout) mInflater.inflate(
				R.layout.dialog_search, null);
		etDialogSearch = (EditText) mLayout.findViewById(R.id.et_search);
		lvDialog = (ListView) mLayout.findViewById(R.id.lv_dialog);
		lvDialog.setBackground(null);
		baseDialog.getWindow().setContentView(mLayout, lp);

		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(INPUT_METHOD_SERVICE);
		// imm.showSoftInput(mLayout, 0);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		Activity a = null;
		if (context instanceof Activity) {
			a = (Activity) context;
		}
		baseDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (navigation.getVisibility() == View.GONE
							|| etSearch.getVisibility() == View.INVISIBLE) {
						navigation.setVisibility(View.VISIBLE);
						etSearch.setVisibility(View.VISIBLE);
						baseDialog.dismiss();
					}
					return true;
				} else {
					return false; // 默认返回 false
				}
			}
		});
		if (a != null && !a.isFinishing() && !baseDialog.isShowing())
			baseDialog.show();
		return baseDialog;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			items = getData();
			System.out.println("nnnnnnnnnn");
			adapter.notifyDataSetChanged();
		}

	}



}
