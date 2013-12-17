package org.espier.note.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.espier.note.R;
import org.espier.note.activity.EditNoteActivity;
import org.espier.note.activity.NoteListActivity;
import org.espier.note.db.DatabaseHelper;
import org.espier.note.model.Note;
import org.espier.note.util.TimeUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
	private Context context;
	private List<Note> items;
	private LayoutInflater inflater;
	private boolean isNull;
	private int startX, endX;
	private GestureDetector detector;
	private DatabaseHelper databaseHelper;
	private ViewHolder currentHolder;
	private ViewHolder oldHolder;
	public NoteAdapter(Context context, List<Note> items, boolean isNull) {
		this.context = context;
		this.items = items;
		this.isNull = isNull;
		inflater = LayoutInflater.from(context);
		databaseHelper = new DatabaseHelper(context);
		detector = new GestureDetector(context, new MyGuestureListener(context));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (items == null || items.size() == 0) {
			return 0;
		} else {
			return items.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.item_note, null);
			holder = new ViewHolder();
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tvDelete = (TextView) convertView
					.findViewById(R.id.tv_delete);
			holder.ivArrow = (ImageView) convertView
					.findViewById(R.id.iv_arrow);
			holder.position = position;

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.tvDelete.setVisibility(View.GONE);
		}
		holder.tvContent.setText(items.get(position).getContent());
//		PrettyTime prettyTime = new PrettyTime();
		Date date = TimeUtils.getDateByString("yyyy-MM-dd HH:mm:ss",
				items.get(position).getCreateTime());
		if (isNull) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT,
					RelativeLayout.TRUE);
			holder.tvContent.setGravity(Gravity.CENTER);
			holder.tvContent.setLayoutParams(params);
			holder.tvTime.setVisibility(View.GONE);
			holder.ivArrow.setVisibility(View.GONE);
			holder.ivArrow.setVisibility(View.GONE);
		}

		if (date != null) {
			holder.tvTime.setText(TimeUtils.getReadableTime(items.get(position).getCreateTime(), context));
		} else {
			holder.tvTime.setText("");
		}

		if (!isNull) {
			convertView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View view, MotionEvent ev) {
					// TODO Auto-generated method stub
					currentHolder = (ViewHolder) view.getTag();
					return detector.onTouchEvent(ev);
				}
			});
		}

		// String time=items.get(position).getCreateTime().substring(0, 10);
		return convertView;
	}

	class MyGuestureListener extends SimpleOnGestureListener {
		private Context context;
		private TextView tvDelete;
		public MyGuestureListener(Context context) {
			this.context = context;
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("===onDoubleTap");
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("===onDoubleTapEvent");
			return super.onDoubleTapEvent(e);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("===onDown");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			System.out.println("===onFling");
			
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("gesture===onLongPress");
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			System.out.println("===onScroll");
			if (distanceX>20) {
				if (oldHolder!=null&&oldHolder.tvDelete.getVisibility()==View.VISIBLE) {
					oldHolder.tvDelete.setVisibility(View.GONE);
				}
				
				if (oldHolder!=null&&oldHolder.position==currentHolder.position) {
					if (oldHolder.tvDelete.getVisibility()==View.VISIBLE) {
						oldHolder.tvDelete.setVisibility(View.GONE);
					}else {
						oldHolder.tvDelete.setVisibility(View.VISIBLE);
					}
				}
				
				oldHolder=currentHolder;
				
				currentHolder.tvDelete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						databaseHelper.deleteNoteById(items.get(
								currentHolder.position).getId());
						items.remove(currentHolder.position);
						((NoteListActivity) context).tvTitle.setText("Notes("
								+ items.size() + ")");
						if (items.size() == 0 || items == null) {
							isNull = true;
						}
						notifyDataSetChanged();
					}
				});
			}
			
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("===onShowPress");
			super.onShowPress(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("===onSingleTapConfirmed");

			return super.onSingleTapConfirmed(e);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("===onSingleTapUp");
			Intent intent = new Intent(context, EditNoteActivity.class);
			intent.putExtra("note", items.get(currentHolder.position));
			intent.putParcelableArrayListExtra("notes",
					(ArrayList<? extends Parcelable>) items);
			intent.putExtra("index", currentHolder.position);
			((Activity) context).startActivity(intent);
			return super.onSingleTapUp(e);
		}

	}

	public static class ViewHolder {
		int position;
		TextView tvContent;
		TextView tvTime;
		TextView tvDelete;
		ImageView ivArrow;

	}
}
