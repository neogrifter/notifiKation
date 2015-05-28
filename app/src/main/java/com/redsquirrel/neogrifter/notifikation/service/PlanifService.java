package com.redsquirrel.neogrifter.notifikation.service;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.redsquirrel.neogrifter.notifikation.model.Notification;

/**
 * Service qui va planifier le moment de l'envoi de la notification
 */
public class PlanifService extends Service {

    public class ServiceBinder extends Binder {
        PlanifService getService() {
            return PlanifService.this;
        }
    }

    /**
     * Action au demarrage
     * @param intent intent
     * @param flags flags
     * @param startId Id
     * @return constante
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("PlanifService", "Received start id " + startId + ": " + intent);

        // We want this service to continue running until it is explicitly stopped, so return sticky.
        return START_STICKY;
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

    // Liaison avec les clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Définir la notification à lancer
     * @param pNotif notification en cours
     */
    public void setNotification(Notification pNotif) {
        new AlarmTask(this, pNotif).run();
    }
}
