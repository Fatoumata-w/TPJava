package com.tp.auth.ui;

import com.tp.auth.model.Contact;

import javax.swing.*;
import java.awt.*;

public class ContactDialog extends JDialog {

    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtEmail;
    private JTextField txtTelephone;

    private JButton btnValider;
    private JButton btnAnnuler;

    private Contact contact;
    private boolean valide = false;

    public ContactDialog(Frame parent, Contact contact) {

        super(parent, true);

        this.contact = contact;

        setTitle(contact == null ? "Ajouter un contact" : "Modifier un contact");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        initComponents();

        if (contact != null) {
            remplirChamps();
        }
    }

    private void initComponents() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel panelChamps = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblNom = new JLabel("Nom :");
        JLabel lblPrenom = new JLabel("Prénom :");
        JLabel lblEmail = new JLabel("Email :");
        JLabel lblTelephone = new JLabel("Téléphone :");

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtEmail = new JTextField();
        txtTelephone = new JTextField();

        panelChamps.add(lblNom);
        panelChamps.add(txtNom);

        panelChamps.add(lblPrenom);
        panelChamps.add(txtPrenom);

        panelChamps.add(lblEmail);
        panelChamps.add(txtEmail);

        panelChamps.add(lblTelephone);
        panelChamps.add(txtTelephone);

        JPanel panelBoutons = new JPanel(new FlowLayout());

        btnValider = new JButton("Valider");
        btnAnnuler = new JButton("Annuler");

        panelBoutons.add(btnValider);
        panelBoutons.add(btnAnnuler);

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        panelPrincipal.add(panelChamps, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        btnValider.addActionListener(e -> valider());

        btnAnnuler.addActionListener(e -> annuler());
    }

    private void remplirChamps() {

        txtNom.setText(contact.getNom());
        txtPrenom.setText(contact.getPrenom());
        txtEmail.setText(contact.getEmail());
        txtTelephone.setText(contact.getTelephone());
    }

    private void valider() {

        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String email = txtEmail.getText().trim();
        String telephone = txtTelephone.getText().trim();

        if (nom.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Le nom est obligatoire.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );

            txtNom.requestFocus();
            return;
        }

        if (prenom.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Le prénom est obligatoire.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );

            txtPrenom.requestFocus();
            return;
        }

        if (contact == null) {

            contact = new Contact();

        }

        contact.setNom(nom);
        contact.setPrenom(prenom);
        contact.setEmail(email);
        contact.setTelephone(telephone);

        valide = true;

        dispose();
    }

    private void annuler() {

        valide = false;

        dispose();
    }

    public Contact getContact() {
        return contact;
    }

    public boolean isValide() {
        return valide;
    }
}