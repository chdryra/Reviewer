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
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;
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
    @Override
    public DataComment getCanonical(IdableList<? extends DataComment> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumComment(id, "", false);

        return new DatumComment(id, getComment(data), false);
    }

    private String getComment(IdableList<? extends DataComment> data) {
        ItemCounter<DataComment, String> counter = getCommentCounter();
        counter.performCount(data);
        String maxComment = counter.getModeItem();
        int nonMax = counter.getNonModeCount();
        if (nonMax > 0) maxComment += " + " + String.valueOf(nonMax);
        return maxComment;
    }

    @NonNull
    private ItemCounter<DataComment, String> getCommentCounter() {
        return new ItemCounter<>(new ItemGetter<DataComment, String>() {
                        @Override
                        public String getItem(DataComment datum) {
                            return datum.getComment();
                        }
                    });
    }
}
