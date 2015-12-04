package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Implementation.DialogGvDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEdit<T extends GvData> extends ReviewDataEditorActionBasic<T> implements
        GridItemAction<T>,
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataEdit.EditListener<T>,
        ActivityResultListener {

    private final LaunchableConfig mConfig;
    private int mAlertDialogRequestCode;
    private final LaunchableUiLauncher mLaunchableFactory;
    private final GvDataPacker<T> mDataPacker;

    public GridItemDataEdit(LaunchableConfig editorConfig,
                            LaunchableUiLauncher launchableFactory,
                            GvDataPacker<T> dataPacker) {
        mConfig = editorConfig;
        mLaunchableFactory = launchableFactory;
        mDataPacker = dataPacker;
    }

    public int getAlertRequestCode() {
        return mAlertDialogRequestCode;
    }

    public int getLaunchableRequestCode() {
        return mConfig.getRequestCode();
    }

    //protected methods
    protected LaunchableUiLauncher getLaunchableFactory() {
        return mLaunchableFactory;
    }

    protected T unpackItem(GvDataPacker.CurrentNewDatum currentNew, Bundle args) {
        return mDataPacker.unpack(currentNew, args);
    }

    protected void editData(T oldDatum, T newDatum) {
        getEditor().replace(oldDatum, newDatum);
    }

    protected void deleteData(T datum) {
        getEditor().delete(datum);
    }

    protected void showAlertDialog(String alert, int requestCode, T item) {
        mAlertDialogRequestCode = requestCode;
        Bundle args = new Bundle();
        if (item != null) mDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
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
    public void onGridItemClick(T item, int position, View v) {
        Bundle args = new Bundle();
        mDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
        launch(mConfig, args);
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        onGridItemClick(item, position, v);
    }

    @Override
    public void onDelete(T data, int requestCode) {
        if(requestCode == getLaunchableRequestCode()) deleteData(data);
    }

    @Override
    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        if(requestCode == getLaunchableRequestCode()) editData(oldDatum, newDatum);
    }

    //For location and URL edit activities
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getLaunchableRequestCode() && data != null) {
            T oldDatum = mDataPacker.unpack(GvDataPacker.CurrentNewDatum.CURRENT, data);
            if (ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                T newDatum = mDataPacker.unpack(GvDataPacker.CurrentNewDatum.NEW, data);
                onEdit(oldDatum, newDatum, requestCode);
            } else if (ActivityResultCode.get(resultCode) == ActivityResultCode.DELETE) {
                onDelete(oldDatum, requestCode);
            }
        }
    }

    protected void launch(LaunchableConfig config, Bundle args) {
        mLaunchableFactory.launch(config, getActivity(), args);
    }
}
