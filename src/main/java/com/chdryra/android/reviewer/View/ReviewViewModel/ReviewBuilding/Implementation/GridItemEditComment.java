package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class GridItemEditComment extends GridItemEdit<GvCommentList.GvComment> {
    private static final int COMMENT_AS_HEADLINE
            = RequestCodeGenerator.getCode("CommentAsHeadline");

    //Constructors
    public GridItemEditComment(LaunchableConfig<GvCommentList.GvComment> editorConfig,
                               FactoryLaunchableUi launchableFactory,
                               GvDataPacker<GvCommentList.GvComment> dataPacker) {
        super(editorConfig, launchableFactory, dataPacker);
    }

    //Overridden
    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == COMMENT_AS_HEADLINE) {
            GvCommentList.GvComment headline = unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
            GvCommentList comments = (GvCommentList) getGridData();
            for (GvCommentList.GvComment comment : comments) {
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
    public void onGridItemClick(GvCommentList.GvComment item, int position, View v) {
        GvCommentList.GvComment unsplit = item.getUnsplitComment();
        super.onGridItemClick(unsplit, position, v);
    }

    @Override
    public void onGridItemLongClick(GvCommentList.GvComment item, int position, View v) {
        if (item.isHeadline()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlertDialog(getActivity().getString(R.string.alert_set_comment_as_headline),
                    COMMENT_AS_HEADLINE, item);
        }
    }
}
