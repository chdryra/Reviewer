/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces;


import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation.DbEntryType;

import com.chdryra.android.mygenerallibrary.LocationServices.LocationId;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowLocation extends ReviewDataRow<RowLocation>, DataLocation {
    ColumnInfo<String> LOCATION_ID = new ColumnInfo<>("location_id", DbEntryType.TEXT);
    ColumnInfo<String> REVIEW_ID = new ColumnInfo<>("review_id", DbEntryType.TEXT);
    ColumnInfo<Double> LATITUDE = new ColumnInfo<>("latitude", DbEntryType.DOUBLE);
    ColumnInfo<Double> LONGITUDE = new ColumnInfo<>("longitude", DbEntryType.DOUBLE);
    ColumnInfo<String> NAME = new ColumnInfo<>("name", DbEntryType.TEXT);
    ColumnInfo<String> ADDRESS = new ColumnInfo<>("address", DbEntryType.TEXT);
    ColumnInfo<String> PROVIDER = new ColumnInfo<>("provider", DbEntryType.TEXT);
    ColumnInfo<String> PROVIDER_ID = new ColumnInfo<>("providers_id", DbEntryType.TEXT);

    @Override
    LatLng getLatLng();

    @Override
    String getName();

    @Override
    ReviewId getReviewId();

    @Override
    String getAddress();

    @Override
    String getShortenedName();

    @Override
    LocationId getLocationId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}
