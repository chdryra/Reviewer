/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StampNodeUi extends ViewUi<TextView, AuthorReference> implements ViewUiBinder.BindableViewUi<NamedAuthor>{
    private DataDate mDate;
    private ViewUiBinder<NamedAuthor> mBinder;

    public StampNodeUi(TextView stamp, final ReviewNode node, final AuthorsRepository repo) {
        super(stamp, new ReferenceValueGetter<AuthorReference>() {
            @Override
            public AuthorReference getValue() {
                return repo.getReference(node.getAuthorId());
            }
        });
        mBinder = new ViewUiBinder<>(this);
        mDate = node.getPublishDate();
    }

    @Override
    public void update(NamedAuthor value) {
        setView(value);
    }

    @Override
    public void onInvalidated() {
        setView(new DatumAuthor());
    }

    @Override
    public void update() {
        mBinder.bind();
    }

    private void setView(NamedAuthor author) {
        String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date(mDate
                .getTime()));
        getView().setText(author.getName() + " / " + date);
    }
}
