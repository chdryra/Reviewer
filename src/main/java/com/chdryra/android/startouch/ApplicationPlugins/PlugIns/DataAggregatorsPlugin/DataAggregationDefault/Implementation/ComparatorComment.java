/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation;


import com.chdryra.android.corelibrary.Aggregation.ComparatorString;
import com.chdryra.android.corelibrary.Aggregation.ItemGetter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorComment extends ComparatorStringable<DataComment> {
    public ComparatorComment(ComparatorString comparitor) {
        super(comparitor, new ItemGetter<DataComment, String>() {
            @Override
            public String getItem(DataComment datum) {
                return datum.getComment().toLowerCase();
            }
        });
    }
}
