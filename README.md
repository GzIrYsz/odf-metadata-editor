# odf-metadata-editor

Ce projet est réalisé par [@Thomas REMY](https://github.com/GzIrYsz) et [@Farah ALIANE](https://github.com/farah951). C'est une mise en pratique
des connaissances acquises dans le cadre de l'UE Programmation Orientée Objet & Java
en deuxième année de licence informatique à CY Université.

## Installation

>[Java 11 ou une version supérieure doit être installé sur votre machine.](https://www.oracle.com/java/technologies/downloads/)

### Option 1

1. Cloner le dépot
```bash
$ git clone https://github.com/mipi-and-co/odf-metadata-editor
```

2. Créer les fat jar
```bash 
$ ./gradlew createJars
```

3. Lancer le jar souhaité
```bash
$ java -jar ./build/libs/cli.jar
$ java -jar ./build/libs/gui.jar
```

### Option 2

1. Télécharger le fichier jar correspondant à la [release](https://github.com/mipi-and-co/odf-metadata-editor/releases/latest) souhaitée.
2. Ouvrir un terminal dans le dossier de téléchargement
3. Lancer le jar
```bash
$ java -jar ./cli.jar
```

## Utilisation

- L'affichage de l'aide est possible à tout moment avec la commande suivante :
```bash
$ java -jar ./build/libs/cli.jar --help
```

- Affichage des métadonnées d'un fichier OpenDocument Text :
```bash
$ java -jar ./build/libs/cli.jar -f="./src/test/resources/testFile1.odt"
```

Sortie :
```


***** Metadonnees principales *****

Titre : Mets un titre

Description :


Sujet : Modele odt projet

Mots-cles : 

Auteur : 


***** Metadonnees secondaires et statistiques *****
Date de creation : 23/11/2022 15:06
Taille du fichier : 751 kB

Nombre d'images : 2
Nombre de pages : 5
Nombre de paragraphes : 22
Nombre de mots : 2016
Nombre de caracteres : 13585
Nombre de caracteres non blancs : 11587

Liste des liens hypertexte : https://google.com/, https://www.lipsum.com/feed/html


```

- Modification du titre d'un fichier OpenDocument Text :
```bash
$ java -jar ./build/libs/cli.jar -f="./src/test/resources/fileTest1.odt" --title="Nouveau titre"
```

Sortie :
```
Metadata modified !
```

- Affichage des fichiers OpenDocument Text présents dans un répertoire :
```bash
$ java -jar ./build/libs/cli.jar -d="."
```

Sortie :
```
D:\Users\Example\odf-metadata-editor\.git: Fichier non existant !
```

- Affichage des fichiers OpenDocument Text présents dans un répertoire (de manière récursive) :
```bash
$ java -jar ./build/libs/cli.jar -rd="."
```

Sortie :
```
.\src\test\resources\testFile1.odt
.\src\test\resources\testFile2.odt
```

## Auteurs

[@Thomas REMY](https://github.com/GzIrYsz),
[@Farah ALIANE](https://github.com/farah951)