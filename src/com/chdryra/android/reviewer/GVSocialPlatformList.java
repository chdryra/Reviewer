/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.SocialPlatformList.SocialPlatform;

import java.util.Comparator;

/**
 * GVReviewDataList: GVReviewSocialPlatform
 * <p>
 * ViewHolder: VHSocialView
 * </p>
 * <p/>
 * <p>
 * Used for social sharing screen showing social platforms and number of followers.
 * </p>
 *
 * @see com.chdryra.android.reviewer.Administrator
 * @see com.chdryra.android.reviewer.SocialPlatformList
 * @see com.chdryra.android.reviewer.FragmentReviewShare
 * @see com.chdryra.android.reviewer.VHSocialView
 */
public class GVSocialPlatformList extends GVReviewDataList<GVSocialPlatformList.GVSocialPlatform> {

    private GVSocialPlatformList(Context context) {
        super(GVType.SOCIAL);
        for (SocialPlatform platform : SocialPlatformList.get(context)) {
            add(new GVSocialPlatform(platform.getName(), platform.getFollowers()));
        }
    }

    static GVSocialPlatformList getLatest(Context context) {
        SocialPlatformList.update(context);
        return new GVSocialPlatformList(context);
    }

    @Override
    protected Comparator<GVSocialPlatform> getDefaultComparator() {
        return new Comparator<GVSocialPlatform>() {

            @Override
            public int compare(GVSocialPlatform lhs, GVSocialPlatform rhs) {
                int ret = 0;
                if (lhs.getFollowers() > rhs.getFollowers()) {
                    ret = 1;
                }
                if (lhs.getFollowers() < rhs.getFollowers()) {
                    ret = -1;
                }

                return ret;
            }
        };
    }

    /**
     * GVData version of: no equivalent as used for review sharing screen.
     * ViewHolder: VHSocialView
     * <p/>
     * <p>
     * Methods for getting the name, number followers, and selection status.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.SocialPlatformList
     */
    class GVSocialPlatform extends GVDualString {
        private int     mFollowers = 0;
        private boolean mIsChosen  = false;

        GVSocialPlatform(String name, int followers) {
            super(name, String.valueOf(followers));
            mFollowers = followers;
        }

        String getName() {
            return getUpper();
        }

        int getFollowers() {
            return mFollowers;
        }

        boolean isChosen() {
            return mIsChosen;
        }

        void press() {
            mIsChosen = !mIsChosen;
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHSocialView();
        }
    }
}
