/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEditData<T extends GvData> extends MenuActionNone<T> {
    private static final int MENU = R.menu.menu_edit_data;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_DONE_ID = R.id.menu_item_done;
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;

    public MenuEditData(String title,
                        MenuActionItem<T> upAction,
                        MenuActionItem<T> doneAction,
                        MenuActionItem<T> deleteAction,
                        MenuActionItem<T> previewAction) {
        this(title, MENU, new int[]{MENU_DONE_ID, MENU_DELETE_ID, MENU_PREVIEW_ID},
                upAction, doneAction, deleteAction, previewAction);
    }

    MenuEditData(String title,
                 int menuId, int[] itemIds,
                 MenuActionItem<T> upAction,
                 MenuActionItem<T> doneAction,
                 MenuActionItem<T> deleteAction,
                 MenuActionItem<T> previewAction) {

        super(menuId, title, upAction);
        if(itemIds.length != 3) {
            throw new IllegalArgumentException("itemIds should be length 3");
        }

        addActionItem(doneAction, itemIds[0], true);
        addActionItem(deleteAction, itemIds[1], false);
        addActionItem(previewAction, itemIds[2], false);
    }

    void addActionItem(MenuActionItem<T> actionItem, int itemId, boolean finishActivity) {
        bindMenuActionItem(actionItem, itemId, finishActivity);
    }

    ReviewDataEditor<T> getEditor() {
        return (ReviewDataEditor<T>) getReviewView();
    }
}
