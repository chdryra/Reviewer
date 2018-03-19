/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditComment extends GridItemEdit<GvComment> {
    private static final int COMMENT_AS_HEADLINE
            = RequestCodeGenerator.getCode("CommentAsHeadline");

    public GridItemEditComment(LaunchableConfig editorConfig,
                               ParcelablePacker<GvComment> dataPacker) {
        super(editorConfig, dataPacker);
    }

    @Override
    public void onGridItemClick(GvComment item, int position, View v) {
        super.onGridItemClick(item.getUnsplitComment(), position, v);
    }

    @Override
    public void onGridItemLongClick(GvComment item, int position, View v) {
        if (item.isHeadline()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlert(Strings.Alerts.SET_COMMENT_AS_HEADLINE, COMMENT_AS_HEADLINE, packItem(item));
        }
    }

    @Override
    public void doAlertPositive(Bundle args) {
        GvComment headline = unpackItem(args);
        for (GvComment comment : getGridData()) {
            comment.setIsHeadline(comment == headline);
        }

        updateEditor();
    }

    @Override
    protected void onDataDeleted(GvComment datum) {
        if (datum.isHeadline()) {
            GvDataList<GvComment> comments = getGridData();
            if (comments.size() > 0) comments.get(0).setIsHeadline(true);
        }
        updateEditor();
    }
}
