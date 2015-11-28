package com.chdryra.android.reviewer.View.DataAggregation;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewId;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.ViewHolders.VhCanonical;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonical<T extends GvData> implements GvDataCollection<T>, Iterable<T> {
    public static final Parcelable.Creator<GvCanonical> CREATOR = new Parcelable
            .Creator<GvCanonical>() {
        @Override
        public GvCanonical createFromParcel(Parcel in) {
            return new GvCanonical(in);
        }

        @Override
        public GvCanonical[] newArray(int size) {
            return new GvCanonical[size];
        }
    };

    private T mCanonical;
    private GvDataList<T> mData;
    private GvDataType<T> mType;

    //Constructors
    public GvCanonical(){

    }

    public GvCanonical(T canonical, GvDataList<T> data) {
        mCanonical = canonical;
        mType = data.getGvDataType();
        if (data.size() == 0) {
            throw new IllegalArgumentException("Data must have size!");
        }
        mData = data;
    }

    public GvCanonical(Parcel in) {
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mCanonical = in.readParcelable(mType.getDataClass().getClassLoader());
        mData = in.readParcelable(GvDataList.class.getClassLoader());
    }

    //public methods
    public T getCanonical() {
        return mCanonical;
    }

    //Overridden

    @Override
    public GvReviewId getGvReviewId() {
        return getCanonical().getGvReviewId();
    }

    @Override
    public void add(T datum) {
        throw new UnsupportedOperationException("GvCanonical does not support adding data. Use constructor");
    }

    @Override
    public void addCollection(IdableCollection<T> data) {
        throw new UnsupportedOperationException("GvCanonical does not support adding data. Use constructor");
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
    public boolean contains(T item) {
        return mData.contains(item);
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return mCanonical.getStringSummary();
    }

    @Override
    public String getReviewId() {
        return mCanonical.getReviewId();
    }

    @Override
    public boolean hasElements() {
        return mData.size() > 0;
    }

    @Override
    public boolean isVerboseCollection() {
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
        return new VhCanonical(mCanonical.getViewHolder());
    }

    @Override
    public boolean isValidForDisplay() {
        return mCanonical.isValidForDisplay();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return mCanonical.hasData(dataValidator);
    }

    @Override
    public Iterator<T> iterator() {
        return mData.iterator();
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
