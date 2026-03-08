package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.ClientDAO;
import models.Client;
import java.awt.*;
import java.util.List;

public class ClientView extends JPanel {
    
    private JTable tableClients;
    private DefaultTableModel tableModel;
    private ClientDAO clientDAO;
    
    // Composants du formulaire
    private JTextField txtCin, txtNom, txtPrenom, txtTelephone, txtEmail, txtAdresse;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnRechercher;
    
    public ClientView() {
        clientDAO = new ClientDAO();
        initUI();
        chargerClients();
    }
    
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel supérieur: Formulaire
        JPanel formPanel = creerFormPanel();
        add(formPanel, BorderLayout.NORTH);
        
        // Panel central: Tableau
        JScrollPane tableScrollPane = creerTablePanel();
        add(tableScrollPane, BorderLayout.CENTER);
        
        // Panel inférieur: Boutons d'action
        JPanel buttonPanel = creerButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel creerFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations du Client"));
        formPanel.setBackground(new Color(240, 248, 255));
        
        // Ligne 1
        formPanel.add(new JLabel("CIN*:"));
        txtCin = new JTextField();
        formPanel.add(txtCin);
        
        formPanel.add(new JLabel("Nom*:"));
        txtNom = new JTextField();
        formPanel.add(txtNom);
        
        // Ligne 2
        formPanel.add(new JLabel("Prénom*:"));
        txtPrenom = new JTextField();
        formPanel.add(txtPrenom);
        
        formPanel.add(new JLabel("Téléphone:"));
        txtTelephone = new JTextField();
        formPanel.add(txtTelephone);
        
        // Ligne 3
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Adresse:"));
        txtAdresse = new JTextField();
        formPanel.add(txtAdresse);
        
        return formPanel;
    }
    
    private JScrollPane creerTablePanel() {
        // Modèle de tableau
        String[] columns = {"ID", "CIN", "Nom", "Prénom", "Téléphone", "Email", "Adresse"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non éditables
            }
        };
        
        tableClients = new JTable(tableModel);
        tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableClients.setRowHeight(25);
        
        // Sélectionner une ligne
        tableClients.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionnerLigneClient();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableClients);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Clients"));
        
        return scrollPane;
    }
    
    private JPanel creerButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnAjouter = new JButton("➕ Ajouter");
        btnModifier = new JButton("✏️ Modifier");
        btnSupprimer = new JButton("🗑️ Supprimer");
        btnRechercher = new JButton("🔍 Rechercher");
        
        // Styles
        btnAjouter.setBackground(new Color(60, 179, 113));
        btnAjouter.setForeground(Color.WHITE);
        btnModifier.setBackground(new Color(30, 144, 255));
        btnModifier.setForeground(Color.WHITE);
        btnSupprimer.setBackground(new Color(220, 20, 60));
        btnSupprimer.setForeground(Color.WHITE);
        btnRechercher.setBackground(new Color(255, 165, 0));
        btnRechercher.setForeground(Color.WHITE);
        
        // Tailles
        Dimension btnSize = new Dimension(120, 35);
        btnAjouter.setPreferredSize(btnSize);
        btnModifier.setPreferredSize(btnSize);
        btnSupprimer.setPreferredSize(btnSize);
        btnRechercher.setPreferredSize(btnSize);
        
        // Actions
        btnAjouter.addActionListener(e -> ajouterClient());
        btnModifier.addActionListener(e -> modifierClient());
        btnSupprimer.addActionListener(e -> supprimerClient());
        btnRechercher.addActionListener(e -> rechercherClient());
        
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnRechercher);
        
        return buttonPanel;
    }
    
    private void chargerClients() {
        tableModel.setRowCount(0); // Vider le tableau
        
        List<Client> clients = clientDAO.findAll();
        for (Client client : clients) {
            tableModel.addRow(new Object[]{
                client.getId(),
                client.getCin(),
                client.getNom(),
                client.getPrenom(),
                client.getTelephone(),
                client.getEmail(),
                client.getAdresse()
            });
        }
    }
    
    private void selectionnerLigneClient() {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow >= 0) {
            txtCin.setText(tableClients.getValueAt(selectedRow, 1).toString());
            txtNom.setText(tableClients.getValueAt(selectedRow, 2).toString());
            txtPrenom.setText(tableClients.getValueAt(selectedRow, 3).toString());
            txtTelephone.setText(tableClients.getValueAt(selectedRow, 4).toString());
            txtEmail.setText(tableClients.getValueAt(selectedRow, 5).toString());
            txtAdresse.setText(tableClients.getValueAt(selectedRow, 6).toString());
        }
    }
    
    private void ajouterClient() {
        if (!validerFormulaire()) {
            return;
        }
        
        Client client = new Client();
        client.setCin(txtCin.getText().trim());
        client.setNom(txtNom.getText().trim());
        client.setPrenom(txtPrenom.getText().trim());
        client.setTelephone(txtTelephone.getText().trim());
        client.setEmail(txtEmail.getText().trim());
        client.setAdresse(txtAdresse.getText().trim());
        
        if (clientDAO.create(client)) {
            JOptionPane.showMessageDialog(this,
                "Client ajouté avec succès!",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            viderFormulaire();
            chargerClients();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'ajout du client!",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modifierClient() {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un client à modifier!",
                "Avertissement",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validerFormulaire()) {
            return;
        }
        
        int clientId = (int) tableClients.getValueAt(selectedRow, 0);
        Client client = clientDAO.findById(clientId);
        
        if (client != null) {
            client.setCin(txtCin.getText().trim());
            client.setNom(txtNom.getText().trim());
            client.setPrenom(txtPrenom.getText().trim());
            client.setTelephone(txtTelephone.getText().trim());
            client.setEmail(txtEmail.getText().trim());
            client.setAdresse(txtAdresse.getText().trim());
            
            if (clientDAO.update(client)) {
                JOptionPane.showMessageDialog(this,
                    "Client modifié avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
                chargerClients();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la modification du client!",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void supprimerClient() {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un client à supprimer!",
                "Avertissement",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int clientId = (int) tableClients.getValueAt(selectedRow, 0);
        String nomClient = tableClients.getValueAt(selectedRow, 2) + " " + 
                          tableClients.getValueAt(selectedRow, 3);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Voulez-vous vraiment supprimer le client: " + nomClient + "?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (clientDAO.delete(clientId)) {
                JOptionPane.showMessageDialog(this,
                    "Client supprimé avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
                viderFormulaire();
                chargerClients();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la suppression du client!",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void rechercherClient() {
        String searchTerm = JOptionPane.showInputDialog(this,
            "Entrez le CIN ou le nom du client:",
            "Recherche Client",
            JOptionPane.QUESTION_MESSAGE);
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            // Rechercher par CIN
            Client client = clientDAO.findByCin(searchTerm.trim());
            if (client != null) {
                // Remplir le formulaire
                txtCin.setText(client.getCin());
                txtNom.setText(client.getNom());
                txtPrenom.setText(client.getPrenom());
                txtTelephone.setText(client.getTelephone());
                txtEmail.setText(client.getEmail());
                txtAdresse.setText(client.getAdresse());
                
                // Sélectionner dans le tableau
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 1).equals(client.getCin())) {
                        tableClients.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Aucun client trouvé avec ce critère.",
                    "Recherche",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private boolean validerFormulaire() {
        if (txtCin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Le CIN est obligatoire!",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            txtCin.requestFocus();
            return false;
        }
        
        if (txtNom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Le nom est obligatoire!",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            txtNom.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void viderFormulaire() {
        txtCin.setText("");
        txtNom.setText("");
        txtPrenom.setText("");
        txtTelephone.setText("");
        txtEmail.setText("");
        txtAdresse.setText("");
        tableClients.clearSelection();
    }
}