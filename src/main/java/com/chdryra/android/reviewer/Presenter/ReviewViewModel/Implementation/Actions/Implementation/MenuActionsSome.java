/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuActionsSome<T extends GvData> extends MenuActionNone<T> {
    public MenuActionsSome(String title, int menuId, int[] itemIds, ArrayList<MenuActionItem<T>>
            items) {
        super(menuId, title, true);
        initialise(itemIds, items);
    }

    protected MenuActionsSome(String title, int menuId, int[] itemIds,
                              ArrayList<MenuActionItem<T>> items, MenuActionItem<T> upAction) {
        super(menuId, title, upAction);
        initialise(itemIds, items);
    }

    @SafeVarargs
    protected static <T extends GvData> ArrayList<MenuActionItem<T>>
    toArrayList(MenuActionItem<T> first, MenuActionItem<T>... others) {
        ArrayList<MenuActionItem<T>> items = new ArrayList<>();
        items.add(first);
        items.addAll(Arrays.asList(others));
        return items;
    }

    private void initialise(int[] itemIds, ArrayList<MenuActionItem<T>> items) {
        if (itemIds.length != items.size()) throwException();

        for (int i = 0; i < itemIds.length; ++i) {
            bindMenuActionItem(items.get(i), itemIds[i], false);
        }
    }

    private void throwException() {
        throw new IllegalArgumentException("itemIds and items must have same length");
    }
}
