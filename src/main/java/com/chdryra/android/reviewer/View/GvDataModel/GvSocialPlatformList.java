/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList.SocialPlatform;

/**
 * Used for social sharing screen showing social platforms and number of followers.
 *
 * @see ApplicationInstance
 * @see SocialPlatformList
 */
public class GvSocialPlatformList extends GvDataList<GvSocialPlatformList.GvSocialPlatform> {
    public static final Parcelable.Creator<GvSocialPlatformList> CREATOR = new Parcelable
            .Creator<GvSocialPlatformList>() {
        //Overridden
        public GvSocialPlatformList createFromParcel(Parcel in) {
            return new GvSocialPlatformList(in);
        }

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

//Classes

    /**
     * {@link } version of: no equivalent as used for review sharing screen.
     * {@link ViewHolder}: {@link VhSocialPlatform}
     *
     * @see SocialPlatformList
     */
    public static class GvSocialPlatform extends GvDualText {
        public static final GvDataType<GvSocialPlatform> TYPE =
                new GvDataType<>(GvSocialPlatform.class, "share", "share");
        public static final Parcelable.Creator<GvSocialPlatform> CREATOR = new Parcelable
                .Creator<GvSocialPlatform>() {
            //Overridden
            public GvSocialPlatform createFromParcel(Parcel in) {
                return new GvSocialPlatform(in);
            }

            public GvSocialPlatform[] newArray(int size) {
                return new GvSocialPlatform[size];
            }
        };
        private int mFollowers = 0;
        private boolean mIsChosen = false;

        //Constructors
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

        //public methods
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

        //Overridden
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
    }
}
