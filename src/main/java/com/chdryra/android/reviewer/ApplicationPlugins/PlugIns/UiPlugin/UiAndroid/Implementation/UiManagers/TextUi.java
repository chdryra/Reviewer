/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.view.View;
import android.widget.TextView;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TextUi<T extends TextView> extends ViewUi<T, String> {
    public TextUi(T view, ValueGetter<String> getter, final Command onClick) {
        this(view, getter);
        getView().setClickable(true);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.execute();
            }
        });
    }

    public TextUi(T view, ValueGetter<String> getter) {
        super(view, getter);
    }

    public String getText() {
        return getView().getText().toString().trim();
    }

    public int getTextColour() {
        return getView().getTextColors().getDefaultColor();
    }

    @Override
    public void update() {
        getView().setText(getValue());
    }
}
