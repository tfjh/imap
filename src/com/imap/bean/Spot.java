package com.imap.bean;

import java.util.ArrayList;

public class Spot {

	private int s_id;
	private double lat;
	private double lng;
	private String name;
	private int visited_time;
	private long last_timel;
	private long last_times;
	private String img_url;
	private String content;
	private ArrayList<String> tag;
	private String audio_url;
	private String author_name;
	private int author_id;
	private String create_time;
	private int degree;//the total amount of user-visiting.
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVisited_time() {
		return visited_time;
	}
	public void setVisited_time(int visited_time) {
		this.visited_time = visited_time;
	}
	public long getLast_timel() {
		return last_timel;
	}
	public void setLast_timel(long last_timel) {
		this.last_timel = last_timel;
	}
	public long getLast_times() {
		return last_times;
	}
	public void setLast_times(long last_times) {
		this.last_times = last_times;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<String> getTag() {
		return tag;
	}
	public void setTag(ArrayList<String> tag) {
		this.tag = tag;
	}
	public String getAudio_url() {
		return audio_url;
	}
	public void setAudio_url(String audio_url) {
		this.audio_url = audio_url;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	
	
}
