/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataEditListener;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEdit<T extends GvDataParcelable> extends LaunchAndAlertableAction<T> implements
        GridItemAction<T>, DataEditListener<T>, ActivityResultListener {
    private static final String TAG = "GridItemEdit:";

    private final ParcelablePacker<T> mDataPacker;

    public GridItemEdit(LaunchableConfig editorConfig, ParcelablePacker<T> dataPacker) {
        super(TAG, editorConfig);
        mDataPacker = dataPacker;
    }

    @Override
    public void onGridItemClick(T item, int position, View v) {
        launchDefaultConfig(packItem(item));
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        onGridItemClick(item, position, v);
    }

    @Override
    public void onDelete(T data, int requestCode) {
        if (requestCode == getLaunchableRequestCode()) deleteData(data);
    }

    @Override
    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        if (requestCode == getLaunchableRequestCode()) editData(oldDatum, newDatum);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getLaunchableRequestCode() && data != null) {
            T oldDatum = mDataPacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, data);
            if (ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                T newDatum = mDataPacker.unpack(ParcelablePacker.CurrentNewDatum.NEW, data);
                if (oldDatum != null && newDatum != null) editData(oldDatum, newDatum);
            } else if (oldDatum != null &&
                    ActivityResultCode.get(resultCode) == ActivityResultCode.DELETE) {
                deleteData(oldDatum);
            }
        }
    }

    void onDataDeleted(T datum) {

    }

    void updateEditor() {
        getGridData().setUnsorted();
        getEditor().update();
    }

    @Nullable
    T unpackItem(Bundle args) {
        return mDataPacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args);
    }

    @NonNull
    Bundle packItem(T item) {
        Bundle args = new Bundle();
        if (item != null)
            mDataPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, item, args);
        return args;
    }

    private void editData(T oldDatum, T newDatum) {
        getEditor().replace(oldDatum, newDatum);
    }

    private void deleteData(T datum) {
        getEditor().delete(datum);
        onDataDeleted(datum);
    }
}
