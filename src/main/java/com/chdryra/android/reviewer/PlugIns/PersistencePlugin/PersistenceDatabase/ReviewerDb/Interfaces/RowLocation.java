package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowLocation extends ReviewDataRow, DataLocation {
    String COLUMN_LOCATION_ID = "location_id";
    String COLUMN_REVIEW_ID = "review_id";
    String COLUMN_LATITUDE = "latitude";
    String COLUMN_LONGITUDE = "longitude";
    String COLUMN_NAME = "name";
    
    @Override
    LatLng getLatLng();

    @Override
    String getName();

    @Override
    ReviewId getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}
