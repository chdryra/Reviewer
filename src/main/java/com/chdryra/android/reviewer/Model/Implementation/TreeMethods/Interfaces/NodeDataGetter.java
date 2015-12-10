package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;


/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface NodeDataGetter<T extends DataReviewIdable> {
    IdableList<T> getData(@NonNull ReviewNode node);
}
