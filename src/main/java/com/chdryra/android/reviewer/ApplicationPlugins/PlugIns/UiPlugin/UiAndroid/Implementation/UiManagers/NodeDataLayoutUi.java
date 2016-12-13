/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class NodeDataLayoutUi<T extends HasReviewId> 
        extends NodeDataUi<T, RefDataList<T>, LinearLayout> {
    private final int mLayout;
    private final int mPlaceholder;
    private final LayoutInflater mInflater;

    protected abstract void updateView(View view, T criterion);

    public NodeDataLayoutUi(LinearLayout view,
                            ValueGetter<RefDataList<T>> getter,
                            @Nullable Command onClick,
                            int layout,
                            int placeholder,
                            LayoutInflater inflater) {
        super(view, getter, onClick);
        mLayout = layout;
        mPlaceholder = placeholder;
        mInflater = inflater;
    }

    @Override
    protected void setEmpty() {
        TextView placeholder = getPlaceholder();
        placeholder.setTypeface(placeholder.getTypeface(), Typeface.ITALIC);
        placeholder.setText(Strings.FORMATTED.NONE);
    }

    @Override
    protected void updateView(IdableList<T> data) {
        getPlaceholder().setVisibility(View.GONE);
        LinearLayout layout = getView();
        for (T datum : data) {
            View datumView = mInflater.inflate(mLayout, null);
            updateView(datumView, datum);
            layout.addView(datumView);
        }
    }

    private TextView getPlaceholder() {
        return (TextView) getView().findViewById(mPlaceholder);
    }
}
