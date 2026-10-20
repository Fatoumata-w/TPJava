package com.tp.auth.dao;

import com.tp.auth.db.Database;
import com.tp.auth.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {

    public List<Contact> findByUser(int userId) {
        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT id, user_id, nom, prenom, email, telephone " +
                     "FROM contacts " +
                     "WHERE user_id = ? " +
                     "ORDER BY nom, prenom";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Contact contact = new Contact();

                    contact.setId(rs.getInt("id"));
                    contact.setUserId(rs.getInt("user_id"));
                    contact.setNom(rs.getString("nom"));
                    contact.setPrenom(rs.getString("prenom"));
                    contact.setEmail(rs.getString("email"));
                    contact.setTelephone(rs.getString("telephone"));

                    contacts.add(contact);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public void insert(Contact c) {

        String sql = "INSERT INTO contacts " +
                     "(user_id, nom, prenom, email, telephone) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getUserId());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getPrenom());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getTelephone());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Contact c) {

        String sql = "UPDATE contacts " +
                     "SET nom = ?, prenom = ?, email = ?, telephone = ? " +
                     "WHERE id = ? AND user_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelephone());
            ps.setInt(5, c.getId());
            ps.setInt(6, c.getUserId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id, int userId) {

        String sql = "DELETE FROM contacts " +
                     "WHERE id = ? AND user_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Contact> search(int userId, String text) {
        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT id, user_id, nom, prenom, email, telephone " +
                     "FROM contacts " +
                     "WHERE user_id = ? " +
                     "AND (nom LIKE ? OR prenom LIKE ? OR email LIKE ?) " +
                     "ORDER BY nom, prenom";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchText = "%" + text + "%";

            ps.setInt(1, userId);
            ps.setString(2, searchText);
            ps.setString(3, searchText);
            ps.setString(4, searchText);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Contact contact = new Contact();

                    contact.setId(rs.getInt("id"));
                    contact.setUserId(rs.getInt("user_id"));
                    contact.setNom(rs.getString("nom"));
                    contact.setPrenom(rs.getString("prenom"));
                    contact.setEmail(rs.getString("email"));
                    contact.setTelephone(rs.getString("telephone"));

                    contacts.add(contact);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }
}