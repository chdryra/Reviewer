/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.LeafDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemCounter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {

    private <T extends HasReviewId> VisitorItemGetter<T> newItemCollector(NodeDataGetter<T> getter) {
        return new VisitorItemGetter<>(getter);
    }

    public VisitorItemGetter<DataSubject> newSubjectsCollector() {
        return newItemCollector(new LeafDataGetter.SubjectGetter());
    }

    public VisitorItemGetter<DataAuthorId> newAuthorsCollector() {
        return newItemCollector(new LeafDataGetter.AuthorGetter());
    }

    public VisitorItemGetter<DataDate> newDatesCollector() {
        return newItemCollector(new LeafDataGetter.DateGetter());
    }

    public VisitorItemGetter<ReviewReference> newLeavesCollector() {
        return newItemCollector(new LeafDataGetter.LeafGetter());
    }

    public VisitorItemCounter<String> newSubjectsCounter() {
        return new VisitorItemCounter.SubjectsCounter();
    }

    public VisitorItemCounter<AuthorId> newAuthorsCounter() {
        return new VisitorItemCounter.AuthorsCounter();
    }

    public VisitorItemCounter<String> newDatesCounter() {
        return new VisitorItemCounter.DatesCounter();
    }

    public VisitorItemCounter<ReviewId> newReviewsCounter() {
        return new VisitorItemCounter.ReviewsCounter();
    }

}
