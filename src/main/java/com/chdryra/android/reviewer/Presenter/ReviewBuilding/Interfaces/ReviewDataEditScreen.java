/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogGvDataAdd;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogGvDataEdit;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDataEditScreen<T extends GvData> extends
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataAdd.GvDataAddListener<T>,
        DialogGvDataEdit.EditListener<T>,
        ActivityResultListener {

    ReviewDataEditor<T> getEditor();

    @Override
    void onActivityResult(int requestCode, int resultCode, Intent data);

    @Override
    void onAlertNegative(int requestCode, Bundle args);

    @Override
    void onAlertPositive(int requestCode, Bundle args);

    @Override
    boolean onGvDataAdd(T data, int requestCode);

    @Override
    void onGvDataCancel(int requestCode);

    @Override
    void onGvDataDone(int requestCode);

    @Override
    void onDelete(T data, int requestCode);

    @Override
    void onEdit(T oldDatum, T newDatum, int requestCode);
}
