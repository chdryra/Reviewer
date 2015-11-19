/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.FragmentDeleteDone;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Launcher.Implementation.LauncherUiImpl;

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
    private static final String TAG = "FragmentEditUrlBrowser";

    private GvUrlList.GvUrl mCurrent;
    private ClearableEditText mUrlEditText;
    private WebView mWebView;
    private String mSearchUrl;

    private GvUrlList.GvUrl createGVData() {
        String urlString = mWebView.getUrl();
        GvUrlList.GvUrl url = null;
        try {
            url = new GvUrlList.GvUrl(mWebView.getTitle(), new URL(urlString));
        } catch (MalformedURLException e1) {
            Log.i(TAG, "MalformedURLException: " + urlString, e1);
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_bad_url),
                    Toast.LENGTH_SHORT).show();
        }

        return url;
    }

    private void loadUrl() {
        String urlString = URLUtil.guessUrl(mUrlEditText.getText().toString());
        mWebView.loadUrl(urlString);
    }

    //Overridden
    @Override
    protected boolean hasDataToDelete() {
        return mCurrent != null;
    }

    @Override
    protected void onDeleteSelected() {
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, mCurrent,
                getNewReturnData());
    }

    @Override
    protected void onDoneSelected() {
        Intent i = getNewReturnData();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, mCurrent, i);
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.NEW, createGVData(), i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = LauncherUiImpl.getArgsForActivity(getActivity());
        if (args != null) {
            mCurrent = (GvUrlList.GvUrl) GvDataPacker.unpackItem(GvDataPacker
                    .CurrentNewDatum.
                    CURRENT, args);
        }
        setDeleteWhatTitle(GvUrlList.GvUrl.TYPE.getDatumName());
        dismissOnDelete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDisplayHomeAsUp(true);

        View v = inflater.inflate(R.layout.fragment_review_url_browser, container, false);
        mWebView = (WebView) v.findViewById(R.id.web_view);
        mUrlEditText = (ClearableEditText) v.findViewById(R.id.edit_text_url);

        mWebView.setWebViewClient(new URLWebViewClient());
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            //Overridden
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    WebView webView = (WebView) v;
                    if (webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Return")
                                .setMessage("Return to review?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    //Overridden
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onUpSelected();
                                    }

                                })
                                .setNegativeButton("No", null)
                                .show();
                        return true;
                    }
                }
                return false;
            }
        });


        mSearchUrl = getResources().getString(R.string.google_search);
        String urlString = mCurrent != null ? mCurrent.getUrl().toExternalForm() : mSearchUrl;

        mUrlEditText.setText(urlString);
        mWebView.loadUrl(urlString);

        mUrlEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //Overridden
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    loadUrl();
                }

                return false;
            }
        });


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_delete_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_item_search) {
            mUrlEditText.setText(mSearchUrl);
            loadUrl();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private class URLWebViewClient extends WebViewClient {
        //Overridden
        @Override
        public void onPageFinished(WebView view, String url) {
            mUrlEditText.setText(view.getUrl());
            super.onPageFinished(view, url);
        }
    }
}
