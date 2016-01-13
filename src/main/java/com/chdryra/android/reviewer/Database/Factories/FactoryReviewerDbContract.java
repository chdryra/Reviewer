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
    private FactoryForeignKeyConstraint mFkFactory;

    public FactoryReviewerDbContract(FactoryDbColumnDef columnFactory,
                                     FactoryForeignKeyConstraint fkFactory) {
        mColumnFactory = columnFactory;
        mFkFactory = fkFactory;
    }

    public ReviewerDbContract newContract(RowValueTypeDefinitions types) {
        TableAuthors authors = new TableAuthors(mColumnFactory, types);
        TableTags tags = new TableTags(mColumnFactory, types);
        TableReviews reviews = new TableReviews(mColumnFactory, types, authors, mFkFactory);
        TableComments comments = new TableComments(mColumnFactory, types, reviews, mFkFactory);
        TableFacts facts = new TableFacts(mColumnFactory, types, reviews, mFkFactory);
        TableImages images = new TableImages(mColumnFactory, types, reviews, mFkFactory);
        TableLocations locations = new TableLocations(mColumnFactory, types, reviews, mFkFactory);

        String id = RowReview.COLUMN_REVIEW_ID;

        return new ReviewerDbContractImpl(id, authors, tags, reviews, comments, facts, images,
                locations);
    }

}
