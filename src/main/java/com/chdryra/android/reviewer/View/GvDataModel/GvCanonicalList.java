package com.chdryra.android.reviewer.View.GvDataModel;

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
}
