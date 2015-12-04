package com.chdryra.android.reviewer.Model.Interfaces.TreeMethods;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VisitorReviewDataGetter<T extends DataReviewIdable> extends VisitorReviewNode{
    IdableList<T> getData();
}
