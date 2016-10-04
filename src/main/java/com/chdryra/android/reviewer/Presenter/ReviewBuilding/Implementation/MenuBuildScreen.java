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
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuBuildScreen<GC extends GvDataList<? extends GvDataParcelable>> extends MenuActionNone<GC> {
    private static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU = R.menu.menu_build_review;

    private ReviewEditor<GC> mEditor;

    public MenuBuildScreen(String title) {
        super(MENU, title, true);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(new DoAverageRating(), MENU_AVERAGE_ID, false);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        try {
            mEditor = (ReviewEditor<GC>) getReviewView();
        } catch (ClassCastException e) {
            throw new RuntimeException("Attached ReviewView should be Editor!", e);
        }
    }

    @Override
    protected void doUpSelected() {
        getReviewView().getApp().getReviewBuilder().discardReviewEditor();
        super.doUpSelected();
    }

    private class DoAverageRating extends MenuActionItemBasic<GC> {
        @Override
        public void doAction(MenuItem item) {
            mEditor.setRatingIsAverage(true);
        }
    }
}
