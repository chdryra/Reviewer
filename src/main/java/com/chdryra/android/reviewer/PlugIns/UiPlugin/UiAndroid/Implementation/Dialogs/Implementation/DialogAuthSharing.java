/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation;



import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chdryra.android.mygenerallibrary.DialogOneButtonFragment;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.LayoutHolder;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAuthSharing extends DialogOneButtonFragment implements LaunchableUi {
    private static final String TAG = "AuthoriseSocialPlatform";
    private static final int LAYOUT = R.layout.dialog_webview;
    private static final int WEB = R.id.web_view_url;

    private LayoutHolder mHolder;
    private OAuthRequest mRequest;
    private AuthorisationListener mListener;

    public interface AuthorisationListener {
        void onAuthorisationCallback(OAuthRequest response);
    }

    public static DialogAuthSharing newDialog(OAuthRequest request) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, request);
        DialogAuthSharing dialog = new DialogAuthSharing();
        dialog.setArguments(args);

        return dialog;
    }

    public DialogAuthSharing() {
        mHolder = new LayoutHolder(LAYOUT, WEB);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUi() {
        mListener = getTargetListener(AuthorisationListener.class);
        mHolder.inflate(getActivity());
        loadRequest();
        return mHolder.getView();
    }

    private void loadRequest() {
        WebView webView = getWebView();
        webView.setWebViewClient(new UrlWebViewClient());
        webView.loadUrl(mRequest.getAuthorisationUrl());
    }

    private WebView getWebView() {
        return (WebView) mHolder.getView(WEB);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unpackRequest();
        setLeftButtonAction(ActionType.CANCEL);
        dismissDialogOnLeftClick();
        setDialogTitle("Loading " + mRequest.getName() + " authorisation");
    }

    private void unpackRequest() {
        Bundle args = getArguments();
        if(args == null) {
            throw new IllegalArgumentException("Must pass OAuthRequest! Use static constructor!");
        }

        mRequest = args.getParcelable(TAG);
    }

    private class UrlWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            setDialogTitle(mRequest.getName() + " authorisation");
            String viewUrl = view.getUrl();
            super.onPageFinished(view, url);
            if(viewUrl.contains(mRequest.getCallbackUrl())) {
                captureCallbackAndReturn(viewUrl);
            }
        }
    }

    private void captureCallbackAndReturn(String callback) {
        mRequest.setCallbackResult(callback);
        mListener.onAuthorisationCallback(mRequest);
        dismiss();
    }
}
