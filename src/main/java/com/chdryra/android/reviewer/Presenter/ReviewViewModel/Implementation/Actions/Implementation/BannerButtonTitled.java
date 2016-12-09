/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonTitled<T extends GvData> extends BannerButtonCommandable<T> {
    private final ButtonTitler mTitler;

    public BannerButtonTitled() {
        mTitler = new ButtonTitler();
    }

    public BannerButtonTitled(String placeHolder) {
        super(placeHolder);
        mTitler = new ButtonTitler();
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        setTitle(getGridData().toString());
        getAdapter().registerObserver(mTitler);
    }

    @Override
    public void onDetachReviewView() {
        super.onDetachReviewView();
        getAdapter().unregisterObserver(mTitler);
    }

    private class ButtonTitler implements DataObservable.DataObserver {
        @Override
        public void onDataChanged() {
            setTitle(getGridData().toString());
        }
    }
}
