package com.imap.database;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 *
 * ��һ������ ����ͼƬ����·����url
 * �ڶ��������� ���صĽ�� 
 * �������������첽����ִ����Ϻ�ķ���ֵ
 * @author Administrator
 *
 */
public class LoadImageAsynTask extends AsyncTask<String, Void, Bitmap> {
	LoadImageAsynTaskCallback loadImageAsynTaskCallback;
	
	
	
	public LoadImageAsynTask(LoadImageAsynTaskCallback loadImageAsynTaskCallback) {
		this.loadImageAsynTaskCallback = loadImageAsynTaskCallback;
	}

	public interface LoadImageAsynTaskCallback{
	   public void	beforeLoadImage();
	   public void afterLoadImage(Bitmap bitmap);
	}

	/**
	 * ���첽����ִ��֮ǰ����
	 */
	@Override
	protected void onPreExecute() {
		//��ʼ���Ĳ���������ôȥʵ��, LoadImageAsynTask ��֪��
		// ��Ҫ�õ������ LoadImageAsynTask ���� ȥʵ�� 
		loadImageAsynTaskCallback.beforeLoadImage();
		super.onPreExecute();
	}

	/**
	 * �첽����ִ��֮�����
	 */
	@Override
	protected void onPostExecute(Bitmap result) {
		loadImageAsynTaskCallback.afterLoadImage(result);
		super.onPostExecute(result);
	}

	/**
	 * ��̨���߳����е��첽���� 
	 * String... params �ɱ䳤�ȵĲ��� 
	 */
	@Override
	protected Bitmap doInBackground(String... params) {
		try {
			String path = params[0];
			URL url = new URL(path);
			HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			return  BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
