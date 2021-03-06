/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.corelibrary.Widgets.ClearableEditText;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.R;
import com.chdryra.android.startouch.Social.Implementation.OAuthRequest;
import com.chdryra.android.startouch.Utils.ParcelablePacker;

/**
 * UI Fragment: web browser. Browser shows URL passed in the arguments,
 * or Google home if null. By clicking "DONE" icon, current URL is added to/edited into review.
 * <p/>
 * <p>
 * In addition:
 * <ul>
 * <li>Text entry for URL.</li>
 * <li>Search icon in ActionBar sends user to Google search.</li>
 * </ul>
 * </p>
 */
public class FragmentOAuthLogin extends Fragment {
    private final static String URL = TagKeyGenerator.getKey(FragmentOAuthLogin.class, "Url");

    private static final int LAYOUT = R.layout.fragment_review_url_browser;
    private static final int WEB_VIEW = R.id.web_view;
    private static final int URL_EDIT_TEXT = R.id.edit_text_url;

    private OAuthRequest mRequest;
    private ClearableEditText mUrlEditText;
    private WebView mWebView;
    private ParcelablePacker<OAuthRequest> mPacker;

    public static FragmentOAuthLogin newInstance(OAuthRequest request) {
        return FactoryFragment.newFragment(FragmentOAuthLogin.class, URL, request);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPacker = new ParcelablePacker<>();
        Bundle args = getArguments();
        if (args != null) mRequest = args.getParcelable(URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(LAYOUT, container, false);
        mWebView = (WebView) v.findViewById(WEB_VIEW);
        mUrlEditText = (ClearableEditText) v.findViewById(URL_EDIT_TEXT);
        mUrlEditText.setOnEditorActionListener(newLoadPageOnActionGoListener());

        initWebView();
        loadInitialPage();

        return v;
    }

    @NonNull
    private TextView.OnEditorActionListener newLoadPageOnActionGoListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) loadUrlInEditText();
                return true;
            }
        };
    }

    private void loadInitialPage() {
        String urlString = mRequest.getAuthorisationUrl();
        mUrlEditText.setText(urlString);
        loadUrlInEditText();
    }

    private void initWebView() {
        mWebView.setWebViewClient(new UrlWebViewClient());
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            //Overridden
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    return goBackIfPossibleOrShowAlert((WebView) v);
                }
                return false;
            }
        });
    }

    private boolean goBackIfPossibleOrShowAlert(WebView webView) {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            showAlertReturnToReview();
        }
        return true;
    }

    private void showAlertReturnToReview() {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Return")
                .setMessage("Return to " + Strings.REVIEW + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void loadUrlInEditText() {
        String urlString = URLUtil.guessUrl(mUrlEditText.getText().toString());
        mWebView.loadUrl(urlString);
    }

    private void captureCallbackAndReturn(String callback) {
        mRequest.setCallbackResult(callback);
        Intent data = new Intent();
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.NEW, mRequest, data);
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }

    private class UrlWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            String viewUrl = view.getUrl();
            super.onPageFinished(view, url);
            if (viewUrl != null && viewUrl.contains(mRequest.getCallbackUrl())) {
                captureCallbackAndReturn(viewUrl);
            }
        }
    }
}
