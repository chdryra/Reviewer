/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.os.AsyncTask;

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

    public AuthorisationRequesterDefault(String consumerKey, String consumerSecret, String callBack,
                                         DefaultApi10a api, String platformName) {
        mCallBack= callBack;
        mService = new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .callback(mCallBack)
                .build(api);

        mPlatformName = platformName;
    }

    @Override
    public void generateAuthorisationRequest(RequestListener<AccessTokenDefault> listener) {
        new OAuthRequestTask(listener).execute();
    }

    @Override
    public void parseRequestResponse(OAuthRequest response, RequestListener<AccessTokenDefault> listener) {
        String callback = response.getCallbackResult();

        String result = StringUtils.remove(callback, mCallBack + "?");
        StringTokenizer tokenizer = new StringTokenizer(result, TOKEN_DELIMETER);

        tokenizer.nextToken();
        String token = tokenizer.nextToken();

        String verifierString = StringUtils.remove(token, "oauth_verifier=");

        Verifier verifier = new Verifier(verifierString);
        new OAuthResponseTask(verifier, listener).execute();
    }

    private class OAuthRequestTask extends AsyncTask<Void, Void, OAuthRequest> {
        private RequestListener<AccessTokenDefault> mListener;

        public OAuthRequestTask(RequestListener<AccessTokenDefault> listener ) {
            mListener = listener;
        }

        @Override
        protected OAuthRequest doInBackground(Void... params) {
            mCurrentRequest = mService.getRequestToken();
            String authorisationUrl = mService.getAuthorizationUrl(mCurrentRequest);

            return new OAuthRequest(mPlatformName, authorisationUrl, mCallBack);
        }

        @Override
        protected void onPostExecute(OAuthRequest request) {
            mListener.onRequestGenerated(request);
        }
    }

    private class OAuthResponseTask extends AsyncTask<Void, Void, AccessTokenDefault> {
        private Verifier mVerifier;
        private RequestListener<AccessTokenDefault> mListener;

        public OAuthResponseTask (Verifier verifier, RequestListener<AccessTokenDefault> listener ) {
            mVerifier = verifier;
            mListener = listener;
        }

        @Override
        protected AccessTokenDefault doInBackground(Void... params) {
            Token accessToken = mService.getAccessToken(mCurrentRequest, mVerifier);
            return new AccessTokenDefault(accessToken.getToken(), accessToken.getSecret());
        }

        @Override
        protected void onPostExecute(AccessTokenDefault token) {
            mListener.onResponseParsed(token);
        }
    }
}
