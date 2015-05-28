package com.redsquirrel.neogrifter.notifikation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.redsquirrel.neogrifter.notifikation.model.Notification;
import com.redsquirrel.neogrifter.notifikation.service.PlanifClient;

import java.util.Calendar;

/**
 * Page de création d'une notification.
 *
 * TODO page de modification aussi ?
 *
 * TODO LOT 2 :
 * - notification avec progress bar (fixe ou skipable)
 * - checkbox pour activer les lights
 */
public class UpdateActivity extends Activity {

    /**
     * Les différents types d'icones
     */
    private enum NotifIcon {
        SMILEY, ALERT, CLOCK
    }

    /**
     * Icone actuellement choisie
     * Par défaut : l'horloge
     */
    private NotifIcon currentIcon = NotifIcon.CLOCK;

    /**
     * Date de planification
     * heures/minutes/secondes/années/mois/jours
     */
    private int calendarHour;
    private int calendarMinute;
    private int calendarYear;
    private int calendarMonth;
    private int calendarDay;

    /**
     * Client pour la planification de la notification créée
     * via services.
     */
    private PlanifClient planifClient;

    /**
     * A la création de la page
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // On se connecte au service pour la future planification
        planifClient = new PlanifClient(this);
        planifClient.doBindService();

        // Initialisation de la liste déroulante des types de chrono
        Spinner chronoTypes = (Spinner) findViewById(R.id.notif_chrono_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.chrono_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chronoTypes.setAdapter(adapter);

        // Initialisation de la partie timing
        setDefaultTimeOnView();
        refreshCalendarTiming();
    }

    /**
     * On quitte la page
     */
    @Override
    protected void onStop() {
        super.onStop();

        // On se déconnecte du service à l'arrêt
        if(planifClient != null)
            planifClient.doUnbindService();
    }

    /**
     * Mise à jour du champs qui affiche la date de la planification
     */
    private void refreshCalendarTiming() {

        TextView displayTime = (TextView) findViewById(R.id.chrono_display);

        // TODO gestion du format local !!!!
        displayTime.setText(
                new StringBuilder()
                        .append(dateFormat(calendarDay))
                        .append("/")
                        .append(dateFormat(calendarMonth + 1))
                        .append("/")
                        .append(calendarYear)
                        .append(" ")
                        .append(dateFormat(calendarHour))
                        .append(":")
                        .append(dateFormat(calendarMinute)));
    }

    /**
     * Au démarrage, on utilise la date du jour.
     */
    private void setDefaultTimeOnView() {
        final Calendar c = Calendar.getInstance();
        calendarYear = c.get(Calendar.YEAR);
        calendarMonth = c.get(Calendar.MONTH);
        calendarDay = c.get(Calendar.DAY_OF_MONTH);
        calendarHour = c.get(Calendar.HOUR_OF_DAY);
        calendarMinute = c.get(Calendar.MINUTE);
    }

    /**
     * Formattage de l'affichage des dates.
     * X devient 0X.
     * @param c valeur à formatter
     * @return valeur formattée
     */
    private static String dateFormat(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * Choix d'une icone
     * @param view vue actuelle
     */
    public void onIconRadioButtonClicked(View view) {
        // Bouton est activé ?
        boolean checked = ((RadioButton) view).isChecked();

        // Quel bouton ?
        switch(view.getId()) {
            case R.id.radio_icon_time:
                if (checked) {
                    // Icone horloge
                    currentIcon = NotifIcon.CLOCK;
                }
                break;
            case R.id.radio_icon_smiley:
                if (checked) {
                    // Icone smiley
                    currentIcon = NotifIcon.SMILEY;
                }
                break;
            case R.id.radio_icon_alert:
                if (checked) {
                    // Icone alerte
                    currentIcon = NotifIcon.ALERT;
                }
                break;
        }
    }

    /**
     * Choix d'un type de planification
     * @param view vue actuelle
     */
    public void onChronoRadioButtonClicked(View view) {
        // Bouton est activé ?
        boolean checked = ((RadioButton) view).isChecked();

        LinearLayout layoutCountdown = (LinearLayout)findViewById(R.id.chronoCountdownLayout);
        LinearLayout layoutCalendar = (LinearLayout)findViewById(R.id.chronoCalendarLayout);

        // Quel bouton ?
        switch(view.getId()) {
            case R.id.radio_chrono_calendar:
                if (checked) {
                    // Calendrier actif (Countdown off)
                    layoutCountdown.setVisibility(View.GONE);
                    layoutCalendar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radio_chrono_countdown:
                if (checked) {
                    // Countdown actif (Calendrier Off)
                    layoutCountdown.setVisibility(View.VISIBLE);
                    layoutCalendar.setVisibility(View.GONE);
                }
                break;
        }
    }

    /**
     * Affichage du Picker pour choix de l'heure
     * @param pView vue actuelle
     */
    public void showTimePickerDialog(View pView) {
        TimePickerDialog picker = new TimePickerDialog(this, timePickerListener, calendarHour, calendarMinute, DateFormat.is24HourFormat(this));
        picker.show();
    }

    /**
     * Affichage du Picker pour choix du jour
     * @param pView vue actuelle
     */
    public void showDatePickerDialog(View pView) {
        DatePickerDialog picker = new DatePickerDialog(this, datePickerListener, calendarYear, calendarMonth, calendarDay);
        picker.show();
    }

    /**
     * Listener pour récupérer
     * le choix d'une heure via le Picker
     */
    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    calendarHour = selectedHour;
                    calendarMinute = selectedMinute;

                    refreshCalendarTiming();
                }
            };

    /**
     * Listener pour récupérer
     * le choix d'un jour via le Picker
     */
    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    // = (view, year, month, day) -> {
                    // TODO check expressions lambda ?
                    calendarYear = year;
                    calendarMonth = month;
                    calendarDay = day;

                    refreshCalendarTiming();
                }
            };

    /**
     * Création de la notification
     * @param pView vue actuelle
     */
    public void updateNotification(View pView)
    {
        // TODO verification si un champs important est vide
        // Faisable au niveau de l'interface comme avec Flex ?

        // Création de l'objet Notification
        Notification currentNotif = getNotification();

        // Sauvegarde des données en base
        SavedData saving = new SavedData(this);
        int idNotif = saving.saveDatas(currentNotif);
        currentNotif.setId(idNotif);

        // Service de planification (Alarme)
        planifClient.setNotification(currentNotif);

        // Quitter la page
        UpdateActivity.this.finish();
    }

    /**
     * Création d'un objet Notification
     * à partir des données de la page actuelle
     * @return objet Notification prêt à usage
     */
    private Notification getNotification() {

        // Gestion de l'icone
        int iconId;
        switch (currentIcon) {
            case SMILEY:
                iconId = R.drawable.ic_smiley;
                break;

            case CLOCK:
                iconId = R.drawable.ic_clock;
                break;

            case ALERT:
                iconId = R.drawable.ic_alert;
                break;

            default:
                // Comme ça, je remarque si il y a un problème.
                iconId = R.drawable.ic_launcher;
                break;
        }


        // Label
        EditText labelText = (EditText) findViewById(R.id.notif_label);
        String label = labelText.getText().toString();

        // Description
        EditText descriptionText = (EditText) findViewById(R.id.notif_description);
        String description = descriptionText.getText().toString();

        // Date de planification
        long notifTimeMillis;
        RadioButton buttonCalendar = (RadioButton) findViewById(R.id.radio_chrono_calendar);
        if (!buttonCalendar.isChecked()) {
            // Mode compte à rebourd (coché par défaut)
            EditText numberText = (EditText) findViewById(R.id.notif_chrono_value);
            String timing = numberText.getText().toString();

            Spinner chronoTypes = (Spinner) findViewById(R.id.notif_chrono_type);
            // TODO getSelectedItemId ?
            int posSelect = chronoTypes.getSelectedItemPosition();
            int multip = 0;
            switch (posSelect) {
                case 0:
                    // Millisecondes -> Minutes
                    multip = 1000 * 60;
                    break;

                case 1:
                    // Millisecondes -> Heures
                    multip = 1000 * 60 * 60;
                    break;

                case 2:
                    // Millisecondes -> Jours
                    multip = 1000 * 60 * 60 * 24;
                    break;

                default:
                    // Millisecondes -> Secondes
                    multip = 1000;
                    break;
            }

            notifTimeMillis = Calendar.getInstance().getTimeInMillis() + Long.parseLong(timing) * multip;
        } else {
            // Mode calendrier
            Calendar timing = Calendar.getInstance();
            timing.set(calendarYear, calendarMonth, calendarDay, calendarHour, calendarMinute);
            notifTimeMillis = timing.getTimeInMillis();
        }

        // Le ID est défini lors de la création en base.
        return new Notification(0, iconId, label, description, notifTimeMillis);
    }

    /**
     * Retour en arrière : aucune action effectuée
     * @param pView vue actuelle
     */
    public void cancelUpdate(View pView)
    {
        UpdateActivity.this.finish();
    }
}
