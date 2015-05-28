package com.redsquirrel.neogrifter.notifikation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Accès à la base de données.
 * Niveau purement BDD.
 */
public class FeedReaderDb extends SQLiteOpenHelper {

    /**
     * Version du schéma.
     * A mettre à jour après chaque modification de structure.
     * TODO remettre à 1 pour la "release"
     */
    private static final int DATABASE_VERSION = 2;

    /**
     * Nom de la base de données de l'application
     */
    private static final String DATABASE_NAME = "FeedReaderNotifikation.db";

    /**
     * Valeurs techniques pour la gestion de tables
     */
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String LONG_TYPE = " LONG";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY = " PRIMARY KEY";

    /**
     * Création de la table des notifications
     */
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedNotification.FeedEntry.TABLE_NAME + " (" +
            FeedNotification.FeedEntry._ID + INTEGER_TYPE + PRIMARY + COMMA_SEP +
            FeedNotification.FeedEntry.COLUMN_NAME_ICON_ID + INTEGER_TYPE + COMMA_SEP +
            FeedNotification.FeedEntry.COLUMN_NAME_LABEL + TEXT_TYPE + COMMA_SEP +
            FeedNotification.FeedEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            FeedNotification.FeedEntry.COLUMN_NAME_TIMER + LONG_TYPE +
            " )";

    /**
     * Suppression de la table des notifications
     */
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedNotification.FeedEntry.TABLE_NAME;

    /**
     * Création du Feeder
     *
     * @param context contexte de l'application
     */
    protected FeedReaderDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Création de la table
     *
     * @param db base de données
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Mise à jour de la table
     *
     * @param db         base de données
     * @param oldVersion ancienne version
     * @param newVersion nouvelle version
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Les données ne sont pas sensibles (elles sont même supposées être temporaires)
        // On peut se permettre d'effacer et recommencer.
        // TODO quelque chose de plus propre ?
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Retour en arrière dans les versions de l'application
     *
     * @param db         base de données
     * @param oldVersion ancienne version
     * @param newVersion nouvelle version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Insérer une nouvelle notification dans la base
     *
     * @param pValues données de la nouvelle notification
     * @return ID de la nouvelle notification
     */
    protected long putDb(ContentValues pValues) {
        // Récupérer la base de données
        SQLiteDatabase db = this.getWritableDatabase();

        // Insérer les données et renvoyer l'identifiant unique créé.
        return db.insert(
                FeedNotification.FeedEntry.TABLE_NAME,
                FeedNotification.FeedEntry.COLUMN_NAME_LABEL,       // TODO NULLABLE ?
                pValues);
    }

    /**
     * Suppression d'un élément de la base
     * @param pId ID de la notification
     * @return true si processus correct
     */
    protected boolean delete(Integer pId) {
        // Récupérer la base de données
        SQLiteDatabase db = this.getWritableDatabase();
        // Suppression
        return db.delete(FeedNotification.FeedEntry.TABLE_NAME, FeedNotification.FeedEntry._ID + "=" + pId, null) > 0;
    }

    /**
     * Lecture de la base de données
     * @param selection condition WHERE
     * @param selectionArgs variables utilisées par la condition WHERE
     * @return données correspondantes
     */
    protected Cursor readDb(String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Les colonnes qui doivent etre lues
        String[] projection = {
                FeedNotification.FeedEntry._ID,
                FeedNotification.FeedEntry.COLUMN_NAME_ICON_ID,
                FeedNotification.FeedEntry.COLUMN_NAME_LABEL,
                FeedNotification.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedNotification.FeedEntry.COLUMN_NAME_TIMER
        };

        // On classe les données par ID
        String sortOrder = FeedNotification.FeedEntry._ID + " DESC";

        return db.query(
                FeedNotification.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

    }
}