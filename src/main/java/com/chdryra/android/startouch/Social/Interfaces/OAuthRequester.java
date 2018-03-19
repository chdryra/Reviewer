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
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface OAuthRequester<Token> {
    interface RequestListener<Token> {
        void onRequestGenerated(OAuthRequest request);

        void onResponseParsed(Token token);
    }

    void generateAuthorisationRequest(RequestListener<Token> listener);

    void parseRequestResponse(OAuthRequest response, RequestListener<Token> listener);
}
