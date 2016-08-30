/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryNullData {
    private static final AuthorId NULL_USER_ID = new AuthorIdParcelable(AuthorId.NULL_ID_STRING);

    public static DataAuthor nullAuthor(ReviewId id) {
        return new DatumAuthor(id, "", NULL_USER_ID);
    }

    public static DataComment nullComment(ReviewId id) {
        return new DatumComment(id, "", false);
    }

    public static DataCriterion nullCriterion(ReviewId id) {
        return new DatumCriterion(id, "", 0f);
    }

    public static DataDate nulDate(ReviewId id) {
        return new DatumDate(id, 0l);
    }

    public static DataFact nullFact(ReviewId id) {
        return new DatumFact(id, "", "");
    }

    public static DataImage nullImage(ReviewId id) {
        return new DatumImage(id);
    }

    public static DataLocation nullLocation(ReviewId id) {
        return new DatumLocation(id);
    }

    public static DataSubject nullSubject(ReviewId id) {
        return new DatumSubject(id, "");
    }

    public static DataTag nullTag(ReviewId id) {
        return new DatumTag(id, "");
    }
}
