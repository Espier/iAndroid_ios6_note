package org.espier.note.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
	private int id;
	private String content;
	private int color;
	private String createTime;

	public Note(int id, String content, int color, String createTime) {
		this.id = id;
		this.content = content;
		this.color = color;
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(content);
		dest.writeInt(color);
		dest.writeString(createTime);

	}

	public static final Parcelable.Creator<Note> CREATOR = new Creator() {

		@Override
		public Note createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			Note note = new Note(source.readInt(), source.readString(),
					source.readInt(),
					source.readString());

			return note;
		}

		@Override
		public Note[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Note[size];
		}
	};

}
