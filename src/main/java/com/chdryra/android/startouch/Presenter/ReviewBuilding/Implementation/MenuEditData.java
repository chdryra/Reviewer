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
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
class MenuEditData<T extends GvData> extends MenuActionNone<T> {
    MenuEditData(String title,
                 int menuId, int[] itemIds,
                 MenuActionItem<T> upAction,
                 MenuActionItem<T> deleteAction,
                 MenuActionItem<T> previewAction) {

        super(menuId, title, upAction);
        if(itemIds.length != 2) {
            throw new IllegalArgumentException("itemIds should be length 3");
        }

        bindMenuActionItem(deleteAction, itemIds[0], false);
        bindMenuActionItem(previewAction, itemIds[1], false);
    }

    ReviewDataEditor<T> getEditor() {
        return (ReviewDataEditor<T>) getReviewView();
    }
}
