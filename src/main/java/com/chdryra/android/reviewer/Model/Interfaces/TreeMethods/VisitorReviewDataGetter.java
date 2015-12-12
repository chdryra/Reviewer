package com.chdryra.android.reviewer.Model.Interfaces.TreeMethods;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableItems;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VisitorReviewDataGetter<T extends HasReviewId> extends VisitorReviewNode{
    IdableItems<T> getData();
}
