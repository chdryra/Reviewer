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
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryEditActions;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataEditPresenter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataAddListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataEditListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.PresenterReviewDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;


/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditData<T extends GvData> extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DataEditListener<T>,
        DataAddListener<T> {

    private static final String GVDATA_TYPE
            = "com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditData.gvdata_type";
    private GvDataType<T> mDataType;
    private PresenterReviewDataEdit<T> mPresenter;

    public ActivityEditData() {

    }

    private ActivityEditData(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    public static <T extends GvData> void start(Activity launcher, GvDataType<T> dataType) {
        //Because activity is typed and want class info at runtime to start activity
        ActivityEditData<T> dummy = new ActivityEditData<>(dataType);
        Intent i = new Intent(launcher, dummy.getClass());
        i.putExtra(GVDATA_TYPE, dataType);
        launcher.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDataType = getIntent().getParcelableExtra(GVDATA_TYPE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ReviewView createReviewView() {
        mPresenter = newPresenterFactory().newPresenter(mDataType);

        return mPresenter.getEditor();
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

    @NonNull
    private FactoryDataEditPresenter newPresenterFactory() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        ReviewBuilderAdapter<?> parentBuilder = app.getReviewBuilderAdapter();

        FactoryEditActions actionsFactory
                = new FactoryEditActions(this, app.getConfigDataUi(), app.getUiLauncher(),
                app.getGvDataFactory(), parentBuilder.getImageChooser());

        FactoryReviewDataEditor editorFactory
                = new FactoryReviewDataEditor(app.getParamsFactory(), actionsFactory);

        return new FactoryDataEditPresenter(this, parentBuilder, editorFactory);
    }
}
