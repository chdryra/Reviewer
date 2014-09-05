package com.chdryra.android.reviewer;

public class Author {
	private UserId mId;
	private String mName;
	
	public Author(String name) {
		mId = UserId.generateID();
		mName = name;
	}
	
	public UserId getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
}
