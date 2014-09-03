package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.chdryra.android.mygenerallibrary.RandomTextUtils;

public class RDUrl implements RData {
	private Review mHoldingReview;
	private URL mURL;

	public RDUrl() {
	}

	public RDUrl(URL url, Review holdingReview) {
		mURL = url;
		mHoldingReview = holdingReview;
	}
	
	public RDUrl(String url, Review holdingReview) throws MalformedURLException, URISyntaxException{
		setURL(url);
		mHoldingReview = holdingReview;
	}

	public RDUrl(RDUrl url, Review holdingReview) throws MalformedURLException, URISyntaxException{
		setURL(url.toString());
		mHoldingReview = holdingReview;
	}
	
	public URL get() {
		return mURL;
	}

	public String toString() {
		return RandomTextUtils.toStringURL(mURL);
	}
	
	public String toShortenedString() {
		return RandomTextUtils.toShortenedStringURL(mURL);
	}
	
	private void setURL(String stringUrl) throws MalformedURLException, URISyntaxException {
		URL url = new URL(stringUrl);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		mURL = uri.toURL();
	}
	
	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}


	@Override
	public boolean hasData() {
		return mURL != null;
	}
}
