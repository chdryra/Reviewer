/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;



import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenImages extends EditScreenReviewDataImpl<GvImageList.GvImage> implements
        ImageChooser.ImageChooserListener{
    private static final GvDataType<GvImageList.GvImage> TYPE = GvImageList.GvImage.TYPE;
    private BannerButtonAddImage mBannerButton;
    private ImageChooser mImageChooser;

    public EditScreenImages(Context context, ReviewBuilderAdapter builder, ImageChooser imageChooser, FactoryReviewDataEditor editorFactory) {
        super(context, builder, TYPE, editorFactory);
        mImageChooser = imageChooser;
    }

    @Override
    protected GridItemEdit<GvImageList.GvImage> newGridItemAction() {
        return new GridItemAddEditImage();
    }

    @Override
    protected BannerButtonEdit<GvImageList.GvImage> newBannerButtonAction() {
        mBannerButton = new BannerButtonAddImage(getBannerButtonTitle());
        return mBannerButton;
    }

    @Override
    public void onChosenImage(GvImageList.GvImage image) {
        mBannerButton.onChosenImage(image);
    }

    //Classes
    private class BannerButtonAddImage extends BannerButtonEdit<GvImageList.GvImage>
            implements ImageChooser.ImageChooserListener{

        //Constructors
        private BannerButtonAddImage(String title) {
            super(TYPE, title);
        }

        private void setCover() {
            GvImageList images = (GvImageList) getGridData();
            GvImageList.GvImage cover = images.getItem(0);
            cover.setIsCover(true);
            getReviewView().notifyObservers();
        }

        //Overridden
        @Override
        public void onClick(View v) {
            getActivity().startActivityForResult(mImageChooser.getChooserIntents(),
                    getLaunchableRequestCode());
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            boolean correctCode = requestCode == getLaunchableRequestCode();
            boolean isOk = ActivityResultCode.OK.equals(resultCode);
            boolean imageExists = mImageChooser.chosenImageExists(ActivityResultCode.get
                    (resultCode), data);

            if (correctCode && isOk && imageExists) mImageChooser.getChosenImage(this);
        }

        @Override
        public void onChosenImage(GvImageList.GvImage image) {
            if (getGridData().size() == 0) image.setIsCover(true);
            if (addData(image) && getGridData().size() == 1) setCover();
        }
    }

    private static class GridItemAddEditImage extends GridItemEdit<GvImageList.GvImage> {
        private static final int IMAGE_AS_COVER = RequestCodeGenerator.getCode("ImageAsCover");

        //Constructors
        private GridItemAddEditImage() {
            super(TYPE);
        }

        //Overridden
        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            GvImageList.GvImage image = (GvImageList.GvImage) item;
            if (image.isCover()) {
                super.onGridItemLongClick(item, position, v);
            } else {
                showAlertDialog(getActivity().getString(R.string.alert_set_image_as_background),
                        IMAGE_AS_COVER, image);
            }
        }

        @Override
        protected void deleteData(GvImageList.GvImage datum) {
            super.deleteData(datum);
            if (datum.isCover()) getReviewView().updateCover();
        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == IMAGE_AS_COVER) {
                GvImageList.GvImage cover = (GvImageList.GvImage) GvDataPacker.unpackItem
                        (GvDataPacker.CurrentNewDatum.CURRENT, args);
                proposeCover(cover);
            }
        }


        private void proposeCover(GvImageList.GvImage image) {
            ReviewDataEditor<GvImageList.GvImage> editor = getEditor();
            if (editor.getParams().manageCover()) {
                GvImageList.GvImage cover = editor.getCover();
                cover.setIsCover(false);
                image.setIsCover(true);
            }
            editor.notifyObservers();
        }
    }
}
