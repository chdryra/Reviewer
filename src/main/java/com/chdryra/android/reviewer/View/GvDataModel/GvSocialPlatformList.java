/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Model.Social.SocialPlatformList.SocialPlatform;

/**
 * Used for social sharing screen showing social platforms and number of followers.
 *
 * @see Administrator
 * @see SocialPlatformList
 */
public class GvSocialPlatformList extends GvDataList<GvSocialPlatformList.GvSocialPlatform> {
    //For testing
    public GvSocialPlatformList() {
        super(GvSocialPlatform.TYPE, null);
    }

    private GvSocialPlatformList(Context context) {
        this();
        for (SocialPlatform platform : SocialPlatformList.getList(context)) {
            add(new GvSocialPlatform(platform.getName(), platform.getFollowers()));
        }
    }

    public static GvSocialPlatformList getLatest(Context context) {
        SocialPlatformList.update(context);
        return new GvSocialPlatformList(context);
    }

    /**
     * {@link } version of: no equivalent as used for review sharing screen.
     * {@link ViewHolder}: {@link VhSocialPlatform}
     *
     * @see SocialPlatformList
     */
    public static class GvSocialPlatform extends GvDualText {
        public static final GvDataType<GvSocialPlatform>         TYPE       =
                new GvDataType<>(GvSocialPlatform.class, "share", "share");
        public static final Parcelable.Creator<GvSocialPlatform> CREATOR    = new Parcelable
                .Creator<GvSocialPlatform>() {
            public GvSocialPlatform createFromParcel(Parcel in) {
                return new GvSocialPlatform(in);
            }

            public GvSocialPlatform[] newArray(int size) {
                return new GvSocialPlatform[size];
            }
        };
        private             int                                  mFollowers = 0;
        private             boolean                              mIsChosen  = false;

        public GvSocialPlatform() {
        }

        //public for testing
        public GvSocialPlatform(String name, int followers) {
            super(name, String.valueOf(followers));
            if (followers < 0) throw new RuntimeException("Should have non-negative followers!");
            mFollowers = followers;
        }

        public GvSocialPlatform(Parcel in) {
            super(in);
            mFollowers = in.readInt();
            mIsChosen = in.readByte() != 0;
        }

        @Override
        public GvDataType<GvSocialPlatform> getGvDataType() {
            return GvSocialPlatform.TYPE;
        }

        @Override
        public String getStringSummary() {
            return getName() + ": " + getFollowers();
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(mFollowers);
            parcel.writeByte((byte) (mIsChosen ? 1 : 0));
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhSocialPlatform();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validateString(getName());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvSocialPlatform)) return false;
            if (!super.equals(o)) return false;

            GvSocialPlatform that = (GvSocialPlatform) o;

            return mFollowers == that.mFollowers && mIsChosen == that.mIsChosen;

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + mFollowers;
            result = 31 * result + (mIsChosen ? 1 : 0);
            return result;
        }

        public String getName() {
            return getUpper();
        }

        public int getFollowers() {
            return mFollowers;
        }

        public boolean isChosen() {
            return mIsChosen;
        }

        public void press() {
            mIsChosen = !mIsChosen;
        }
    }
}
