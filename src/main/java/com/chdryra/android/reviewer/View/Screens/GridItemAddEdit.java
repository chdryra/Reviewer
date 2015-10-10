package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
@SuppressWarnings("EmptyMethod")
public class GridItemAddEdit<T extends GvData> extends ReviewViewAction.GridItemAction {
    private static final String TAG = "GridItemEditListener";

    private GvDataType<T> mDataType;
    private final ConfigGvDataUi.LaunchableConfig mConfig;
    private Fragment mListener;
    private ReviewDataEditor<T> mEditor;

    protected GridItemAddEdit(GvDataType<T> dataType) {
        mDataType = dataType;
        mConfig = ConfigGvDataUi.getConfig(mDataType).getEditorConfig();
        setListener(new EditListener() {
        });
    }

    protected ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    //protected methods
    protected Fragment getListener() {
        return mListener;
    }

    protected void setListener(Fragment listener) {
        mListener = listener;
        super.registerActionListener(listener, TAG);
    }

    protected int getLaunchableRequestCode() {
        return mConfig.getRequestCode();
    }

    protected void editData(T oldDatum, T newDatum) {
        mEditor.replace(oldDatum, newDatum);
    }

    protected void deleteData(T datum) {
        mEditor.delete(datum);
    }

    protected void showAlertDialog(String alert, int requestCode, GvData item) {
        Bundle args = new Bundle();
        if (item != null) {
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
        }
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
        DialogShower.show(dialog, getListener(), requestCode, DialogAlertFragment.ALERT_TAG);
    }

    protected void onDialogAlertNegative(int requestCode, Bundle args) {

    }

    protected void onDialogAlertPositive(int requestCode, Bundle args) {

    }

    //Overridden
    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mEditor = ReviewDataEditor.cast(getReviewView(), mDataType);
    }

    @Override
    public void onGridItemClick(GvData item, int position, View v) {
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);

        LauncherUi.launch(mConfig.getLaunchable(), mListener, getLaunchableRequestCode(),
                mConfig.getTag(), args);
    }

    protected abstract class EditListener extends Fragment
            implements DialogGvDataEdit.GvDataEditListener<T>,
            DialogAlertFragment.DialogAlertListener {

        //Overridden
        @Override
        public void onGvDataDelete(T data) {
            deleteData(data);
        }

        @Override
        public void onGvDataEdit(T oldDatum, T newDatum) {
            editData(oldDatum, newDatum);
        }

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {
            onDialogAlertNegative(requestCode, args);
        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            onDialogAlertPositive(requestCode, args);
        }

        //For location and URL edit activities
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == getLaunchableRequestCode() && data != null) {
                T oldDatum = (T)GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, data);
                if (ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                    T newDatum = (T)GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data);
                    onGvDataEdit(oldDatum, newDatum);
                } else if (ActivityResultCode.get(resultCode) == ActivityResultCode.DELETE) {
                    onGvDataDelete(oldDatum);
                }
            }
        }
    }
}
