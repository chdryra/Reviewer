/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatform;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Singleton that holds the list of social platforms on which reviews can be shared. Placeholders
 * for finding latest number of followers on each platform.
 */
public class SocialPlatformList implements Iterable<SocialPlatform<?>>{
    private final LinkedList<SocialPlatform<?>> mPlatforms;

    public SocialPlatformList() {
        mPlatforms = new LinkedList<>();
    }

    public void add(SocialPlatform<?> platform) {
        mPlatforms.add(platform);
    }

    public int size() {
        return mPlatforms.size();
    }

    public SocialPlatform<?> getPlatform(String name) {
        SocialPlatform<?> platform = null;
        for(SocialPlatform<?> p : mPlatforms) {
            if(p.getName().equals(name)) {
                platform = p;
                break;
            }
        }

        if(platform == null) throw new IllegalArgumentException("Platform " + name + " not found!");

        return platform;
    }

    @Override
    public Iterator<SocialPlatform<?>> iterator() {
        return mPlatforms.iterator();
    }

    public void logout() {
        for(SocialPlatform<?> platform : mPlatforms) {
            platform.logout();
        }
    }
}
