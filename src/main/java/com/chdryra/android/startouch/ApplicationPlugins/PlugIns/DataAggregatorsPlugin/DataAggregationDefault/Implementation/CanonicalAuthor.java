/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation;


import com.chdryra.android.corelibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.corelibrary.Aggregation.DifferenceComparator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthor implements CanonicalDatumMaker<DataAuthorName> {
    private final DifferenceComparator<? super DataAuthorName, DifferenceBoolean> mComparitor;

    public CanonicalAuthor(DifferenceComparator<? super DataAuthorName, DifferenceBoolean>
                                   comparitor) {
        mComparitor = comparitor;
    }

    @Override
    public DataAuthorName getCanonical(IdableList<? extends DataAuthorName> data) {
        ReviewId id = data.getReviewId();
        DataAuthorName nullAuthor = FactoryNullData.nullAuthor(id);
        if (data.size() == 0) return nullAuthor;

        DataAuthorName reference = data.get(0);
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (DataAuthorName author : data) {
            DifferenceBoolean difference = mComparitor.compare(reference, author);
            if (!difference.lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new DatumAuthorName(id, reference.getName(), reference.getAuthorId());
    }
}
