package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import android.os.Parcel;
import android.os.Parcelable;

public class RDUrl implements RData {
	private Review mHoldingReview;
	private URL mURL;

	public RDUrl() {
	}

	public RDUrl(String url, Review holdingReview) throws MalformedURLException, URISyntaxException{
		setURL(url);
		mHoldingReview = holdingReview;
	}
	
	public RDUrl(Parcel in) throws MalformedURLException, URISyntaxException{
		setURL(in.readString());
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
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mURL.toExternalForm());
	}

	public static final Parcelable.Creator<RDUrl> CREATOR 
	= new Parcelable.Creator<RDUrl>() {
	    public RDUrl createFromParcel(Parcel in) {
	    	RDUrl url = null;
	    	try {
				url = new RDUrl(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        return url;
	    }

	    public RDUrl[] newArray(int size) {
	        return new RDUrl[size];
	    }
	};
}
