/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthor implements CanonicalDatumMaker<DataAuthor> {
    private DifferenceComparitor<? super DataAuthor, DifferenceBoolean> mComparitor;

    public CanonicalAuthor(DifferenceComparitor<? super DataAuthor, DifferenceBoolean> comparitor) {
        mComparitor = comparitor;
    }

    @Override
    public DataAuthor getCanonical(IdableList<? extends DataAuthor> data) {
        ReviewId id = data.getReviewId();
        DataAuthor nullAuthor = FactoryNullData.nullAuthor(id);
        if (data.size() == 0) return nullAuthor;

        DataAuthor reference = data.getItem(0);
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (DataAuthor author : data) {
            DifferenceBoolean difference = mComparitor.compare(reference, author);
            if (!difference.lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new DatumAuthor(id, reference.getName(), reference.getAuthorId());
    }
}
