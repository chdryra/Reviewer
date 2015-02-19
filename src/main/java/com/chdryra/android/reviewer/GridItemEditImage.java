/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditImage extends GridItemEdit {
    private static final String TAG            = "GridItemEditImageListener";
    private static final int    IMAGE_AS_COVER = 200;
    private static final String DIALOG_TAG     = "DialogAlertTag";
    private GvImageList.GvImage mCoverProposition;
    private Fragment            mListener;

    public GridItemEditImage() {
        super(ConfigGvDataUi.getConfig(GvDataList.GvType.IMAGES).getEditorConfig());
        mListener = new EditImageListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onGridItemLongClick(GvDataList.GvData item, View v) {
        if (getViewReview() == null) return;

        GvImageList.GvImage image = (GvImageList.GvImage) item;
        if (image.isCover()) {
            onGridItemClick(item, v);
            return;
        }

        mCoverProposition = image;
        String alert = getActivity().getString(R.string.dialog_set_image_as_background);
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, new Bundle());
        DialogShower.show(dialog, mListener, IMAGE_AS_COVER, DIALOG_TAG);
    }

    private abstract class EditImageListener extends EditListener implements DialogAlertFragment
            .DialogAlertListener {

        @Override
        public void onGvDataDelete(GvDataList.GvData data) {
            super.onGvDataDelete(data);
            GvImageList.GvImage image = (GvImageList.GvImage) data;
            if (image.isCover()) getViewReview().updateCover();
        }

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == IMAGE_AS_COVER) {
                getViewReview().proposeCover(mCoverProposition);
                getViewReview().updateCover();
            }
        }
    }
}
