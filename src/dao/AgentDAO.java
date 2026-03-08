package dao;

import database.Database;
import models.Agent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgentDAO {
    
    // CRÉER un agent
    public boolean create(Agent agent) {
        String sql = "INSERT INTO agents (numero_agent, nom, prenom, date_embauche) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, agent.getNumeroAgent());
            pstmt.setString(2, agent.getNom());
            pstmt.setString(3, agent.getPrenom());
            pstmt.setDate(4, new java.sql.Date(agent.getDateEmbauche().getTime()));
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur création agent: " + e.getMessage());
            return false;
        }
    }
    
    // LIRE tous les agents
    public List<Agent> findAll() {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT * FROM agents ORDER BY nom, prenom";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Agent agent = new Agent();
                agent.setId(rs.getInt("id"));
                agent.setNumeroAgent(rs.getString("numero_agent"));
                agent.setNom(rs.getString("nom"));
                agent.setPrenom(rs.getString("prenom"));
                agent.setDateEmbauche(rs.getDate("date_embauche"));
                agents.add(agent);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lecture agents: " + e.getMessage());
        }
        
        return agents;
    }
    
    // RECHERCHER par numéro d'agent
    public Agent findByNumero(String numeroAgent) {
        String sql = "SELECT * FROM agents WHERE numero_agent = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroAgent);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Agent agent = new Agent();
                agent.setId(rs.getInt("id"));
                agent.setNumeroAgent(rs.getString("numero_agent"));
                agent.setNom(rs.getString("nom"));
                agent.setPrenom(rs.getString("prenom"));
                agent.setDateEmbauche(rs.getDate("date_embauche"));
                return agent;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche agent: " + e.getMessage());
        }
        
        return null;
    }
    
    // RECHERCHER par ID
    public Agent findById(int id) {
        String sql = "SELECT * FROM agents WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Agent agent = new Agent();
                agent.setId(rs.getInt("id"));
                agent.setNumeroAgent(rs.getString("numero_agent"));
                agent.setNom(rs.getString("nom"));
                agent.setPrenom(rs.getString("prenom"));
                agent.setDateEmbauche(rs.getDate("date_embauche"));
                return agent;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche agent: " + e.getMessage());
        }
        
        return null;
    }
    
    // METTRE À JOUR un agent
    public boolean update(Agent agent) {
        String sql = "UPDATE agents SET numero_agent=?, nom=?, prenom=? WHERE id=?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, agent.getNumeroAgent());
            pstmt.setString(2, agent.getNom());
            pstmt.setString(3, agent.getPrenom());
            pstmt.setInt(4, agent.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur mise à jour agent: " + e.getMessage());
            return false;
        }
    }
    
    // SUPPRIMER un agent
    public boolean delete(int id) {
        String sql = "DELETE FROM agents WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression agent: " + e.getMessage());
            return false;
        }
    }
}
