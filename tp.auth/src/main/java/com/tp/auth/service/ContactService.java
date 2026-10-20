package com.tp.auth.service;

import com.tp.auth.dao.ContactDao;
import com.tp.auth.model.Contact;

import java.util.List;

public class ContactService {

    private ContactDao contactDao;

    public ContactService() {
        contactDao = new ContactDao();
    }

    public List<Contact> getContacts(int userId) {
        return contactDao.findByUser(userId);
    }

    public void ajouterContact(Contact contact) {

        if (contact.getNom() == null || contact.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }

        if (contact.getPrenom() == null || contact.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }

        contactDao.insert(contact);
    }

    public void modifierContact(Contact contact) {

        if (contact.getNom() == null || contact.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }

        if (contact.getPrenom() == null || contact.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }

        contactDao.update(contact);
    }

    public void supprimerContact(int id, int userId) {
        contactDao.delete(id, userId);
    }

    public List<Contact> rechercherContacts(int userId, String texte) {
        return contactDao.search(userId, texte);
    }
}