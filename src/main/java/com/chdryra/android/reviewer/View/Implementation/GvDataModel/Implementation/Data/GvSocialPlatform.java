package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.ViewHolders.VhSocialPlatform;

/**
 * {@link } version of: no equivalent as used for review sharing screen.
 * {@link ViewHolder}: {@link VhSocialPlatform}
 *
 * @see SocialPlatformList
 */
public class GvSocialPlatform extends GvDualText {
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
