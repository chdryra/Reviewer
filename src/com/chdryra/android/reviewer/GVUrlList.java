package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.RandomTextUtils;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVUrlList extends GVReviewDataList<GVUrlList.GVUrl> {

	public GVUrlList() {
		super(GVType.URLS);
	}
	
	public void add(String urlString) throws MalformedURLException, URISyntaxException {
		add(new GVUrl(urlString));	
	}
	
	public void add(URL url) {
		add(new GVUrl(url));
	}
	
	public boolean contains(String urlString) {
		try {
			return contains(new GVUrl(urlString));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean contains(URL url) {
		return contains(new GVUrl(url));
	}
	
	public void remove(String urlString) {
		try {
			remove(new GVUrl(urlString));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void remove(URL url) {
		remove(new GVUrl(url));
	}
	
	class GVUrl implements GVData {
		URL mUrl;
		
		private GVUrl(URL url) {
			mUrl = url;
		}
		
		private GVUrl(String stringUrl) throws MalformedURLException, URISyntaxException{
			URL url = new URL(stringUrl);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			mUrl = uri.toURL();
		}

		public URL getUrl() {
			return mUrl;
		}
		
		public String toString() {
			return RandomTextUtils.toStringURL(mUrl);
		}
		
		public String toShortenedString() {
			return RandomTextUtils.toShortenedStringURL(mUrl);
		}
	
		@Override
		public ViewHolder getViewHolder() {
			return new VHUrlView();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((mUrl == null) ? 0 : mUrl.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVUrl other = (GVUrl) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mUrl == null) {
				if (other.mUrl != null)
					return false;
			} else if (!mUrl.equals(other.mUrl))
				return false;
			return true;
		}

		private GVUrlList getOuterType() {
			return GVUrlList.this;
		}
	}
}
