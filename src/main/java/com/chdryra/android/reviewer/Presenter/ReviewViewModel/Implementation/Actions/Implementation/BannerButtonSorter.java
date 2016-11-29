/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import android.view.View;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.NamedComparator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BannerButtonSorter<T extends GvData> extends BannerButtonActionNone<T> {
    private final ComparatorCollection<? super T> mComparators;
    private NamedComparator<? super T> mCurrentComparator;

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
        sort(mComparators.next());
    }

    private void sort(NamedComparator<? super T> comparator) {
        mCurrentComparator = comparator;
        getAdapter().sort(comparator);
        setTitle(comparator.getName());
    }
}
