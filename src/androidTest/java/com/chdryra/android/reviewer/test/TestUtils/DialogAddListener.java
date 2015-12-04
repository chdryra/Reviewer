/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.app.Fragment;

import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Implementation.DialogGvDataAdd;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddListener<T extends GvData> extends Fragment implements
        DialogGvDataAdd.GvDataAddListener<T> {

    private T mAddData;
    private T mDoneData;

    //public methods
    public T getAddData() {
        return mAddData;
    }

    public T getDoneData() {
        return mDoneData;
    }

    public void reset() {
        mAddData = null;
        mDoneData = null;
    }

    //Overridden
    @Override
    public boolean onGvDataAdd(T data, int requestCode) {
        if (data != null) {
            mAddData = data;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onGvDataCancel(int requestCode) {
        mAddData = null;
        mDoneData = null;
    }

    @Override
    public void onGvDataDone(int requestCode) {
        mDoneData = mAddData;
    }
}
