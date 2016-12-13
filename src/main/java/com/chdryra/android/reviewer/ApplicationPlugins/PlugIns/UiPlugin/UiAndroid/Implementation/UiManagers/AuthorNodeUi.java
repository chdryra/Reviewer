/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.annotation.NonNull;
import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class AuthorNodeUi extends TextUi<TextView> {
    private NamedAuthor mNamedAuthor;

    public AuthorNodeUi(TextView view, AuthorReference author) {
        super(view, new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                return "";
            }
        });
        author.dereference(setAuthorAndUpdate());
    }

    @Override
    public String getValue() {
        return mNamedAuthor != null ? mNamedAuthor.getName() : "";
    }

    @NonNull
    private DataReference.DereferenceCallback<NamedAuthor> setAuthorAndUpdate() {
        return new DataReference.DereferenceCallback<NamedAuthor>() {
            @Override
            public void onDereferenced(DataValue<NamedAuthor> value) {
                if (value.hasValue()) {
                    mNamedAuthor = value.getData();
                    AuthorNodeUi.this.update();
                }
            }
        };
    }

}
