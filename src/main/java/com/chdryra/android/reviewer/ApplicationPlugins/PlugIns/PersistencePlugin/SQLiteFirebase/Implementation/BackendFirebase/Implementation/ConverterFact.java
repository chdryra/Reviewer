/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Fact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterFact implements ListItemConverter<DataFact> {
    @Override
    public DataFact convert(ReviewId id, DataSnapshot snapshot) {
        Fact value = snapshot.getValue(Fact.class);
        return value == null ? new DatumFact(id) : newUrlOrFact(id, value);
    }

    @NonNull
    private DataFact newUrlOrFact(ReviewId id, Fact fact) {
        String label = fact.getLabel();
        String value = fact.getValue();
        if(fact.isUrl()) {
            try {
                return new DatumUrl(id, label, new URL(value));
            } catch (MalformedURLException e) {
                return new DatumFact(id, label, value);
            }
        } else {
            return new DatumFact(id, label, value);
        }
    }
}