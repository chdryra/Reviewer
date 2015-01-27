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

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActionGridItemEditImage extends ActionGridItemEdit {
    private static final int    IMAGE_AS_COVER = 200;
    private static final String DIALOG_TAG     = "DialogAlertTag";
    private GvImageList.GvImage mCoverProposition;

    public ActionGridItemEditImage(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.IMAGES);
    }

    @Override
    public void onSetReviewView() {
        super.onSetReviewView();
        getReviewView().setGridCellDimension(ReviewView.CellDimension.HALF, ReviewView
                .CellDimension.HALF);
    }

    protected Fragment getNewListener() {
        return new EditImageListener() {

        };
    }

    @Override
    public void onGridItemLongClick(GvDataList.GvData item) {
        GvImageList.GvImage image = (GvImageList.GvImage) item;
        if (image.isCover()) {
            onGridItemClick(item);
            return;
        }

        mCoverProposition = image;
        String alert = getActivity().getString(R.string.dialog_set_image_as_background);
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, new Bundle());
        DialogShower.show(dialog, getListener(), IMAGE_AS_COVER, DIALOG_TAG);
    }

    private void setNewCover() {
        GvImageList images = (GvImageList) getData();
        GvImageList.GvImage cover = images.getRandomCover();
        if (!cover.isValidForDisplay() && images.size() > 0) {
            cover = images.getItem(0);
            cover.setIsCover(true);
        }
        getReviewView().setCover(cover);
    }

    private abstract class EditImageListener extends EditListener implements DialogAlertFragment
            .DialogAlertListener {

        @Override
        public void onGvDataDelete(GvDataList.GvData data) {
            GvImageList.GvImage image = (GvImageList.GvImage) data;
            super.onGvDataDelete(data);
            if (image.isCover()) setNewCover();
        }

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == IMAGE_AS_COVER) {
                ReviewView view = getReviewView();
                view.getCover().setIsCover(false);
                mCoverProposition.setIsCover(true);
                view.setCover(mCoverProposition);
            }
        }
    }
}
