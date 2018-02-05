/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.View;
import android.widget.Button;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextUi extends SimpleViewUi<Button, String> {
    private final ButtonAction<?> mAction;
    private boolean mIsVisible = true;

    public ContextUi(final ReviewView<?> reviewView, final View view, final int buttonId) {
        super((Button) view.findViewById(buttonId), new ReferenceValueGetter<String>() {
            @Override
            public String getValue() {
                ButtonAction<?> action = reviewView.getActions().getContextualAction();
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
        mAction = reviewView.getActions().getContextualAction();
        initialise(view, reviewView.getParams().getContextViewParams());
    }

    private void initialise(View layout, ReviewViewParams.ContextView params) {
        if (mAction == null) {
            layout.setVisibility(View.GONE);
            mIsVisible = false;
            return;
        }

        Button button = getView();
        setBackgroundAlpha(params.getAlpha());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAction.onClick(v);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
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
