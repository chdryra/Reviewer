package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CriteriaDb implements IdableList<CriterionDb> {
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
    public void add(CriterionDb datum) {
        mCriteria.add(datum);
    }

    @Override
    public void add(IdableCollection<CriterionDb> data) {
        for(CriterionDb datum : data) {
            add(datum);
        }
    }

    @Override
    public Iterator<CriterionDb> iterator() {
        return mCriteria.iterator();
    }
}
