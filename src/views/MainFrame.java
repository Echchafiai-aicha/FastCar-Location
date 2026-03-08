package views;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        setTitle("FastCar Location - Système de Gestion");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Créer la barre de menus
        creerMenuBar();
        
        // Créer le panel principal avec onglets
        tabbedPane = new JTabbedPane();
        
        // Ajouter les différents onglets
        tabbedPane.addTab("🏠 Tableau de Bord", creerDashboardPanel());
        tabbedPane.addTab("👥 Gestion Clients", new ClientView());
        tabbedPane.addTab("🚗 Gestion Voitures", new VoitureView());
        tabbedPane.addTab("📄 Gestion Contrats", new ContratView());

        
        // Ajouter le panel d'onglets à la fenêtre
        add(tabbedPane, BorderLayout.CENTER);
        
        // Ajouter une barre d'état
        add(creerStatusBar(), BorderLayout.SOUTH);
    }
    
    private void creerMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Fichier
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem itemNouveauContrat = new JMenuItem("Nouveau Contrat");
        JMenuItem itemImprimer = new JMenuItem("Imprimer");
        JMenuItem itemQuitter = new JMenuItem("Quitter");
        
        itemQuitter.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment quitter l'application?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        menuFichier.add(itemNouveauContrat);
        menuFichier.addSeparator();
        menuFichier.add(itemImprimer);
        menuFichier.addSeparator();
        menuFichier.add(itemQuitter);
        
        // Menu Édition
        JMenu menuEdition = new JMenu("Édition");
        JMenuItem itemRechercher = new JMenuItem("Rechercher");
        JMenuItem itemPreferences = new JMenuItem("Préférences");
        menuEdition.add(itemRechercher);
        menuEdition.add(itemPreferences);
        
        // Menu Aide
        JMenu menuAide = new JMenu("Aide");
        JMenuItem itemAPropos = new JMenuItem("À propos");
        JMenuItem itemAide = new JMenuItem("Aide");
        
        itemAPropos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "FastCar Location v1.0\n" +
                "Système de gestion de location de voitures\n" +
                "© 2024 - Tous droits réservés",
                "À propos",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        menuAide.add(itemAide);
        menuAide.add(itemAPropos);
        
        // Ajouter les menus à la barre
        menuBar.add(menuFichier);
        menuBar.add(menuEdition);
        menuBar.add(menuAide);
        
        setJMenuBar(menuBar);
    }
    
    private JPanel creerDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        
        // En-tête du dashboard
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(0, 100));
        
        JLabel titleLabel = new JLabel("TABLEAU DE BORD FASTCAR LOCATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Corps du dashboard avec statistiques
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Carte 1: Nombre de clients
        JPanel cardClients = creerStatCard("👥 Clients", "50", new Color(60, 179, 113));
        
        // Carte 2: Voitures disponibles
        JPanel cardVoitures = creerStatCard("🚗 Voitures", "12", new Color(30, 144, 255));
        
        // Carte 3: Contrats actifs
        JPanel cardContrats = creerStatCard("📄 Contrats", "8", new Color(255, 165, 0));
        
        // Carte 4: Revenus du mois
        JPanel cardRevenus = creerStatCard("💰 Revenus", "15,200 MAD", new Color(186, 85, 211));
        
        statsPanel.add(cardClients);
        statsPanel.add(cardVoitures);
        statsPanel.add(cardContrats);
        statsPanel.add(cardRevenus);
        
        dashboardPanel.add(statsPanel, BorderLayout.CENTER);
        
        // Actions rapides
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions Rapides"));
        
        JButton btnNouveauContrat = new JButton("📄 Nouveau Contrat");
        JButton btnNouveauClient = new JButton("👥 Nouveau Client");
        JButton btnNouvelleVoiture = new JButton("🚗 Nouvelle Voiture");
        
        // Styles des boutons
        btnNouveauContrat.setBackground(new Color(70, 130, 180));
        btnNouveauContrat.setForeground(Color.WHITE);
        btnNouveauContrat.setFont(new Font("Arial", Font.BOLD, 14));
        btnNouveauContrat.setPreferredSize(new Dimension(200, 50));
        
        btnNouveauClient.setBackground(new Color(60, 179, 113));
        btnNouveauClient.setForeground(Color.WHITE);
        btnNouveauClient.setFont(new Font("Arial", Font.BOLD, 14));
        btnNouveauClient.setPreferredSize(new Dimension(200, 50));
        
        btnNouvelleVoiture.setBackground(new Color(255, 165, 0));
        btnNouvelleVoiture.setForeground(Color.WHITE);
        btnNouvelleVoiture.setFont(new Font("Arial", Font.BOLD, 14));
        btnNouvelleVoiture.setPreferredSize(new Dimension(200, 50));
        
        // Actions des boutons
        btnNouveauContrat.addActionListener(e -> {
            tabbedPane.setSelectedIndex(3); // Aller à l'onglet Contrats
        });
        
        actionsPanel.add(btnNouveauContrat);
        actionsPanel.add(btnNouveauClient);
        actionsPanel.add(btnNouvelleVoiture);
        
        dashboardPanel.add(actionsPanel, BorderLayout.SOUTH);
        
        return dashboardPanel;
    }
    
    private JPanel creerStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        card.setPreferredSize(new Dimension(200, 150));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(Color.WHITE);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel creerStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel statusLabel = new JLabel("  Prêt");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel userLabel = new JLabel("Connecté en tant que: Admin  ");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(userLabel, BorderLayout.EAST);
        
        return statusBar;
    }
}