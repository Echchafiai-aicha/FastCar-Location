package models;

import java.util.Date;

public class Agent {
    private int id;
    private String numeroAgent;
    private String nom;
    private String prenom;
    private Date dateEmbauche;
    
    // Constructeurs
    public Agent() {
        this.dateEmbauche = new Date();
    }
    
    public Agent(String numeroAgent, String nom, String prenom) {
        this();
        this.numeroAgent = numeroAgent;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    // Getters
    public int getId() { return id; }
    public String getNumeroAgent() { return numeroAgent; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public Date getDateEmbauche() { return dateEmbauche; }
    public String getNomComplet() { return nom + " " + prenom; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setNumeroAgent(String numeroAgent) { this.numeroAgent = numeroAgent; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setDateEmbauche(Date dateEmbauche) { 
        this.dateEmbauche = dateEmbauche; 
    }
    
    @Override
    public String toString() {
        return getNomComplet() + " (" + numeroAgent + ")";
    }
}