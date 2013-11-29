package org.espier.note.activity;

import java.util.ArrayList;
import java.util.List;

import org.espier.note.adapter.NoteEditAdapter;
import org.espier.note.model.Note;

import android.os.Bundle;

import com.aphidmobile.flip.FlipCards;
import com.aphidmobile.flip.FlipViewController;

public class EditNoteActivity extends BaseAcvitity {
	private FlipViewController flipView;
	private FlipCards flipCards;
	private List<Note> items;
	private Note note;
	private NoteEditAdapter adapter;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		note = (Note) getIntent().getSerializableExtra("note");
		items = new ArrayList<Note>();
		items = getIntent().getParcelableArrayListExtra("notes");
		index = getIntent().getIntExtra("index", 0);
		System.out.println("index===" + index);
		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
		flipCards = flipView.cards;
		adapter = new NoteEditAdapter(this, items);
		adapter.setController(flipView);
		flipView.setAdapter(adapter, index);
		// flipView.setSelection(index);
		setContentView(flipView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		flipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		flipView.onPause();
	}
}
