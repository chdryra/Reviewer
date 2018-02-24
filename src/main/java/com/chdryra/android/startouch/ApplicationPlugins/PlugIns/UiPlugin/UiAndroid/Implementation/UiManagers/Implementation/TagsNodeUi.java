/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.graphics.Typeface;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsNodeUi extends ViewUi<TextView, DataListRef<DataTag>> implements Bindable<IdableList<DataTag>> {
    private static final int MAX_TAGS = 10;
    private ViewUiBinderOld<IdableList<DataTag>> mBinder;

    public TagsNodeUi(TextView tags, final ReviewNode node) {
        super(tags, new ReferenceValueGetter<DataListRef<DataTag>>() {
            @Override
            public DataListRef<DataTag> getValue() {
                return node.getTags();
            }
        });
        mBinder = new ViewUiBinderOld<>(this);
    }

    @Override
    public void update(IdableList<DataTag> value) {
        setView(value);
    }

    @Override
    public void onInvalidated() {
        setView(new IdableDataList<DataTag>(getReferenceValue().getReviewId()));
    }

    @Override
    public void update() {
        mBinder.bind();
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
