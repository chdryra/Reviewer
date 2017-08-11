/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class CommandsList extends ArrayList<Command> implements Collection<Command>{
    public CommandsList() {
        super();
    }

    public CommandsList(@NonNull Collection<? extends Command> c) {
        super(c);
    }

    public CommandsList(int initialCapacity) {
        super(initialCapacity);
    }

    public ArrayList<String> getCommandNames() {
        ArrayList<String> names = new ArrayList<>();
        for(Command command : this) {
            names.add(command.getName());
        }

        return names;
    }

    public void execute(String name) {
        for (Command command : this) {
            if(command.getName().equals(name)) {
                command.execute();
                break;
            }
        }
    }
}
