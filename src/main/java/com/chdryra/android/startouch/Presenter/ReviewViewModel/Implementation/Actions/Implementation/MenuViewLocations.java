/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuOptionsItem;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuViewLocations extends MenuViewData<GvLocation.Reference> {
    private static final int MENU = R.menu.menu_view_locations;
    private static final int MENU_MAP_ID = R.id.menu_item_map_locations;
    private static final int MENU_OPTIONS_ID = R.id.menu_item_options_locations;

    public MenuViewLocations(MenuOptionsItem<GvLocation.Reference> options,
                             MenuActionItem<GvLocation.Reference> mapLocations) {
        super(GvLocation.Reference.TYPE, MENU, MENU_OPTIONS_ID, options);
        bindMenuActionItem(mapLocations, MENU_MAP_ID, false);
    }
}
