# Description
Ce projet est un SDK afin de créer rapidement un Mastermind.
Le projet est découpé en 3 packages:
- `dao`: package pour gérer la persistence
- `datamodel`: package contenant un model possible de la partie métier
- `service`: package disposant des services pour jouer

Attention, ce projet ne dispose pas de vue. Pour cela, voir la partie "évolutions possibles".

# Pions disponibles
Le nombre de pions de couleurs différentes est de 6 et les couleurs sont: 
- jaune 
- bleu
- rouge 
- vert
- orange
- noir

Il y a également des pions noirs, blancs et transparents utilisés pour donner des indications à chaque étape du jeu.

# Prérequis
- Git
- JDK 8 ou plus
- Maven 3.0 ou plus

# Tests
* Dans le projet Mastermind
* Sélectionnez la classe `CoreMastermindManagementTest`
* Clique droit et sélectionner l'option de lancement de test

# Evolutions possibles
- Modification du nombre de pions à jouer
- Modification du nombre de tours
- Ajouter un package `binding` pour déclarer des services REST
- Ajouter un package `ui` pour avoir une interface graphique
- Ajouter un package `container` pour lancer l'application web