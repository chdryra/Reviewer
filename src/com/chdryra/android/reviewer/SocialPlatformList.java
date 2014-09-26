/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;

import java.util.Iterator;
import java.util.LinkedList;

public class SocialPlatformList implements Iterable<SocialPlatformList.SocialPlatform>{
	public enum Platform {
		//Cannot access string resources without a context
        TWITTER(R.string.twitter),
		FACEBOOK(R.string.facebook),
		TUMBLR(R.string.tumblr),
		FOURSQUARE(R.string.foursquare),
		WHATSAPP(R.string.whatsapp),
		EMAIL(R.string.email);
		
		private final int mPlatformId;
		
		Platform(int platformId) {
			mPlatformId = platformId;
		}
		
		public String toString(Context context) {
			return context.getResources().getString(mPlatformId);
		}		
	}

	private static SocialPlatformList sList;
	private final LinkedList<SocialPlatform> mPlatforms;
	
	private SocialPlatformList(Context context) {
		mPlatforms = new LinkedList<SocialPlatformList.SocialPlatform>();
		Platform[] platforms = Platform.values();
		for(Platform platform : platforms)
			mPlatforms.add(new SocialPlatform(platform.toString(context)));
	}

	public static SocialPlatformList get(Context context, boolean latest) {
		if(sList == null)
			sList = new SocialPlatformList(context);

		if(latest)
			sList.update();
		
		return sList;
	}
	
	private void update() {
		for(SocialPlatform platform : this)
			platform.update();
	}
	
	class SocialPlatform {
		private final String mName;
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
