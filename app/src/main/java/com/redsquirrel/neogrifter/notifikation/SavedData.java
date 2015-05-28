package com.redsquirrel.neogrifter.notifikation;

import android.content.Context;

import com.redsquirrel.neogrifter.notifikation.database.FeedReaderNotification;
import com.redsquirrel.neogrifter.notifikation.model.Notification;

/**
 * Pont entre l'application Android et le système de sauvegarde des données
 */
public class SavedData {

    /**
     * Contexte de l'application courante
     */
    private Context currentContext;

    /**
     * Constructeur
     * @param pContext contexte de l'application
     */
    public SavedData(Context pContext) {
        currentContext = pContext;
    }

    /**
     * Sauvegarder cette notification
     * @param pNotif notification à sauvegarder
     * @return ID de la notification
     */
    public int saveDatas(Notification pNotif) {
        FeedReaderNotification mDbHelper = new FeedReaderNotification(currentContext);
        long idNotif = mDbHelper.put(pNotif);

        return (int) idNotif;
    }

    public boolean removeData(Notification pNotif) {
        FeedReaderNotification mDbHelper = new FeedReaderNotification(currentContext);
        return mDbHelper.remove(pNotif.getId());
    }

    /**
     * Récupère toutes les notifications
     * @return liste des notifications au format tableau.
     */
    public Notification[] loadDatas() {
        FeedReaderNotification mDbHelper = new FeedReaderNotification(currentContext);
        return mDbHelper.readAll();
    }

    /**
     * Récupère une notification
     * @param pId ID de la notification
     * @return notification correspondante
     */
    public Notification loadData(Integer pId) {
        FeedReaderNotification mDbHelper = new FeedReaderNotification(currentContext);
        return mDbHelper.readById(pId);
    }
}
