/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ImagesSizeUi extends FormattedSectionUi<DataSize> {
    public ImagesSizeUi(LinearLayout section) {
        super(section, Strings.Formatted.IMAGES);
    }

    @Override
    public void update(DataSize value) {
        if (value.getSize() > 0) {
            showGrid();
        } else {
            showPlaceholder();
        }
    }

    @Override
    public void onInvalidated() {
        showPlaceholder();
    }

    private void showPlaceholder() {
        TextView placeholder = getValueView();
        placeholder.setVisibility(View.VISIBLE);
        placeholder.setTypeface(placeholder.getTypeface(), Typeface.ITALIC);
        placeholder.setText(Strings.Formatted.NONE);
    }

    private void showGrid() {
        getValueView().setVisibility(View.GONE);
    }
}
