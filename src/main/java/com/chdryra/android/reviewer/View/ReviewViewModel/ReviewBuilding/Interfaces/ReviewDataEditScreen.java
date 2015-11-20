package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDataEditScreen<T extends GvData> extends
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataAdd.GvDataAddListener<T>,
        DialogGvDataEdit.GvDataEditListener<T>,
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
    void onGvDataDelete(T data, int requestCode);

    @Override
    void onGvDataEdit(T oldDatum, T newDatum, int requestCode);
}
