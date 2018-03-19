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
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class MenuEditLocations extends MenuEditData<GvLocation> {
    private static final GvDataType<GvLocation> TYPE = GvLocation.TYPE;
    private static final int MENU = R.menu.menu_edit_locations;
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_MAP_ID = R.id.menu_item_map_locations;

    public MenuEditLocations(MenuActionItem<GvLocation> upAction,
                             MenuActionItem<GvLocation> deleteAction,
                             MenuActionItem<GvLocation> previewAction,
                             MenuActionItem<GvLocation> mapAction) {
        super(TYPE.getDataName(), MENU,
                new int[]{MENU_DELETE_ID, MENU_PREVIEW_ID},
                upAction, deleteAction, previewAction);
        bindMenuActionItem(mapAction, MENU_MAP_ID, false);
    }
}
