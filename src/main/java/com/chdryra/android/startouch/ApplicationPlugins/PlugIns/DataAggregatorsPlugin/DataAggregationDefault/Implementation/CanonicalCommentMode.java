/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.mygenerallibrary.Aggregation.ItemGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentMode extends CanonicalStringMaker<DataComment> {
    @Override
    public DataComment getCanonical(IdableList<? extends DataComment> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return FactoryNullData.nullComment(id);

        return new DatumComment(id, getModeString(data), false);
    }

    @Override
    @NonNull
    protected ItemGetter<DataComment, String> getStringGetter() {
        return new ItemGetter<DataComment, String>() {
            @Override
            public String getItem(DataComment datum) {
                return datum.getComment();
            }
        };
    }

    @NonNull
    @Override
    protected String formatModeString(String modeString, int nonMode) {
        return String.valueOf(nonMode + 1) + " comments";
    }
}
