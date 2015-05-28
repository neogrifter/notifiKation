package com.redsquirrel.neogrifter.notifikation.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.redsquirrel.neogrifter.notifikation.model.Notification;

/**
 * Tache : Planification de la notification via AlarmManager
 */
public class AlarmTask implements Runnable{

    /**
     * Notification en cours de traitement
     */
    private final Notification notification;

    /**
     * Manager Android pour planification
     */
    private final AlarmManager manager;

    /**
     * Contexte de l'application actuelle
     */
    private final Context context;

    /**
     * Constructeur
     * @param context contexte de l'application
     * @param pNotif notification à traiter
     */
    public AlarmTask(Context context, Notification pNotif) {
        this.context = context;
        this.manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.notification = pNotif;
    }

    /**
     * Execution de la tache
     */
    @Override
    public void run() {
        Intent intent = new Intent(context, DisplayNotificationService.class);
        intent.putExtra(DisplayNotificationService.INTENT_NOTIFICATION, true);
        intent.putExtra(DisplayNotificationService.INTENT_NOTIFICATION_DATA, notification);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        // Planifier l'alarme.
        // ATTENTION : notification perdue si l'appareil est éteint et rallumé.
        // TODO quelque chose pour relancer les alarmes au démarrage de l'appareil ?
        manager.set(AlarmManager.RTC, notification.getDisplayTiming(), pendingIntent);
    }
}