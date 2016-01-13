package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.RowAuthorConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.RowCommentConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Interfaces.RowConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.RowFactConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.RowImageConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.RowLocationConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.RowReviewConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.RowTagConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryRowConverter {
    private Map<Class<? extends DbTableRow>, RowConverter<? extends DbTableRow>> mMap;

    public FactoryRowConverter() {
        mMap = new HashMap<>();
        mMap.put(RowReview.class, new RowReviewConverter());
        mMap.put(RowAuthor.class, new RowAuthorConverter());
        mMap.put(RowImage.class, new RowImageConverter());
        mMap.put(RowComment.class, new RowCommentConverter());
        mMap.put(RowLocation.class, new RowLocationConverter());
        mMap.put(RowFact.class, new RowFactConverter());
        mMap.put(RowTag.class, new RowTagConverter());
    }

    public <T extends DbTableRow> RowConverter<T> newConverter(Class<T> rowClass) {
        return (RowConverter<T>) mMap.get(rowClass);
    }

}
