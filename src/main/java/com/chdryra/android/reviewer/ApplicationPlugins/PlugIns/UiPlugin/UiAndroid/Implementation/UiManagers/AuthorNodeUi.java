/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class AuthorNodeUi extends FormattedSectionUi<String> {
    private NamedAuthor mNamedAuthor;

    public AuthorNodeUi(LinearLayout section, AuthorReference author) {
        super(section, new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                return "";
            }
        }, Strings.FORMATTED.AUTHOR);
        author.dereference(setAuthorAndUpdate());
    }

    @Override
    public String getValue() {
        return mNamedAuthor != null ? mNamedAuthor.getName() : "";
    }

    @Override
    public void update() {
        getValueView().setText(getValue());
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
