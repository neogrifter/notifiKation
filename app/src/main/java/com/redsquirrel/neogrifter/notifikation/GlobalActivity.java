package com.redsquirrel.neogrifter.notifikation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.redsquirrel.neogrifter.notifikation.model.Notification;
import com.redsquirrel.neogrifter.notifikation.model.NotificationAdapter;

/**
 * Activité de la page de départ / page principale
 * - Affichage d'une liste de notifications en cours
 * - Modification ou suppression de ces notifications
 * - Bouton pour créer une nouvelle notification
 * - Menu de l'ActionBar
 */
public class GlobalActivity extends ActionBarActivity {

    /**
     * Objet pour la gestion de l'affichage des éléments de la liste
     */
    private NotificationAdapter adapter;

    /**
     * Actions à la création de la page
     * (au démarrage, donc)
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
    }

    /**
     * Action au demarrage de la page
     * (création + retour sur la page)
     * >> Mise à jour de la liste
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Accès à la base de données
        SavedData loading = new SavedData(this);
        Notification[] liste = loading.loadDatas();

        // Adaptation du format pour la liste
        adapter = new NotificationAdapter(this, liste);

        // Mise à jour de la liste
        final ListView listview = (ListView) findViewById(R.id.notifications_listview);
        listview.setAdapter(adapter);
    }

    /**
     * Création du menu de l'ActionBar
     * @param menu menu de l'ActionBar
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_global, menu);
        return true;
    }

    /**
     * Sélection d'une option du menu de l'ActionBar.
     * @param item option choisie
     * @return booleen
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Quelle option du menu ?
        int id = item.getItemId();

        if (id == R.id.action_about) {
            // Option "A Propos"
            Intent intent = new Intent(GlobalActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        // Pas d'autres pour l'instant...

        return super.onOptionsItemSelected(item);
    }

    /**
     * Bouton : création d'une nouvelle notification
     * Démarrage d'une nouvelle page / activité
     * @param v vue actuelle
     */
    public void addNewNotification(View v) {
        Intent intent = new Intent(GlobalActivity.this, UpdateActivity.class);
        startActivity(intent);
    }

    /**
     * Bouton : modification d'une notification existante.
     * @param v vue actuelle
     */
    public void updateNotification(View v) {
        System.out.println("Vous avez appuyé sur MODIFICATION");
        System.out.println("Votre cible = " + v.getTag());
        /*
        Notification notifToUpdate = (Notification) v.getTag();
        // TODO envoyer les infos dans le update
        System.out.println("UPDATE DE " + notifToUpdate.toString());
        */
    }

    /**
     * Id pour contenir la position de la notification
     * et la transmettre dans le **###*** Listener.
     * C'est officiel : je déteste ces trucs.
     */
    private int tmpPos;

    /**
     * Bouton : suppression d'une notification existante.
     * @param v vue actuelle
     */
    public void deleteNotification(View v) {
        tmpPos = (int) v.getTag();

        DialogInterface.OnClickListener deleteDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteNotification(tmpPos);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Non, donc on ne fait rien
                        break;
                }
            }
        };

        // Affichage de l'alerte
        String texte = getString(R.string.notif_list_remove_ask);
        String oui = getString(R.string.notif_list_remove_yes);
        String non = getString(R.string.notif_list_remove_no);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(texte).setPositiveButton(oui, deleteDialogClickListener)
                .setNegativeButton(non, deleteDialogClickListener)
                .show();
    }

    private void deleteNotification(int pPosition) {
        Notification notifToRemove = adapter.getItem(pPosition);

        // Supprimer de la base
        SavedData deleting = new SavedData(this);
        deleting.removeData(notifToRemove);

        // TODO suppression de AlarmManager

        this.onStart();
    }

}
