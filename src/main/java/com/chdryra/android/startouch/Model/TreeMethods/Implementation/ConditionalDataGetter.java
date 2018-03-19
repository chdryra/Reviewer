/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.TreeMethods.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.NodeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConditionalDataGetter<T extends HasReviewId> extends ConditionalValueGetter<T>
        implements NodeDataGetter<T> {
    public ConditionalDataGetter(Condition condition, NodeDataGetter<T> method) {
        super(condition, method);
    }
}
