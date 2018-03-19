/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.GridItemActionNone;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemListenable<T extends GvData> extends GridItemActionNone<T>
        implements GridItemAction<T> {
    private final ArrayList<ClickListener<T>> mListeners;

    interface ClickListener<T extends GvData> {
        void onGridItemClick(T item, int position, View v);

        void onGridItemLongClick(T item, int position, View v);
    }

    public GridItemListenable() {
        mListeners = new ArrayList<>();
    }

    public void registerListener(ClickListener<T> listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(ClickListener<T> listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void onGridItemClick(T item, int position, View v) {
        for (ClickListener<T> listener : mListeners) {
            listener.onGridItemClick(item, position, v);
        }
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        for (ClickListener<T> listener : mListeners) {
            listener.onGridItemLongClick(item, position, v);
        }
    }
}
