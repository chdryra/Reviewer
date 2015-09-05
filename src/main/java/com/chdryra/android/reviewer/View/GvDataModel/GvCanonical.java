package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonical<T extends GvData> implements GvDataCollection<T> {
    public static final Parcelable.Creator<GvCanonical> CREATOR = new Parcelable
            .Creator<GvCanonical>() {
        public GvCanonical createFromParcel(Parcel in) {
            return new GvCanonical(in);
        }

        public GvCanonical[] newArray(int size) {
            return new GvCanonical[size];
        }
    };

    private T mCanonical;
    private GvDataList<T> mData;
    private GvDataType mType;

    public GvCanonical(@NotNull T canonical, @NotNull GvDataList<T> data) {
        mCanonical = canonical;
        if (data.size() == 0) {
            throw new IllegalArgumentException("Data must have size!");
        }
        mData = data;
        mType = GvTypeMaker.newType(this.getClass(), canonical.getGvDataType());
    }

    public GvCanonical(GvCanonical<T> gvCanoncial) {
        this(FactoryGvData.copy(gvCanoncial.getCanonical()), FactoryGvData.copy(gvCanoncial
                .toList()));
    }

    public GvCanonical(Parcel in) {
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mCanonical = in.readParcelable(mType.getDataClass().getClassLoader());
        mData = in.readParcelable(GvDataList.class.getClassLoader());
    }

    public T getCanonical() {
        return mCanonical;
    }

    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public void sort() {
        mData.sort();
    }

    @Override
    public T getItem(int position) {
        return mData.getItem(position);
    }

    @Override
    public GvDataList<T> toList() {
        return mData;
    }

    @Override
    public GvDataType getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return mCanonical.getStringSummary();
    }

    @Override
    public GvReviewId getReviewId() {
        return mCanonical.getReviewId();
    }

    @Override
    public boolean hasElements() {
        return mData.size() > 0;
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvCanonical)) return false;

        GvCanonical<?> that = (GvCanonical<?>) o;

        if (mCanonical != null ? !mCanonical.equals(that.mCanonical) : that.mCanonical != null)
            return false;
        if (mData != null ? !mData.equals(that.mData) : that.mData != null) return false;
        return !(mType != null ? !mType.equals(that.mType) : that.mType != null);

    }

    @Override
    public int hashCode() {
        int result = mCanonical != null ? mCanonical.hashCode() : 0;
        result = 31 * result + (mData != null ? mData.hashCode() : 0);
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        return result;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhCanonical(mCanonical.getGvDataType());
    }

    @Override
    public boolean isValidForDisplay() {
        return mCanonical.isValidForDisplay();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mType, flags);
        dest.writeParcelable(mCanonical, flags);
        dest.writeParcelable(mData, flags);
    }
}
