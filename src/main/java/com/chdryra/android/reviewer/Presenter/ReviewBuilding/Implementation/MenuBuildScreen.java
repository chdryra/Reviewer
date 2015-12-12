package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Context;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuBuildScreen<T extends GvData> extends MenuActionNone<T> {
    public static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU = R.menu.menu_build_review;

    private ReviewEditor<T> mEditor;
    private final MenuActionItem mActionItem;

    public MenuBuildScreen(String title) {
        super(MENU, title, true);
        mActionItem = new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(Context context, MenuItem item) {
                mEditor.setRatingIsAverage(true);
            }
        };
    }

    //Overridden
    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        try {
            mEditor = (ReviewEditor<T>)getReviewView();
        } catch (ClassCastException e) {
            throw new RuntimeException("Attached ReviewView should be Editor!", e);
        }
    }
}
