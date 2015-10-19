package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonEdit<T extends GvData>
        extends ReviewViewAction.BannerButtonAction {
    private static final String TAG = "ActionBannerButtonAddListener";

    private GvDataType<T> mDataType;
    private final ConfigGvDataUi.LaunchableConfig mConfig;
    private Fragment mListener;
    private GvDataList<T> mAdded;
    private ReviewDataEditor<T> mEditor;

    protected BannerButtonEdit(GvDataType<T> dataType, String title) {
        super(title);
        mDataType = dataType;
        mConfig = ConfigGvDataUi.getConfig(mDataType).getAdderConfig();
        setListener(new AddListener() {
        });
    }

    //protected methods
    protected Fragment getListener() {
        return mListener;
    }

    protected void setListener(Fragment listener) {
        mListener = listener;
        super.registerActionListener(listener, TAG);
    }

    protected int getRequestCode() {
        return mConfig.getRequestCode();
    }

    //TODO make type safe
    protected boolean addData(T data) {
        return mEditor.add(data);
    }

    protected void showAlertDialog(String alert, int requestCode) {
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, new Bundle());
        DialogShower.show(dialog, getListener(), requestCode, DialogAlertFragment.ALERT_TAG);
    }

    protected void onDialogAlertNegative(int requestCode) {

    }

    protected void onDialogAlertPositive(int requestCode) {

    }

    private void initDataList() {
        mAdded = FactoryGvData.newDataList(mDataType);
    }

    //Overridden
    @Override
    public void onClick(View v) {
        LauncherUi.launch(mConfig.getLaunchable(), getListener(), getRequestCode(),
                mConfig.getTag(), new Bundle());
    }

    @Override
    public void onAttachReviewView() {
        mEditor = ReviewDataEditor.cast(getReviewView(), mDataType);
    }

    // Dialogs expected to communicate directly with target fragments so using "invisible"
    // fragment as listener.
    //Restrictions on how fragments are constructed mean I have to use an abstract class...
    protected abstract class AddListener extends Fragment
            implements DialogGvDataAdd.GvDataAddListener<T>,
            DialogAlertFragment.DialogAlertListener {

        private AddListener() {
            initDataList();
        }

        //Overridden
        @Override
        public boolean onGvDataAdd(T data) {
            boolean success = addData(data);
            if (success) mAdded.add(data);
            return success;
        }

        @Override
        public void onGvDataCancel() {
            for (T added : mAdded) {
                mEditor.delete(added);
            }
            initDataList();
        }

        @Override
        public void onGvDataDone() {
            initDataList();
        }

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {
            onDialogAlertNegative(requestCode);
        }

        //For location and URL add activities
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == getRequestCode() && data != null
                    && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                T datum = (T)GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data);
                onGvDataAdd(datum);
            }
        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            onDialogAlertPositive(requestCode);
        }
    }
}
