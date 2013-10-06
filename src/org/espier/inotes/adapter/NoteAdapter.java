package org.espier.inotes.adapter;

import java.util.Date;
import java.util.List;

import org.espier.inotes.R;
import org.espier.inotes.activity.NoteListActivity;
import org.espier.inotes.db.DatabaseHelper;
import org.espier.inotes.model.Note;
import org.espier.inotes.util.TimeUtils;
import org.ocpsoft.prettytime.PrettyTime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
	private ViewHolder touchHolder;
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
		PrettyTime prettyTime = new PrettyTime();
		Date date = TimeUtils.getDateByString("yyyy-MM-dd HH:mm:ss",
				items.get(position).getCreateTime());
		if (isNull) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			holder.tvContent.setLayoutParams(params);
			holder.tvTime.setVisibility(View.GONE);
			holder.ivArrow.setVisibility(View.GONE);
			holder.ivArrow.setVisibility(View.GONE);
		}

		if (date != null) {
			String time = prettyTime.format(date);
			holder.tvTime.setText(time);
		} else {
			holder.tvTime.setText("");
		}

		// convertView.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View view, MotionEvent event) {
		// // TODO Auto-generated method stub
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_DOWN:
		// System.out.println("===down");
		// startX = (int) event.getX();
		// break;
		// case MotionEvent.ACTION_MOVE:
		// System.out.println("===move");
		// break;
		// case MotionEvent.ACTION_UP:
		// System.out.println("===up");
		// endX = (int) event.getX();
		// if (Math.abs(endX - startX) > 60) {
		// if (holder.tvDelete.getVisibility() == view.VISIBLE) {
		// holder.tvDelete.setVisibility(View.GONE);
		// } else {
		// holder.tvDelete.setVisibility(View.VISIBLE);
		// holder.tvDelete
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View view) {
		// // TODO Auto-generated method stub
		// databaseHelper.deleteNoteById(items
		// .get(position).getId());
		// items.remove(position);
		// notifyDataSetChanged();
		//
		// }
		// });
		// }
		//
		// }
		// if (Math.abs(endX - startX) < 5) {
		// System.out.println("===onclick");
		// Intent intent = new Intent(context,
		// EditNoteActivity.class);
		// intent.putExtra("note", items.get(position));
		// intent.putParcelableArrayListExtra("notes",
		// (ArrayList<? extends Parcelable>) items);
		// intent.putExtra("index", position);
		// System.out.println("index===" + position);
		// ((Activity) context).startActivity(intent);
		// }
		// break;
		// default:
		// break;
		// }
		// return true;
		// }
		// });

		convertView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				// TODO Auto-generated method stub
				touchHolder = (ViewHolder) view.getTag();
				return detector.onTouchEvent(ev);
			}
		});
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
			touchHolder.tvDelete.setVisibility(View.VISIBLE);
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("gesture===onLongPress");
			CharSequence[] alertItems = { "删除", "取消" };
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setItems(alertItems, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if (which == 0) {
						System.out.println("id=="
								+ items.get(touchHolder.position).getId());
						boolean delete = databaseHelper.deleteNoteById(items
								.get(touchHolder.position).getId());
						// System.out.println("position=="+position+","+"delete=="+delete);
						items.remove(touchHolder.position);
						if (null == items || items.size() == 0) {
							isNull = true;
							items = ((NoteListActivity) context).getData();
							((NoteListActivity) context).adapter = new NoteAdapter(
									context, items, isNull);
							((NoteListActivity) context).listView
									.setAdapter(((NoteListActivity) context).adapter);
						}
						notifyDataSetChanged();
					} else if (which == 1) {

					}
				}
			});
			builder.setTitle("确定要删除吗？");
			builder.show();
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			System.out.println("===onScroll");
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
