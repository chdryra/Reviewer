/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumCounter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentMode implements CanonicalDatumMaker<DataComment> {
    //Overridden
    @Override
    public DataComment getCanonical(IdableList<? extends DataComment> data) {
        String id = data.getReviewId();
        if (data.size() == 0) return new DatumComment(id, "", false);

        DatumCounter<DataComment, String> counter = getCounter(data);
        return new DatumComment(id, formatComment(counter), false);
    }

    private String formatComment(DatumCounter<DataComment, String> counter) {
        String maxComment = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxComment += " + " + String.valueOf(nonMax);
        return maxComment;
    }

    @NonNull
    private DatumCounter<DataComment, String> getCounter(IdableList<? extends DataComment> data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataComment, String>() {
                        @Override
                        public String getData(DataComment datum) {
                            return datum.getComment();
                        }
                    });
    }
}
