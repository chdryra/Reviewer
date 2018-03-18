/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
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
    private final ClusterManager<ReviewClusterItem> mClusterManager;
    private final ReviewsRepoReadable mRepo;

    private int mCounter = 0;

    private interface ItemCallback {
        void onItem(@Nullable ReviewClusterItem item);
    }

    public LocationPlotter(DataListRef<DataLocation> locations,
                           ClusterManager<ReviewClusterItem> clusterManager,
                           ReviewsRepoReadable repo) {
        mLocations = locations;
        mClusterManager = clusterManager;
        mRepo = repo;
    }

    public void unbind() {
        mLocations.unsubscribe(this);
    }

    public void bind() {
        mLocations.subscribe(this);
    }

    @Override
    public void onItemAdded(DataLocation location) {
        asItem(location, new ItemCallback() {
            @Override
            public void onItem(@Nullable ReviewClusterItem item) {
                if (item != null) {
                    mClusterManager.addItem(item);
                    mClusterManager.cluster();
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
        mCounter = 0;
        for (DataLocation location : newLocations) {
            asItem(location, new ItemCallback() {
                @Override
                public void onItem(@Nullable ReviewClusterItem item) {
                    mClusterManager.addItem(item);
                    if (++mCounter == newLocations.size()) {
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
