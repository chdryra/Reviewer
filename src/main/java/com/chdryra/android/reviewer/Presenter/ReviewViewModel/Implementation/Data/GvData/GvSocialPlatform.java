/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhSocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * {@link } version of: no equivalent as used for review sharing screen.
 * {@link ViewHolder}: {@link VhSocialPlatform}
 */
public class GvSocialPlatform extends GvDualText implements DataSocialPlatform {
    public static final GvDataType<GvSocialPlatform> TYPE =
            new GvDataType<>(GvSocialPlatform.class, "share", "share");
    public static final Creator<GvSocialPlatform> CREATOR = new Creator<GvSocialPlatform>() {
        @Override
        public GvSocialPlatform createFromParcel(Parcel in) {
            return new GvSocialPlatform(in);
        }

        @Override
        public GvSocialPlatform[] newArray(int size) {
            return new GvSocialPlatform[size];
        }
    };

    private static final String PLACEHOLDER = "--";
    private SocialPlatform<?> mPlatform;
    private boolean mIsChosen = false;
    private int mFollowers = 0;

    public GvSocialPlatform() {
    }

    public GvSocialPlatform(SocialPlatform<?> platform) {
        super(platform.getName(), PLACEHOLDER);
        mPlatform = platform;
    }

    public GvSocialPlatform(Parcel in) {
        throw new UnsupportedOperationException("Parcelable not supported!");
    }

    public boolean isChosen() {
        return mIsChosen;
    }

    public void press() {
        mIsChosen = !mIsChosen;
    }

    @Override
    public void getFollowers(FollowersListener listener) {
        mPlatform.getFollowers(listener);
    }

    public boolean isAuthorised() {
        return mPlatform.isAuthorised();
    }

    public String getPlaceHolder() {
        return PLACEHOLDER;
    }

    public SocialPlatform<?> getPlatform() {
        return mPlatform;
    }

    @Override
    public String getName() {
        return getUpper();
    }

    @Override
    public GvDataType<GvSocialPlatform> getGvDataType() {
        return GvSocialPlatform.TYPE;
    }

    @Override
    public String getStringSummary() {
        return getName();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        throw new UnsupportedOperationException("Parcelable not supported!");
    }


    @Override
    public ViewHolder getViewHolder() {
        return new VhSocialPlatform();
    }

    @Override
    public boolean isValidForDisplay() {
        return getName() != null && getName().length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvSocialPlatform)) return false;
        if (!super.equals(o)) return false;

        GvSocialPlatform that = (GvSocialPlatform) o;

        if (mIsChosen != that.mIsChosen) return false;
        if (mFollowers != that.mFollowers) return false;
        return !(mPlatform != null ? !mPlatform.equals(that.mPlatform) : that.mPlatform != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mPlatform != null ? mPlatform.hashCode() : 0);
        result = 31 * result + (mIsChosen ? 1 : 0);
        result = 31 * result + mFollowers;
        return result;
    }
}
