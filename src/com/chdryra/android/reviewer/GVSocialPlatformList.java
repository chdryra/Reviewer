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

import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.SocialPlatformList.SocialPlatform;

import java.util.Comparator;

/**
 * GVReviewDataList: GVReviewSocialPlatform
 * <p>
 * ViewHolder: VHSocialView
 * </p>
 * <p/>
 * <p>
 * Used for social sharing screen showing social platforms and number of followers.
 * </p>
 *
 * @see com.chdryra.android.reviewer.Administrator
 * @see com.chdryra.android.reviewer.SocialPlatformList
 * @see com.chdryra.android.reviewer.FragmentReviewShare
 * @see com.chdryra.android.reviewer.VHSocialView
 */
public class GVSocialPlatformList extends GVReviewDataList<GVSocialPlatformList.GVSocialPlatform> {

    private GVSocialPlatformList(Context context) {
        super(GVType.SOCIAL);
        for (SocialPlatform platform : SocialPlatformList.get(context)) {
            add(new GVSocialPlatform(platform.getName(), platform.getFollowers()));
        }
    }

    static GVSocialPlatformList getLatest(Context context) {
        SocialPlatformList.update(context);
        return new GVSocialPlatformList(context);
    }

    /**
     * GVData version of: no equivalent as used for review sharing screen.
     * ViewHolder: VHSocialView
     * <p/>
     * <p>
     * Methods for getting the name, number followers, and selection status.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.SocialPlatformList
     */
    static class GVSocialPlatform extends GVDualString {
        public static final Parcelable.Creator<GVSocialPlatform> CREATOR    = new Parcelable
                .Creator<GVSocialPlatform>() {
            public GVSocialPlatform createFromParcel(Parcel in) {
                return new GVSocialPlatform(in);
            }

            public GVSocialPlatform[] newArray(int size) {
                return new GVSocialPlatform[size];
            }
        };
        private             int                                  mFollowers = 0;
        private             boolean                              mIsChosen  = false;

        GVSocialPlatform(String name, int followers) {
            super(name, String.valueOf(followers));
            mFollowers = followers;
        }

        public GVSocialPlatform(Parcel in) {
            super(in);
            mFollowers = in.readInt();
            mIsChosen = in.readByte() != 0;
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

        @Override
        public ViewHolder getViewHolder() {
            return new VHSocialView();
        }

        @Override
        public boolean isValidForDisplay() {
            return getName() != null && getName().length() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GVSocialPlatform)) return false;
            if (!super.equals(o)) return false;

            GVSocialPlatform that = (GVSocialPlatform) o;

            if (mFollowers != that.mFollowers) return false;
            if (mIsChosen != that.mIsChosen) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + mFollowers;
            result = 31 * result + (mIsChosen ? 1 : 0);
            return result;
        }

        @Override
        public int describeContents() {
            return super.describeContents();
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(mFollowers);
            parcel.writeByte((byte) (mIsChosen ? 1 : 0));
        }
    }

    @Override
    protected Comparator<GVSocialPlatform> getDefaultComparator() {
        return new Comparator<GVSocialPlatform>() {

            @Override
            public int compare(GVSocialPlatform lhs, GVSocialPlatform rhs) {
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
}
