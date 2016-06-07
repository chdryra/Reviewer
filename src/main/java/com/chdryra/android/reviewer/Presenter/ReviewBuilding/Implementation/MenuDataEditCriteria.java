/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class MenuDataEditCriteria extends MenuDataEdit<GvCriterion>
        implements DataObservable.DataObserver {
    private static final GvDataType<GvCriterion> TYPE = GvCriterion.TYPE;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_DONE_ID = R.id.menu_item_done;
    private static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU = R.menu.menu_edit_criteria;

    private final MenuItemCriteriaRatingAverage mActionItem;

    public MenuDataEditCriteria() {
        super(TYPE.getDataName(), TYPE.getDataName(), false, true, MENU);
        mActionItem = new MenuItemCriteriaRatingAverage();
    }

    @Override
    public void onDataChanged() {
        if (getEditor().isRatingAverage()) mActionItem.setAverageRating();
    }

    @Override
    protected void addMenuItems() {
        bindDefaultDeleteActionItem(MENU_DELETE_ID);
        bindDefaultDoneActionItem(MENU_DONE_ID);
        bindMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        getReviewView().registerDataObserver(this);
    }

    @Override
    public void onDetachReviewView() {
        getReviewView().unregisterDataObserver(this);
        super.onDetachReviewView();
    }

    public class MenuItemCriteriaRatingAverage implements MenuActionItem {
        public void setAverageRating() {
            float rating = 0;
            GvCriterionList children = (GvCriterionList) getGridData();
            for (GvCriterion child : children) {
                rating += child.getRating() / children.size();
            }

            getEditor().setRating(rating, false);
        }

        @Override
        public void doAction(MenuItem item) {
            getEditor().setRatingIsAverage(true);
        }
    }
}
