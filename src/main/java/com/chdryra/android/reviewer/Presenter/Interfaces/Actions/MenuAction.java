package com.chdryra.android.reviewer.Presenter.Interfaces.Actions;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface MenuAction<T extends GvData> extends ReviewViewAction<T> {
    interface MenuActionItem {
        void doAction(Context context, MenuItem item);
    }

    boolean hasOptionsMenu();

    void inflateMenu(Menu menu, MenuInflater inflater);

    void bindMenuActionItem(MenuActionItem item, int itemId, boolean finishActivity);

    boolean onItemSelected(MenuItem item);

    @Override
    void onAttachReviewView();

    @Override
    void onUnattachReviewView();

    @Override
    void attachReviewView(ReviewView<T> reviewView);

    @Override
    Activity getActivity();

    @Override
    ReviewViewAdapter<T> getAdapter();

    @Override
    ReviewView<T> getReviewView();
}
