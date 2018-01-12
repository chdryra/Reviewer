/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public interface SocialPlatform<T> extends DataSocialPlatform, Authorisable<T> {
    SocialPublisherAsync getPublisher();

    OAuthRequester<T> getOAuthRequester();

    LoginUi getLoginUi(LaunchableUi loginLaunchable, PlatformAuthoriser.Callback listener);

    void logout();

    void publish(Review review, SocialPublisherListener listener);

    @Override
    boolean isAuthorised();

    @Override
    void setAuthorisation(@Nullable T t);

    @Override
    void setAuthListener(AuthorisationListener listener);

    @Override
    String getName();

    @Override
    void getFollowers(FollowersListener listener);
}
