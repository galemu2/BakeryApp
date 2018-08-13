package com.udacity.bakingapp.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface BakeryContract {

    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT) @NotNull
    String ITEM = "itemName";

    @DataType(TEXT) @NotNull
    String INGREDIENT = "ingredientName";

    @DataType(TEXT) @NotNull
    String UNIT = "unit";

    @DataType(TEXT) @NotNull
    String QUANTITY = "ingredientQuantity";



}
