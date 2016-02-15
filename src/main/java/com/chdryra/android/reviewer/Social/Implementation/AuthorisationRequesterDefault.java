/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.apache.commons.lang3.StringUtils;

import java.util.StringTokenizer;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorisationRequesterDefault implements OAuthRequester<AccessTokenDefault>{
    private static final String TOKEN_DELIMETER = "&";

    private String mPlatformName;
    private String mCallBack;
    private OAuth10aService mService;
    private Token mCurrentRequest;
    private boolean mLocked;

    public AuthorisationRequesterDefault(String consumerKey, String consumerSecret, String callBack,
                                         DefaultApi10a api, String platformName) {
        mCallBack= callBack;
        mService = new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .callback(mCallBack)
                .build(api);

        mPlatformName = platformName;
        mLocked = false;
    }

    @Override
    public OAuthRequest generateAuthorisationRequest() {
        if(mLocked) throw new RuntimeException("Authorisation request already in progress!");
        mLocked = true;
        mCurrentRequest = mService.getRequestToken();
        String authorisationUrl = mService.getAuthorizationUrl(mCurrentRequest);

        return new OAuthRequest(mPlatformName, authorisationUrl, mCallBack);
    }

    @Override
    public AccessTokenDefault parseRequestResponse(OAuthRequest returned) {
        if(!mLocked) throw new RuntimeException("Authorisation request not in progress!");

        String callback = returned.getCallbackResult();

        String result = StringUtils.remove(callback, mCallBack + "?");
        StringTokenizer tokenizer = new StringTokenizer(result, TOKEN_DELIMETER);

        tokenizer.nextToken();
        String token = tokenizer.nextToken();

        String verifierString = StringUtils.remove(token, "oauth_verifier=");
        Token accessToken = mService.getAccessToken(mCurrentRequest, new Verifier(verifierString));

        mLocked = false;

        return new AccessTokenDefault(accessToken.getToken(), accessToken.getSecret());
    }
}
