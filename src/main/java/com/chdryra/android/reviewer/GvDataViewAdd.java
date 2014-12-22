/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Implementation of {@link com.chdryra.android.reviewer.GvDataView} that also interacts with some
 * UI component that manages the adding of the data, for example a dialog or activity.
 *
 * @param <T>
 */
public class GvDataViewAdd<T extends GvDataList.GvData> implements GvDataView<T> {

    private GvDataAdder<T>      mAdder;
    private GvDataViewLayout<T> mLayout;

    public interface GvDataAdder<T extends GvDataList.GvData> {
        public void setKeyboardAction(EditText editText);

        public void setTitle(String title);

        public T getNullingItem();
    }

    public GvDataViewAdd(GvDataAdder<T> adder, GvDataViewLayout<T> layout) {
        mAdder = adder;
        mLayout = layout;
    }

    @Override
    public void initialiseView(T data) {
        mAdder.setKeyboardAction(mLayout.getEditTextForKeyboardAction());
    }

    @Override
    public void updateView(T data) {
        mLayout.updateViews(mAdder.getNullingItem());
        mAdder.setTitle("+ " + mLayout.getTitleOnAdd(data));
    }

    @Override
    public T getGvData() {
        return mLayout.createGvDataFromViews();
    }
}
