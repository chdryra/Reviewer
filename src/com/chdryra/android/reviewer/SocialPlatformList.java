package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedList;

public class SocialPlatformList implements Iterable<SocialPlatformList.SocialPlatform>{
	public enum Platform {
		TWITTER("twitter"),
		FACEBOOK("facebook"),
		TUMBLR("tumblr"),
		FOURSQUARE("foursquare"),
		WHATSAPP("whatsapp"),
		EMAIL("email");
		
		private String mPlatform;
		
		Platform(String platform) {
			mPlatform = platform;
		}
		
		public String toString() {
			return mPlatform;
		}		
	}

	private static SocialPlatformList sList;
	private LinkedList<SocialPlatform> mPlatforms;
	
	private SocialPlatformList() {
		mPlatforms = new LinkedList<SocialPlatformList.SocialPlatform>();
		Platform[] platforms = Platform.values();
		for(Platform platform : platforms)
			mPlatforms.add(new SocialPlatform(platform.toString()));
	}

	public static SocialPlatformList get(boolean latest) {
		if(sList == null)
			sList = new SocialPlatformList();

		if(latest)
			sList.update();
		
		return sList;
	}
	
	private void update() {
		for(SocialPlatform platform : this)
			platform.update();
	}
	
	class SocialPlatform {
		private String mName;
		private int mFollowers;
		
		public SocialPlatform(String name) {
			mName = name;
			update();
		}
		
		public String getName() {
			return mName;
		}
		
		public int getFollowers() {
			return mFollowers;
		}
		
		public void update() {
			mFollowers = 0;
		}
	}
	
	@Override
	public Iterator<SocialPlatform> iterator() {
		return mPlatforms.iterator();
	}
}
