package org.espier.inotes.adapter;

import java.util.Date;
import java.util.List;

import org.espier.inotes.R;
import org.espier.inotes.model.Note;
import org.espier.inotes.util.TimeUtils;
import org.ocpsoft.prettytime.PrettyTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
	private Context context;
	private List<Note> items;
	private LayoutInflater inflater;
	private boolean isNull;
	public NoteAdapter(Context context,List<Note> items,boolean isNull) {
		this.context=context;
		this.items=items;
		this.isNull=isNull;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (items == null || items.size() == 0) {
			return 0;
		}else {
			return items.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView==null||convertView.getTag()==null) {
			convertView=inflater.inflate(R.layout.item_note, null);
			holder=new ViewHolder();
			holder.tvContent=(TextView)convertView.findViewById(R.id.tv_content);
			holder.tvTime=(TextView)convertView.findViewById(R.id.tv_time);
			holder.ibDelete=(ImageButton)convertView.findViewById(R.id.ib_delete);
			holder.ivArrow=(ImageView)convertView.findViewById(R.id.iv_arrow);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.tvContent.setText(items.get(position).getContent());
		PrettyTime prettyTime=new PrettyTime();
		Date date = TimeUtils.getDateByString("yyyy-MM-dd HH:mm:ss",
				items.get(position).getCreateTime());
		if (isNull) {
			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
			holder.tvContent.setLayoutParams(params);
			holder.tvTime.setVisibility(View.GONE);
			holder.ivArrow.setVisibility(View.GONE);
		}
		
		if (date!=null) {
			String time=prettyTime.format(date);
			holder.tvTime.setText(time);
		}else {
			holder.tvTime.setText("");
		}
		
//		String time=items.get(position).getCreateTime().substring(0, 10);
		
		return convertView;
	}
	class ViewHolder{
		TextView tvContent;
		TextView tvTime;
		ImageButton ibDelete;
		ImageView ivArrow;
	}
}
