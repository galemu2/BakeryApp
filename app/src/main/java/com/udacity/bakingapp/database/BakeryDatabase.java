package com.udacity.bakingapp.database;


import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = BakeryDatabase.VERSION )
public class BakeryDatabase {


    public static final int VERSION = 1;

    @Table(BakeryContract.class)
    public static final String BAKERY = "favPastry";
}
