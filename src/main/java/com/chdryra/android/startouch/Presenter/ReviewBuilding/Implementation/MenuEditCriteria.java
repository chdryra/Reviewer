/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class MenuEditCriteria extends MenuEditData<GvCriterion> {
    private static final GvDataType<GvCriterion> TYPE = GvCriterion.TYPE;
    private static final int MENU = R.menu.menu_edit_criteria;
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;

    public MenuEditCriteria(MenuActionItem<GvCriterion> upAction,
                            MenuActionItem<GvCriterion> deleteAction,
                            MenuActionItem<GvCriterion> previewAction,
                            MenuActionItem<GvCriterion> averageAction) {
        super(TYPE.getDataName(), MENU,
                new int[]{MENU_DELETE_ID, MENU_PREVIEW_ID},
                upAction, deleteAction, previewAction);
        bindMenuActionItem(averageAction, MENU_AVERAGE_ID, false);
    }
}
