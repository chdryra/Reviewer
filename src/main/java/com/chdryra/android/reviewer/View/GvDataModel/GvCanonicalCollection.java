package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.View.GvDataSorting.GvDataComparators;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonicalCollection<T extends GvData> implements GvDataCollection<GvCanonical>,
        Iterable<GvCanonical> {
    public static final Parcelable.Creator<GvCanonicalCollection> CREATOR = new Parcelable
            .Creator<GvCanonicalCollection>() {
        public GvCanonicalCollection createFromParcel(Parcel in) {
            return new GvCanonicalCollection(in);
        }

        public GvCanonicalCollection[] newArray(int size) {
            return new GvCanonicalCollection[size];
        }
    };

    private GvDataList<GvCanonical> mData;
    private GvDataType<T> mType;
    private Comparator<GvCanonical> mComparator;

    public GvCanonicalCollection(GvDataType<T> type) {
        mType = type;
        GvDataType<GvCanonical> listType =
                new GvDataType<>(GvCanonical.class, type.getDatumName(), type.getDataName());
        mData = new GvDataList<>(listType, null);
        setComparator();
    }

    public GvCanonicalCollection(Parcel in) {
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mData = in.readParcelable(GvDataList.class.getClassLoader());
        setComparator();
    }

    public void add(GvCanonical<T> canonical) {
        mData.add(canonical);
    }

    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public void sort() {
        mData.sort(mComparator);
    }

    @Override
    public GvCanonical<T> getItem(int position) {
        //TODO make type safe
        return mData.getItem(position);
    }

    @Override
    public GvDataList<GvCanonical> toList() {
        return mData;
    }

    @Override
    public boolean contains(GvCanonical item) {
        return mData.contains(item);
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return mData.getStringSummary();
    }

    @Override
    public GvReviewId getReviewId() {
        return null;
    }

    @Override
    public boolean hasElements() {
        return mData.hasElements();
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mType, flags);
        dest.writeParcelable(mData, flags);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhDataCollection();
    }

    @Override
    public boolean isValidForDisplay() {
        return true;
    }

    @Override
    public Iterator<GvCanonical> iterator() {
        return mData.iterator();
    }

    private void setComparator() {
        final Comparator<T> comparator = GvDataComparators.getDefaultComparator(mType);
        mComparator = new Comparator<GvCanonical>() {
            @Override
            public int compare(GvCanonical lhs, GvCanonical rhs) {
                //TODO make type safe
                return comparator.compare((T) lhs.getCanonical(), (T) rhs.getCanonical());
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvCanonicalCollection)) return false;

        GvCanonicalCollection<?> that = (GvCanonicalCollection<?>) o;

        if (!mData.equals(that.mData)) return false;
        return mType.equals(that.mType);

    }

    @Override
    public int hashCode() {
        int result = mData.hashCode();
        result = 31 * result + mType.hashCode();
        return result;
    }
}
