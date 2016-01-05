package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface IdableCollection<T extends HasReviewId> extends Collection<T> {
    T getItem(int position);
}
