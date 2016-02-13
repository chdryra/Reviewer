/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Singleton that holds the list of social platforms on which reviews can be shared. Placeholders
 * for finding latest number of followers on each platform.
 */
public class SocialPlatformList implements Iterable<SocialPlatform>{
    private final LinkedList<SocialPlatform> mPlatforms;

    public SocialPlatformList() {
        mPlatforms = new LinkedList<>();
    }

    public void add(Context context, SocialPublisher publisher) {
        mPlatforms.add(new SocialPlatformImpl(context, publisher));
    }

    public int size() {
        return mPlatforms.size();
    }

    @Override
    public Iterator<SocialPlatform> iterator() {
        return mPlatforms.iterator();
    }
}
