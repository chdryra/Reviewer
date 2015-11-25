/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewId;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDate implements CanonicalDatumMaker<GvDate> {
    //Overridden
    @Override
    public GvDate getCanonical(GvDataList<GvDate> data) {
        GvReviewId id = new GvReviewId(data.getReviewId());
        if (data.size() == 0) return new GvDate(id, 0);

        DatumCounter<GvDate, Date> counter = new DatumCounter<>(data,
                new DataGetter<GvDate, Date>() {
                    //Overridden
                    @Override
                    public Date getData(GvDate datum) {
                        return new Date(datum.getTime());
                    }
                });

        Date canon = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) {
            for (GvDate date : data) {
                Date candidate = new Date(date.getTime());
                if (candidate.after(canon)) canon = candidate;
            }
        }

        return new GvDate(id, canon.getTime());
    }
}
