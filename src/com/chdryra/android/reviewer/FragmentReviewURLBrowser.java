package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class FragmentReviewURLBrowser extends FragmentDeleteDone {
	public static final String URL = "com.chdryra.android.reviewer.url";
	public static final String URL_OLD = "com.chdryra.android.reviewer.url_old";
	
	private URL mURL;
	private ClearableEditText mURLEditText;
	private WebView mWebView;
	private String mSearchURL;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mURL =(URL) getActivity().getIntent().getSerializableExtra(URL);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_url_title));
		setDismissOnDelete(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_url_browse, container, false);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

		mWebView = (WebView)v.findViewById(R.id.web_view);
	    mURLEditText= (ClearableEditText)v.findViewById(R.id.edit_text_url);
	    
	    mWebView.setWebViewClient(new URLWebViewClient());
	    mWebView.setOnKeyListener(new View.OnKeyListener()
	    {
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event)
	        {
	            if(event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK)
	            {
	                WebView webView = (WebView) v;
	                if(webView.canGoBack())
                    {
                        webView.goBack();
                        return true;
                    } else {
                    	new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Return")
                        .setMessage("Return to review?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
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
	    
	    
	    mSearchURL = getResources().getString(R.string.google_search);
	    String urlString = mURL != null? mURL.toExternalForm() : mSearchURL;
	    	
	    mURLEditText.setText(urlString);
    	mWebView.loadUrl(urlString);
    	
    	mURLEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				 if(actionId == EditorInfo.IME_ACTION_GO) {
					 loadURL();  
				 }
				 
				return false;
			}
		});

    	
	    return v;
	}
	
	private void loadURL() {
		String urlString = URLUtil.guessUrl(mURLEditText.getText().toString()); 		
		mWebView.loadUrl(urlString);
	}
	
	@Override
	protected void onDoneSelected() {
		String urlString = mWebView.getUrl();
		try {
			URL url = new URL(URLUtil.guessUrl(urlString));
			Intent i = getNewReturnData();
			i.putExtra(URL, url);
			i.putExtra(URL_OLD, mURL);
		} catch (MalformedURLException e) {
			Toast.makeText(getActivity(), getResources().getString(R.string.toast_bad_url), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_search_delete_done, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_item_search) {
            mURLEditText.setText(mSearchURL);
            loadURL();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
	}
	
	private class URLWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			mURLEditText.setText(view.getUrl());
			super.onPageFinished(view, url);
		}
	}

	@Override
	protected void onDeleteSelected() {
		Intent i = getNewReturnData();
		i.putExtra(URL_OLD, mURL);
    	mWebView.loadUrl(mSearchURL);		
	}

	@Override
	protected boolean hasDataToDelete() {
		return mURL != null;
	}
}
