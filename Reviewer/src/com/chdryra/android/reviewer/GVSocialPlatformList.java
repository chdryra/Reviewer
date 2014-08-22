package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.SocialPlatformList.SocialPlatform;

public class GVSocialPlatformList extends GVReviewDataList<GVSocialPlatformList.GVSocialPlatform> {
	private static GVSocialPlatformList sGVList;
	
	private GVSocialPlatformList(boolean latest) {
		super(GVType.SOCIAL);
		for(SocialPlatform platform : SocialPlatformList.get(latest))
			add(new GVSocialPlatform(platform.getName(), platform.getFollowers()));
	}

	public static GVSocialPlatformList getCurrent() {
		if(sGVList == null)
			sGVList = new GVSocialPlatformList(false);
		
		return sGVList;
	}
	
	public static GVSocialPlatformList getLatest() {
		sGVList = new GVSocialPlatformList(true);
		return sGVList;
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
