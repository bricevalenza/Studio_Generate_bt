# Studio_Generate_bt

!!!! Le code est pas propre, c'est de la bidouille mais ça répondait à mon besoin simple, sans pour autant partir sur un developpement complexe que je ne saurai pas faire de toute façon !!

Le fichier .md contient l'UUID de la lunii (présent à la racine de la carte SD)
	- Intégrer l'histoire dans Studio, puis convertir le pack dans Studio en version Fs;
	- Se rendre ici : C:\Users\USER\\.studio\library
	- Un dossier _converted apparait : c'est l'histoire au format FS.
	- Récupérer  le fichier ri à l'intérieur
	
	- Dans le répertoire "BT" du projet Eclipse , mettre le fichier .md, et le fichier .ri du pack de l'histoire à intégrer
	- Lancer le java "Simple" en tant que java application;
	- Cela va générer le fichier bt.
	- Mettre le fichier bt dans le répertoire de l'histoire
	- Renommer le repertoire du pack (_converted) en conservant les 8 dernier caractère en majuscule;
	- Dans la carte SD ouvrir le fichier .pi à la racine  avec un éditeur hexadecimal Hxd
	- Rajouter une ligne avec a la fin les 8 dernier caractère (le début de la ligne importe peu)
![image](https://user-images.githubusercontent.com/799962/198629394-e50d7560-3417-40ab-839e-809614392b83.png)
	- Copier le repertoire de l'histoire dans la Lunii dossier ;content (avec les autres)
	- 
![image](https://user-images.githubusercontent.com/799962/198630134-1dbf0e26-38d2-460e-9df9-3e3a55c5113e.png)



Le projet Eclipse : 
![image](https://user-images.githubusercontent.com/799962/198629747-06db225e-0e1c-4996-890d-048075a6c141.png)
