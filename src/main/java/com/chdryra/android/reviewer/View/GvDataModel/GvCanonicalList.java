package com.chdryra.android.reviewer.View.GvDataModel;

import com.chdryra.android.reviewer.View.GvDataSorting.GvDataSorters;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonicalList<T extends GvData> extends GvDataList<GvCanonical<T>> {
    private final Comparator<T> mComparator;

    public GvCanonicalList(GvDataType<T> type) {
        this(type, null);
    }

    public GvCanonicalList(GvDataType<T> type, GvReviewId id) {
        super(GvTypeMaker.newType(GvCanonicalList.class, type), id);
        mComparator = GvDataSorters.getDefaultComparator(type);
    }

    public GvCanonicalList(GvCanonicalList<T> data) {
        super(data);
        //TODO make type safe
        mComparator = GvDataSorters.getDefaultComparator(data.getGvDataType().getElementType());
    }

    @Override
    protected Comparator<GvCanonical<T>> getDefaultComparator() {
        return new Comparator<GvCanonical<T>>() {
            @Override
            public int compare(GvCanonical<T> lhs, GvCanonical<T> rhs) {
                return mComparator.compare(lhs.getCanonical(), rhs.getCanonical());
            }
        };
    }
}
