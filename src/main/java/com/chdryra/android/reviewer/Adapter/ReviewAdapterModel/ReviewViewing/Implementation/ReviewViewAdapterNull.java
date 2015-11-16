package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewAdapterNull implements ReviewViewAdapter {
    @Override
    public void attachReviewView(ReviewView view) {

    }

    @Override
    public ReviewView getReviewView() {
        return null;
    }

    @Override
    public String getSubject() {
        return "";
    }

    @Override
    public float getRating() {
        return 0f;
    }

    @Override
    public GvImageList getCovers() {
        return new GvImageList();
    }

    @Override
    public GvDataList getGridData() {
        return new GvList();
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return false;
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        return this;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return this;
    }

    @Override
    public void registerGridDataObserver(GridDataObserver observer) {

    }

    @Override
    public void unregisterGridDataObserver(GridDataObserver observer) {

    }

    @Override
    public void notifyGridDataObservers() {

    }
}
