# Whitespaces

## Alkalmazás
A projektünk témája egy webes alkalmazás elkészítése volt. Az applikáció funkciói regisztráció és belépés után érhetőek el. Tanárként regisztrálva különböző kvízeket hozhatunk létre, módosíthatjuk, törölhetjük azokat továbbá megtekinthetjük a diákok eredményeit. Diákként pedig kvízeket kereshetünk, kitölthetjük azokat és megtekinthetjük az eredményeinket.

## Projekt eszközei
-   Verziókövető rendszerként a git/github-ot használtuk, workflowként gitflow workflow alkalmaztunk.
-   Build system-ként a Travis CI-t használtuk a megfelelő github pluginnal.
-   Teszteléshez a Spring teszt modulját és a mockito-t használtuk.
-   A kód ellenőrzésére Maven checkstyle és javadoc generator plugint használtuk.

## Szükséges eszközök
Az alkalmazás futtatásához a Java SE legalább 8-as verziójára van szükség. További szükséges eszköz a Apache Maven és az Angular CLI. Az utóbbit a Node.js Package Manager segítségével lehet legkönnyebben telepíteni. 
Mindegyik szükséges program telepítéséhez a saját oldalukon található részletes leírás. Az alkalmazás futtatásához nincs szükség az eszközök különleges konfigurálására.

## Alkalmazás indítása
Az első indítás előtt, az alkalmazás fő könyvtárában (tester\) indítsunk egy terminált, melybe be kell gépelni az mvn clean install parancsot. Ezután a szerver indításához a tester-backend mappába átlépve az mvn spring-boot:run parancsot kell beírni. 
Végül a kliens indításához a tester-frontend\src\main\frontend mappában egy terminál ablakban az npm start parancsot kell beírni. Ha minden sikeresen elindult, akkor az alkalmazást a http://localhost:4200/ címen a érhetjük el, ahol a főoldal fogad minket.

## Linkek
- [Dokumentáció](https://gjakab.github.io/Whitespaces/)
- [Travis CI](https://travis-ci.org/gjakab/Whitespaces/)