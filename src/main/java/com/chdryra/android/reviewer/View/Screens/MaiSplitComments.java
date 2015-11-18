package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .AdapterCommentsAggregate;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Screens.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.Screens.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSplitComments<T extends GvData> implements MenuActionNone.MenuActionItem {
    private static final int UNSPLIT_ICON = R.drawable.ic_action_return_from_full_screen;
    private static final int SPLIT_ICON = R.drawable.ic_action_full_screen;
    private static final int TOAST_SPLIT = R.string.toast_split_comment;
    private static final int TOAST_UNSPLIT = R.string.toast_unsplit_comment;

    private boolean mCommentsAreSplit = false;
    private MenuAction<T> mParent;

    //Constructors
    public MaiSplitComments(MenuAction<T> parent) {
        mParent = parent;
    }

    public void updateGridDataUi() {
        ReviewView<T> view = mParent.getReviewView();
        if (view == null) return;

        //Hacky central...
        GvDataList<T> data = view.getGridData();
        GvDataType<T> dataType = data.getGvDataType();
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            GvCommentList comments = (GvCommentList) data;
            if (mCommentsAreSplit) {
                view.setGridViewData((GvDataList<T>) comments.getSplitComments());
            } else {
                view.setGridViewData(data);
            }
        } else {
            AdapterCommentsAggregate adapter = (AdapterCommentsAggregate) view.getAdapter();
            adapter.setSplit(mCommentsAreSplit);
        }
    }

    //Overridden
    @Override
    public void doAction(Context context, MenuItem item) {
        mCommentsAreSplit = !mCommentsAreSplit;

        item.setIcon(mCommentsAreSplit ? UNSPLIT_ICON : SPLIT_ICON);
        if (mCommentsAreSplit) {
            Toast.makeText(context, TOAST_SPLIT, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, TOAST_UNSPLIT, Toast.LENGTH_SHORT).show();
        }

        updateGridDataUi();
    }
}
