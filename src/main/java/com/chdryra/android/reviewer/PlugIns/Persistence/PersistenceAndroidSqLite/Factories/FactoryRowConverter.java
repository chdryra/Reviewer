package com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Factories;

import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.RowAuthorConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.RowCommentConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Interfaces.RowConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.RowFactConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.RowImageConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.RowLocationConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.RowReviewConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.RowTagConverter;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;

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