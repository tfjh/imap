package com.imap.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.example.imap.R;

public class HttpUtil {
	
	
	String path;
	HttpClient client = new DefaultHttpClient();
	HttpPost post;
	HttpResponse response;
	final String BASE_PATH = "http://175.186.129.145:8080/iMap";
	
//	if user == null , no such user!
	public User UserLogin(String name, String passwd){
		
		
		//测试用
		if("lmw".equals(name)){
			User user = new User();
			user.setEmail("");
			user.setId(1);
			user.setMsg("hhh");
			user.setName("lmw");
			user.setSex("male");
			return user;
		}
		
		
		User user = new User();
		path = BASE_PATH+"/checkLogin";
		String[] str = getResponse(path+"?username="+name+"&password="+passwd);
//		System.out.println(str[4]);
		if(str[4].equals("-1"))
			return null;
		user.setMsg(str[0]);
		user.setName(str[1]);
		user.setSex(str[2]);
		user.setEmail(str[3]);
		user.setId(Integer.parseInt(str[4]));
		return user;
	}
	
//	if id = -1, fail
//	if id = -2, username is already used;
	public int UserRegister(String name, String password, String sex, String email, String msg){
		path = BASE_PATH+"/userRegist";
		int id = -1;
		String[] str = getResponse(path+"?username="+name+"&password="+password+"&sex="+sex+"&email="+email+"&msg="+msg);
		id = Integer.parseInt(str[0]);
		return id;
	}
	
	public void UserUpload(String name, String password, String sex, String email, String msg, int id){
		path = BASE_PATH+"/userUpload";
		String[] str = getResponse(path+"?username="+name+"&password="+password+"&sex="+sex+"&email="+email+"&msg="+msg+"&id="+id);
	}
	
	public List<Location> getLocation(){
		path  = BASE_PATH+"/getLocation";
		List<Location> list = new ArrayList<Location>();
		Location location = new Location();
		String[] str = getResponse(path);
//		System.out.println("i am already out of getResponse And the length of the string[] is "+str.length);
//		System.out.println(str[0]);
		for(int i=0;i<str.length;i++){
			location = new Location();
			String[] s = str[i].split("@");
//			for(int j=0;j<s.length;j++){
				location.setLatLng(new LatLng(Double.parseDouble(s[0]),Double.parseDouble(s[1])));
				location.setPic_url(s[2]);
				location.setComment(s[3]);
				location.setName(s[4]);
				location.setId(Integer.parseInt(s[5]));
				location.setIcon_id(R.drawable.icon_marka);
				location.setBitmap(BitmapDescriptorFactory.fromResource(location
						.getIcon_id()));
//			}
			list.add(location);
		}
		return list;
	}
	
	
	public List<Comment> getLocComment(int locId){
		path = BASE_PATH+"/getLocComment";
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = new Comment();
		String[] str = getResponse(path+"?location="+locId);
//		System.out.println("Comment[0] is "+str[0]);
		for(int i=0;i<str.length;i++){
			comment = new Comment();
			String[] s = str[i].split("@");
//			for(int j=0;j<s.length;j++){
				comment.setId(Integer.parseInt(s[0]));
				comment.setUser_name(s[1]);
//				comment.setTitle(s[2]);
				comment.setContent(s[2]);
				System.out.println(s[2]+" of comment "+i);
//				comment.setSendtime(s[3]);
				comment.setLoc_id(Integer.parseInt(s[3]));
				comment.setPic(null);
//			}
			list.add(comment);
		}
		
		return list;
	}
	
	public void addComment(String username, String content, int loc_id,int user_id){
		path = BASE_PATH+"/addComment";
		getResponse(path+"?username="+username+"&content="+content+"&loc_id="+loc_id+"&user_id="+user_id);
	}
	
/*---------------------------------------Helper Function--------------------------------------------*/
	public String[] getResponse(String param){
		post = new HttpPost(param);
		String str = "";
		try {
			response = client.execute(post);
//			System.out.println("i am already excute httppost");
			str = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseHtml(str);
	}
	
	public String[] parseHtml(String str){
		String[] re = null;
		re = str.split("#");
		return re;
	}
}
