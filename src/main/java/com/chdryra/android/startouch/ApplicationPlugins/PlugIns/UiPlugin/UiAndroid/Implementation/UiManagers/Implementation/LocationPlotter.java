/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.ClusterManager;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/03/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationPlotter implements CollectionReference
        .ItemSubscriber<DataLocation> {
    private final DataListRef<DataLocation> mLocations;
    private final GoogleMap mMap;
    private final ClusterManager<ReviewClusterItem> mClusterManager;
    private final ReviewsRepoReadable mRepo;

    private int mInitialSize = 0;
    private int mInitialCounter = 0;
    private int mCollectionCounter = 0;
    private LatLngBounds.Builder mBuilder;

    private interface ItemCallback {
        void onItem(@Nullable ReviewClusterItem item);
    }

    public LocationPlotter(DataListRef<DataLocation> locations,
                           GoogleMap map,
                           ClusterManager<ReviewClusterItem> clusterManager,
                           ReviewsRepoReadable repo) {
        mLocations = locations;
        mMap = map;
        mClusterManager = clusterManager;
        mRepo = repo;
    }

    public void unbind() {
        mLocations.unsubscribe(this);
    }

    public void bind() {
        mInitialSize = 0;
        mInitialCounter = 0;
        mLocations.getSize().dereference(new DataReference.DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(DataValue<DataSize> value) {
                if(value.hasValue()) {
                    mInitialSize = value.getData().getSize();
                    mBuilder = mInitialSize > 0 ? new LatLngBounds.Builder() : null;
                }
                mLocations.subscribe(LocationPlotter.this);
            }
        });
    }

    @Override
    public void onItemAdded(final DataLocation location) {
        asItem(location, new ItemCallback() {
            @Override
            public void onItem(@Nullable ReviewClusterItem item) {
                if (item != null) {
                    mClusterManager.addItem(item);
                    mClusterManager.cluster();
                    if(mBuilder != null) mBuilder.include(location.getLatLng());
                }

                if(mBuilder != null && ++mInitialCounter >= mInitialSize) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mBuilder.build(), 100));
                }
            }
        });
    }

    @Override
    public void onItemRemoved(DataLocation location) {
        asItem(location, new ItemCallback() {
            @Override
            public void onItem(@Nullable ReviewClusterItem item) {
                if (item != null) {
                    mClusterManager.removeItem(item);
                    mClusterManager.cluster();
                }
            }
        });
    }

    @Override
    public void onCollectionChanged(final Collection<DataLocation> newLocations) {
        mClusterManager.clearItems();
        mCollectionCounter = 0;
        for (DataLocation location : newLocations) {
            asItem(location, new ItemCallback() {
                @Override
                public void onItem(@Nullable ReviewClusterItem item) {
                    mClusterManager.addItem(item);
                    if (++mCollectionCounter == newLocations.size()) {
                        mClusterManager.cluster();
                    }
                }
            });
        }
    }

    @Override
    public void onInvalidated(CollectionReference<DataLocation, ?, ?> reference) {
        mClusterManager.clearItems();
        mClusterManager.cluster();
    }

    private void asItem(final DataLocation location, final ItemCallback callback) {
        mRepo.getReference(location.getReviewId(), new
                RepoCallback() {
                    @Override
                    public void onRepoCallback(RepoResult result) {
                        if (result.isReference()) {
                            callback.onItem(new ReviewClusterItem(result.getReference(),
                                    location));
                        }
                    }
                });
    }
}
