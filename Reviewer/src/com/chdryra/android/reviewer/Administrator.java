package com.chdryra.android.reviewer;

public class Administrator {

	private static Administrator sAdministrator;
	private static final Author sAnonymousAuthor = new Author();
	private Author mCurrentAuthor;
	
	private Administrator() {
		mCurrentAuthor = sAnonymousAuthor;
	}
	
	public static Administrator getInstance() {
		if(sAdministrator == null)
			sAdministrator = new Administrator();
		
		return sAdministrator;
	}
	
	public void setCurrentAuthor(Author author) {
		getInstance().mCurrentAuthor = author;
	}
	
	public static Author getCurrentAuthor() {
		return getInstance().mCurrentAuthor;
	}
	
	public static Author getAnonymousAuthor() {
		return sAnonymousAuthor;
	}
	
	public static GVSocialPlatformList getSocialPlatformList(boolean updated) {
		if(updated)
			return GVSocialPlatformList.getCurrent();
		else
			return GVSocialPlatformList.getLatest();
	}
}
