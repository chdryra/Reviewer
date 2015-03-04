/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * A hotchpotch of methods required in order to use the same xml layout for both adding and
 * editing {@link com.chdryra.android.reviewer.GvDataList.GvData}
 *
 * @param <T>: {@link com.chdryra.android.reviewer.GvDataList.GvData} type
 */
public abstract class GvDataEditLayout<T extends GvDataList.GvData> {
    private final LayoutHolder         mHolder;
    private final GvDataEditManager<T> mViewManager;
    private final T                    mNullingItem;
    private final int                  mEditTextId;

    private interface GvDataEditManager<T extends GvDataList.GvData> {
        void initialise(T data);

        void onAddEdit(T data);
    }

    public interface GvDataEditor {
        public void setKeyboardAction(EditText editText);

        public void setDeleteConfirmTitle(String title);
    }

    public interface GvDataAdder {
        public void setKeyboardAction(EditText editText);

        public void setTitle(String title);
    }

    public abstract T createGvData();

    public abstract void updateLayout(T data);

    public GvDataEditLayout(Class<T> gvDataClass, int layoutId, int[] viewIds,
            int keyboardEditTextId, GvDataAdder adder) {
        mEditTextId = keyboardEditTextId;
        mViewManager = new GvDataViewManagerAdd(adder);
        mHolder = new LayoutHolder(layoutId, viewIds);
        mNullingItem = FactoryGvData.newNull(gvDataClass);
    }

    public GvDataEditLayout(Class<T> gvDataClass, int layoutId, int[] viewIds,
            int keyboardEditTextId, GvDataEditor editor) {
        mEditTextId = keyboardEditTextId;
        mViewManager = new GvDataViewManagerEdit(editor);
        mHolder = new LayoutHolder(layoutId, viewIds);
        mNullingItem = FactoryGvData.newNull(gvDataClass);
    }

    public void clearViews() {
        updateLayout(mNullingItem);
    }

    public EditText getEditTextForKeyboardAction() {
        return (EditText) mHolder.getView(mEditTextId);
    }

    public View createLayoutUi(Context context, T data) {
        mHolder.inflate(context);
        mViewManager.initialise(data);
        return mHolder.getView();
    }

    public void onActivityAttached(Activity activity, Bundle args) {

    }

    public View getView(int viewId) {
        return mHolder.getView(viewId);
    }

    public void onAdd(T data) {
        mViewManager.onAddEdit(data);
    }

    private class GvDataViewManagerEdit implements GvDataEditLayout.GvDataEditManager<T> {
        private GvDataEditor mEditor;

        private GvDataViewManagerEdit(GvDataEditor editor) {
            mEditor = editor;
        }

        @Override
        public void initialise(T data) {
            mEditor.setKeyboardAction(getEditTextForKeyboardAction());
            mEditor.setDeleteConfirmTitle(data.getStringSummary());
            updateLayout(data);
        }

        @Override
        public void onAddEdit(T data) {
        }
    }

    private class GvDataViewManagerAdd implements GvDataEditLayout.GvDataEditManager<T> {
        private GvDataAdder mAdder;

        private GvDataViewManagerAdd(GvDataAdder adder) {
            mAdder = adder;
        }

        @Override
        public void initialise(T data) {
            mAdder.setKeyboardAction(getEditTextForKeyboardAction());
        }

        @Override
        public void onAddEdit(T data) {
            clearViews();
            mAdder.setTitle("+ " + data.getStringSummary());
        }
    }
}
