/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumCounter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDate implements CanonicalDatumMaker<DataDateReview> {
    //Overridden
    @Override
    public DatumDateReview getCanonical(IdableList<DataDateReview> data) {
        String id = data.getReviewId();
        if (data.size() == 0) return new DatumDateReview(id, 0);

        DatumCounter<DataDateReview, Date> counter = getCounter(data);

        return new DatumDateReview(id, getCanonicalTime(data, counter));
    }

    private long getCanonicalTime(IdableList<DataDateReview> data, DatumCounter<DataDateReview,
                Date> counter) {
        Date canon = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) {
            for (DataDateReview date : data) {
                Date candidate = new Date(date.getTime());
                if (candidate.after(canon)) canon = candidate;
            }
        }

        return  canon.getTime();
    }

    @NonNull
    private DatumCounter<DataDateReview, Date> getCounter(IdableList<DataDateReview> data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataDateReview, Date>() {
                        @Override
                        public Date getData(DataDateReview datum) {
                            return new Date(datum.getTime());
                        }
                    });
    }

}
