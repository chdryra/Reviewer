/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.OAuthRequest;

/**
 * UI Fragment: web browser. Browser shows URL passed in the arguments,
 * or Google home if null. By clicking "Done" icon, current URL is added to/edited into review.
 * <p/>
 * <p>
 * In addition:
 * <ul>
 * <li>Text entry for URL.</li>
 * <li>Search icon in ActionBar sends user to Google search.</li>
 * </ul>
 * </p>
 */
public class FragmentOAuthUrlBrowser extends Fragment {
    private static final String REQUEST = "com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentViewUrlBrowser.url";

    private static final int LAYOUT = R.layout.fragment_review_url_browser;
    private static final int WEB_VIEW = R.id.web_view;

    private OAuthRequest mRequest;

    public static FragmentOAuthUrlBrowser newInstance(OAuthRequest request) {
        return FactoryFragment.newFragment(FragmentOAuthUrlBrowser.class, REQUEST, request);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) mRequest = args.getParcelable(REQUEST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(LAYOUT, container, false);

        WebView webView = (WebView) v.findViewById(WEB_VIEW);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new OAuthWebViewClient());
        webView.loadUrl(mRequest.getAuthorisationUrl());

        return v;
    }

    private class OAuthWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            mRequest.setCallbackResult(view.getUrl());
            super.onPageFinished(view, url);
        }
    }
}
