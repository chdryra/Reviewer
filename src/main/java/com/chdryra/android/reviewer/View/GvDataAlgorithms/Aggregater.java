package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;

/**
 * Created by: Rizwan Choudrey
 * On: 14/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class Aggregater {
    public static GvDataMap<GvAuthorList.GvAuthor, GvDataList<GvAuthorList.GvAuthor>> aggregate
            (GvDataList<GvAuthorList.GvAuthor> data) {
        GvDataAggregater<GvAuthorList.GvAuthor> aggregator = new GvDataAggregater<>(data);
        ComparitorGvAuthor comparitor = new ComparitorGvAuthor();
        DifferenceBoolean difference = new DifferenceBoolean(false);
        CanonicalAuthor canonical = new CanonicalAuthor();
        return aggregator.aggregate(comparitor, difference, canonical);
    }
}
