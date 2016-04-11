/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 March, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation
        .AddEditLayoutBasic;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class AddEditLayoutTest<T extends GvData> extends AndroidTestCase {
    protected final AddEditLayoutBasic<T> mLayout;
    private final GvDataType mDataType;
    protected EditText mEditText;

    protected abstract void enterData(T datum);

    protected abstract void checkViewAndDataEquivalence(T datum, boolean result);

    //Constructors
    public AddEditLayoutTest(GvDataType dataType, AddEditLayoutBasic<T> layout) {
        mDataType = dataType;
        mLayout = layout;
    }

    @SmallTest
    public void testCreateGvDataFromViews() {
        T datum = newDatum();
        enterData(datum);
        assertEquals(datum, mLayout.createGvDataFromInputs());
    }

    @SmallTest
    public void testUpdateViews() {
        T datum = newDatum();
        checkViewAndDataEquivalence(datum, false);
        mLayout.updateLayout(datum);
        checkViewAndDataEquivalence(datum, true);
    }

    @SmallTest
    public void testGetEditTextForKeyboardAction() {
        assertEquals(mEditText, mLayout.getEditTextForKeyboardAction());
    }

    protected View getView(int viewId) {
        return mLayout.getView(viewId);
    }

    protected T newDatum() {
        return (T) GvDataMocker.getDatum(mDataType);
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mLayout.createLayoutUi(getContext(), newDatum());
    }
}
