/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataGetter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorComment extends ComparitorStringable<DataComment> {
    public ComparitorComment() {
        super(new DataGetter<DataComment, String>() {
            @Override
            public String getData(DataComment datum) {
                return datum.getComment().toLowerCase();
            }
        });
    }
}
