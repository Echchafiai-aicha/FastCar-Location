package models;

import java.util.Date;

public class Client {
    private int id;
    private String cin;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private Date dateInscription;
    
    // Constructeurs
    public Client() {
        this.dateInscription = new Date();
    }
    
    public Client(String cin, String nom, String prenom) {
        this();
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public Client(String cin, String nom, String prenom, String telephone, String email) {
        this(cin, nom, prenom);
        this.telephone = telephone;
        this.email = email;
    }
    
    // Getters
    public int getId() { return id; }
    public String getCin() { return cin; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getAdresse() { return adresse; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public Date getDateInscription() { return dateInscription; }
    public String getNomComplet() { return nom + " " + prenom; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setCin(String cin) { this.cin = cin; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setEmail(String email) { this.email = email; }
    public void setDateInscription(Date dateInscription) { 
        this.dateInscription = dateInscription; 
    }
    
    @Override
    public String toString() {
        return getNomComplet() + " (" + cin + ")";
    }
}