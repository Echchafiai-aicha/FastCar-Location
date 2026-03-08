package dao;

import database.Database;
import models.Voiture;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureDAO {
    
    // CRÉER une voiture
    public boolean create(Voiture voiture) {
        String sql = "INSERT INTO voitures (matricule, marque, modele, couleur, prix_journalier, kilometrage, etat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, voiture.getMatricule());
            pstmt.setString(2, voiture.getMarque());
            pstmt.setString(3, voiture.getModele());
            pstmt.setString(4, voiture.getCouleur());
            pstmt.setDouble(5, voiture.getPrixJournalier());
            pstmt.setInt(6, voiture.getKilometrage());
            pstmt.setString(7, voiture.getEtat());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur création voiture: " + e.getMessage());
            return false;
        }
    }
    
    // LIRE toutes les voitures
    public List<Voiture> findAll() {
        List<Voiture> voitures = new ArrayList<>();
        String sql = "SELECT * FROM voitures ORDER BY marque, modele";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Voiture voiture = resultSetToVoiture(rs);
                voitures.add(voiture);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lecture voitures: " + e.getMessage());
        }
        
        return voitures;
    }
    
    // LIRE les voitures disponibles
    public List<Voiture> findDisponibles() {
        List<Voiture> voitures = new ArrayList<>();
        String sql = "SELECT * FROM voitures WHERE etat = 'Disponible' ORDER BY marque, modele";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Voiture voiture = resultSetToVoiture(rs);
                voitures.add(voiture);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lecture voitures disponibles: " + e.getMessage());
        }
        
        return voitures;
    }
    
    // RECHERCHER par matricule
    public Voiture findByMatricule(String matricule) {
        String sql = "SELECT * FROM voitures WHERE matricule = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, matricule);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return resultSetToVoiture(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche voiture: " + e.getMessage());
        }
        
        return null;
    }
    
    // RECHERCHER par ID
    public Voiture findById(int id) {
        String sql = "SELECT * FROM voitures WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return resultSetToVoiture(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche voiture: " + e.getMessage());
        }
        
        return null;
    }
    
    // METTRE À JOUR une voiture
    public boolean update(Voiture voiture) {
        String sql = "UPDATE voitures SET matricule=?, marque=?, modele=?, couleur=?, prix_journalier=?, kilometrage=?, etat=? WHERE id=?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, voiture.getMatricule());
            pstmt.setString(2, voiture.getMarque());
            pstmt.setString(3, voiture.getModele());
            pstmt.setString(4, voiture.getCouleur());
            pstmt.setDouble(5, voiture.getPrixJournalier());
            pstmt.setInt(6, voiture.getKilometrage());
            pstmt.setString(7, voiture.getEtat());
            pstmt.setInt(8, voiture.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur mise à jour voiture: " + e.getMessage());
            return false;
        }
    }
    
    // CHANGER l'état d'une voiture
    public boolean changerEtat(int id, String etat) {
        String sql = "UPDATE voitures SET etat = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, etat);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur changement état voiture: " + e.getMessage());
            return false;
        }
    }
    
    // SUPPRIMER une voiture
    public boolean delete(int id) {
        String sql = "DELETE FROM voitures WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression voiture: " + e.getMessage());
            return false;
        }
    }
    
    // Convertir ResultSet en objet Voiture
    private Voiture resultSetToVoiture(ResultSet rs) throws SQLException {
        Voiture voiture = new Voiture();
        voiture.setId(rs.getInt("id"));
        voiture.setMatricule(rs.getString("matricule"));
        voiture.setMarque(rs.getString("marque"));
        voiture.setModele(rs.getString("modele"));
        voiture.setCouleur(rs.getString("couleur"));
        voiture.setPrixJournalier(rs.getDouble("prix_journalier"));
        voiture.setKilometrage(rs.getInt("kilometrage"));
        voiture.setEtat(rs.getString("etat"));
        return voiture;
    }
}
