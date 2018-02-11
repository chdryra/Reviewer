/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuFollow<T extends GvData> extends MenuActionNone<T> {
    private static final int FOLLOW = R.id.menu_item_follow;
    private static final int MENU = R.menu.menu_follow;

    private DataReference<AuthorName> mAuthor;

    public MenuFollow(MenuActionItem<T> follow, DataReference<AuthorName> author) {
        super(MENU, "", true);
        bindMenuActionItem(follow, FOLLOW, false);
        mAuthor = author;
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mAuthor.dereference(new DataReference.DereferenceCallback<AuthorName>() {
            @Override
            public void onDereferenced(DataValue<AuthorName> value) {
                if(value.hasValue()) getCurrentScreen().setTitle(value.getData().getName());
            }
        });
    }
}
