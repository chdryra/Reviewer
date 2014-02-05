package com.chdryra.android.reviewer;

import java.util.Hashtable;

public class IntentObjectHolder {
	 private static IntentObjectHolder sInstance;
	 private Hashtable<String, Object> mData;

	    private IntentObjectHolder() {
	        mData = new Hashtable<String, Object>();
	    }

	    private static IntentObjectHolder getInstance() {
	        if(sInstance == null)
	            sInstance = new IntentObjectHolder();
	     
	        return sInstance;
	    }

	    public static void addObject(String key, Object object) {
	        getInstance().mData.put(key, object);
	    }

	    public static Object getObject(String key) {
	        return getInstance().mData.remove(key);
	    }
}
