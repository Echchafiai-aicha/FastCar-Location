package views;

import javax.swing.*;
import java.awt.*;

/**
 * Vue pour la gestion des contrats.
 * Ce panel est très basique pour l'instant et peut être étendu
 * avec un formulaire, un tableau, etc.
 */
public class ContratView extends JPanel {
    public ContratView() {
        setLayout(new BorderLayout());
        // panneau temporaire indiquant l'espace des contrats
        JLabel placeholder = new JLabel("Gestion des contrats", SwingConstants.CENTER);
        placeholder.setFont(new Font("Arial", Font.BOLD, 24));
        add(placeholder, BorderLayout.CENTER);
    }
}
