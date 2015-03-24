/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.app.Fragment;

import com.chdryra.android.reviewer.DialogAddGvData;
import com.chdryra.android.reviewer.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddListener<T extends GvData> extends Fragment implements
        DialogAddGvData.GvDataAddListener<T> {

    private T mInterim;
    private T mData;

    @Override
    public boolean onGvDataAdd(T data) {
        if (data != null) {
            mInterim = data;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onGvDataCancel() {
        mData = null;
    }

    @Override
    public void onGvDataDone() {
        mData = mInterim;
    }

    public T getData() {
        return mData;
    }
}
