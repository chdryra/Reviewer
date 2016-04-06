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

import com.chdryra.android.mygenerallibrary.Dialogs.DialogAlertFragment;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;


/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface PresenterReviewDataEdit<T extends GvData> extends
        DialogAlertFragment.DialogAlertListener,
        DataAddListener<T>,
        DataEditListener<T>,
        ActivityResultListener {

    ReviewDataEditor<T> getEditor();

    @Override
    void onActivityResult(int requestCode, int resultCode, Intent data);

    @Override
    void onAlertNegative(int requestCode, Bundle args);

    @Override
    void onAlertPositive(int requestCode, Bundle args);

    @Override
    boolean onAdd(T data, int requestCode);

    @Override
    void onCancel(int requestCode);

    @Override
    void onDone(int requestCode);

    @Override
    void onDelete(T data, int requestCode);

    @Override
    void onEdit(T oldDatum, T newDatum, int requestCode);
}
