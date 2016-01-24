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

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class GridItemDataEditComment extends GridItemDataEdit<GvComment> {
    private static final int COMMENT_AS_HEADLINE
            = RequestCodeGenerator.getCode("CommentAsHeadline");

    //Constructors
    public GridItemDataEditComment(LaunchableConfig editorConfig,
                                   LaunchableUiLauncher launchableFactory,
                                   GvDataPacker<GvComment> dataPacker) {
        super(editorConfig, launchableFactory, dataPacker);
    }

    //Overridden
    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == COMMENT_AS_HEADLINE) {
            GvComment headline = unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
            GvCommentList comments = (GvCommentList) getGridData();
            for (GvComment comment : comments) {
                if (comment == headline) {
                    comment.setIsHeadline(true);
                } else {
                    comment.setIsHeadline(false);
                }
            }
            getReviewView().notifyObservers();
        }
    }

    @Override
    public void onGridItemClick(GvComment item, int position, View v) {
        GvComment unsplit = item.getUnsplitComment();
        super.onGridItemClick(unsplit, position, v);
    }

    @Override
    public void onGridItemLongClick(GvComment item, int position, View v) {
        if (item.isHeadline()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlertDialog(getActivity().getString(R.string.alert_set_comment_as_headline),
                    COMMENT_AS_HEADLINE, item);
        }
    }
}
