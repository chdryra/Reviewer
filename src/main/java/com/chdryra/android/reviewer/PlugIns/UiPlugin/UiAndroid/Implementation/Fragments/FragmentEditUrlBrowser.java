/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.Widgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.Activities.FragmentDeleteDone;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.R;

import java.net.MalformedURLException;
import java.net.URL;

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
public class FragmentEditUrlBrowser extends FragmentDeleteDone {
    private final static String URL = "com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentEditUrlBrowser.url";
    private static final String TAG = "FragmentEditUrlBrowser";

    private static final int LAYOUT = R.layout.fragment_review_url_browser;
    private static final int WEB_VIEW = R.id.web_view;
    private static final int URL_EDIT_TEXT = R.id.edit_text_url;

    private static final int MENU = R.menu.menu_search_delete_done;
    private static final int MENU_ITEM_SEARCH = R.id.menu_item_search;

    private static final int BAD_URL = R.string.toast_bad_url;
    private static final int SEARCH_URL = R.string.google_search;

    private GvUrl mCurrent;
    private ClearableEditText mUrlEditText;
    private WebView mWebView;
    private String mSearchUrl;
    private ParcelablePacker<GvUrl> mPacker;

    public static FragmentEditUrlBrowser newInstance(GvUrl url) {
        return FactoryFragment.newFragment(FragmentEditUrlBrowser.class, URL, url);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPacker = new ParcelablePacker<>();
        Bundle args = getArguments();
        if (args != null) mCurrent = args.getParcelable(URL);
        setDeleteWhatTitle(GvUrl.TYPE.getDatumName());
        dismissOnDelete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDisplayHomeAsUp(true);

        View v = inflater.inflate(LAYOUT, container, false);
        mWebView = (WebView) v.findViewById(WEB_VIEW);
        mUrlEditText = (ClearableEditText) v.findViewById(URL_EDIT_TEXT);
        mUrlEditText.setOnEditorActionListener(newLoadPageOnActionGoListener());
        mSearchUrl = getResources().getString(SEARCH_URL);

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
        String urlString = mCurrent != null && mCurrent.isValidForDisplay() ?
                mCurrent.getUrl().toExternalForm() : mSearchUrl;
        mUrlEditText.setText(urlString);
        loadUrlInEditText();
    }

    private void initWebView() {
        mWebView.setWebViewClient(new URLWebViewClient());
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
                .setMessage("Return to review?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onUpSelected();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(MENU, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == MENU_ITEM_SEARCH) {
            mUrlEditText.setText(mSearchUrl);
            loadUrlInEditText();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected boolean hasDataToDelete() {
        return mCurrent != null;
    }

    @Override
    protected void onDeleteSelected() {
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, mCurrent,
                getNewReturnData());
    }

    @Override
    protected void onDoneSelected() {
        Intent i = getNewReturnData();
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, mCurrent, i);
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.NEW, createGvDataFromBrowser(), i);
    }

    private GvUrl createGvDataFromBrowser() {
        String urlString = mWebView.getUrl();
        GvUrl url = null;
        try {
            url = new GvUrl(mWebView.getTitle(), new URL(urlString));
        } catch (MalformedURLException e1) {
            Log.i(TAG, "MalformedURLException: " + urlString, e1);
            makeToast(BAD_URL);
        }

        return url;
    }

    private void makeToast(int messageId) {
        Toast.makeText(getActivity(), getResources().getString(messageId), Toast.LENGTH_SHORT).show();
    }

    private void loadUrlInEditText() {
        String urlString = URLUtil.guessUrl(mUrlEditText.getText().toString());
        mWebView.loadUrl(urlString);
    }

    private class URLWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            mUrlEditText.setText(view.getUrl());
            super.onPageFinished(view, url);
        }
    }
}
