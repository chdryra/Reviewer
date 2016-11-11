/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionItemBasic;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuReviewBuild<GC extends GvDataList<? extends GvDataParcelable>>
        extends MenuActionNone<GC> implements ReviewEditor.ModeListener{
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;
    private static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU = R.menu.menu_build_review;

    private ReviewEditor<GC> mEditor;
    private MenuActionItem<GC> mPreview;
    private MenuActionItem<GC> mAverageRating;

    public MenuReviewBuild(String title, LaunchFormattedCommand command) {
        super(MENU, title, true);
        mAverageRating = new DoAverageRating();
        mPreview = new PreviewReview(command);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mPreview, MENU_PREVIEW_ID, false);
        bindMenuActionItem(mAverageRating, MENU_AVERAGE_ID, false);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        try {
            mEditor = (ReviewEditor<GC>) getReviewView();
        } catch (ClassCastException e) {
            throw new RuntimeException("Attached ReviewView should be Editor!", e);
        }

        mEditor.registerListener(this);
    }

    @Override
    public void onDetachReviewView() {
        super.onDetachReviewView();
        mEditor.unregisterListener(this);
    }

    @Override
    protected void doUpSelected() {
        getReviewView().getApp().getReviewEditor().discardEditor();
        super.doUpSelected();
    }

    @Override
    protected void onMenuInflated() {
        onEditMode(mEditor.getEditMode());
    }

    @Override
    public void onEditMode(ReviewEditor.EditMode mode) {
        MenuItem item = getItem(mAverageRating);
        if(item != null) {
            //item.setVisible(mode.equals(ReviewEditor.EditMode.FULL));
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
    }

    private class DoAverageRating extends MenuActionItemBasic<GC> {
        @Override
        public void doAction(MenuItem item) {
            mEditor.setRatingIsAverage(true);
        }
    }

    private class PreviewReview extends MenuActionItemBasic<GC> {
        private LaunchFormattedCommand mCommand;

        private PreviewReview(LaunchFormattedCommand command) {
            mCommand = command;
        }

        @Override
        public void doAction(MenuItem item) {
            mCommand.execute(mEditor.buildPreview(), false);
        }
    }
}
