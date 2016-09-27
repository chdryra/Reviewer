/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PresenterReviewDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataAddListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataEditListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;


/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditData<T extends GvDataParcelable> extends ActivityReviewView implements
        AlertListener,
        DataEditListener<T>,
        DataAddListener<T> {

    private static final String DATA_TYPE = TagKeyGenerator.getKey(ActivityEditData.class, "DataType");

    private GvDataType<T> mDataType;
    private PresenterReviewDataEdit<T> mPresenter;

    public ActivityEditData() {

    }

    private ActivityEditData(GvDataType<T> dataType) {

    }

    public static <T extends GvDataParcelable> void start(Activity launcher, GvDataType<T> dataType) {
        //Because activity is typed and want class info at runtime to start activity
        ActivityEditData<T> dummy = new ActivityEditData<>(dataType);
        Intent i = new Intent(launcher, dummy.getClass());
        i.putExtra(DATA_TYPE, dataType);
        launcher.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDataType = getIntent().getParcelableExtra(DATA_TYPE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = AndroidAppInstance.getInstance(this);
        ReviewEditor<?> editor = app.getReviewEditor();
        ReviewDataEditor<T> dataEditor = editor.newDataEditor(mDataType);
        mPresenter = new PresenterReviewDataEdit<>(dataEditor);
        return dataEditor;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mPresenter.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mPresenter.onAlertPositive(requestCode, args);
    }

    @Override
    public void onDelete(T data, int requestCode) {
        mPresenter.onDelete(data, requestCode);
    }

    @Override
    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        mPresenter.onEdit(oldDatum, newDatum, requestCode);
    }

    @Override
    public boolean onAdd(T data, int requestCode) {
        return mPresenter.onAdd(data, requestCode);
    }

    @Override
    public void onCancel(int requestCode) {
        mPresenter.onCancel(requestCode);
    }

    @Override
    public void onDone(int requestCode) {
        mPresenter.onDone(requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
