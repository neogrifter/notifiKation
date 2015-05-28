package com.redsquirrel.neogrifter.notifikation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.redsquirrel.neogrifter.notifikation.model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Pont entre l'application (objet Notification) et la base de données.
 */
public class FeedReaderNotification extends FeedReaderDb {

    /**
     * Constructeur
     * @param context contexte de l'application
     */
    public FeedReaderNotification(Context context) {
        super(context);
    }

    /**
     * Insérer un nouvel objet dans la base de données
     * @param pNotif nouvelle notification
     * @return ID unique de la notification
     */
    public long put(Notification pNotif) {
        // Valeurs de l'objet. Les noms de colonnes sont les clés.
        ContentValues values = new ContentValues();
        values.put(FeedNotification.FeedEntry.COLUMN_NAME_ICON_ID, pNotif.getIconId());
        values.put(FeedNotification.FeedEntry.COLUMN_NAME_LABEL, pNotif.getLabel());
        values.put(FeedNotification.FeedEntry.COLUMN_NAME_DESCRIPTION, pNotif.getDescription());
        values.put(FeedNotification.FeedEntry.COLUMN_NAME_TIMER, pNotif.getDisplayTiming());

        return putDb(values);
    }

    /**
     * Suppression d'un élément de la base
     * @param pId ID de la notification
     * @return true si processus correct
     */
    public boolean remove(Integer pId) {
        return delete(pId);
    }

    /**
     * Récupération de toutes les notifications en base.
     * @return tableau des notifications
     */
    public Notification[] readAll() {
        Cursor cursor = readDb(null, null);

        boolean hasNext = cursor.moveToFirst();
        List<Notification> liste = new ArrayList<>();

        while(hasNext) {
            liste.add(cursorToNotification(cursor));
            hasNext = cursor.moveToNext();
        }

        return liste.toArray(new Notification[liste.size()]);
    }

    /**
     * Récupération d'une notification précise.
     * @param pId ID de la notification
     * @return Notification demandée
     */
    public Notification readById(Integer pId) {
        String selection = FeedNotification.FeedEntry._ID + " = ?s";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = Integer.toString(pId);
        Cursor cursor = readDb(selection, selectionArgs);

        if (cursor.moveToFirst()) {
            // Notification trouvée
            return cursorToNotification(cursor);
        }
        else {
            // Pas de notification pour cet ID
            return null;
        }
    }

    /**
     * Convertir le curseur SQL en objet applicatif
     * @param pCursor curseur contenant les infos de la base
     * @return objet Notification
     */
    private Notification cursorToNotification(Cursor pCursor) {
        int notifId = pCursor.getInt(pCursor.getColumnIndexOrThrow(FeedNotification.FeedEntry._ID));
        int iconId = pCursor.getInt(pCursor.getColumnIndexOrThrow(FeedNotification.FeedEntry.COLUMN_NAME_ICON_ID));
        String label = pCursor.getString(pCursor.getColumnIndexOrThrow(FeedNotification.FeedEntry.COLUMN_NAME_LABEL));
        String description = pCursor.getString(pCursor.getColumnIndexOrThrow(FeedNotification.FeedEntry.COLUMN_NAME_DESCRIPTION));
        long timer = pCursor.getLong(pCursor.getColumnIndexOrThrow(FeedNotification.FeedEntry.COLUMN_NAME_TIMER));

        return new Notification(notifId, iconId, label, description, timer);
    }
}
