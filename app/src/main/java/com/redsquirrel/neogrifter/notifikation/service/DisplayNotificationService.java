package com.redsquirrel.neogrifter.notifikation.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.redsquirrel.neogrifter.notifikation.model.Notification;

import java.io.Serializable;

/**
 * Date planifiée atteinte : ce service crée la notification
 */
public class DisplayNotificationService extends Service {

    public class ServiceBinder extends Binder {
        DisplayNotificationService getService() {
            return DisplayNotificationService.this;
        }
    }

    /**
     * Tag pour relier le service à l'application NotifiKation
     */
    public static final String INTENT_NOTIFICATION = "com.redsquirrel.neogrifter.notifikation.NOTIFICATION";
    public static final String INTENT_NOTIFICATION_DATA = "com.redsquirrel.neogrifter.notifikation.NOTIFICATION_DATA";

    /**
     * Manager Android pour créer des notifications
     */
    private NotificationManager mNM;

    /**
     * Création du service
     */
    @Override
    public void onCreate() {
        Log.i("DisplayNotif.Service", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * Démarrage du service
     * @param intent intent
     * @param flags flags
     * @param startId ID
     * @return constante
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // Le service a été démarré pour la création de notification > action, donc !
        if(intent.getBooleanExtra(INTENT_NOTIFICATION, false)) {
            showNotification(intent.getSerializableExtra(INTENT_NOTIFICATION_DATA));
        }

        return START_NOT_STICKY;
    }

    /**
     * Bind
     * @param intent intent
     * @return valeur
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // interraction avec les clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Création de la notification
     * @param pNotification notification à déclencher
     */
    private void showNotification(Serializable pNotification) {

        Notification currentNotif = (Notification) pNotification;

        // Créer la notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(currentNotif.getIconId())
                .setContentTitle(currentNotif.getLabel())
                .setContentText(currentNotif.getDescription())
                .setWhen(currentNotif.getDisplayTiming());

        // Affichage
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(currentNotif.getId(), mBuilder.build());

        // Stopper les services.
        stopSelf();
    }
}