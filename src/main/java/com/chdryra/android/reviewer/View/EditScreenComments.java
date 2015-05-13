/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenComments {
    public static class BannerButton extends EditScreen.BannerButton {

        public BannerButton(String title) {
            super(ConfigGvDataUi.getConfig(GvCommentList.TYPE).getAdderConfig(), title);
        }

        @Override
        protected boolean addData(GvData data) {
            boolean added = super.addData(data);
            if (getGridData().size() == 1) {
                GvCommentList.GvComment comment = (GvCommentList.GvComment) data;
                comment.setIsHeadline(true);
                getReviewView().updateUi();
            }
            return added;
        }
    }

    public static class GridItem extends EditScreen.GridItem {
        private static final int COMMENT_AS_HEADLINE = 200;

        public GridItem() {
            super(ConfigGvDataUi.getConfig(GvCommentList.TYPE).getEditorConfig());
        }

        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            GvCommentList.GvComment unsplit = ((GvCommentList.GvComment) item).getUnsplitComment();
            super.onGridItemClick(unsplit, position, v);
        }

        @Override
        protected void deleteData(GvData datum) {
            super.deleteData(datum);
            GvCommentList.GvComment comment = (GvCommentList.GvComment) datum;
            if (comment.isHeadline()) {
                comment.setIsHeadline(false);
                GvCommentList comments = (GvCommentList) getGridData();
                if (comments.getHeadlines().size() == 0 && comments.size() > 0) {
                    comments.getItem(0).setIsHeadline(true);
                }
            }
        }

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
                getReviewView().updateUi();
            }
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

    public static class Menu extends EditScreen.Menu implements GridDataObservable.GridDataObserver {
        public static final  int MENU_DELETE_ID = R.id.menu_item_delete;
        public static final  int MENU_DONE_ID   = R.id.menu_item_done;
        public static final  int MENU_SPLIT_ID  = R.id.menu_item_split_comment;
        private static final int MENU           = R.menu.fragment_review_comment;

        private boolean mCommentsAreSplit = false;

        public Menu() {
            super(GvCommentList.TYPE.getDataName(), GvCommentList.TYPE.getDataName(), false, true,
                    MENU);
        }

        @Override
        public void onGridDataChanged() {
            updateGridDataUi();
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            getReviewView().registerGridDataObserver(this);
        }

        @Override
        protected void addMenuItems() {
            addDefaultDeleteActionItem(MENU_DELETE_ID);
            addDefaultDoneActionItem(MENU_DONE_ID);
            addMenuActionItem(getSplitOrUnsplitCommentsAction(), MENU_SPLIT_ID, false);
        }

        @Override
        public void onUnattachReviewView() {
            getReviewView().unregisterGridDataObserver(this);
            super.onUnattachReviewView();
        }

        private MenuActionItem getSplitOrUnsplitCommentsAction() {
            return new MenuActionItem() {
                @Override
                public void doAction(MenuItem item) {
                    splitOrUnsplitComments(item);
                }
            };
        }

        private void updateGridDataUi() {
            //Change grid data
            GvCommentList comments = (GvCommentList) getGridData();
            if (comments != null) {
                if (mCommentsAreSplit) {
                    getReviewView().setGridViewData(comments.getSplitComments());
                } else {
                    getReviewView().resetGridViewData();
                }
            }
        }

        private void splitOrUnsplitComments(MenuItem item) {
            mCommentsAreSplit = !mCommentsAreSplit;

            //Change menu icons
            item.setIcon(mCommentsAreSplit ? R.drawable.ic_action_return_from_full_screen : R
                    .drawable.ic_action_full_screen);
            if (mCommentsAreSplit) {
                Toast.makeText(getActivity(), R.string.toast_split_comment,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), R.string.toast_unsplit_comment,
                        Toast.LENGTH_SHORT).show();
            }

            updateGridDataUi();
        }
    }
}
