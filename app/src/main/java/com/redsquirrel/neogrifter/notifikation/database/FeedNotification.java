package com.redsquirrel.neogrifter.notifikation.database;

import android.provider.BaseColumns;

/**
 * Objet pour les noms de colonnes de la notification
 */
public class FeedNotification {

    /**
     * Constructeur
     */
    public FeedNotification() {}

    /**
     * Définition de la table pour les notifications
     */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "notification";
        public static final String COLUMN_NAME_ICON_ID = "iconid";
        public static final String COLUMN_NAME_LABEL = "label";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TIMER = "timestamp";
    }

}
