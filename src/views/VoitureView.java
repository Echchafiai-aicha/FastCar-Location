 package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.VoitureDAO;
import models.Voiture;
import java.awt.*;
import java.util.List;

public class VoitureView extends JPanel {
    
    private JTable tableVoitures;
    private DefaultTableModel tableModel;
    private VoitureDAO voitureDAO;
    
    // Composants
    private JTextField txtMatricule, txtMarque, txtModele, txtCouleur, txtPrix, txtKilometrage;
    private JComboBox<String> cmbEtat;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnDisponible;
    
    public VoitureView() {
        voitureDAO = new VoitureDAO();
        initUI();
        chargerVoitures();
    }
    
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel supérieur: Formulaire
        JPanel formPanel = creerFormPanel();
        add(formPanel, BorderLayout.NORTH);
        
        // Panel central: Tableau
        JScrollPane tableScrollPane = creerTablePanel();
        add(tableScrollPane, BorderLayout.CENTER);
        
        // Panel inférieur: Boutons
        JPanel buttonPanel = creerButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel creerFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations de la Voiture"));
        formPanel.setBackground(new Color(240, 255, 240));
        
        // Ligne 1
        formPanel.add(new JLabel("Matricule*:"));
        txtMatricule = new JTextField();
        formPanel.add(txtMatricule);
        
        formPanel.add(new JLabel("Marque*:"));
        txtMarque = new JTextField();
        formPanel.add(txtMarque);
        
        // Ligne 2
        formPanel.add(new JLabel("Modèle*:"));
        txtModele = new JTextField();
        formPanel.add(txtModele);
        
        formPanel.add(new JLabel("Couleur:"));
        txtCouleur = new JTextField();
        formPanel.add(txtCouleur);
        
        // Ligne 3
        formPanel.add(new JLabel("Prix journalier*:"));
        txtPrix = new JTextField();
        formPanel.add(txtPrix);
        
        formPanel.add(new JLabel("Kilométrage:"));
        txtKilometrage = new JTextField();
        formPanel.add(txtKilometrage);
        
        // Ligne 4
        formPanel.add(new JLabel("État:"));
        cmbEtat = new JComboBox<>(new String[]{"Disponible", "Louée", "En maintenance"});
        formPanel.add(cmbEtat);
        
        return formPanel;
    }
    
    private JScrollPane creerTablePanel() {
        String[] columns = {"ID", "Matricule", "Marque", "Modèle", "Couleur", "Prix/jour", "Kilométrage", "État"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) return Double.class; // Prix journalier
                if (columnIndex == 6) return Integer.class; // Kilométrage
                return String.class;
            }
        };
        
        tableVoitures = new JTable(tableModel);
        tableVoitures.setRowHeight(25);
        tableVoitures.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionnerLigneVoiture();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableVoitures);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Voitures"));
        
        return scrollPane;
    }
    
    private JPanel creerButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnAjouter = new JButton("➕ Ajouter Voiture");
        btnModifier = new JButton("✏️ Modifier");
        btnSupprimer = new JButton("🗑️ Supprimer");
        btnDisponible = new JButton("✅ Marquer Disponible");
        
        // Styles
        btnAjouter.setBackground(new Color(60, 179, 113));
        btnAjouter.setForeground(Color.WHITE);
        btnModifier.setBackground(new Color(30, 144, 255));
        btnModifier.setForeground(Color.WHITE);
        btnSupprimer.setBackground(new Color(220, 20, 60));
        btnSupprimer.setForeground(Color.WHITE);
        btnDisponible.setBackground(new Color(50, 205, 50));
        btnDisponible.setForeground(Color.WHITE);
        
        // Tailles
        Dimension btnSize = new Dimension(180, 35);
        btnAjouter.setPreferredSize(btnSize);
        btnModifier.setPreferredSize(new Dimension(120, 35));
        btnSupprimer.setPreferredSize(new Dimension(120, 35));
        btnDisponible.setPreferredSize(new Dimension(200, 35));
        
        // Actions
        btnAjouter.addActionListener(e -> ajouterVoiture());
        btnModifier.addActionListener(e -> modifierVoiture());
        btnSupprimer.addActionListener(e -> supprimerVoiture());
        btnDisponible.addActionListener(e -> marquerDisponible());
        
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnDisponible);
        
        return buttonPanel;
    }
    
    private void chargerVoitures() {
        tableModel.setRowCount(0);
        
        List<Voiture> voitures = voitureDAO.findAll();
        for (Voiture voiture : voitures) {
            tableModel.addRow(new Object[]{
                voiture.getId(),
                voiture.getMatricule(),
                voiture.getMarque(),
                voiture.getModele(),
                voiture.getCouleur(),
                voiture.getPrixJournalier(),
                voiture.getKilometrage(),
                voiture.getEtat()
            });
        }
    }
    
    
    private void selectionnerLigneVoiture() {
        int selectedRow = tableVoitures.getSelectedRow();
        if (selectedRow >= 0) {
            txtMatricule.setText(tableVoitures.getValueAt(selectedRow, 1).toString());
            txtMarque.setText(tableVoitures.getValueAt(selectedRow, 2).toString());
            txtModele.setText(tableVoitures.getValueAt(selectedRow, 3).toString());
            txtCouleur.setText(tableVoitures.getValueAt(selectedRow, 4).toString());
            txtPrix.setText(tableVoitures.getValueAt(selectedRow, 5).toString());
            txtKilometrage.setText(tableVoitures.getValueAt(selectedRow, 6).toString());
            cmbEtat.setSelectedItem(tableVoitures.getValueAt(selectedRow, 7));
        }
    }
    
    private void ajouterVoiture() {
        if (!validerFormulaire()) {
            return;
        }
        
        Voiture voiture = new Voiture();
        voiture.setMatricule(txtMatricule.getText().trim());
        voiture.setMarque(txtMarque.getText().trim());
        voiture.setModele(txtModele.getText().trim());
        voiture.setCouleur(txtCouleur.getText().trim());
        voiture.setPrixJournalier(Double.parseDouble(txtPrix.getText().trim()));
        voiture.setKilometrage(txtKilometrage.getText().isEmpty() ? 0 : 
                              Integer.parseInt(txtKilometrage.getText().trim()));
        voiture.setEtat(cmbEtat.getSelectedItem().toString());
        
        if (voitureDAO.create(voiture)) {
            JOptionPane.showMessageDialog(this,
                "Voiture ajoutée avec succès!",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            viderFormulaire();
            chargerVoitures();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'ajout de la voiture!",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modifierVoiture() {
        int selectedRow = tableVoitures.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner une voiture à modifier!",
                "Avertissement",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validerFormulaire()) {
            return;
        }
        
        int voitureId = (int) tableVoitures.getValueAt(selectedRow, 0);
        Voiture voiture = voitureDAO.findById(voitureId);
        
        if (voiture != null) {
            voiture.setMatricule(txtMatricule.getText().trim());
            voiture.setMarque(txtMarque.getText().trim());
            voiture.setModele(txtModele.getText().trim());
            voiture.setCouleur(txtCouleur.getText().trim());
            voiture.setPrixJournalier(Double.parseDouble(txtPrix.getText().trim()));
            voiture.setKilometrage(txtKilometrage.getText().isEmpty() ? 0 : 
                                  Integer.parseInt(txtKilometrage.getText().trim()));
            voiture.setEtat(cmbEtat.getSelectedItem().toString());
            
            if (voitureDAO.update(voiture)) {
                JOptionPane.showMessageDialog(this,
                    "Voiture modifiée avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
                chargerVoitures();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la modification de la voiture!",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void supprimerVoiture() {
        int selectedRow = tableVoitures.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner une voiture à supprimer!",
                "Avertissement",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int voitureId = (int) tableVoitures.getValueAt(selectedRow, 0);
        String matricule = tableVoitures.getValueAt(selectedRow, 1).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Voulez-vous vraiment supprimer la voiture: " + matricule + "?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (voitureDAO.delete(voitureId)) {
                JOptionPane.showMessageDialog(this,
                    "Voiture supprimée avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
                viderFormulaire();
                chargerVoitures();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la suppression de la voiture!",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void marquerDisponible() {
        int selectedRow = tableVoitures.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner une voiture!",
                "Avertissement",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int voitureId = (int) tableVoitures.getValueAt(selectedRow, 0);
        
        if (voitureDAO.changerEtat(voitureId, "Disponible")) {
            JOptionPane.showMessageDialog(this,
                "Voiture marquée comme disponible!",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            chargerVoitures();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erreur lors du changement d'état!",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validerFormulaire() {
        if (txtMatricule.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Le matricule est obligatoire!",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            txtMatricule.requestFocus();
            return false;
        }
        
        if (txtMarque.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "La marque est obligatoire!",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            txtMarque.requestFocus();
            return false;
        }
        
        if (txtModele.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Le modèle est obligatoire!",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            txtModele.requestFocus();
            return false;
        }
        
        try {
            if (!txtPrix.getText().trim().isEmpty()) {
                Double.parseDouble(txtPrix.getText().trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Le prix journalier doit être un nombre!",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            txtPrix.requestFocus();
            return false;
        }
        
        try {
            if (!txtKilometrage.getText().trim().isEmpty()) {
                Integer.parseInt(txtKilometrage.getText().trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Le kilométrage doit être un nombre entier!",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            txtKilometrage.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void viderFormulaire() {
        txtMatricule.setText("");
        txtMarque.setText("");
        txtModele.setText("");
        txtCouleur.setText("");
        txtPrix.setText("");
        txtKilometrage.setText("");
        cmbEtat.setSelectedIndex(0);
        tableVoitures.clearSelection();
    }
}