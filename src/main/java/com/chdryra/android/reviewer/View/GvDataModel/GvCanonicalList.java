package com.chdryra.android.reviewer.View.GvDataModel;

import com.chdryra.android.reviewer.View.GvDataSorting.GvDataSorters;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonicalList<T extends GvData> extends GvDataList<GvCanonical<T>> {

    public GvCanonicalList(GvDataType<T> type) {
        super(GvTypeMaker.newType(GvCanonicalList.class, type), null);
    }

    public GvCanonicalList(GvReviewId id, GvDataType<T> type) {
        super(GvTypeMaker.newType(GvCanonicalList.class, type), id);
    }

    public GvCanonicalList(GvCanonicalList<T> data) {
        super(data);
    }

    @Override
    protected Comparator<GvCanonical<T>> getDefaultComparator() {
        //TODO make type safe;
        return new Comparator<GvCanonical<T>>() {

            @Override
            public int compare(GvCanonical<T> lhs, GvCanonical<T> rhs) {
                Comparator<T> comparator =
                        GvDataSorters.getDefaultComparator(getGvDataType().getElementType());
                return comparator.compare(lhs.getCanonical(), rhs.getCanonical());
            }
        };
    }
}
