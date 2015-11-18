package com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.Implementation.GridItemActionNone;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
@SuppressWarnings("EmptyMethod")
public class GridItemEdit<T extends GvData> extends GridItemActionNone implements
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataEdit.GvDataEditListener<T>,
        ActivityResultListener {
    private GvDataType<T> mDataType;
    private final LaunchableConfig mConfig;
    private ReviewDataEditor<T> mEditor;
    private int mAlertDialogRequestCode;

    public GridItemEdit(GvDataType<T> dataType) {
        mDataType = dataType;
        mConfig = ApplicationInstance.ConfigGvDataUi.getConfig(mDataType).getEditorConfig();
    }

    public int getAlertRequestCode() {
        return mAlertDialogRequestCode;
    }

    public int getLaunchableRequestCode() {
        return mConfig.getRequestCode();
    }

    //protected methods
    protected ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    protected void editData(T oldDatum, T newDatum) {
        mEditor.replace(oldDatum, newDatum);
    }

    protected void deleteData(T datum) {
        mEditor.delete(datum);
    }

    protected void showAlertDialog(String alert, int requestCode, GvData item) {
        mAlertDialogRequestCode = requestCode;
        Bundle args = new Bundle();
        if (item != null) GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
        DialogShower.showAlert(alert, getActivity(), requestCode, DialogAlertFragment.ALERT_TAG);
    }

    //Overridden
    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {

    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mEditor = (ReviewDataEditor<T>) getReviewView();
    }

    @Override
    public void onGridItemClick(GvData item, int position, View v) {
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);

        LauncherUi.launch(mConfig.getLaunchable(), getActivity(), getLaunchableRequestCode(),
                mConfig.getTag(), args);
    }

    @Override
    public void onGvDataDelete(T data, int requestCode) {
        if(requestCode == getLaunchableRequestCode()) deleteData(data);
    }

    @Override
    public void onGvDataEdit(T oldDatum, T newDatum, int requestCode) {
        if(requestCode == getLaunchableRequestCode()) editData(oldDatum, newDatum);
    }

    //For location and URL edit activities
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getLaunchableRequestCode() && data != null) {
            T oldDatum = (T)GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, data);
            if (ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                T newDatum = (T)GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data);
                onGvDataEdit(oldDatum, newDatum, requestCode);
            } else if (ActivityResultCode.get(resultCode) == ActivityResultCode.DELETE) {
                onGvDataDelete(oldDatum, requestCode);
            }
        }
    }
}
