package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class RDUrl implements RData {
	private Review mHoldingReview;
	private URL mURL;

	public RDUrl() {
	}

	public RDUrl(String url, Review holdingReview) throws MalformedURLException, URISyntaxException{
		setURL(url);
		mHoldingReview = holdingReview;
	}

	public URL get() {
		return mURL;
	}

	public String toString() {
		String url = mURL != null? mURL.toExternalForm() : null;
		return url;
	}
	
	public String toShortenedString() {
		String url = toString();
	    String protocol = mURL.getProtocol();
        String result = url.replaceFirst(protocol + ":", "");
        if (result.startsWith("//"))
            result = result.substring(2);
        
        result = result.trim();
        if(result.endsWith("/"))
        	result = (String)result.subSequence(0, result.length() - 1);
        
        return result;
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
