/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditImagesGridItem extends EditScreenGridItem {
    private static final int    IMAGE_AS_COVER = 200;
    private GvImageList.GvImage mCoverProposition;

    public EditImagesGridItem() {
        super(ConfigGvDataUi.getConfig(GvImageList.TYPE).getEditorConfig());
        setListener(new EditImageListener() {
        });
    }

    @Override
    public void onGridItemLongClick(GvDataList.GvData item, View v) {
        if (getReviewView() == null) return;

        GvImageList.GvImage image = (GvImageList.GvImage) item;
        if (image.isCover()) {
            onGridItemClick(item, v);
            return;
        }

        mCoverProposition = image;
        String alert = getActivity().getString(R.string.dialog_set_image_as_background);
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, new Bundle());
        DialogShower.show(dialog, getListener(), IMAGE_AS_COVER, DialogAlertFragment.ALERT_TAG);
    }

    private abstract class EditImageListener extends EditListener implements DialogAlertFragment
            .DialogAlertListener {

        @Override
        public void onGvDataDelete(GvDataList.GvData data) {
            super.onGvDataDelete(data);
            GvImageList.GvImage image = (GvImageList.GvImage) data;
            if (image.isCover()) {
                image.setIsCover(false);
                getReviewView().updateCover();
            }
        }

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == IMAGE_AS_COVER) {
                getEditor().proposeCover(mCoverProposition);
                getReviewView().updateUi();
            }
        }
    }
}
