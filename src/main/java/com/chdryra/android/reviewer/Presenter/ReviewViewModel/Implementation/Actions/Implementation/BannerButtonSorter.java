/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import android.view.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.NamedComparator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.AsyncSortable;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BannerButtonSorter<T extends GvData> extends BannerButtonActionNone<T> {
    private final ComparatorCollection<? super T> mComparators;
    private NamedComparator<? super T> mCurrentComparator;
    private boolean mLocked = false;

    public BannerButtonSorter(ComparatorCollection<? super T> comparators) {
        mComparators = comparators;
        mCurrentComparator = mComparators.next();
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        sort(mCurrentComparator);
    }

    @Override
    public void onClick(View v) {
        if(!mLocked) sort(mComparators.next());
    }

    private void sort(final NamedComparator<? super T> comparator) {
        mLocked = true;
        setTitle(Strings.Buttons.SORTING);
        getAdapter().sort(comparator, new AsyncSortable.OnSortedCallback() {
            @Override
            public void onSorted(CallbackMessage message) {
                if(!message.isError()){
                    mCurrentComparator = comparator;
                } else {
                    getCurrentScreen().showToast("Problems sorting: " + message.getMessage());
                }
                setTitle(mCurrentComparator.getName());
                mLocked = false;
            }
        });
    }
}
