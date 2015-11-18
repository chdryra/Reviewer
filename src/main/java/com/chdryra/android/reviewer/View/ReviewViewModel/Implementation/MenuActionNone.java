package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements MenuAction<T> {
    private static final int MENU_UP_ID = android.R.id.home;
    private static final ActivityResultCode RESULT_UP = ActivityResultCode.UP;

    private final String mTitle;
    private final SparseArray<MenuActionItemInfo> mActionItems;
    private int mMenuId = -1;
    private boolean mDisplayHomeAsUp = true;

    //Constructors
    public MenuActionNone() {
        this(-1, null, true);
    }

    public MenuActionNone(int menuId, String title, boolean displayHomeAsUp) {
        mMenuId = menuId;
        mTitle = title;
        mDisplayHomeAsUp = displayHomeAsUp;
        mActionItems = new SparseArray<>();
        if (mDisplayHomeAsUp) bindMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
    }

    public MenuActionNone(String title) {
        this(-1, title, true);
    }

    @Override
    public boolean hasOptionsMenu() {
        return mMenuId != -1;
    }

    @Override
    public void inflateMenu(Menu menu, MenuInflater inflater) {
        if (hasOptionsMenu()) inflater.inflate(mMenuId, menu);
    }

    @Override
    public void bindMenuActionItem(MenuActionItem item, int itemId, boolean finishActivity) {
        mActionItems.put(itemId, new MenuActionItemInfo(item, finishActivity));
    }

    @Override
    public boolean onItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        MenuActionItemInfo actionItem = mActionItems.get(itemId);
        if (actionItem != null) {
            actionItem.mItem.doAction(getActivity(), item);
            if (actionItem.mFinishActivity) getActivity().finish();
            return true;
        }

        return false;
    }

    protected void addMenuItems() {
    }

    protected void sendResult(ActivityResultCode result) {
        if (result != null) getActivity().setResult(result.get(), null);
    }

    protected void doUpSelected() {
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            Intent i = NavUtils.getParentActivityIntent(getActivity());
            NavUtils.navigateUpTo(getActivity(), i);
        }
    }

    //private methods
    private MenuActionItem getUpActionItem() {
        return new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(Context context, MenuItem item) {
                doUpSelected();
                sendResult(RESULT_UP);
            }
        };
    }

    //Overridden
    @Override
    public void onAttachReviewView() {
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(mDisplayHomeAsUp);
            actionBar.setDisplayShowHomeEnabled(false);
            if (mTitle != null) actionBar.setTitle(mTitle);
            addMenuItems();
        }
    }

    private class MenuActionItemInfo {
        private final boolean mFinishActivity;
        private final MenuActionItem mItem;

        private MenuActionItemInfo(MenuActionItem item, boolean finishActivity) {
            mItem = item;
            mFinishActivity = finishActivity;
        }
    }
}
