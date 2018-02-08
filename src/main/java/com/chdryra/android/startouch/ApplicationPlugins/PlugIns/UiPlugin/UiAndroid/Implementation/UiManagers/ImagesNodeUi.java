/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ImagesNodeUi extends FormattedSectionUi<ReviewItemReference<DataSize>> {
    private final ViewUi<RecyclerView, RefDataList<DataImage>> mGrid;

    public ImagesNodeUi(LinearLayout section,
                        ViewUi<RecyclerView, RefDataList<DataImage>> grid,
                        final ReviewNode node) {
        super(section, new ReferenceValueGetter<ReviewItemReference<DataSize>>() {
            @Override
            public ReviewItemReference<DataSize> getValue() {
                return node.getImages().getSize();
            }
        }, Strings.Formatted.IMAGES);
        mGrid = grid;
    }

    @Override
    public void update() {
        getReferenceValue().dereference(new DataReference.DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(DataValue<DataSize> value) {
                if(value.hasValue()) {
                    int size = value.getData().getSize();
                    if(size > 0) {
                        showGrid();
                    } else {
                        showPlaceholder();
                    }
                }
            }
        });
    }

    private void showPlaceholder() {
        TextView placeholder = getValueView();
        placeholder.setVisibility(View.VISIBLE);
        placeholder.setTypeface(placeholder.getTypeface(), Typeface.ITALIC);
        placeholder.setText(Strings.Formatted.NONE);
    }

    private void showGrid() {
        getValueView().setVisibility(View.GONE);
        mGrid.update();
    }
}