/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public interface SocialPlatform<T> extends DataSocialPlatform, OAuthorisable<T>{
    SocialPublisher getPublisher();

    OAuthRequester<T> getOAuthRequester();

    SocialPlatformAuthUi getAuthUi(Activity activity,
                                   LaunchableUi authorisationUi,
                                   LaunchableUiLauncher launcher,
                                   AuthorisationListener listener);

    void publish(Review review, TagsManager tagsManager, SocialPublisherListener listener);

    @Override
    boolean isAuthorised();

    @Override
    void setAccessToken(@Nullable T t);

    @Override
    String getName();

    @Override
    void getFollowers(FollowersListener listener);
}
