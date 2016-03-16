/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Factories;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.ReviewerDbContractImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Factories.FactoryForeignKeyConstraint;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbContract {
    private FactoryDbColumnDef mColumnFactory;
    private FactoryForeignKeyConstraint mFkFactory;

    public FactoryReviewerDbContract() {
        mColumnFactory = new FactoryDbColumnDef();
        mFkFactory = new FactoryForeignKeyConstraint();
    }

    public ReviewerDbContract newContract() {
        TableAuthors authors = new TableAuthors(mColumnFactory);
        TableTags tags = new TableTags(mColumnFactory);
        TableReviews reviews = new TableReviews(mColumnFactory, authors, mFkFactory);
        TableComments comments = new TableComments(mColumnFactory, reviews, mFkFactory);
        TableFacts facts = new TableFacts(mColumnFactory, reviews, mFkFactory);
        TableImages images = new TableImages(mColumnFactory, reviews, mFkFactory);
        TableLocations locations = new TableLocations(mColumnFactory, reviews, mFkFactory);

        return new ReviewerDbContractImpl(authors, tags, reviews, comments, facts, images, locations);
    }

}
