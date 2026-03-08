 package models;

import java.util.Date;

public class Contrat {
    private int id;
    private String numeroContrat;
    private Client client;
    private Voiture voiture;
    private Agent agent;
    private Date dateDebut;
    private Date dateFin;
    private double montantTotal;
    private String modePaiement; // Espèce, Carte, Virement
    private String etat; // En cours, Terminé, Annulé
    private Date dateCreation;
    
    // Constructeurs
    public Contrat() {
        this.etat = "En cours";
        this.dateCreation = new Date();
    }
    
    public Contrat(Client client, Voiture voiture, Agent agent) {
        this();
        this.client = client;
        this.voiture = voiture;
        this.agent = agent;
    }
    
    // Getters
    public int getId() { return id; }
    public String getNumeroContrat() { return numeroContrat; }
    public Client getClient() { return client; }
    public Voiture getVoiture() { return voiture; }
    public Agent getAgent() { return agent; }
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }
    public double getMontantTotal() { return montantTotal; }
    public String getModePaiement() { return modePaiement; }
    public String getEtat() { return etat; }
    public Date getDateCreation() { return dateCreation; }
    
    // Calcul du nombre de jours de location
    public long getNombreJours() {
        if (dateDebut != null && dateFin != null) {
            long difference = dateFin.getTime() - dateDebut.getTime();
            return difference / (1000 * 60 * 60 * 24); // Convertir millisecondes en jours
        }
        return 0;
    }
    
    // Calcul du montant total
    public void calculerMontant() {
        if (voiture != null) {
            long jours = getNombreJours();
            if (jours > 0) {
                this.montantTotal = jours * voiture.getPrixJournalier();
            }
        }
    }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setNumeroContrat(String numeroContrat) { this.numeroContrat = numeroContrat; }
    public void setClient(Client client) { this.client = client; }
    public void setVoiture(Voiture voiture) { this.voiture = voiture; }
    public void setAgent(Agent agent) { this.agent = agent; }
    public void setDateDebut(Date dateDebut) { 
        this.dateDebut = dateDebut; 
        calculerMontant(); // Recalculer le montant si la date change
    }
    public void setDateFin(Date dateFin) { 
        this.dateFin = dateFin; 
        calculerMontant(); // Recalculer le montant si la date change
    }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
    public void setEtat(String etat) { this.etat = etat; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    @Override
    public String toString() {
        return "Contrat " + numeroContrat + " - " + 
               (client != null ? client.getNomComplet() : "") + 
               " / " + (voiture != null ? voiture.getMatricule() : "");
    }
}