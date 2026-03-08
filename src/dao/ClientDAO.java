package dao;

import database.Database;
import models.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    
    // CRÉER un client
    public boolean create(Client client) {
        String sql = "INSERT INTO clients (cin, nom, prenom, adresse, telephone, email, date_inscription) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getCin());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getPrenom());
            pstmt.setString(4, client.getAdresse());
            pstmt.setString(5, client.getTelephone());
            pstmt.setString(6, client.getEmail());
            pstmt.setDate(7, new java.sql.Date(client.getDateInscription().getTime()));
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur création client: " + e.getMessage());
            return false;
        }
    }
    
    // LIRE tous les clients
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients ORDER BY nom, prenom";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Client client = resultSetToClient(rs);
                clients.add(client);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lecture clients: " + e.getMessage());
        }
        
        return clients;
    }
    
    // RECHERCHER par CIN
    public Client findByCin(String cin) {
        String sql = "SELECT * FROM clients WHERE cin = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cin);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return resultSetToClient(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche client: " + e.getMessage());
        }
        
        return null;
    }
    
    // RECHERCHER par ID
    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return resultSetToClient(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche client: " + e.getMessage());
        }
        
        return null;
    }
    
    // METTRE À JOUR un client
    public boolean update(Client client) {
        String sql = "UPDATE clients SET cin=?, nom=?, prenom=?, adresse=?, telephone=?, email=? WHERE id=?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getCin());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getPrenom());
            pstmt.setString(4, client.getAdresse());
            pstmt.setString(5, client.getTelephone());
            pstmt.setString(6, client.getEmail());
            pstmt.setInt(7, client.getId());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur mise à jour client: " + e.getMessage());
            return false;
        }
    }
    
    // SUPPRIMER un client
    public boolean delete(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression client: " + e.getMessage());
            return false;
        }
    }
    
    // COMPTER le nombre de clients
    public int count() {
        String sql = "SELECT COUNT(*) FROM clients";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur comptage clients: " + e.getMessage());
        }
        
        return 0;
    }
    
    // Convertir ResultSet en objet Client
    private Client resultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setCin(rs.getString("cin"));
        client.setNom(rs.getString("nom"));
        client.setPrenom(rs.getString("prenom"));
        client.setAdresse(rs.getString("adresse"));
        client.setTelephone(rs.getString("telephone"));
        client.setEmail(rs.getString("email"));
        client.setDateInscription(rs.getDate("date_inscription"));
        return client;
    }
}