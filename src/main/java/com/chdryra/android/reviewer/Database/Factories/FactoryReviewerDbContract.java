package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Database.Implementation.ReviewerDbContractImpl;
import com.chdryra.android.reviewer.Database.Implementation.TableAuthors;
import com.chdryra.android.reviewer.Database.Implementation.TableComments;
import com.chdryra.android.reviewer.Database.Implementation.TableFacts;
import com.chdryra.android.reviewer.Database.Implementation.TableImages;
import com.chdryra.android.reviewer.Database.Implementation.TableLocations;
import com.chdryra.android.reviewer.Database.Implementation.TableReviews;
import com.chdryra.android.reviewer.Database.Implementation.TableTags;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbContract {
    private FactoryDbColumnDef mColumnFactory;
    private FactoryForeignKeyConstraint mConstraintFactory;

    public FactoryReviewerDbContract(FactoryDbColumnDef columnFactory,
                                     FactoryForeignKeyConstraint constraintFactory) {
        mColumnFactory = columnFactory;
        mConstraintFactory = constraintFactory;
    }

    public ReviewerDbContract newContract(RowValueTypeDefinitions valueDefinitions) {
        TableAuthors authors = new TableAuthors(mColumnFactory, valueDefinitions);
        TableTags tags = new TableTags(mColumnFactory, valueDefinitions);
        TableReviews reviews = new TableReviews(mColumnFactory, valueDefinitions, authors, mConstraintFactory);
        TableComments comments = new TableComments(mColumnFactory, valueDefinitions, reviews, mConstraintFactory
        );
        TableFacts factes = new TableFacts(mColumnFactory, valueDefinitions, reviews, mConstraintFactory);
        TableImages images = new TableImages(mColumnFactory, valueDefinitions, reviews, mConstraintFactory);
        TableLocations locations = new TableLocations(mColumnFactory, valueDefinitions, reviews, mConstraintFactory);
        String id = RowReview.COLUMN_REVIEW_ID;

        return new ReviewerDbContractImpl(id, authors, tags, reviews, comments, factes, images,
                locations);
    }

}
