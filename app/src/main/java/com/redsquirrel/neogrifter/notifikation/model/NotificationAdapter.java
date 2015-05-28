package com.redsquirrel.neogrifter.notifikation.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.redsquirrel.neogrifter.notifikation.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Adapter pour l'affichage des notifications en liste à l'acceuil
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {

    /**
     * Contexte de la page
     */
    private final Context context;

    /**
     * Liste de notifications
     */
    private final Notification[] values;

    /**
     * Constructeur
     * @param context contexte de la page
     * @param values liste de notifications
     */
    public NotificationAdapter(Context context, Notification[] values) {
        super(context, R.layout.adapter_notification, values);
        this.context = context;
        this.values = values;
    }

    /**
     * Créer la vue correspondante pour la liste
     * @param position position dans la liste
     * @param convertView vue actuelle
     * @param parent vue parente
     * @return vue pour la ligne
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // récupérer le layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // TODO Pattern ViewHolder ?
        View rowView = inflater.inflate(R.layout.adapter_notification, parent, false);

        Notification currentNotif = values[position];

        // Mettre à jour les champs
        TextView textView = (TextView) rowView.findViewById(R.id.adapt_label);
        textView.setText(currentNotif.getLabel());

        TextView timerView = (TextView) rowView.findViewById(R.id.adapt_timer);
        Calendar dateNotif = Calendar.getInstance();
        dateNotif.setTimeInMillis(currentNotif.getDisplayTiming());
        timerView.setText(dateNotif.getTime().toString());

        ImageView imageView = (ImageView) rowView.findViewById(R.id.adapt_icon);
        imageView.setImageResource(currentNotif.getIconId());

        Button buttonModif = (Button) rowView.findViewById(R.id.adapt_button_update);
        buttonModif.setTag(position);

        Button buttonDelete = (Button) rowView.findViewById(R.id.adapt_button_delete);
        buttonDelete.setTag(position);

        return rowView;
    }

}
