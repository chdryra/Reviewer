package com.chdryra.android.reviewer.View.AndroidViews.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Implementation.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Implementation.DialogGvDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryEditActions;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryEditScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditData<T extends GvData> extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataEdit.EditListener<T>,
        DialogGvDataAdd.GvDataAddListener<T>{

    private static final String GVDATA_TYPE
            = "com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditData.gvdata_type";
    private GvDataType<T> mDataType;
    private ReviewDataEditScreen<T> mScreen;

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
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        mScreen = newEditScreen(app);
        return mScreen.getEditor();
    }

    private ReviewDataEditScreen<T> newEditScreen(ApplicationInstance app) {
        ReviewBuilderAdapter parentBuilder = app.getReviewBuilderAdapter();

        FactoryEditActions actionsFactory = newActionsFactory(app, parentBuilder);
        FactoryReviewDataEditor editorFactory = newEditorFactory(app.getParamsFactory(), actionsFactory);

        return new FactoryEditScreen(this, parentBuilder, editorFactory).newScreen(mDataType);
    }

    @NonNull
    private FactoryReviewDataEditor newEditorFactory(FactoryReviewViewParams paramsFactory,
                                                     FactoryEditActions actionsFactory) {
        return new FactoryReviewDataEditor(paramsFactory, actionsFactory);
    }

    @NonNull
    private FactoryEditActions newActionsFactory(ApplicationInstance app, ReviewBuilderAdapter
            parentBuilder) {
        return new FactoryEditActions(this, app.getConfigDataUi(),
                    app.getUiLauncher(), app.getGvDataFactory(), parentBuilder.getImageChooser());
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
    public void onDelete(T data, int requestCode) {
        mScreen.onDelete(data, requestCode);
    }

    @Override
    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        mScreen.onEdit(oldDatum, newDatum, requestCode);
    }

    @Override
    public boolean onGvDataAdd(T data, int requestCode) {
        return mScreen.onGvDataAdd(data, requestCode);
    }

    @Override
    public void onGvDataCancel(int requestCode) {
        mScreen.onGvDataCancel(requestCode);
    }

    @Override
    public void onGvDataDone(int requestCode) {
        mScreen.onGvDataDone(requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mScreen.onActivityResult(requestCode, resultCode, data);
    }
}
