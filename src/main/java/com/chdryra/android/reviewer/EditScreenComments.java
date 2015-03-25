/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
        private GvCommentList.GvComment mHeadlineProposition;

        public GridItem() {
            super(ConfigGvDataUi.getConfig(GvCommentList.TYPE).getEditorConfig());
        }

        @Override
        public void onGridItemClick(GvData item, View v) {
            super.onGridItemClick(((GvCommentList.GvComment) item).getUnsplitComment(), v);
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
        public void onGridItemLongClick(GvData item, View v) {
            GvCommentList.GvComment comment = (GvCommentList.GvComment) item;
            if (comment.isHeadline()) {
                super.onGridItemLongClick(item, v);
            } else {
                showAlertDialog(getActivity().getString(R.string.dialog_set_comment_as_headline),
                        COMMENT_AS_HEADLINE, comment);
            }
        }
    }

    public static class Menu extends EditScreen.Menu {
        public static final  int MENU_DELETE_ID = R.id.menu_item_delete;
        public static final  int MENU_DONE_ID   = R.id.menu_item_done;
        public static final  int MENU_SPLIT_ID  = R.id.menu_item_split_comment;
        private static final int MENU           = R.menu.fragment_review_comment;
        private final DataSetObserver mObserver;
        private boolean mCommentsAreSplit = false;

        public Menu() {
            super(GvCommentList.TYPE.getDataName(), GvCommentList.TYPE.getDataName(), false, true,
                    MENU);
            mObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    updateGridDataUi();
                }
            };
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            getReviewView().registerGridDataObserver(mObserver);
        }

        @Override
        protected void addMenuItems() {
            addDefaultDeleteActionItem(MENU_DELETE_ID);
            addDefaultDoneActionItem(MENU_DONE_ID);
            addMenuActionItem(getSplitOrUnsplitCommentsAction(), MENU_SPLIT_ID, false);
        }

        @Override
        public void onUnattachReviewView() {
            getReviewView().unregisterGridDataObserver(mObserver);
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
