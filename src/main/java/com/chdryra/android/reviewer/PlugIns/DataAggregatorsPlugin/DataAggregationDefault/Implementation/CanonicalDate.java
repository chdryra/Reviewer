/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.CanonicalDatumMaker;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDate implements CanonicalDatumMaker<DataDateReview> {
    @Override
    public DataDateReview getCanonical(IdableList<? extends DataDateReview> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return NullData.nulDate(id);

        return new DatumDateReview(id, getMostRecent(data));
    }

    private long getMostRecent(IdableList<? extends DataDateReview> data) {
        Date canon = new Date(data.getItem(0).getTime());
        for (DataDateReview date : data) {
            Date candidate = new Date(date.getTime());
            if (candidate.after(canon)) canon = candidate;
        }

        return  canon.getTime();
    }
}
