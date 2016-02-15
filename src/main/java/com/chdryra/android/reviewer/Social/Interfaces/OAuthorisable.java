/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface OAuthorisable<Token> extends OAuthRequester<Token>{
    boolean isAuthorised();

    void setAccessToken(Token token);

    @Override
    OAuthRequest generateAuthorisationRequest();

    @Override
    Token parseRequestResponse(OAuthRequest returned);
}
