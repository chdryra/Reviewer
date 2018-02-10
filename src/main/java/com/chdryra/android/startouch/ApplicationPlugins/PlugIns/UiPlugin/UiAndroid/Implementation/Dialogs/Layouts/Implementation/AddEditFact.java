/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;

import com.chdryra.android.corelibrary.TextUtils.TextUtils;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.R;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditFact extends AddEditLayoutBasic<GvFact> {
    private static final int LAYOUT = R.layout.dialog_fact_add_edit;
    private static final int LABEL = R.id.fact_label_edit_text;
    private static final int VALUE = R.id.fact_value_edit_text;

    public AddEditFact(GvDataAdder adder) {
        super(GvFact.class, new LayoutHolder(LAYOUT, LABEL, VALUE), VALUE, adder);
    }

    public AddEditFact(GvDataEditor editor) {
        super(GvFact.class, new LayoutHolder(LAYOUT, LABEL, VALUE), VALUE, editor);
    }

    @Override
    public GvFact createGvDataFromInputs() {
        String label = ((EditText) getView(LABEL)).getText().toString().trim();
        String value = ((EditText) getView(VALUE)).getText().toString().trim();
        ArrayList<String> urls = TextUtils.getLinks(value);

        GvFact fact = null;
        if (urls.size() > 0) fact = GvFact.newFactOrUrl(label, urls.get(0));
        if (fact == null) fact = new GvFact(label, value);

        return fact;
    }

    @Override
    public void updateView(GvFact fact) {
        ((EditText) getView(LABEL)).setText(fact.getLabel());
        ((EditText) getView(VALUE)).setText(fact.getValue());
        getView(LABEL).requestFocus();
    }
}
