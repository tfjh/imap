package com.imap.database;

import android.graphics.Bitmap;

public class Comment {
	private int id;
	private String user_name;
//	private String title;
	private String content;
//	private String sendtime;
	private Bitmap pic;
	private int loc_id;

	public Comment(){}
	
	public int getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(int loc_id) {
		this.loc_id = loc_id;
	}
	
	public Comment(String user_name, String content,
			String sendtime, Bitmap pic) {
		super();
		this.user_name = user_name;
//		this.title = title;
		this.pic = pic;
		this.content = content;
//		this.sendtime = sendtime;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Bitmap getPic() {
		return pic;
	}

	public void setPic(Bitmap pic) {
		this.pic = pic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
