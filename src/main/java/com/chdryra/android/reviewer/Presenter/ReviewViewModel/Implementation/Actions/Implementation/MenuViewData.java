/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuViewData<T extends GvData> extends MenuHideableActions<T> {
    private final int mOptionsId;

    public MenuViewData(GvDataType<T> dataType, int menuId, int optionsId, MenuActionItem<T> options) {
        this(dataType.getDataName(), menuId, optionsId, options);
    }

    public MenuViewData(String title, int menuId, int optionsId, MenuActionItem<T> options) {
        super(title, menuId);
        mOptionsId = optionsId;
        bindHideableItem(options, optionsId, false);
    }

    @Override
    protected boolean hideCondition(int itemId) {
        return itemId != mOptionsId || !hasStamp();
    }

    private boolean hasStamp() {
        return getReviewView().getAdapter().getStamp().isValid();
    }
}