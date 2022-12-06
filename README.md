# tournoiMC
## Présentation
tournoiMC est un plugin spigot permettant de créer et gérer un tournoi minecraft speedrun sur plusieurs boss. Ces boss peuvent être tué en équipe.

## Les boss
### Liste
Les boss présent dans le tournoi devront être tués dans l'ordre suivant :
- Le grand guardien
- Le wither
- L'ender-dragon

### Spécificité de l'ender dragon
Le jeu regénère l'ender dragon dès lors qu'il à été tué, avec un délai de 80 secondes.

## Les commandes
### Administrateur
- ``/startspeedrun`` : permet de démarrer le tournoi.
- ``/finishspeedrun``: permet de terminer le tournoi.
- ``/resetspeedrun``: Réinitialise les équipes.
- ``/stopjoin``: Active / désactive la possibilité aux nouveaux joueurs de rejoindre le serveur. Les op et les membres de teams ne sont pas affectés.

### Joueurs
- ``/createteam <nomteam>``: permet de créer une team
- ``/jointeam <nomteam>``: permet de rejoindre une team
- ``/leaveteam``: permet de quitter une team
- ``/sendcoords``: permet d'envoyer ses coordonnés à tout les membres de son équipe
- ``/fautfairequoi``: informe le joueur du prochain boss à tuer
