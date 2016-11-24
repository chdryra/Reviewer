/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuFollow<T extends GvData> extends MenuActionNone<T> {
    private static final int FOLLOW = R.id.menu_item_follow;
    private static final int MENU = R.menu.menu_follow;

    private DataReference<NamedAuthor> mAuthor;

    public MenuFollow(MenuActionItem<T> follow, DataReference<NamedAuthor> author) {
        super(MENU, "", true);
        bindMenuActionItem(follow, FOLLOW, false);
        mAuthor = author;
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mAuthor.dereference(new DataReference.DereferenceCallback<NamedAuthor>() {
            @Override
            public void onDereferenced(DataValue<NamedAuthor> value) {
                if(value.hasValue()) getCurrentScreen().setTitle(value.getData().getName());
            }
        });
    }
}
