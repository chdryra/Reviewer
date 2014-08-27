package com.chdryra.android.reviewer;

import android.content.Context;

public class Administrator {

	private static Administrator sAdministrator;
	private Context mContext;
	
	private static Author sAnonymousAuthor = new Author();
	private Author mCurrentAuthor;
	
	private Administrator(Context c) {
		mCurrentAuthor = sAnonymousAuthor;
		mContext = c.getApplicationContext();
	}
	
	public static Administrator get(Context c) {
		if(sAdministrator == null)
			sAdministrator = new Administrator(c);
		
		return sAdministrator;
	}
	
	public void setCurrentAuthor(Author author) {
		mCurrentAuthor = author;
	}
	
	public Author getCurrentAuthor() {
		return mCurrentAuthor;
	}
	
	public static GVSocialPlatformList getSocialPlatformList(boolean latest) {
		if(latest)
			return GVSocialPlatformList.getLatest();
		else
			return GVSocialPlatformList.getCurrent();
	}
}
