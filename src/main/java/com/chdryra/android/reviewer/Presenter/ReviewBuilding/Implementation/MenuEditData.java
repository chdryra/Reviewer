/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
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

    private static final ActivityResultCode RESULT_PREVIEW = ActivityResultCode.OTHER;

    private static final int ALERT_DIALOG = RequestCodeGenerator.getCode("DeleteConfirm");

    private final MaiDataEditor<T> mDeleteAction;
    private final MaiDataEditor<T> mDoneAction;
    private final MaiDataEditor<T> mPreviewAction;

    private ReviewDataEditor<T> mEditor;

    public MenuEditData(String title,
                        MaiDataEditor<T> deleteAction,
                        MaiDataEditor<T> doneAction,
                        MaiDataEditor<T> previewAction) {
        this(MENU, title, deleteAction, doneAction, previewAction);
    }

    public MenuEditData(int menuId, @Nullable String title,
                        MaiDataEditor<T> deleteAction,
                        MaiDataEditor<T> doneAction,
                        MaiDataEditor<T> previewAction) {
        super(menuId, title, true);
        mDeleteAction = deleteAction;
        mDoneAction = doneAction;
        mPreviewAction = previewAction;

        addMenuItems();
    }

    public int getAlertRequestCode() {
        return ALERT_DIALOG;
    }

    //protected methods
    ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    void bindDeleteActionItem(int deleteId) {
        bindMenuActionItem(mDeleteAction, deleteId, false);
    }

    void bindDoneActionItem(int doneId) {
        bindMenuActionItem(mDoneAction, doneId, true);
    }

    void bindPreviewActionItem(int previewId) {
        bindMenuActionItem(mPreviewAction, previewId, false);
    }

    @Override
    protected void addMenuItems() {
        bindDeleteActionItem(MENU_DELETE_ID);
        bindDoneActionItem(MENU_DONE_ID);
        bindPreviewActionItem(MENU_PREVIEW_ID);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mEditor = (ReviewDataEditor<T>) getReviewView();
        mDeleteAction.onAttachReviewView();
        mDoneAction.onAttachReviewView();
        mPreviewAction.onAttachReviewView();
    }

    @Override
    public void onDetachReviewView() {
        mDeleteAction.onDetachReviewView();
        mDoneAction.onDetachReviewView();
        mPreviewAction.onDetachReviewView();
        super.onDetachReviewView();
    }
}
