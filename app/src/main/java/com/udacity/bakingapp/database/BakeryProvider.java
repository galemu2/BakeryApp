package com.udacity.bakingapp.database;


import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;
/*,
        packageName = "com.udacity.bakingapp.database.provider"*/
@ContentProvider(authority = BakeryProvider.AUTHORITY, database = BakeryDatabase.class)
public class BakeryProvider {


    public static final String AUTHORITY = "com.udacity.bakingapp.database.provider";

    @TableEndpoint(table = BakeryDatabase.BAKERY)
    public static class Bakery {

        @ContentUri(path = "favPastry",
                type = "vnd.android.cursor.dir/list",
                defaultSort = BakeryContract._ID + " ASC")
        public static final Uri BAKERY = Uri.parse("content://" + AUTHORITY+"/favPastry");
    }

}
