package com.imap.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class Util {
	
	public static void main(String args[]){
		try {
			URL url = new URL("http://10.107.10.173:8080/iMap/index.jsp");
			URLConnection urlconn = url.openConnection();
			InputStream input = urlconn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = reader.readLine();
			System.out.println(line);
			System.out.println("sdfsdfsdfsdfsdfdsfsdfsd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public String getPlaceName() {
		
		return "寮犳睙澶у瀹胯垗";
	}

	public String getPlaceAdress() {

		return "钄′鸡璺�1433鍙�";
	}

	public String getPlaceDescription() {

		return "鐙珛鍗荡锛岀敺濂虫贩浣忥紝绌鸿皟娲楄。鏈虹儹姘村櫒";
	}

	public String getPlaceIconURL() {
		return "";
	}

	public ArrayList<Comment> getCommentlist() {
		ArrayList<Comment> list = new ArrayList<Comment>();
		Comment comment = new Comment("浠婂ぉ澶╂皵鐪熷ソ", "太鍊煎緱鎷ユ湁", "1111", null);
		list.add(comment);
		comment = new Comment("isky", 
				"Orz銆傘�傘�傘�傘�傘�傘�傘�傘�傘��", "1111",
				null);
		list.add(comment);
		comment = new Comment("LMW",  "good", "1111", null);
		list.add(comment);
		comment = new Comment("LMW2",  "goodgood", "1111", null);
		list.add(comment);
		comment = new Comment("LMW3", "goodgoodgood", "1111", null);
		list.add(comment);
		comment = new Comment("LMW4",  "goodgoodgoodgood", "1111", null);
		list.add(comment);
		comment = new Comment("LMW5",  "goodgoodgoodgoodgood", "1111",
				null);
		list.add(comment);
		comment = new Comment("LMW6",  "goodgoodgoodgoodgoodgood",
				"1111", null);
		list.add(comment);
		comment = new Comment("LMW7",  "goodgoodgoodgoodgoodgoodgood",
				"1111", null);
		list.add(comment);
		comment = new Comment("LMW8", 
				"goodgoodgoodgoodgoodgoodgoodgood", "1111", null);
		list.add(comment);
		comment = new Comment("LMW9", 
				"goodgoodgoodgoodgoodgoodgoodgoodgood", "1111", null);
		list.add(comment);
		comment = new Comment("LMW10", 
				"goodgoodgoodgoodgoodgoodgoodgood", "1111", null);
		list.add(comment);
		return list;

	}

	public List<Map<String, Object>> getCommentsData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		ArrayList<Comment> arrayList = getCommentlist();
		int i = 0;
		while (i < arrayList.size()) {
			Comment comment = arrayList.get(i);
			map.put("title", null);
			map.put("user_name", comment.getUser_name());
			map.put("comment", comment.getContent());
			list.add(map);

			map = new HashMap<String, Object>();
			i++;

		}

		return list;

	}
	
	/**
	 * 鏄惁闇�瑕侀獙璇佺爜,鐜板湪榛樿涓嶉渶瑕�
	 */
	public String isNeedCaptcha(){
		return null;
	}
	public boolean Login(String name, String pwd, String captchavalue,
			String result, Context applicationContext) {
		// TODO Auto-generated method stub
		
		return true;
	}
	public int getcommentsnum(){
		return 10;
	}
	
}
