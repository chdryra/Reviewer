/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActionFilter<T extends GvData> extends ReviewViewActionBasic<T> {
    @Nullable
    protected ReviewViewAdapter.Filterable<T> getFilterAdapter() {
        try {
            return (ReviewViewAdapter.Filterable<T>) getAdapter();
        } catch (Exception e) {
            return null;
        }
    }

    protected void doFiltering(CharSequence s) {
        ReviewViewAdapter.Filterable<T> adapter = getFilterAdapter();
        if (adapter != null) adapter.filterGrid(s.toString());
    }
}
