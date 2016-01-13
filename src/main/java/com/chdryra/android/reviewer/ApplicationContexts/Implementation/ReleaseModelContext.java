package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Model.Factories.FactorySocialPlatformList;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryTreeFlattener;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;

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

        setSocialPlatforms(new FactorySocialPlatformList().newList(context));

        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository();

        ReviewsRepositoryMutable persistence
                = persistencePlugin.newPersistenceRepository(context, this);

        TreeFlattener flattener = getTreeFlattener(getVisitorsFactory(), getNodeTraversersFactory());
        setAuthorsFeed(persistence, getReviewsFactory(), flattener);

        setReviewsSource(repoFactory.newReviewsSource(persistence, getReviewsFactory(), flattener));
    }

    private TreeFlattener getTreeFlattener(FactoryVisitorReviewNode visitorFactory,
                                           FactoryNodeTraverser traverserFactory) {
        FactoryTreeFlattener flattenerFactory = new FactoryTreeFlattener(visitorFactory,
                traverserFactory);
        return flattenerFactory.newFlattener();
    }
    private void setAuthorsFeed(ReviewsRepositoryMutable sourceAndDestination,
                                FactoryReviews reviewsFactory,
                                TreeFlattener flattener) {
        FactoryReviewsFeed feedFactory = new FactoryReviewsFeed(reviewsFactory, flattener);
        ReviewsFeedMutable reviewsFeed = feedFactory.newMutableFeed(sourceAndDestination);

        setAuthorsFeed(reviewsFeed);
    }

    private void setReviewsFactory(DataAuthor author) {
        FactoryReviewPublisher publisherFactory = new FactoryReviewPublisher(author);
        ConverterMd converter = new FactoryMdConverter().newMdConverter();
        setFactoryReviews(new FactoryReviews(publisherFactory, new FactoryReviewNode(), converter));
    }
}
