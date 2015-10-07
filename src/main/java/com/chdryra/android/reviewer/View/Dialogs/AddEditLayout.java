/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.EditText;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * A hotchpotch of methods required in order to use the same xml layout for both adding and
 * editing {@link GvData}
 *
 * @param <T>: {@link GvData} type
 */
public abstract class AddEditLayout<T extends GvData> extends DialogLayout<T> {
    private final GvDataEditManager<T> mViewManager;
    private final T mNullingItem;
    private final int mEditTextId;

    private interface GvDataEditManager<T extends GvData> {
        //abstract
        void initialise(T data);

        void onAddEdit(T data);
    }

    public interface GvDataEditor {
        //abstract
        void setKeyboardAction(EditText editText);

        void setDeleteConfirmTitle(String title);
    }

    public interface GvDataAdder {
        //abstract
        void setKeyboardAction(EditText editText);

        void setTitle(String title);
    }

    //abstract
    public abstract T createGvData();

    //Constructors
    public AddEditLayout(Class<T> gvDataClass, int layoutId, int[] viewIds,
                         int keyboardEditTextId, GvDataAdder adder) {
        super(layoutId, viewIds);
        mEditTextId = keyboardEditTextId;
        mViewManager = new GvDataViewManagerAdd(adder);
        mNullingItem = FactoryGvData.newNull(gvDataClass);
    }

    public AddEditLayout(Class<T> gvDataClass, int layoutId, int[] viewIds,
                         int keyboardEditTextId, GvDataEditor editor) {
        super(layoutId, viewIds);
        mEditTextId = keyboardEditTextId;
        mViewManager = new GvDataViewManagerEdit(editor);
        mNullingItem = FactoryGvData.newNull(gvDataClass);
    }

    //public methods
    public EditText getEditTextForKeyboardAction() {
        return (EditText) getView(mEditTextId);
    }

    public void clearViews() {
        updateLayout(mNullingItem);
    }

    public void onAdd(T data) {
        mViewManager.onAddEdit(data);
    }

    //Overridden
    @Override
    public void initialise(T data) {
        mViewManager.initialise(data);
    }

    public class GvDataViewManagerAdd implements AddEditLayout.GvDataEditManager<T> {
        private final GvDataAdder mAdder;

        private GvDataViewManagerAdd(GvDataAdder adder) {
            mAdder = adder;
        }

        //Overridden
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

    private class GvDataViewManagerEdit implements AddEditLayout.GvDataEditManager<T> {
        private final GvDataEditor mEditor;

        private GvDataViewManagerEdit(GvDataEditor editor) {
            mEditor = editor;
        }

        //Overridden
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
}
