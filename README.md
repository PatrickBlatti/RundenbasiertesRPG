# RundenbasiertesRPG
**Epic**
**Ausgangslage**

Für das OOP Sommersemester 2024 für die TEKO soll ein Spiel erstellt werden. Es soll folgende Elemente enthalten:
-	ein grafisches Element auf Basis von Java Swing/AWT
-	 ein zufälliges Element, was durch den Einsatz eines Zufallszahlengenerators erreicht wird,
-	 ist interaktiv 
-	zum Logging wird log4j verwendet
-	 die Visualisierung beruht auf einem Gitter (rechteckig, hexagonal, ...)
-	es wird eine Konfigurationsdatei verwendet, um grundlegende Eigenschaften festzulegen
-	 optional auch ein kollaboratives Element




**Ziel**

Grundlegendes Gameplay:

Ein Rundenbasiertes RPG wird erstellt (Beispiel für Kampfsystem: Final Fantasy 4). Der Spieler kann agieren, anschliessend agiert der Computer-Gegner. Mögliche Aktionen sind Angriff, Verteidigen, Heilen. Die jeweiligen Aktionen haben eine Trefferchance (Zufallsgenerator).
Die verbleibenden Hitpoints werden sowohl für den Gegner als auch für den Spieler angezeigt. 

Mögliche weitere Mechaniken:

-	Die Effektivität der einzelnen Handlungen werden von Statuswerten des Spielers und der Computer-Gegners bestimmt.
-	Besiegte Gegner geben Punkte.
-	Der Spieler kann seine Satuswerte zwischen den Kämpfen mit den erhaltenen Punkten verbessern.
-	Mehrere Gegner / Helden die miteinander kämpfen.
-	Die genauen Statuswerte des Gegners werden ausgewürfelt
-	Bosse, die schwieriger sind und mehr Punkte geben
-	Klassen für Helden und Gegner
-	Eine rudimentäre Story


Die genaue Ausführung wird in den einzelnen Stories detailliert werden. Die Ausführungstiefe hängt davon ab, wie gut mit der Entwicklung vorwärts gekommen wird.

**Zum Projekt**

Das Projekt wurde mit der Entwicklungsumgebung IntelliJ Version 2024.1.1 erstelt. Wenn der Ordner mit dieser Version geöffnet wird, dann lässt sich das Programm kompilieren.
Das Spiel wird aus dem GameController gestartet.

Das Spiel lässt sich über die Main-Funktion der Klasse GameController starten.


**Dokumentation**

Eine JavaDoc Dokumentation befindet sich unter folgender URL: https://patrickblatti.github.io/RundenbasiertesRPG/index.html
Im Ordner Documentation befinden sich zusätzliche Dateien, welche für das Projekt wichtig sind.
