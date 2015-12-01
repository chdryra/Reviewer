package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CriteriaDb extends AbstractCollection<CriterionDb> implements IdableList<CriterionDb> {
    private String mReviewId;
    private ArrayList<CriterionDb> mCriteria;

    public CriteriaDb(String reviewId) {
        mReviewId = reviewId;
        mCriteria = new ArrayList<>();
    }

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public int size() {
        return mCriteria.size();
    }

    @Override
    public CriterionDb getItem(int position) {
        return mCriteria.get(position);
    }

    @Override
    public boolean add(CriterionDb datum) {
        return mCriteria.add(datum);
    }

    @Override
    public Iterator<CriterionDb> iterator() {
        return mCriteria.iterator();
    }
}
