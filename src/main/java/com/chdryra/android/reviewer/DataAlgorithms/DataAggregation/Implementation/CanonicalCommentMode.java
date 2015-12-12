/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataGetter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentMode implements CanonicalDatumMaker<DataComment> {
    //Overridden
    @Override
    public DataComment getCanonical(IdableList<? extends DataComment> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumComment(id, "", false);

        DatumCounter<DataComment, String> counter = getCommentCounter(data);
        return new DatumComment(id, getModeComment(counter), false);
    }

    private String getModeComment(DatumCounter<DataComment, String> counter) {
        String maxComment = counter.getModeItem();
        int nonMax = counter.getNonModeCount();
        if (nonMax > 0) maxComment += " + " + String.valueOf(nonMax);
        return maxComment;
    }

    @NonNull
    private DatumCounter<DataComment, String> getCommentCounter(IdableList<? extends DataComment>
                                                                            data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataComment, String>() {
                        @Override
                        public String getData(DataComment datum) {
                            return datum.getComment();
                        }
                    });
    }
}
