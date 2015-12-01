package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface IdableCollection<T extends DataReviewIdable> extends Collection<T> {
    T getItem(int position);
}
