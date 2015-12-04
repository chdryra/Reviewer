package com.chdryra.android.reviewer.Model.Implementation.Social;

import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatform;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public class SocialPlatformImpl implements SocialPlatform {
    private final String mName;
    private int mFollowers;

    public SocialPlatformImpl(String name) {
        mName = name;
        update();
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public int getFollowers() {
        return mFollowers;
    }

    @Override
    public void update() {
        mFollowers = 0;
    }
}
