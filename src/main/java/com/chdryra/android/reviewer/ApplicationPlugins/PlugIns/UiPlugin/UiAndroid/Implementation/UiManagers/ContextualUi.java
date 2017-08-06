/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ButtonAction;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextualUi extends SimpleViewUi<Button, String> {
    private final ButtonAction<?> mAction;
    private boolean mIsVisible = true;

    public ContextualUi(final View view, final int buttonId, @Nullable final ButtonAction<?> action) {
        super((Button) view.findViewById(buttonId), new ReferenceValueGetter<String>() {
            @Override
            public String getValue() {
                return action != null ? action.getButtonTitle() : "";
            }
        }, new ViewValueGetter<String>() {
            @Override
            public String getValue() {
                return ((Button) view.findViewById(buttonId)).getText().toString().trim();
            }
        },new ViewValueSetter<String>() {
            @Override
            public void setValue(String value) {
                ((Button) view.findViewById(buttonId)).setText(value);
            }
        });
        mAction = action;
        initialise(view);
    }

    private void initialise(View view) {
        if (mAction == null) {
            view.setVisibility(View.GONE);
            mIsVisible = false;
            return;
        }

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAction.onClick(v);
            }
        });
        getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mAction.onLongClick(v);
            }
        });
        update();
    }

    @Override
    public void update() {
        if(mIsVisible) super.update();
    }
}
