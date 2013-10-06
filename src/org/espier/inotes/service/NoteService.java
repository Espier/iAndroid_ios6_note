package org.espier.inotes.service;

import java.util.ArrayList;
import java.util.List;

import org.espier.inotes.db.DatabaseHelper;
import org.espier.inotes.model.Note;
import org.espier.inotes.widget.NoteWidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

public class NoteService extends Service {
	private int[] appWidgetIds;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		int index = intent.getIntExtra("index", 0);
		int viewId = intent.getIntExtra("viewId", 0);
		System.out.println("index===" + index + ",viewId=" + viewId);

		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		List<Note> notes = databaseHelper.findAllNotes();
		if (null != notes && notes.size() >= 0) {
			LocalBroadcastManager manager = LocalBroadcastManager
					.getInstance(this);
			Intent it = new Intent();
			it.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			it.putParcelableArrayListExtra("notes",
					(ArrayList<? extends Parcelable>) notes);
			it.putExtra("viewId", viewId);
			it.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			BroadcastReceiver receiver = new NoteWidget();
			manager.registerReceiver(receiver, new IntentFilter(
					AppWidgetManager.ACTION_APPWIDGET_UPDATE));
			manager.sendBroadcast(it);
			manager.unregisterReceiver(receiver);
		}
		return super.onStartCommand(intent, flags, startId);
	}

}
