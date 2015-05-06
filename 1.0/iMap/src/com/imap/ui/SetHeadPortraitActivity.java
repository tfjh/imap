package com.imap.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.imap.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SetHeadPortraitActivity extends Activity implements
		OnClickListener {

	private static final int PHOTO_REQUEST_CAMERA = 1;// from camera
	private static final int PHOTO_REQUEST_GALLERY = 2;// from gallery
	private static final int PHOTO_REQUEST_CUT = 3;// cut photo

	private ImageView mFace;
	private Bitmap bitmap;
	private TextView titile_name;
	/* photo name */
	private static final String PHOTO_FILE_NAME = "iMap/photo/temp_photo.jpg";
	private static final String HEADPORTRAIT_FILE_NAME = "iMap/photo/headportrait.jpg";
	private File tempFile;
	
	{
		File tmp = new File(Environment.getExternalStorageDirectory(),"iMap/photo");
		if(!tmp.exists()){
			tmp.mkdirs();
		}
	}
	
	Button titlebarrightbt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setheadportrait);
		this.mFace = (ImageView) this.findViewById(R.id.iv_image);
		setupView();
		setText();
		try {
			setImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setListener();
	}

	public void setupView() {
		titile_name = (TextView) this.findViewById(R.id.myTitle);
		titlebarrightbt = (Button) this.findViewById(R.id.titlebarrightbt);
	}

	public void setText() {
		titile_name.setText(R.string.changeheadportrait);
	}

	// 把新头像bitmap上传到服务器
	public void upload() {

	}

	public void setImage() throws Exception {
		titlebarrightbt.setBackgroundDrawable(null);
		
		File temp = new File(Environment.getExternalStorageDirectory(),
				HEADPORTRAIT_FILE_NAME);
		// 使用默认图片
		if (temp.exists() == false)
			return;
		System.out.println("in the activity SETHEAD, i can read the picture file!");
		FileInputStream input = new FileInputStream(temp);
		Bitmap imageBitmap = BitmapFactory.decodeStream(input);
		mFace.setImageBitmap(imageBitmap);
		input.close();
	}

	/**
	 * 点击确认按钮调用这个方法
	 * 
	 * @throws IOException
	 */
	public void confirm(View view) throws IOException {
		try{
			if (bitmap == null)
				return;
			upload();
			
			tempFile = new File(Environment.getExternalStorageDirectory(),
					HEADPORTRAIT_FILE_NAME);
			if (tempFile.exists() == true) {
				tempFile.delete();
			}
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileOutputStream out = new FileOutputStream(tempFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
//			Intent intent = new Intent(SetHeadPortraitActivity.this,
//					UserInfoActivity.class);
//			startActivity(intent);
		}finally{
			finish();
		}
		
	}

	/*
	 * 
	 * public void upload(View view) { try { ByteArrayOutputStream out = new
	 * ByteArrayOutputStream(); bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
	 * out); out.flush(); out.close(); byte[] buffer = out.toByteArray();
	 * 
	 * byte[] encode = Base64.encode(buffer, Base64.DEFAULT); String photo = new
	 * String(encode);
	 * 
	 * RequestParams params = new RequestParams(); params.put("photo", photo);
	 * String url = "http://110.65.99.66:8080/jerry/UploadImgServlet";
	 * 
	 * AsyncHttpClient client = new AsyncHttpClient(); client.post(url, params,
	 * new AsyncHttpResponseHandler() {
	 * 
	 * @Override public void onSuccess(int statusCode, Header[] headers, byte[]
	 * responseBody) { try { if (statusCode == 200) {
	 * 
	 * Toast.makeText(MainActivity.this, "ͷ���ϴ��ɹ�!", 0) .show(); } else {
	 * Toast.makeText(MainActivity.this, "��������쳣�������룺" + statusCode,
	 * 0).show();
	 * 
	 * } } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 * 
	 * @Override public void onFailure(int statusCode, Header[] headers, byte[]
	 * responseBody, Throwable error) { Toast.makeText(MainActivity.this,
	 * "��������쳣��������  > " + statusCode, 0).show();
	 * 
	 * } });
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
	/**
	 * 点击从图库查找会自动调用这个方法
	 */
	public void gallery(View view) {

		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/**
	 * 点击拍照获取图片按钮执行这个方法
	 */
	public void camera(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

		if (hasSdcard()) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}

	/**
	 * startActivityForResul会调用这个方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {

				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(SetHeadPortraitActivity.this,
						R.string.SDcardnoexist, 0).show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				// delete the tempfile
				bitmap = data.getParcelableExtra("data");
				this.mFace.setImageBitmap(bitmap);
//				boolean delete = tempFile.delete();
//				System.out.println("delete = " + delete);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片的方法
	 */
	private void crop(Uri uri) {
		//
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		//
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		//
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		//
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// ȡ������ʶ��
		intent.putExtra("return-data", true);// true:������uri��false������uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	/**
	 * does the SDcard exist
	 */
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void setListener() {
		findViewById(R.id.back_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == findViewById(R.id.back_button)) {
			Intent intent = new Intent(SetHeadPortraitActivity.this,
					UserInfoActivity.class);

			startActivity(intent);
			finish();
		}
	}
}
