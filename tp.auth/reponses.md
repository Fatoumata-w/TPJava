# Réponses TP2

## Q1

La colonne `user_id` permet de savoir à quel utilisateur appartient chaque contact.

Elle permet donc d'afficher seulement les contacts de l'utilisateur connecté.

## Q2

Sans `ON DELETE CASCADE`, on ne pourrait pas supprimer un utilisateur s'il possède encore des contacts.

Avec `ON DELETE CASCADE`, ses contacts sont supprimés automatiquement.

## Q3

Il faut vérifier `user_id` pour empêcher un utilisateur de modifier ou supprimer le contact d'un autre utilisateur.

Donc on vérifie `id` et `user_id` dans le `WHERE`.