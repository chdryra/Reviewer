package com.chdryra.android.reviewer;

public class Author {
	private UserId mId;
	
	public Author() {
		mId = UserId.generateID();
	}
	
	public UserId getId() {
		return mId;
	}
}
