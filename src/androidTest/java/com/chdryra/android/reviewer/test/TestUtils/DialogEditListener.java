/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.app.Fragment;

import com.chdryra.android.reviewer.View.Dialogs.DialogEditGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditListener<T extends GvData> extends Fragment implements
        DialogEditGvData.GvDataEditListener<T> {

    private T mDataOld;
    private T mDataNew;

    @Override
    public void onGvDataDelete(T data) {
        mDataOld = data;
    }

    @Override
    public void onGvDataEdit(T oldDatum, T newDatum) {
        mDataOld = oldDatum;
        mDataNew = newDatum;
    }

    public T getDataOld() {
        return mDataOld;
    }

    public T getDataNew() {
        return mDataNew;
    }
}
