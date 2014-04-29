package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewURL extends SherlockFragment {

	private static final int DELETE_CONFIRM = DialogBasicFragment.DELETE_CONFIRM;
	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;

	private ControllerReviewNode mController;
	private ClearableEditText mURLEditText;
	private WebView mWebView;

	private String mOldURL;
	private boolean mDeleteConfirmed = false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getActivity().getIntent().getExtras());
	    if(mController.hasURL())
	    	mOldURL = mController.getURLString();
		setRetainInstance(true);
	    setHasOptionsMenu(true);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);		
		
		View v = inflater.inflate(R.layout.fragment_review_url, container, false);
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mWebView = (WebView)v.findViewById(R.id.web_view);
	    mURLEditText= (ClearableEditText)v.findViewById(R.id.edit_text_url);
	    
	    mWebView.setWebViewClient(new URLWebViewClient());
	    
	    String urlString = "http://www.google.com";
	    if (mController.hasURL())
	    	urlString = mController.getURLString();
	    	
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
	
	private void setURL() {
		String url = mWebView.getUrl();
		try {
			mController.setURL(url);
		} catch (MalformedURLException e) {
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_bad_url), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (URISyntaxException e) {
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_bad_url), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	private void sendResult(int resultCode) {
		if(resultCode == RESULT_DELETE && mController.hasURL()) {
			if(mDeleteConfirmed) {
				mController.deleteURL();
			} else {
				DialogBasicFragment.showDeleteConfirmDialog(getResources().getString(R.string.url_activity_title), 
						FragmentReviewURL.this, DELETE_CONFIRM, getFragmentManager());
				return;
			}
		}
		
		
		if(resultCode == Activity.RESULT_OK)
			setURL();
		
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_search_delete_done, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				sendResult(Activity.RESULT_CANCELED);
				break;
				
			case R.id.menu_item_delete:
				sendResult(RESULT_DELETE);
				break;
				
			case R.id.menu_item_done:
				sendResult(Activity.RESULT_OK);
				break;
			
			default:
				break;
		}
	
		return super.onOptionsItemSelected(item);

	}
	
	private class URLWebViewClient extends WebViewClient {
		@Override
		public void onLoadResource(WebView view, String url) {
			mURLEditText.setText(view.getUrl());
			super.onLoadResource(view, url);
		}
	}
}
