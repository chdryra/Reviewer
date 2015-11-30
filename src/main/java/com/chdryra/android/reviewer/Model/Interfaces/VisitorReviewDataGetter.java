package com.chdryra.android.reviewer.Model.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VisitorReviewDataGetter<T extends DataReviewIdable> extends VisitorReviewNode{

    IdableList<T> getData();
}
