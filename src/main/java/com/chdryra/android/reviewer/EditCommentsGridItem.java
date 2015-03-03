/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditCommentsGridItem extends EditScreenGridItem {
    private static final int COMMENT_AS_HEADLINE = 200;
    private GvCommentList.GvComment mHeadlineProposition;

    public EditCommentsGridItem() {
        super(ConfigGvDataUi.getConfig(GvCommentList.TYPE).getEditorConfig());
        setListener(new EditCommentListener() {
        });
    }

    @Override
    public void onGridItemClick(GvDataList.GvData item, View v) {
        super.onGridItemClick(((GvCommentList.GvComment) item).getUnSplitComment(), v);
    }

    @Override
    public void onGridItemLongClick(GvDataList.GvData item, View v) {
        if (getReviewView() == null) return;

        GvCommentList.GvComment comment = (GvCommentList.GvComment) item;
        if (comment.isHeadline()) {
            onGridItemClick(item, v);
            return;
        }

        mHeadlineProposition = comment;
        String alert = getActivity().getString(R.string.dialog_set_comment_as_headline);
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, new Bundle());
        DialogShower.show(dialog, getListener(), COMMENT_AS_HEADLINE,
                DialogAlertFragment.ALERT_TAG);
    }

    private void setHeadline() {
        for (GvCommentList.GvComment comment : (GvCommentList) getGridData()) {
            if (comment == mHeadlineProposition) {
                comment.setIsHeadline(true);
            } else {
                comment.setIsHeadline(false);
            }
        }
    }

    private abstract class EditCommentListener extends EditListener implements DialogAlertFragment
            .DialogAlertListener {

        @Override
        public void onGvDataDelete(GvDataList.GvData data) {
            super.onGvDataDelete(data);
            GvCommentList.GvComment comment = (GvCommentList.GvComment) data;
            if (comment.isHeadline()) {
                comment.setIsHeadline(false);
                GvCommentList comments = (GvCommentList) getGridData();
                if (comments.getHeadlines().size() == 0 && comments.size() > 0) {
                    comments.getItem(0).setIsHeadline(true);
                }
            }
        }

        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == COMMENT_AS_HEADLINE) {
                setHeadline();
                getReviewView().updateUi();
            }
        }
    }
}
