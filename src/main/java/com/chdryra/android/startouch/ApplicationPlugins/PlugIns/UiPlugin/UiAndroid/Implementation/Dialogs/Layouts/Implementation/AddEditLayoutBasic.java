/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

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
public abstract class AddEditLayoutBasic<T extends GvData> extends DatumLayoutBasic<T>
        implements DatumLayoutEdit<T> {
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private final GvDataEditManager<T> mViewManager;
    private final T mNullingItem;
    private final int mEditTextId;

    private interface GvDataEditManager<T extends GvData> {
        void setup();

        void initialise(T data);

        void onAddEdit(T data);
    }

    AddEditLayoutBasic(Class<T> gvDataClass, LayoutHolder holder,
                       int keyboardEditTextId, GvDataAdder adder) {
        super(holder);
        mEditTextId = keyboardEditTextId;
        mViewManager = new GvDataViewManagerAdd(adder);
        mNullingItem = newNull(gvDataClass);
    }

    AddEditLayoutBasic(Class<T> gvDataClass, LayoutHolder holder,
                       int keyboardEditTextId, GvDataEditor editor) {
        super(holder);
        mEditTextId = keyboardEditTextId;
        mViewManager = new GvDataViewManagerEdit(editor);
        mNullingItem = newNull(gvDataClass);
    }

    //public methods
    @Override
    public EditText getEditTextForKeyboardAction() {
        return (EditText) getView(mEditTextId);
    }

    @Override
    public void clearViews() {
        updateView(mNullingItem);
    }

    @Override
    public void onAdd(T data) {
        mViewManager.onAddEdit(data);
    }

    @Override
    protected void onLayoutInflated() {
        mViewManager.setup();
    }

    @Override
    public void initialise(T data) {
        mViewManager.initialise(data);
    }

    private T newNull(Class<T> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName());
        }
    }

    private class GvDataViewManagerAdd implements AddEditLayoutBasic.GvDataEditManager<T> {
        private final GvDataAdder mAdder;

        private GvDataViewManagerAdd(GvDataAdder adder) {
            mAdder = adder;
        }

        //Overridden

        @Override
        public void setup() {
            mAdder.setKeyboardAction(getEditTextForKeyboardAction());
        }

        @Override
        public void initialise(T data) {

        }

        @Override
        public void onAddEdit(T data) {
            clearViews();
            mAdder.setTitle("+ " + data);
        }
    }

    private class GvDataViewManagerEdit implements AddEditLayoutBasic.GvDataEditManager<T> {
        private final GvDataEditor mEditor;

        private GvDataViewManagerEdit(GvDataEditor editor) {
            mEditor = editor;
        }

        //Overridden

        @Override
        public void setup() {
            mEditor.setKeyboardAction(getEditTextForKeyboardAction());
        }

        @Override
        public void initialise(T data) {
            mEditor.setDeleteTitle(data.toString());
            updateView(data);
        }

        @Override
        public void onAddEdit(T data) {
        }
    }
}
