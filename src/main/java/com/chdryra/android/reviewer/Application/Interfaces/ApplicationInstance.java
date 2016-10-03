/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationInstance {
    String APP_NAME = "Teeqr";

    AuthenticationSuite getAuthentication();

    LocationServicesSuite getLocationServices();

    UiSuite getUi();

    RepositorySuite getRepository();

    ReviewBuilderSuite getReviewBuilder();

    void logout();

    //Social suite
    SocialPlatformList getSocialPlatformList();

    SocialProfile getSocialProfile();

    //UtilSuite
    @Nullable
    Review unpackReview(Bundle args);

    void setReturnResult(ActivityResultCode result);

    TagsManager getTagsManager();

    ReviewPublisher getPublisher();
}

