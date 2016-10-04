/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;

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

    SocialSuite getSocial();

    void logout();

    void setReturnResult(ActivityResultCode result);
}

