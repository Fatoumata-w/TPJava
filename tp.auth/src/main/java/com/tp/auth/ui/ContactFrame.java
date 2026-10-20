package com.tp.auth.ui;

import com.tp.auth.model.Contact;
import com.tp.auth.service.ContactService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ContactFrame extends JFrame {

    private int userId;

    private ContactService contactService;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtRecherche;

    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnRechercher;

    public ContactFrame(int userId) {

        this.userId = userId;
        this.contactService = new ContactService();

        setTitle("Gestion des contacts");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        chargerContacts();
    }

    private void initComponents() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel panelHaut = new JPanel(new FlowLayout());

        JLabel lblRecherche = new JLabel("Recherche :");

        txtRecherche = new JTextField(20);

        btnRechercher = new JButton("Rechercher");

        panelHaut.add(lblRecherche);
        panelHaut.add(txtRecherche);
        panelHaut.add(btnRechercher);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nom", "Prénom", "Email", "Téléphone"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelBas = new JPanel(new FlowLayout());

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");

        panelBas.add(btnAjouter);
        panelBas.add(btnModifier);
        panelBas.add(btnSupprimer);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBas, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        btnAjouter.addActionListener(e -> ajouterContact());

        btnModifier.addActionListener(e -> modifierContact());

        btnSupprimer.addActionListener(e -> supprimerContact());

        btnRechercher.addActionListener(e -> rechercherContacts());

        txtRecherche.addActionListener(e -> rechercherContacts());
    }

    private void chargerContacts() {

        tableModel.setRowCount(0);

        List<Contact> contacts = contactService.getContacts(userId);

        for (Contact contact : contacts) {

            tableModel.addRow(new Object[]{
                    contact.getId(),
                    contact.getNom(),
                    contact.getPrenom(),
                    contact.getEmail(),
                    contact.getTelephone()
            });
        }
    }

    private void ajouterContact() {

        ContactDialog dialog = new ContactDialog(this, null);

        dialog.setVisible(true);

        if (dialog.isValide()) {

            Contact contact = dialog.getContact();

            contact.setUserId(userId);

            contactService.ajouterContact(contact);

            chargerContacts();
        }
    }

    private void modifierContact() {

        int ligne = table.getSelectedRow();

        if (ligne == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Sélectionnez un contact.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        int id = (int) tableModel.getValueAt(ligne, 0);

        String nom = (String) tableModel.getValueAt(ligne, 1);
        String prenom = (String) tableModel.getValueAt(ligne, 2);
        String email = (String) tableModel.getValueAt(ligne, 3);
        String telephone = (String) tableModel.getValueAt(ligne, 4);

        Contact contact = new Contact(
                id,
                userId,
                nom,
                prenom,
                email,
                telephone
        );

        ContactDialog dialog = new ContactDialog(this, contact);

        dialog.setVisible(true);

        if (dialog.isValide()) {

            Contact contactModifie = dialog.getContact();

            contactModifie.setId(id);
            contactModifie.setUserId(userId);

            contactService.modifierContact(contactModifie);

            chargerContacts();
        }
    }

    private void supprimerContact() {

        int ligne = table.getSelectedRow();

        if (ligne == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Sélectionnez un contact.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        int id = (int) tableModel.getValueAt(ligne, 0);

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment supprimer ce contact ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            contactService.supprimerContact(id, userId);

            chargerContacts();
        }
    }

    private void rechercherContacts() {

        String texte = txtRecherche.getText().trim();

        if (texte.isEmpty()) {
            chargerContacts();
            return;
        }

        tableModel.setRowCount(0);

        List<Contact> contacts =
                contactService.rechercherContacts(userId, texte);

        for (Contact contact : contacts) {

            tableModel.addRow(new Object[]{
                    contact.getId(),
                    contact.getNom(),
                    contact.getPrenom(),
                    contact.getEmail(),
                    contact.getTelephone()
            });
        }
    }
}