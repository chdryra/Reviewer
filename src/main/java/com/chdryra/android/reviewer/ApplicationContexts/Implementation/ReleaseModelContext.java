/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {

    public ReleaseModelContext(Context context,
                               DataAuthor author,
                               PersistencePlugin persistencePlugin) {
        setReviewsFactory(author);

        setTagsManager(new FactoryTagsManager().newTagsManager());

        setDataValidator(new DataValidator());

        setVisitorsFactory(new FactoryVisitorReviewNode());

        setTreeTraversersFactory(new FactoryNodeTraverser());
    }

    private void setReviewsFactory(DataAuthor author) {
        ConverterMd converter = new FactoryMdConverter().newMdConverter();
        FactoryReviews factoryReviews = new FactoryReviews(new FactoryReviewNode(), converter);
        factoryReviews.setAuthorsStamp(new AuthorsStamp(author));
        setFactoryReviews(factoryReviews);
    }
}
