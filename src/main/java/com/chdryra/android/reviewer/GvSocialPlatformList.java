/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.SocialPlatformList.SocialPlatform;

import java.util.Comparator;

/**
 * Used for social sharing screen showing social platforms and number of followers.
 *
 * @see com.chdryra.android.reviewer.Administrator
 * @see com.chdryra.android.reviewer.SocialPlatformList
 */
public class GvSocialPlatformList extends GvDataList<GvSocialPlatformList.GvSocialPlatform> {
    public static final GvType TYPE = GvType.SOCIAL;

    private GvSocialPlatformList(Context context) {
        super(TYPE);
        for (SocialPlatform platform : SocialPlatformList.get(context)) {
            add(new GvSocialPlatform(platform.getName(), platform.getFollowers()));
        }
    }

    static GvSocialPlatformList getLatest(Context context) {
        SocialPlatformList.update(context);
        return new GvSocialPlatformList(context);
    }

    @Override
    protected Comparator<GvSocialPlatform> getDefaultComparator() {
        return new Comparator<GvSocialPlatform>() {

            @Override
            public int compare(GvSocialPlatform lhs, GvSocialPlatform rhs) {
                int ret = 0;
                if (lhs.getFollowers() > rhs.getFollowers()) {
                    ret = 1;
                }
                if (lhs.getFollowers() < rhs.getFollowers()) {
                    ret = -1;
                }

                return ret;
            }
        };
    }

    /**
     * {@link } version of: no equivalent as used for review sharing screen.
     * {@link ViewHolder}: {@link VHSocialPlatform}
     *
     * @see com.chdryra.android.reviewer.SocialPlatformList
     */
    static class GvSocialPlatform extends GvDualText {
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

        GvSocialPlatform(String name, int followers) {
            super(name, String.valueOf(followers));
            mFollowers = followers;
        }

        public GvSocialPlatform(Parcel in) {
            super(in);
            mFollowers = in.readInt();
            mIsChosen = in.readByte() != 0;
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHSocialPlatform();
        }

        @Override
        public boolean isValidForDisplay() {
            return getName() != null && getName().length() > 0;
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
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(mFollowers);
            parcel.writeByte((byte) (mIsChosen ? 1 : 0));
        }

        String getName() {
            return getUpper();
        }

        int getFollowers() {
            return mFollowers;
        }

        boolean isChosen() {
            return mIsChosen;
        }

        void press() {
            mIsChosen = !mIsChosen;
        }
    }
}
