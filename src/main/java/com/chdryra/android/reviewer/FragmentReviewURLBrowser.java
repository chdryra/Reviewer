/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

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

import java.net.MalformedURLException;
import java.net.URISyntaxException;

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
public class FragmentReviewURLBrowser extends FragmentDeleteDone {
    private static final String TAG = "FragmentReviewUrlBrowser";

    private GvUrlList.GvUrl   mCurrent;
    private ClearableEditText mUrlEditText;
    private WebView           mWebView;
    private String            mSearchUrl;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrent = (GvUrlList.GvUrl) GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum
                        .CURRENT,
                LauncherUI.getArgsForActivity(getActivity()));
        setDeleteWhatTitle(GvDataList.GvType.URLS.getDatumString());
        dismissOnDelete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setDisplayHomeAsUp(true);

        View v = inflater.inflate(R.layout.fragment_review_url_browse, container, false);
        mWebView = (WebView) v.findViewById(R.id.web_view);
        mUrlEditText = (ClearableEditText) v.findViewById(R.id.edit_text_url);

        mWebView.setWebViewClient(new URLWebViewClient());
        mWebView.setOnKeyListener(new View.OnKeyListener() {
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

    private GvUrlList.GvUrl createGVData() {
        String urlString = mWebView.getUrl();
        GvUrlList.GvUrl url = null;
        try {
            url = new GvUrlList.GvUrl(urlString);
        } catch (MalformedURLException e1) {
            Log.i(TAG, "MalformedURLException: " + urlString, e1);
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_bad_url),
                    Toast.LENGTH_SHORT).show();
        } catch (URISyntaxException e2) {
            Log.i(TAG, "URLSyntaxException: " + urlString, e2);
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_bad_url),
                    Toast.LENGTH_SHORT).show();
        }

        return url;
    }

    private void loadUrl() {
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
