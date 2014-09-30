/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.SocialPlatformList.SocialPlatform;

public class GVSocialPlatformList extends GVReviewDataList<GVSocialPlatformList.GVSocialPlatform> {

	private GVSocialPlatformList(Context context) {
		super(GVType.SOCIAL);
		for(SocialPlatform platform : SocialPlatformList.get(context))
			add(new GVSocialPlatform(platform.getName(), platform.getFollowers()));
	}

	public static GVSocialPlatformList getLatest(Context context) {
        SocialPlatformList.get(context).update();
		return new GVSocialPlatformList(context);
	}
	
	@Override
	protected Comparator<GVSocialPlatform> getDefaultComparator() {
		return new Comparator<GVSocialPlatform>() {

			@Override
			public int compare(GVSocialPlatform lhs, GVSocialPlatform rhs) {
				int ret = 0;
				if(lhs.getFollowers() > rhs.getFollowers())
					ret = 1;
				if(lhs.getFollowers() < rhs.getFollowers())
					ret = -1;
				
				return ret;
			}
		};
	}
	
	class GVSocialPlatform extends GVDualString{
		private int mFollowers = 0;
		private boolean mIsChosen = false;
		
		public GVSocialPlatform(String name, int followers) {
			super(name, String.valueOf(followers));
			mFollowers = followers;
		}
		
		public String getName() {
			return getUpper();
		}
		
		public int getFollowers() {
			return mFollowers;
		}
		
		public boolean isChosen() {
			return mIsChosen;
		}
		
		public void press() {
			mIsChosen = !mIsChosen;
		}
		
		@Override
		public ViewHolder getViewHolder() {
			return new VHSocialView();
		}
	}	
}
