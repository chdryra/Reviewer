/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.View;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AsyncSortable<T> {
    interface OnSortedCallback {
        void onSorted(CallbackMessage message);
    }

    void sort(Comparator<? super T> comparator, OnSortedCallback callback);
}
