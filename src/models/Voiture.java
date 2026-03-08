package models;

public class Voiture {
    private int id;
    private String matricule;
    private String marque;
    private String modele;
    private String couleur;
    private double prixJournalier;
    private int kilometrage;
    private String etat; // Disponible, Louée, En maintenance
    
    // Constructeurs
    public Voiture() {
        this.etat = "Disponible";
    }
    
    public Voiture(String matricule, String marque, String modele, double prixJournalier) {
        this();
        this.matricule = matricule;
        this.marque = marque;
        this.modele = modele;
        this.prixJournalier = prixJournalier;
    }
    
    // Getters
    public int getId() { return id; }
    public String getMatricule() { return matricule; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public String getCouleur() { return couleur; }
    public double getPrixJournalier() { return prixJournalier; }
    public int getKilometrage() { return kilometrage; }
    public String getEtat() { return etat; }
    public boolean isDisponible() { return "Disponible".equals(etat); }
    public String getDescription() { 
        return marque + " " + modele + " (" + matricule + ")"; 
    }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setModele(String modele) { this.modele = modele; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setPrixJournalier(double prixJournalier) { this.prixJournalier = prixJournalier; }
    public void setKilometrage(int kilometrage) { this.kilometrage = kilometrage; }
    public void setEtat(String etat) { this.etat = etat; }
    
    @Override
    public String toString() {
        return getDescription();
    }
}