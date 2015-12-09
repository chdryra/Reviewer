/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.Social;

import android.content.Context;

import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatform;
import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatformList;
import com.chdryra.android.reviewer.R;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Singleton that holds the list of social platforms on which reviews can be shared. Placeholders
 * for finding latest number of followers on each platform.
 */
public class SocialPlatformListImpl implements SocialPlatformList {
    private final LinkedList<SocialPlatform> mPlatforms;

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

        Platform(int platformId) {
            mPlatformId = platformId;
        }

        private String toString(Context context) {
            return context.getResources().getString(mPlatformId);
        }
    }

    public SocialPlatformListImpl(Context context) {
        mPlatforms = new LinkedList<>();
        for (Platform platform : Platform.values()) {
            mPlatforms.add(new SocialPlatformImpl(platform.toString(context)));
        }
    }

    @Override
    public void update() {
        for (SocialPlatform platform : this) {
            platform.update();
        }
    }

    @Override
    public int size() {
        return mPlatforms.size();
    }

    //Overridden
    @Override
    public Iterator<SocialPlatform> iterator() {
        return mPlatforms.iterator();
    }
}
