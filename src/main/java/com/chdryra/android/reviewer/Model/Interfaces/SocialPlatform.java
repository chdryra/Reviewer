package com.chdryra.android.reviewer.Model.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSocialPlatform;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public interface SocialPlatform extends DataSocialPlatform {
    void update();

    @Override
    String getName();

    @Override
    int getFollowers();
}
