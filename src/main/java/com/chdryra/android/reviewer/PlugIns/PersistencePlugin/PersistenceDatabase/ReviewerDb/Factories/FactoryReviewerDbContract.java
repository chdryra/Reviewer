package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.ReviewerDbContractImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbContract {
    private FactoryDbColumnDef mColumnFactory;
    private FactoryForeignKeyConstraint mFkFactory;

    public FactoryReviewerDbContract(FactoryDbColumnDef columnFactory,
                                     FactoryForeignKeyConstraint fkFactory) {
        mColumnFactory = columnFactory;
        mFkFactory = fkFactory;
    }

    public ReviewerDbContract newContract() {
        TableAuthors authors = new TableAuthors(mColumnFactory);
        TableTags tags = new TableTags(mColumnFactory);
        TableReviews reviews = new TableReviews(mColumnFactory, authors, mFkFactory);
        TableComments comments = new TableComments(mColumnFactory, reviews, mFkFactory);
        TableFacts facts = new TableFacts(mColumnFactory, reviews, mFkFactory);
        TableImages images = new TableImages(mColumnFactory, reviews, mFkFactory);
        TableLocations locations = new TableLocations(mColumnFactory, reviews, mFkFactory);

        String id = RowReview.REVIEW_ID.getName();

        return new ReviewerDbContractImpl(id, authors, tags, reviews, comments, facts, images,
                locations);
    }

}
