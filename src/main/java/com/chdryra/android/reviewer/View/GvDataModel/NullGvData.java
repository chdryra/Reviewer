package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.VHString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
class NullGvData implements GvData {
    private static final GvDataType<NullGvData> TYPE = new GvDataType<>(NullGvData.class, "NULL");
    public static final Parcelable.Creator<NullGvData> CREATOR = new Parcelable
            .Creator<NullGvData>() {
        //Overridden
        public NullGvData createFromParcel(Parcel in) {
            return new NullGvData();
        }

        public NullGvData[] newArray(int size) {
            return new NullGvData[size];
        }
    };

    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return TYPE;
    }

    @Override
    public String getStringSummary() {
        return "";
    }

    @Override
    public GvReviewId getReviewId() {
        return GvReviewId.NULL_ID;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public boolean isVerboseCollection() {
        return false;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhText(new VHString.VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                return "";
            }
        });
    }

    @Override
    public boolean isValidForDisplay() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
