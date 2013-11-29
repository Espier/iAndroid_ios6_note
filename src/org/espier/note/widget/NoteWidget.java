package org.espier.note.widget;

import java.util.ArrayList;
import java.util.List;

import org.espier.note.R;
import org.espier.note.activity.EditNoteActivity;
import org.espier.note.db.DatabaseHelper;
import org.espier.note.model.Note;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;

public class NoteWidget extends AppWidgetProvider {
	private Note note;
	private DatabaseHelper databaseHelper;
	private List<Note> notes;
	private int appWidgetId;
	private RemoteViews views;
	public static final String ACTION_UP = "org.espiser.inotes.weidgt.up";
	public static final String ACTION_DOWN = "org.espiser.inotes.weidgt.down";
	public static final String ACTION_WRITE = "org.espiser.inotes.weidgt.write";
	public static final String ACTION_EDIT = "org.espiser.inotes.weidgt.edit";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int index = intent.getIntExtra("index", -1);
		System.out.println("===index-intent=" + index);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		views = new RemoteViews(context.getPackageName(), R.layout.widget);
		databaseHelper = new DatabaseHelper(context);
		notes = databaseHelper.findAllNotes();
		if (intent.getAction().equals(ACTION_WRITE)) {

			System.out.println("===write");
			Intent intent1 = new Intent(context, EditNoteActivity.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent1);

		} else if (intent.getAction().equals(ACTION_DOWN)) {
			System.out.println("index111====" + index + ",size()=="
					+ notes.size());
			if (index < notes.size() - 1) {
				index++;
			}
			if (index == notes.size() - 1) {
				views.setImageViewResource(R.id.iv_widget_down,
						R.drawable.widget_down_grey);
				views.setImageViewResource(R.id.iv_widget_up,
						R.drawable.widget_up);
				views.setTextViewText(R.id.tv_widget_content, "i==" + index
						+ notes.get(index).getContent());
				setViews(context, index, views);
				System.out.println("1115555555555555==" + index);
				manager.updateAppWidget(new ComponentName(context,
						NoteWidget.class), views);

			} else {
				views.setImageViewResource(R.id.iv_widget_down,
						R.drawable.widget_down);
				views.setTextViewText(R.id.tv_widget_content, "i==" + index
						+ notes.get(index).getContent());
				setViews(context, index, views);
				manager.updateAppWidget(new ComponentName(context,
						NoteWidget.class), views);
			}

		} else if (intent.getAction().equals(ACTION_UP)) {
			if (index != 0) {
				index--;
			}
			if (index > 0) {
				views.setImageViewResource(R.id.iv_widget_up,
						R.drawable.widget_up);
				views.setTextViewText(R.id.tv_widget_content, "i==" + index
						+ notes.get(index).getContent());
				setViews(context, index, views);
				manager.updateAppWidget(new ComponentName(context,
						NoteWidget.class), views);
			} else {
				views.setImageViewResource(R.id.iv_widget_up,
						R.drawable.widget_up_grey);
				views.setImageViewResource(R.id.iv_widget_down,
						R.drawable.widget_down);
				views.setTextViewText(R.id.tv_widget_content, "i==" + index
						+ notes.get(index).getContent());
				setViews(context, index, views);
				manager.updateAppWidget(new ComponentName(context,
						NoteWidget.class), views);
			}

		} else if (intent.getAction().equals(ACTION_EDIT)) {
			setViews(context, index, views);
			manager.updateAppWidget(
					new ComponentName(context, NoteWidget.class), views);
			Intent intent1 = new Intent(context, EditNoteActivity.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent1.putExtra("note", notes.get(index));
			intent1.putParcelableArrayListExtra("notes",
					(ArrayList<? extends Parcelable>) notes);
			intent1.putExtra("index", index);
			context.startActivity(intent1);
		}

		System.out.println("=====onReceive");
		super.onReceive(context, intent);

	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			Log.i("myLog", "this is [" + appWidgetId + "] onDelete!");
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;
		int index = 0;
		databaseHelper=new DatabaseHelper(context);
		for (int i = 0; i < N; i++) {
			appWidgetId = appWidgetIds[i];
			Log.i("myLog", "this is [" + appWidgetId + "] onUpdate!");
			views = new RemoteViews(context.getPackageName(),
					R.layout.widget);
			notes = databaseHelper.findAllNotes();
			if (null != notes && notes.size() > 0) {
				note = notes.get(i);
			} else {
				String no =context.getResources().getString(R.string.no_note);
				note = new Note(0, no, 0, "");
			}
			views.setImageViewResource(R.id.iv_widget_up,
						R.drawable.widget_up_grey);
			System.out.println("note===" + note.getContent());
			views.setTextViewText(R.id.tv_widget_content, note.getContent());
			setUpAndDownClick(context, views, R.id.iv_widget_write,
					ACTION_WRITE, index);
			setUpAndDownClick(context, views, R.id.tv_widget_content,
					ACTION_EDIT, index);
			setUpAndDownClick(context, views, R.id.iv_widget_down, ACTION_DOWN,
					index);
			setUpAndDownClick(context, views, R.id.iv_widget_up, ACTION_UP,
					index);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}


	}

	private void setUpAndDownClick(Context context, RemoteViews views,
			int viewId, String action, int index) {
		System.out.println("===indexddd" + index);
		Intent intent = new Intent(context, NoteWidget.class);
		// intent.setClass(context, NoteWidget.class);
		// intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		intent.setAction(action);
		intent.putExtra("index", index);
		// filter.addAction(action);
		// filter.addAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

		// intent.setData(Uri.parse("custom:" + index));
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(viewId, pi);
	}

	private void setViews(Context context, int index, RemoteViews views) {
		setUpAndDownClick(context, views, R.id.iv_widget_write, ACTION_WRITE,
				index);
		setUpAndDownClick(context, views, R.id.iv_widget_down, ACTION_DOWN,
				index);
		setUpAndDownClick(context, views, R.id.iv_widget_up, ACTION_UP, index);
		setUpAndDownClick(context, views, R.id.tv_widget_content, ACTION_EDIT,
				index);
	}
}
