/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuUi implements OptionSelectListener{
    private final MenuAction<?> mAction;

    public MenuUi(MenuAction<?> action) {
        mAction = action;
    }

    public void inflate(Menu menu, MenuInflater inflater) {
        mAction.inflateMenu(menu, inflater);
    }

    public boolean onItemSelected(MenuItem item) {
        return mAction.onItemSelected(item);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mAction.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mAction.onOptionsCancelled(requestCode);
    }
}
