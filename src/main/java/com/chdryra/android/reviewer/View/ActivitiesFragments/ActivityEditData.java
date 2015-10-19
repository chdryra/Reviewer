package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Screens.EditScreenReviewData;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditData<T extends GvData> extends ActivityReviewView
        implements DialogAlertFragment.DialogAlertListener, DialogGvDataEdit.GvDataEditListener<T> {
    private GvDataType<T> mDataType;
    private EditScreenReviewData<T> mScreen;

    public ActivityEditData(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    protected ReviewView createView() {
        mScreen = EditScreenReviewData.newScreen(this, mDataType);
        return mScreen.getEditor();
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mScreen.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mScreen.onAlertPositive(requestCode, args);
    }

    @Override
    public void onGvDataDelete(T data) {

    }

    @Override
    public void onGvDataEdit(T oldDatum, T newDatum) {

    }
}
