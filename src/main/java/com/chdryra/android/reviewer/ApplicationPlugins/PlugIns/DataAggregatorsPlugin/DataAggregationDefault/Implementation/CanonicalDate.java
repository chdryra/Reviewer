/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.CanonicalDatumMaker;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDate implements CanonicalDatumMaker<DataDate> {
    @Override
    public DataDate getCanonical(IdableList<? extends DataDate> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return FactoryNullData.nulDate(id);

        return new DatumDate(id, getMostRecent(data));
    }

    private long getMostRecent(IdableList<? extends DataDate> data) {
        Date canon = new Date(data.getItem(0).getTime());
        for (DataDate date : data) {
            Date candidate = new Date(date.getTime());
            if (candidate.after(canon)) canon = candidate;
        }

        return  canon.getTime();
    }
}
