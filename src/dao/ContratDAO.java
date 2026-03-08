 package dao;

import database.Database;
import models.Contrat;
import models.Client;
import models.Voiture;
import models.Agent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratDAO {
    
    // CRÉER un contrat
    public boolean create(Contrat contrat) {
        String sql = "INSERT INTO contrats (numero_contrat, id_client, id_voiture, id_agent, date_debut, date_fin, montant_total, mode_paiement, etat_contrat, date_creation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, contrat.getNumeroContrat());
            pstmt.setInt(2, contrat.getClient().getId());
            pstmt.setInt(3, contrat.getVoiture().getId());
            pstmt.setInt(4, contrat.getAgent().getId());
            pstmt.setDate(5, new java.sql.Date(contrat.getDateDebut().getTime()));
            pstmt.setDate(6, new java.sql.Date(contrat.getDateFin().getTime()));
            pstmt.setDouble(7, contrat.getMontantTotal());
            pstmt.setString(8, contrat.getModePaiement());
            pstmt.setString(9, contrat.getEtat());
            pstmt.setDate(10, new java.sql.Date(contrat.getDateCreation().getTime()));
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur création contrat: " + e.getMessage());
            return false;
        }
    }
    
    // LIRE tous les contrats
    public List<Contrat> findAll() {
        List<Contrat> contrats = new ArrayList<>();
        String sql = "SELECT c.*, " +
                    "cl.id as client_id, cl.cin, cl.nom as client_nom, cl.prenom as client_prenom, cl.telephone as client_telephone, " +
                    "v.id as voiture_id, v.matricule, v.marque, v.modele, v.prix_journalier, " +
                    "a.id as agent_id, a.numero_agent, a.nom as agent_nom, a.prenom as agent_prenom " +
                    "FROM contrats c " +
                    "JOIN clients cl ON c.id_client = cl.id " +
                    "JOIN voitures v ON c.id_voiture = v.id " +
                    "JOIN agents a ON c.id_agent = a.id " +
                    "ORDER BY c.date_creation DESC";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Contrat contrat = resultSetToContrat(rs);
                contrats.add(contrat);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lecture contrats: " + e.getMessage());
        }
        
        return contrats;
    }
    
    // RECHERCHER par numéro de contrat
    public Contrat findByNumero(String numeroContrat) {
        String sql = "SELECT c.*, " +
                    "cl.id as client_id, cl.cin, cl.nom as client_nom, cl.prenom as client_prenom, cl.telephone as client_telephone, " +
                    "v.id as voiture_id, v.matricule, v.marque, v.modele, v.prix_journalier, " +
                    "a.id as agent_id, a.numero_agent, a.nom as agent_nom, a.prenom as agent_prenom " +
                    "FROM contrats c " +
                    "JOIN clients cl ON c.id_client = cl.id " +
                    "JOIN voitures v ON c.id_voiture = v.id " +
                    "JOIN agents a ON c.id_agent = a.id " +
                    "WHERE c.numero_contrat = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroContrat);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return resultSetToContrat(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche contrat: " + e.getMessage());
        }
        
        return null;
    }
    
    // RECHERCHER par ID
    public Contrat findById(int id) {
        String sql = "SELECT c.*, " +
                    "cl.id as client_id, cl.cin, cl.nom as client_nom, cl.prenom as client_prenom, cl.telephone as client_telephone, " +
                    "v.id as voiture_id, v.matricule, v.marque, v.modele, v.prix_journalier, " +
                    "a.id as agent_id, a.numero_agent, a.nom as agent_nom, a.prenom as agent_prenom " +
                    "FROM contrats c " +
                    "JOIN clients cl ON c.id_client = cl.id " +
                    "JOIN voitures v ON c.id_voiture = v.id " +
                    "JOIN agents a ON c.id_agent = a.id " +
                    "WHERE c.id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return resultSetToContrat(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche contrat: " + e.getMessage());
        }
        
        return null;
    }
    
    // METTRE À JOUR un contrat
    public boolean update(Contrat contrat) {
        String sql = "UPDATE contrats SET id_client=?, id_voiture=?, id_agent=?, date_debut=?, date_fin=?, montant_total=?, mode_paiement=?, etat_contrat=? WHERE id=?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, contrat.getClient().getId());
            pstmt.setInt(2, contrat.getVoiture().getId());
            pstmt.setInt(3, contrat.getAgent().getId());
            pstmt.setDate(4, new java.sql.Date(contrat.getDateDebut().getTime()));
            pstmt.setDate(5, new java.sql.Date(contrat.getDateFin().getTime()));
            pstmt.setDouble(6, contrat.getMontantTotal());
            pstmt.setString(7, contrat.getModePaiement());
            pstmt.setString(8, contrat.getEtat());
            pstmt.setInt(9, contrat.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur mise à jour contrat: " + e.getMessage());
            return false;
        }
    }
    
    // CHANGER l'état d'un contrat
    public boolean changerEtat(int id, String etat) {
        String sql = "UPDATE contrats SET etat_contrat = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, etat);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur changement état contrat: " + e.getMessage());
            return false;
        }
    }
    
    // SUPPRIMER un contrat
    public boolean delete(int id) {
        String sql = "DELETE FROM contrats WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression contrat: " + e.getMessage());
            return false;
        }
    }
    
    // COMPTER le nombre de contrats
    public int count() {
        String sql = "SELECT COUNT(*) FROM contrats";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur comptage contrats: " + e.getMessage());
        }
        
        return 0;
    }
    
    // Convertir ResultSet en objet Contrat
    private Contrat resultSetToContrat(ResultSet rs) throws SQLException {
        Contrat contrat = new Contrat();
        contrat.setId(rs.getInt("id"));
        contrat.setNumeroContrat(rs.getString("numero_contrat"));
        
        // Client
        Client client = new Client();
        client.setId(rs.getInt("client_id"));
        client.setCin(rs.getString("cin"));
        client.setNom(rs.getString("client_nom"));
        client.setPrenom(rs.getString("client_prenom"));
        client.setTelephone(rs.getString("client_telephone"));
        contrat.setClient(client);
        
        // Voiture
        Voiture voiture = new Voiture();
        voiture.setId(rs.getInt("voiture_id"));
        voiture.setMatricule(rs.getString("matricule"));
        voiture.setMarque(rs.getString("marque"));
        voiture.setModele(rs.getString("modele"));
        voiture.setPrixJournalier(rs.getDouble("prix_journalier"));
        contrat.setVoiture(voiture);
        
        // Agent
        Agent agent = new Agent();
        agent.setId(rs.getInt("agent_id"));
        agent.setNumeroAgent(rs.getString("numero_agent"));
        agent.setNom(rs.getString("agent_nom"));
        agent.setPrenom(rs.getString("agent_prenom"));
        contrat.setAgent(agent);
        
        // Dates et montants
        contrat.setDateDebut(rs.getDate("date_debut"));
        contrat.setDateFin(rs.getDate("date_fin"));
        contrat.setMontantTotal(rs.getDouble("montant_total"));
        contrat.setModePaiement(rs.getString("mode_paiement"));
        contrat.setEtat(rs.getString("etat_contrat"));
        contrat.setDateCreation(rs.getDate("date_creation"));
        
        return contrat;
    }
}