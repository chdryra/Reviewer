/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;


import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Provides a callback for when the add button is pressed
 */
public interface DataAddListener<T extends GvData> {
    boolean onAdd(T data, int requestCode);

    void onCancel(int requestCode);

    void onDone(int requestCode);
}
