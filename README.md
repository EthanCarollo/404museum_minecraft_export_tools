# Zone Exporter Mod - Minecraft 1.21 (NeoForge, Kotlin)

Zone Exporter est un mod Minecraft développé en Kotlin avec NeoForge, compatible avec la version 1.21. Il permet d’exporter une zone de blocs sélectionnée dans un fichier `.txt`, utilisable dans des moteurs comme Unity.

## Fonctionnalités

### Item : Zone Selector
- Un item spécial nommé `Zone Selector` est ajouté par le mod.
- Permet de définir une zone de blocs via deux clics successifs sur des blocs dans le monde.
- Après le second clic, le mod :
  - Scanne tous les blocs dans la zone cubique définie entre les deux points.
  - Récupère le type de chaque bloc (`minecraft:stone`, `minecraft:dirt`, etc.).
  - Écrit les données dans un fichier texte au format brut.

## Format du fichier exporté

- Le fichier est généré dans le dossier `exports/` à la racine du répertoire de jeu.
- Le nom du fichier suit le format suivant :
```

exported\_blocks\_YYYY-MM-DD\_HH-MM-SS.txt

```
- Le contenu du fichier est structuré en couches (par axe Y), chaque ligne représentant une rangée de blocs sur l’axe Z, les blocs étant séparés par des virgules.
- Une ligne vide sépare chaque couche Y.

Exemple simplifié :
```

minecraft\:stone, minecraft\:stone, minecraft\:grass\_block
minecraft\:stone, minecraft\:dirt, minecraft\:grass\_block

minecraft\:air, minecraft\:air, minecraft\:air
minecraft\:air, minecraft\:air, minecraft\:air

```

## Installation

### Prérequis
- Minecraft 1.21
- NeoForge installé
- Java 17 ou plus récent
- Kotlin standard lib (gérée via Gradle)

### Étapes
1. Télécharger le fichier `.jar` depuis [GitHub Releases](#) ou CurseForge.
2. Placer le fichier dans le dossier `mods/` de votre instance Minecraft.
3. Lancer le jeu avec le profil NeoForge.

## Utilisation

1. Donnez-vous l'item `Zone Selector` via la commande :
```

/give @p zone\_exporter

```
2. Clic droit sur le premier bloc (point de départ).
3. Clic droit sur le second bloc (point final).
4. Le fichier `.txt` est automatiquement généré dans le dossier `exports`.

## Licence

Ce projet est open source, sous licence MIT. Voir le fichier `LICENSE` pour plus d'informations.