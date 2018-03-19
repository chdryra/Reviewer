/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.widget.TextView;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class TextBinder<T> implements ReferenceBinder<T> {
    private static final String INVALID_REFERENCE = "Invalid reference";
    private final DataReference<T> mReference;
    private final StringGetter<T> mGetter;
    private final TextView mView;

    public interface StringGetter<T> {
        String getString(T value);
    }

    public TextBinder(DataReference<T> reference, TextView view, StringGetter<T> getter) {
        mReference = reference;
        mGetter = getter;
        mView = view;
    }

    @Override
    public void onReferenceValue(T value) {
        mView.setText(mGetter.getString(value));
    }

    @Override
    public void onInvalidated(DataReference<T> reference) {
        mView.setText(INVALID_REFERENCE);
        unbind();
    }

    @Override
    public void bind() {
        mView.setText(Strings.Progress.FETCHING);
        mReference.subscribe(this);
    }

    @Override
    public void unbind() {
        mReference.unsubscribe(this);
    }
}
