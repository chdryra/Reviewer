/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.startouch.test.TestUtils;

import android.app.Fragment;

import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataEditListener;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditListener<T extends GvData> extends Fragment implements
        DataEditListener<T> {

    private T mDataOld;
    private T mDataNew;

    //public methods
    public T getDataOld() {
        return mDataOld;
    }

    public T getDataNew() {
        return mDataNew;
    }

    //Overridden
    @Override
    public void onDelete(T data, int requestCode) {
        mDataOld = data;
    }

    @Override
    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        mDataOld = oldDatum;
        mDataNew = newDatum;
    }
}
