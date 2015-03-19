/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenImages {
    public static class BannerButton extends EditScreen.BannerButton {
        private ImageChooser mImageChooser;

        public BannerButton(String title) {
            super(ConfigGvDataUi.getConfig(GvImageList.TYPE).getAdderConfig(), title);
            setListener(new AddImageListener() {
            });
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mImageChooser = Administrator.getImageChooser(getActivity());
        }

        @Override
        public void onClick(View v) {
            getListener().startActivityForResult(mImageChooser.getChooserIntents(),
                    getRequestCode());
        }

        private void setCover() {
            GvImageList images = (GvImageList) getGridData();
            GvImageList.GvImage cover = images.getItem(0);
            cover.setIsCover(true);
            getReviewView().updateUi();
        }

        private abstract class AddImageListener extends Fragment implements ImageChooser
                .ImageChooserListener {

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                boolean correctCode = requestCode == getRequestCode();
                boolean isOk = ActivityResultCode.OK.equals(resultCode);
                boolean imageExists = mImageChooser.chosenImageExists(ActivityResultCode.get
                        (resultCode), data);

                if (correctCode && isOk && imageExists) mImageChooser.getChosenImage(this);
            }

            @Override
            public void onImageChosen(GvImageList.GvImage image) {
                if (addData(image) && getGridData().size() == 1) setCover();
            }
        }
    }

    public static class GridItem extends EditScreen.GridItem {
        private static final int IMAGE_AS_COVER = 200;
        private GvImageList.GvImage mCoverProposition;

        public GridItem() {
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
}
