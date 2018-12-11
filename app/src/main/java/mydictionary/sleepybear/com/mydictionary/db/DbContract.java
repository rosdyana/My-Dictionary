/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary.db;

import android.provider.BaseColumns;

public class DbContract {
    static String TABLE_ID_EN = "table_id_en";
    static String TABLE_EN_ID = "table_en_id";

    static final class DictionaryColumns implements BaseColumns {
        static String COL_ID = "id";
        static String COL_KEYWORD = "keyword";
        static String COL_VALUE = "value";
    }
}
