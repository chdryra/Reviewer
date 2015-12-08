package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface IdableList<T extends DataReviewIdable> extends IdableCollection<T>, DataReviewIdable {
    @Override
    String getReviewId();
}