/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 March, 2015
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 13/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditLocationsGridItem extends EditScreenGridItem {
    private static final int EDIT_ON_MAP = 200;
    private GvLocationList.GvLocation mMapLocation;

    public EditLocationsGridItem() {
        super(ConfigGvDataUi.getConfig(GvLocationList.TYPE).getEditorConfig());
        setListener(new EditLocationListener() {
        });
    }

    @Override
    public void onGridItemLongClick(GvDataList.GvData item, View v) {
        if (getReviewView() == null) return;

        mMapLocation = (GvLocationList.GvLocation) item;
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, mMapLocation, args);

        String alert = getActivity().getString(R.string.dialog_edit_on_map);
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
        DialogShower.show(dialog, getListener(), EDIT_ON_MAP, DialogAlertFragment.ALERT_TAG);
    }

    private abstract class EditLocationListener extends EditListener implements DialogAlertFragment
            .DialogAlertListener {

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == EDIT_ON_MAP) {
                LaunchableUi mapUi = ConfigGvDataUi.getLaunchable(ActivityEditLocationMap.class);
                LauncherUi.launch(mapUi, this, EDIT_ON_MAP, null, args);
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == EDIT_ON_MAP) {
                if (ActivityResultCode.get(resultCode).equals(ActivityResultCode.DONE)) {
                    editData(mMapLocation, GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum
                            .NEW, data));
                } else if (ActivityResultCode.get(resultCode).equals(ActivityResultCode.DELETE)) {
                    deleteData(mMapLocation);
                }
            }
        }
    }
}

