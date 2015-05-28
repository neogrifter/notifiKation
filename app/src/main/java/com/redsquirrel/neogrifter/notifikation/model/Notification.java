package com.redsquirrel.neogrifter.notifikation.model;

import java.io.Serializable;

/**
 * Notification
 */
public class Notification implements Serializable {

    /**
     * ID de la notification
     * Défini par la base de données
     */
    private int id;

    /**
     * Label
     */
    private String label;

    /**
     * ID de l'icone
     */
    private int iconId;

    /**
     * Description
     */
    private String description;

    /**
     * Date d'affichage (millisecondes)
     */
    private long displayTiming;

    /**
     * Constructeur
     * @param pId ID de la notification
     * @param pIconId ID de l'icone
     * @param pLabel label
     * @param pDescription description
     * @param pTiming date d'affichage en millisecondes
     */
    public Notification(int pId, int pIconId, String pLabel, String pDescription, long pTiming) {
        this.id = pId;
        this.label = pLabel;
        this.description = pDescription;
        this.displayTiming = pTiming;
        this.iconId = pIconId;
    }

    /**
     * Constructeur
     * @param pId ID de la notification
     * @param pLabel Label
     * @param pTiming date d'affichage en millisecondes
     * @deprecated utiliser l'autre constructeur
     */
    public Notification(int pId, String pLabel, int pTiming) {
        this.id = pId;
        this.label = pLabel;
        this.displayTiming = pTiming;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDisplayTiming() {
        return displayTiming;
    }

    public void setDisplayTiming(long displayTiming) {
        this.displayTiming = displayTiming;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
