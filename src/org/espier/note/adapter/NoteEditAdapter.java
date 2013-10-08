package org.espier.note.adapter;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.espier.note.R;
import org.espier.note.activity.NoteListActivity;
import org.espier.note.db.DatabaseHelper;
import org.espier.note.model.Note;
import org.espier.note.util.TimeUtils;
import org.espier.note.view.LinedEditText;
import org.espier.note.view.MyLinearLayout;
import org.espier.note.view.UINavigation;
import org.ocpsoft.prettytime.PrettyTime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;

public class NoteEditAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private int repeatCount = 1;
	private List<Note> items;
	private Context context;
	private FlipViewController controller;
	private DatabaseHelper databaseHelper;
	private Animation animation;

	public NoteEditAdapter(Context context, List<Note> items) {
		this.items = items;
		this.context = context;
		inflater = LayoutInflater.from(context);
		databaseHelper = new DatabaseHelper(context);
	}

	public void setController(FlipViewController controller) {
		this.controller = controller;
	}

	@Override
	public int getCount() {
		if (items == null || items.size() == 0) {
			return 1;
		} else {
			return items.size();
		}
	}

	public int getRepeatCount() {
		return 0;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.activity_edit_note, null);
			holder = new ViewHolder();
			holder.navigation = (UINavigation) convertView
					.findViewById(R.id.navigation);
			holder.tvLeft = (TextView) holder.navigation
					.findViewById(R.id.tv_left);
			holder.tvRight = (TextView) holder.navigation
					.findViewById(R.id.tv_right);
			holder.llContainer = (MyLinearLayout) convertView
					.findViewById(R.id.ll_container);
			holder.llHeader = (LinearLayout) holder.llContainer
					.findViewById(R.id.ll_header);
			holder.llRemove = (LinearLayout) convertView
					.findViewById(R.id.ll_remove);
			holder.ivRomoveTop = (ImageView) convertView
					.findViewById(R.id.iv_remove_top);
			holder.llAction = (LinearLayout) convertView
					.findViewById(R.id.ll_aciton);
			// System.out.println("holder.llContainer.isShow="+holder.llContainer.isShow);
			// if (holder.llContainer.isShow) {
			// holder.llHeader.setPadding(0, 0, 0, 0);
			// holder.llContainer.invalidate();
			// holder.llContainer.isShow=true;
			// }
			// holder.isShow=holder.llContainer.isShow;
			holder.etContent = (LinedEditText) convertView
					.findViewById(R.id.let_content);
			holder.navigation = (UINavigation) convertView
					.findViewById(R.id.navigation);
			holder.ivRight = (ImageView) convertView
					.findViewById(R.id.iv_right);
			holder.ivLeft = (ImageView) convertView.findViewById(R.id.iv_left);
			holder.ivAction = (ImageView) convertView
					.findViewById(R.id.iv_action);
			holder.rgColors = (RadioGroup) convertView
					.findViewById(R.id.rg_color);
			holder.rbBrown = (RadioButton) convertView
					.findViewById(R.id.rb_brown);
			holder.rbGreen = (RadioButton) convertView
					.findViewById(R.id.rb_green);
			holder.rbBlue = (RadioButton) convertView
					.findViewById(R.id.rb_blue);
			holder.rbBlack = (RadioButton) convertView
					.findViewById(R.id.rb_black);
			holder.rbPurple = (RadioButton) convertView
					.findViewById(R.id.rb_purple);
			holder.rbRed = (RadioButton) convertView.findViewById(R.id.rb_red);
			holder.rbOrange = (RadioButton) convertView
					.findViewById(R.id.rb_orange);
			holder.tvReadableTime = (TextView) convertView
					.findViewById(R.id.tv_readable_time);
			holder.tvDate = (TextView) convertView
					.findViewById(R.id.tv_month_day);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_hour_minite);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			System.out.println("===holder.llContainer.isShow111="
					+ holder.llHeader.isShown());
			// holder.llHeader.removeView(holder.llHeader);
			// holder.llHeader.setPadding(0, -1 * holder.llHeader.getHeight(),
			// 0,
			// 0);
			// holder.llHeader.invalidate();
		}
		if (items == null) {
			holder.llAction.setVisibility(View.GONE);
			// holder.etContent.setText(items.get(position).getContent());
			// Date date = TimeUtils.getDateByString("yyyy-MM-dd HH:mm:ss",
			// new Date());
			PrettyTime prettyTime = new PrettyTime();
			holder.tvReadableTime.setText(prettyTime.format(new Date()));
			// String dateStr = DateFormat.getDateInstance(DateFormat.FULL)
			// .format(new Date());
			Date date = new Date();
			holder.tvDate.setText(date.getMonth() + 1 + "月" + date.getDate()
					+ "日");
			holder.tvTime.setText(TimeUtils.getHour() + ":"
					+ TimeUtils.getTime());
		} else {
			controller.setViews(holder.ivLeft, holder.ivRight);
			holder.etContent.setText(items.get(position).getContent());
			Date date = TimeUtils.getDateByString("yyyy-MM-dd HH:mm:ss", items
					.get(position).getCreateTime());
			PrettyTime prettyTime = new PrettyTime();
			holder.tvReadableTime.setText(prettyTime.format(date));
			String dateStr = DateFormat.getDateInstance(DateFormat.FULL)
					.format(date);
			holder.tvDate.setText(date.getMonth() + 1 + "月" + date.getDate()
					+ "日");
			holder.tvTime.setText(date.getHours() + ":" + date.getMinutes());
		}
		holder.tvRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String date = TimeUtils.getSimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", new Date());
				Note note = new Note(1, holder.etContent.getText().toString(),
						date);
				databaseHelper.insertNote(note);
				Intent intent = new Intent(context, NoteListActivity.class);
				((Activity) context).setResult(1, intent);
				((Activity) context).finish();
			}
		});
		holder.tvLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Activity) context).finish();
			}
		});
		holder.ivAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
				intent.putExtra(Intent.EXTRA_TEXT, items.get(position)
						.getContent());
				((Activity) context).startActivity(intent);
			}
		});
		holder.rgColors
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						if (checkedId == holder.rbBrown.getId()) {
							holder.etContent.setTextColor(context
									.getResources().getColorStateList(
											R.color.text_brown));
						} else if (checkedId == holder.rbGreen.getId()) {
							holder.etContent.setTextColor(context
									.getResources().getColorStateList(
											R.color.text_green));
						} else if (checkedId == holder.rbBlue.getId()) {
							holder.etContent.setTextColor(context
									.getResources().getColorStateList(
											R.color.text_blue));
						} else if (checkedId == holder.rbBlack.getId()) {
							holder.etContent.setTextColor(context
									.getResources().getColorStateList(
											R.color.text_black));
						} else if (checkedId == holder.rbPurple.getId()) {
							holder.etContent.setTextColor(context
									.getResources().getColorStateList(
											R.color.text_purple));
						} else if (checkedId == holder.rbRed.getId()) {
							holder.etContent.setTextColor(context
									.getResources().getColorStateList(
											R.color.text_red));
						} else if (checkedId == holder.rbOrange.getId()) {
							holder.etContent.setTextColor(context
									.getResources().getColorStateList(
											R.color.text_orange));
						}
					}

				});
		holder.llRemove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("提示");
				builder.setMessage("确定删除吗？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								databaseHelper.deleteNoteById(items.get(
										position).getId());
								// animation = AnimationUtils.loadAnimation(
								// context, R.anim.anim_remove);
								// animation.setDuration(300);
								// holder.ivRomoveTop.startAnimation(animation);
								Intent intent = new Intent(context,
										NoteListActivity.class);
								((Activity) context).setResult(1, intent);
								((Activity) context).finish();
							}
						});
				builder.setNeutralButton("取消", null);
				builder.show();

			}
		});
		// String time=items.get(position).getCreateTime().substring(0, 10);
		// holder.tvTime.setText(time);
		return convertView;
	}

	class ViewHolder {
		LinedEditText etContent;
		UINavigation navigation;
		MyLinearLayout llContainer;
		LinearLayout llHeader;
		LinearLayout llAction;
		TextView tvLeft;
		TextView tvRight;
		ImageView ivRight;
		ImageView ivLeft;
		ImageView ivAction;
		LinearLayout llRemove;
		ImageView ivRomoveTop;
		RadioGroup rgColors;
		RadioButton rbBrown;
		RadioButton rbGreen;
		RadioButton rbBlue;
		RadioButton rbBlack;
		RadioButton rbPurple;
		RadioButton rbRed;
		RadioButton rbOrange;
		TextView tvReadableTime;
		TextView tvDate;
		TextView tvTime;
		boolean isShow;
	}

}
