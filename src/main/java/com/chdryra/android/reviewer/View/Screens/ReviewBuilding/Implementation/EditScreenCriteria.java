/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation;

import android.content.Context;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Screens.Interfaces.GridDataObservable;
import com.chdryra.android.reviewer.View.Screens.MenuAction;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Factories.FactoryReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenCriteria extends EditScreenReviewDataImpl<GvCriterionList.GvCriterion> {
    private static final GvDataType<GvCriterionList.GvCriterion> TYPE =
            GvCriterionList.GvCriterion.TYPE;

    public EditScreenCriteria(Context context, ReviewBuilderAdapter builder, FactoryReviewDataEditor editorFactory) {
        super(context, builder, TYPE, editorFactory);
    }

    @Override
    protected MenuDataEdit<GvCriterionList.GvCriterion> newMenuAction() {
        return new MenuEditCriteria();
    }

    //Classes
    private class MenuEditCriteria extends MenuDataEdit<GvCriterionList.GvCriterion>
            implements GridDataObservable.GridDataObserver {
        public static final int MENU_DELETE_ID = R.id.menu_item_delete;
        public static final int MENU_DONE_ID = R.id.menu_item_done;
        public static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
        private static final int MENU = R.menu.menu_edit_children;

        private final MenuItemCriteriaRatingAverage mActionItem;

        //Constructors
        private MenuEditCriteria() {
            super(TYPE, TYPE.getDataName(), TYPE.getDataName(), false, true, MENU);
            mActionItem = new MenuItemCriteriaRatingAverage();
        }

        //Overridden
        @Override
        public void onGridDataChanged() {
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
            getReviewView().registerGridDataObserver(this);
        }

        @Override
        public void onUnattachReviewView() {
            getReviewView().unregisterGridDataObserver(this);
            super.onUnattachReviewView();
        }

        public class MenuItemCriteriaRatingAverage implements MenuAction.MenuActionItem {
            public void setAverageRating() {
                float rating = 0;
                GvCriterionList children = (GvCriterionList) getGridData();
                for (GvCriterionList.GvCriterion child : children) {
                    rating += child.getRating() / children.size();
                }

                getEditor().setRating(rating, false);
            }

            //Overridden
            @Override
            public void doAction(Context context, MenuItem item) {
                getEditor().setRatingIsAverage(true);
            }
        }
    }
}
