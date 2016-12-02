/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuOptionsItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuViewDataDefault<T extends GvData> extends MenuViewData<T> {
    private static final int OPTIONS = MenuOptions.OPTIONS;
    private static final int MENU = MenuOptions.MENU;

    public MenuViewDataDefault(GvDataType<T> dataType, MenuOptionsItem<T> options) {
        super(dataType, MENU, OPTIONS, options);
    }

    public MenuViewDataDefault(String title, MenuOptionsItem<T> options) {
        super(title, MENU, OPTIONS, options);
    }
}
