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
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuth20Service;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class OAuthRequester20<T> implements OAuthRequester<T>{
    private static final String OAUTH_VERIFIER = "oauth_verifier=";

    private String mPlatformName;
    private String mCallBack;
    private OAuth20Service mService;

    protected abstract T newAccessToken(Token token);

    public OAuthRequester20(String consumerKey, String consumerSecret, String callBack,
                            DefaultApi20 api, String platformName) {
        mCallBack= callBack;
        mService = new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .callback(mCallBack)
                .build(api);

        mPlatformName = platformName;
    }

    @Override
    public void generateAuthorisationRequest(RequestListener<T> listener) {
        new OAuthRequestTask(listener).execute();
    }

    @Override
    public void parseRequestResponse(OAuthRequest response, RequestListener<T> listener) {
        String callback = response.getCallbackResult();
        String verifier = StringUtils.substringAfter(callback, OAUTH_VERIFIER);
        new OAuthResponseTask(new Verifier(verifier), listener).execute();
    }

    private class OAuthRequestTask extends AsyncTask<Void, Void, OAuthRequest> {
        private RequestListener<T> mListener;

        public OAuthRequestTask(RequestListener<T> listener ) {
            mListener = listener;
        }

        @Override
        protected OAuthRequest doInBackground(Void... params) {
            String authorisationUrl = mService.getAuthorizationUrl();

            return new OAuthRequest(mPlatformName, authorisationUrl, mCallBack);
        }

        @Override
        protected void onPostExecute(OAuthRequest request) {
            mListener.onRequestGenerated(request);
        }
    }

    private class OAuthResponseTask extends AsyncTask<Void, Void, T> {
        private Verifier mVerifier;
        private RequestListener<T> mListener;

        public OAuthResponseTask (Verifier verifier, RequestListener<T> listener ) {
            mVerifier = verifier;
            mListener = listener;
        }

        @Override
        protected T doInBackground(Void... params) {
            Token accessToken = mService.getAccessToken(mVerifier);
            return newAccessToken(accessToken);
        }

        @Override
        protected void onPostExecute(T token) {
            mListener.onResponseParsed(token);
        }
    }
}
