/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEditDataDefault<T extends GvData> extends MenuEditData<T> {
    private static final int MENU = R.menu.menu_edit_data;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;

    public MenuEditDataDefault(String title,
                               MenuActionItem<T> upAction,
                               MenuActionItem<T> deleteAction,
                               MenuActionItem<T> previewAction) {
        super(title, MENU, new int[]{MENU_DELETE_ID, MENU_PREVIEW_ID},
                upAction, deleteAction, previewAction);
    }
}
