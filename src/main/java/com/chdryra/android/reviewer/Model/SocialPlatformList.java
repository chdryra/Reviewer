/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model;

import android.content.Context;

import com.chdryra.android.reviewer.R;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Singleton that holds the list of social platforms on which reviews can be shared. Placeholders
 * for finding latest number of followers on each platform.
 */
public class SocialPlatformList implements Iterable<SocialPlatformList.SocialPlatform> {
    private static SocialPlatformList         sList;
    private final  LinkedList<SocialPlatform> mPlatforms;

    /**
     * Enum for specifying the social platforms available together with their text labels.
     */
    public enum Platform {
        //Cannot access string resources without a context
        TWITTER(R.string.twitter),
        FACEBOOK(R.string.facebook),
        TUMBLR(R.string.tumblr),
        FOURSQUARE(R.string.foursquare),
        WHATSAPP(R.string.whatsapp),
        EMAIL(R.string.email);

        private final int mPlatformId;

        private Platform(int platformId) {
            mPlatformId = platformId;
        }

        private String toString(Context context) {
            return context.getResources().getString(mPlatformId);
        }
    }

    private SocialPlatformList(Context context) {
        mPlatforms = new LinkedList<>();
        Platform[] platforms = Platform.values();
        for (Platform platform : platforms) {
            mPlatforms.add(new SocialPlatform(platform.toString(context)));
        }
    }

    public static SocialPlatformList getList(Context context) {
        if (sList == null) {
            sList = new SocialPlatformList(context);
        }

        return sList;
    }

    public static void update(Context context) {
        for (SocialPlatform platform : getList(context)) {
            platform.update();
        }
    }

    public int size() {
        return mPlatforms.size();
    }

    @Override
    public Iterator<SocialPlatform> iterator() {
        return mPlatforms.iterator();
    }

    /**
     * Holds the name and number of followers for a social platform. Placeholder to update the
     * number of followers.
     */
    public class SocialPlatform {
        private final String mName;
        private       int    mFollowers;

        private SocialPlatform(String name) {
            mName = name;
            update();
        }

        public String getName() {
            return mName;
        }

        public int getFollowers() {
            return mFollowers;
        }

        private void update() {
            mFollowers = 0;
        }
    }
}
