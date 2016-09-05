/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditTag extends AddEditLayoutBasic<GvTag> {
    private static final int LAYOUT = R.layout.dialog_tag_add_edit;
    private static final int TAG = R.id.tag_edit_text;

    public AddEditTag(GvDataAdder adder) {
        super(GvTag.class, new LayoutHolder(LAYOUT, TAG), TAG, adder);
    }

    public AddEditTag(GvDataEditor editor) {
        super(GvTag.class, new LayoutHolder(LAYOUT, TAG), TAG, editor);
    }

    @Override
    public GvTag createGvDataFromInputs() {
        return new GvTag(((EditText) getView(TAG)).getText().toString().trim());
    }

    @Override
    public void updateLayout(GvTag tag) {
        ((EditText) getView(TAG)).setText(tag.getString());
    }
}
