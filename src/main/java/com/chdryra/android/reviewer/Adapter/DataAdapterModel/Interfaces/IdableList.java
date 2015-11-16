package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface IdableList<T extends DataReview> extends IdableCollection<T>, DataReview{
    @Override
    String getReviewId();
}
