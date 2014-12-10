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
public class VgSocialPlatformList extends VgDataList<VgSocialPlatformList.VgSocialPlatform> {

    private VgSocialPlatformList(Context context) {
        super(GvType.SOCIAL);
        for (SocialPlatform platform : SocialPlatformList.get(context)) {
            add(new VgSocialPlatform(platform.getName(), platform.getFollowers()));
        }
    }

    static VgSocialPlatformList getLatest(Context context) {
        SocialPlatformList.update(context);
        return new VgSocialPlatformList(context);
    }

    @Override
    protected Comparator<VgSocialPlatform> getDefaultComparator() {
        return new Comparator<VgSocialPlatform>() {

            @Override
            public int compare(VgSocialPlatform lhs, VgSocialPlatform rhs) {
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
    static class VgSocialPlatform extends VgDualText {
        public static final Parcelable.Creator<VgSocialPlatform> CREATOR    = new Parcelable
                .Creator<VgSocialPlatform>() {
            public VgSocialPlatform createFromParcel(Parcel in) {
                return new VgSocialPlatform(in);
            }

            public VgSocialPlatform[] newArray(int size) {
                return new VgSocialPlatform[size];
            }
        };
        private             int                                  mFollowers = 0;
        private             boolean                              mIsChosen  = false;

        VgSocialPlatform(String name, int followers) {
            super(name, String.valueOf(followers));
            mFollowers = followers;
        }

        public VgSocialPlatform(Parcel in) {
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
            if (!(o instanceof VgSocialPlatform)) return false;
            if (!super.equals(o)) return false;

            VgSocialPlatform that = (VgSocialPlatform) o;

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
