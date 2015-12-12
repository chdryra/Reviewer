/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatform;
import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatformList;

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

    //Constructors
    //For testing
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

    public void update() {
        mList.update();
    }
}
