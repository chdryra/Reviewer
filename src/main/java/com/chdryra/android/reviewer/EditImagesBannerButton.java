/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditImagesBannerButton extends EditScreenBannerButton {
    private static final String TAG = "BannerButtonAddImageListener";

    private ImageChooser mImageChooser;
    private Fragment     mListener;

    public EditImagesBannerButton(String title) {
        super(ConfigGvDataUi.getConfig(GvDataList.GvType.IMAGES).getAdderConfig(), title);
        mListener = new AddImageListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mImageChooser = Administrator.getImageChooser(getActivity());
    }

    @Override
    public void onClick(View v) {
        mListener.startActivityForResult(mImageChooser.getChooserIntents(), getRequestCode());
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
