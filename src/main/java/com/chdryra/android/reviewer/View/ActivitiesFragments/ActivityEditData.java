package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Screens.EditScreenImages;
import com.chdryra.android.reviewer.View.Screens.EditScreenReviewData;
import com.chdryra.android.reviewer.View.Screens.FactoryEditScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditData<T extends GvData> extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataEdit.GvDataEditListener<T>,
        DialogGvDataAdd.GvDataAddListener<T>, ImageChooser.ImageChooserListener{

    private static final String GVDATA_TYPE
            = "com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditData.gvdata_type";
    private GvDataType<T> mDataType;
    private EditScreenReviewData<T> mScreen;

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
        ReviewBuilderAdapter builder = Administrator.getInstance(this).getReviewBuilderAdapter();
        FactoryEditScreen factory = new FactoryEditScreen(this, builder);
        mScreen = factory.newScreen(mDataType);
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
    public void onGvDataDelete(T data, int requestCode) {
        mScreen.onGvDataDelete(data, requestCode);
    }

    @Override
    public void onGvDataEdit(T oldDatum, T newDatum, int requestCode) {
        mScreen.onGvDataEdit(oldDatum, newDatum, requestCode);
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

    @Override
    public void onChosenImage(GvImageList.GvImage image) {
        if(mDataType.equals(GvImageList.GvImage.TYPE)) {
            EditScreenImages screen = (EditScreenImages) mScreen;
            screen.onChosenImage(image);
        }
    }
}
