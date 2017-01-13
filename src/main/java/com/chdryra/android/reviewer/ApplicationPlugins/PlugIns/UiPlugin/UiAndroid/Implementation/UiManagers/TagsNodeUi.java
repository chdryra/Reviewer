/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Typeface;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsNodeUi extends ViewUi<TextView, RefDataList<DataTag>> {
    private static final int MAX_TAGS = 10;
    private boolean mIsBound = false;

    public TagsNodeUi(TextView tags, final ReviewNode node) {
        super(tags, new ValueGetter<RefDataList<DataTag>>() {
            @Override
            public RefDataList<DataTag> getValue() {
                return node.getTags();
            }
        });
    }

    @Override
    public void update() {
        if(!mIsBound) {
            mIsBound = true;
            getValue().dereference(new DataReference.DereferenceCallback<IdableList<DataTag>>() {
                @Override
                public void onDereferenced(DataValue<IdableList<DataTag>> value) {
                    if (value.hasValue()) setView(value.getData());
                }
            });
        }
    }

    private void setView(IdableList<DataTag> data) {
        if(data.size() > 0) {
            getView().setText(DataFormatter.formatTags(data, MAX_TAGS, null));
        } else {
            getView().setTypeface(getView().getTypeface(), Typeface.ITALIC);
            getView().setText(Strings.Formatted.NO_TAGS);
        }
    }
}
