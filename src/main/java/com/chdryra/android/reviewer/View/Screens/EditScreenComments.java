/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenComments {
    private static final GvDataType<GvCommentList.GvComment> TYPE =
            GvCommentList.GvComment.TYPE;

    //Classes
    public static class GridItemAddEditComments extends GridItemAddEdit<GvCommentList.GvComment> {
        private static final int COMMENT_AS_HEADLINE = 200;

        //Constructors
        public GridItemAddEditComments() {
            super(TYPE);
        }

        //Overridden
        @Override
        protected void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == COMMENT_AS_HEADLINE) {
                GvCommentList.GvComment headline = (GvCommentList.GvComment) GvDataPacker
                        .unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
                GvCommentList comments = (GvCommentList) getGridData();
                for (GvCommentList.GvComment comment : comments) {
                    if (comment == headline) {
                        comment.setIsHeadline(true);
                    } else {
                        comment.setIsHeadline(false);
                    }
                }
                getReviewView().update();
            }
        }

        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            GvCommentList.GvComment unsplit = ((GvCommentList.GvComment) item).getUnsplitComment();
            super.onGridItemClick(unsplit, position, v);
        }

        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            GvCommentList.GvComment comment = (GvCommentList.GvComment) item;
            if (comment.isHeadline()) {
                super.onGridItemLongClick(item, position, v);
            } else {
                showAlertDialog(getActivity().getString(R.string.alert_set_comment_as_headline),
                        COMMENT_AS_HEADLINE, comment);
            }
        }
    }

    public static class MenuEditComment extends MenuDataEdit<GvCommentList.GvComment>
            implements GridDataObservable.GridDataObserver {
        public static final int MENU_DELETE_ID = R.id.menu_item_delete;
        public static final int MENU_DONE_ID = R.id.menu_item_done;
        public static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
        private static final int MENU = R.menu.menu_edit_comments;

        private final MaiSplitComments mSplitter;

        //Constructors
        public MenuEditComment() {
            super(TYPE, TYPE.getDataName(), TYPE.getDataName(), false, true, MENU);
            mSplitter = new MaiSplitComments(this);
        }

        //Overridden
        @Override
        public void onGridDataChanged() {
            mSplitter.updateGridDataUi();
        }

        @Override
        protected void addMenuItems() {
            bindDefaultDeleteActionItem(MENU_DELETE_ID);
            bindDefaultDoneActionItem(MENU_DONE_ID);
            bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            getReviewView().registerGridDataObserver(this);
        }

        @Override
        public void onUnattachReviewView() {
            getReviewView().unregisterGridDataObserver(this);
            super.onUnattachReviewView();
        }
    }
}
