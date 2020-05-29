# TP IHM - Lucas Briatte

Ce TP a été réalisé le 29/05/2020 dans le cadre du TP noté d'IHM à Polytech Paris Saclay.
L'intégralité du code est commenté et documenté.

## Objectif

L’objectif de cet exercice est de réaliser un éditeur graphique minimal. Dans ce
TP on aborde le Dessin dans un canevas, le picking, le drag and drop, ainsi que
le undo-redo. Voici la fenêtre graphique à implementer pour l’application.

# Réalisation

![Image du projet](https://imgur.com/PdpS8Pq)

# Fonctionalités

- Ajout de formes (rectangle, ligne, ellipse)
- Modification de la position d'une forme
- Modification de la couleur d'une forme
- Selection d'une forme
- Clonage d'une forme
- Retour en arrière (CTRL+Z)
- Retour en "avant" (CTRL+Y)

# Réponses aux questions

Afin de mettre en place une architecture MVC il faudrait déterminer quels seraient les modèles, les vues et les contrôleurs.
J'ai dans ce projet choisis d'organiser le modèle MVC de la façon suivante.

## Modèle

### CanvaShape

Cette classe correspond aux objets géométriques qui seront affichés sur le canva.

### Task

Cette interface permet de définir un ensemble de méthodes qui seront utilisées par toutes les opérations relatives aux formes géométriques (déplacement, changement de couleur, suppression, ajout...) afin de permettre un retour en arrière et en "avant" des opérations.

### Adding, Clone, ColorChange, DeleteShape, Move

Ces classes permettent d'appliquer des opérations sur les formes géométriques et d'annuler ou non l'opération effectuées. Elles implémentent l'interface Task.

### History

Cette méthode regroupe l'ensemble des opérations - Les Task précédemment citées - effectuées sur l'application afin de pouvoir les annuler ou non.

## Vue

La vue de l'application est un fichier JXML, dans le cas de cette application gui.jxml.

## Contrôleur

Le contrôleur permet de répondre aux input :
- Selection de l'outil (selection/déplacement, ligne, ellipse, rectangle)
- Clonage d'une forme
- Suppression d'une forme
- Appui clavier sur les touches CTRL+Z ou CTRL+Y

# Problèmes connus

- Dans certains cas les formes géométriques ont des "résidus" lors de l'affichage (une autre forme géométrique plus petite)
- Il faut appuyer 2 fois sur CTRL+Z/CTRL+Y pour que l'action de retour en arrière/avant s'effectue