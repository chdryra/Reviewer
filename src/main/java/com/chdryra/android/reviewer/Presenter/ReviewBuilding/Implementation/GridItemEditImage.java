/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditImage extends GridItemEdit<GvImage> {
    private static final int IMAGE_AS_COVER = RequestCodeGenerator.getCode("ImageAsCover");

    public GridItemEditImage(LaunchableConfig editorConfig,
                             ParcelablePacker<GvImage> dataPacker) {
        super(editorConfig, dataPacker);
    }

    @Override
    public void onGridItemLongClick(GvImage item, int position, View v) {
        if (item.isCover()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlert(Strings.Alerts.SET_IMAGE_AS_BACKGROUND, IMAGE_AS_COVER, packItem(item));
        }
    }

    @Override
    protected void doAlertPositive(Bundle args) {
        GvImage newCover = unpackItem(args);
        if(newCover == null) return;

        ReviewDataEditor<GvImage> editor = getEditor();
        if (editor.getParams().manageCover()) {
            editor.getCover().setIsCover(false);
            newCover.setIsCover(true);
        }

        updateEditor();
    }

    @Override
    protected void onDataDeleted(GvImage datum) {
        if(datum.isCover()) {
            GvDataList<GvImage> images = getGridData();
            if(images.size() > 0) images.getItem(0).setIsCover(true);
        }
        updateEditor();
    }

    @Override
    protected void onUpdateEditor() {
        getEditor().updateCover();
    }
}
