/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Interfaces;


import com.chdryra.android.startouch.Social.Implementation.OAuthRequest;

/**
 * Created by: Rizwan Choudrey
 * On: 25/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface OAuthListener {
    void onAuthorisationCallback(OAuthRequest response);
}
