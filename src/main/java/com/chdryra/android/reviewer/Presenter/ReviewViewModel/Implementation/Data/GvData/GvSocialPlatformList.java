/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * Used for social sharing screen showing social platforms and number of followers.
 *
 * @see ApplicationInstance
 * @see SocialPlatformList
 */
public class GvSocialPlatformList extends GvDataListImpl<GvSocialPlatform> {
    public static final Parcelable.Creator<GvSocialPlatformList> CREATOR = new Parcelable
            .Creator<GvSocialPlatformList>() {
        @Override
        public GvSocialPlatformList createFromParcel(Parcel in) {
            return new GvSocialPlatformList(in);
        }

        @Override
        public GvSocialPlatformList[] newArray(int size) {
            return new GvSocialPlatformList[size];
        }
    };

    private SocialPlatformList mList;

    public GvSocialPlatformList() {
        super(GvSocialPlatform.TYPE, null);
    }

    public GvSocialPlatformList(Parcel in) {
        super(in);
    }

    public GvSocialPlatformList(SocialPlatformList list) {
        this();
        mList = list;
        for (SocialPlatform platform : list) {
            add(new GvSocialPlatform(platform.getName(), platform.getFollowers()));
        }
    }
}
