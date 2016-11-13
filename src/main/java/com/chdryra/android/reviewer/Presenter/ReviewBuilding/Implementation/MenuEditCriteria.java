/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class MenuEditCriteria extends MenuEditData<GvCriterion>
        implements DataObservable.DataObserver {
    private static final GvDataType<GvCriterion> TYPE = GvCriterion.TYPE;
    private static final int MENU = R.menu.menu_edit_criteria;
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_DONE_ID = R.id.menu_item_done;
    private static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;

    private MaiDataEditor<GvCriterion> mAverageAction;

    public MenuEditCriteria(MaiDataEditor<GvCriterion> deleteAction,
                            MaiDataEditor<GvCriterion> doneAction,
                            MaiDataEditor<GvCriterion> previewAction,
                            MaiDataEditor<GvCriterion> averageAction) {
        super(MENU, TYPE.getDataName(), deleteAction, doneAction, previewAction);
        mAverageAction = averageAction;
    }

    @Override
    public void onDataChanged() {
        getEditor().update();
    }

    @Override
    protected void addMenuItems() {
        bindDeleteActionItem(MENU_DELETE_ID);
        bindDoneActionItem(MENU_DONE_ID);
        bindPreviewActionItem(MENU_PREVIEW_ID);
        bindMenuActionItem(mAverageAction, MENU_AVERAGE_ID, false);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        getReviewView().registerObserver(this);
    }

    @Override
    public void onDetachReviewView() {
        getReviewView().unregisterObserver(this);
        super.onDetachReviewView();
    }
}
