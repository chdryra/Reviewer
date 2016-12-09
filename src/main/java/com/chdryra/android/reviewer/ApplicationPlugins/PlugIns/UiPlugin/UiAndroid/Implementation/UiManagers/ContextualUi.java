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
import android.widget.LinearLayout;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextualUi {
    private final Button mButton;
    private final ContextualButtonAction<?> mAction;
    private boolean mIsVisible = true;

    public ContextualUi(LinearLayout view, int buttonId, @Nullable ContextualButtonAction<?> action, int textColour) {
        mButton = (Button) view.findViewById(buttonId);
        mButton.setTextColor(textColour);
        mAction = action;

        initialise(view);
    }

    private void initialise(LinearLayout view) {
        if (mAction == null) {
            view.setVisibility(View.GONE);
            mIsVisible = false;
            return;
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAction.onClick(v);
            }
        });

        mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mAction.onLongClick(v);
            }
        });
        
        update();
    }

    public void update() {
        if(mIsVisible) mButton.setText(mAction.getButtonTitle());
    }
}
