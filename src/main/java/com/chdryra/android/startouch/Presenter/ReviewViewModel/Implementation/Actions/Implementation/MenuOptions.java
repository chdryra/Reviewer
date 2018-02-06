/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuOptionsItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuOptions<T extends GvData> extends MenuActionsSome<T> {
    public static final int OPTIONS = R.id.menu_item_options;
    public static final int MENU = R.menu.menu_review_options;

    private MenuOptionsItem<T> mOptions;

    protected MenuOptions(String title, MenuActionItem<T> upAction, MenuOptionsItem<T> options) {
        super(title, MENU, new int[]{OPTIONS}, toArrayList(options), upAction);
        mOptions = options;
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mOptions.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mOptions.onOptionsCancelled(requestCode);
    }
}
