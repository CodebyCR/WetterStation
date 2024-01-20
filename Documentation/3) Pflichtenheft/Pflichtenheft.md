
## Mockups

###  Applikation Mockup

![[Wetterstation Mockup.drawio-3.svg]]


### Tagesbericht Modal Window Mockup

![[Modal Window Mockup.drawio.svg]]


## Pflichtenheft

**Pflichtenheft für die Entwicklung der Wetterdatenverarbeitungssoftware**

**I. Einleitung**

Das Pflichtenheft definiert die Anforderungen und Spezifikationen für die Entwicklung einer Windows-Software zur Verarbeitung von Wetterdaten, die von verschiedenen Wetterstationen in der Umgebung von Hagen erfasst werden.

**II. Funktionale Qualitätsanforderungen**

1. **Möglichkeit zum Einlesen von '.csv'-Dateien**
   - Die Software muss in der Lage sein, Wetterdaten aus '.csv'-Dateien einzulesen. Dies funktioniert über einen Button mit der Beschriftung 'Datei einlesen', zu sehen ist dieser rechts oben im [Applikation Mockup](##Mockups ###Applikation Mockup).

2. **Erstellung eines Tagesberichts (pro Wetterstation) mit folgenden Informationen & wo diese zu finden sind**
   - Name der Station -> Im [Tagesbericht Modal Window Mockup](##Mockups ### Tagesbericht Modal Window Mockup) mittig oben ('Station 1' als Platzhalter)
   - Datum -> Im [Tagesbericht Modal Window Mockup](##Mockups ### Tagesbericht Modal Window Mockup) mittig oben ('24. Feb 2024' als Platzhalter) mit '|' vom Stationsnamen separiert.
   - Durchschnittstemperatur -> Im [Tagesbericht Modal Window Mockup](##Mockups ### Tagesbericht Modal Window Mockup) an 3. Stelle in der  Listenansicht ('20,07 °C' als Platzhalter).
   - Minimale Temperatur -> Im [Tagesbericht Modal Window Mockup](##Mockups ### Tagesbericht Modal Window Mockup) an 1. Stelle in der  Listenansicht ('16,02 °C' als Platzhalter).
   - Maximale Temperatur -> Im [Tagesbericht Modal Window Mockup](##Mockups ### Tagesbericht Modal Window Mockup) an 2. Stelle in der  Listenansicht ('22,34 °C' als Platzhalter).
   - Diagramm mit dem Temperaturverlauf an einem Tag. -> Im [Tagesbericht Modal Window Mockup](### Tagesbericht Modal Window Mockup) Zentriert unten
   
3. **Der Tagesbericht soll nur angezeigt werden, nicht als Datei vorliegen**
   - Die Tagesberichte sollen in der Software angezeigt, aber nicht als separate Dateien gespeichert werden. -> Erfolgt über das Modal Window.

4. **Suchoption nach Datum & Wettermessstation in einer Eingabe**
   - Benutzer sollten nach Datum und Wettermessstation suchen können, um relevante Daten zu finden (z. B. 28.08.2023 | Station2). Die Suche erfolgt über ein Suchfeld, dieses ist sichtbar im [Applikation Mockup](##Mockups ###Applikation Mockup) mittig oben.

5. **Rohdatenzugriff**
   - Benutzer sollten die Möglichkeit haben, auf die Rohdaten zuzugreifen. -> Im [Applikation Mockup](##Mockups ###Applikation Mockup), gibt es zur jeder Wetterstation einen Button mit einen Augen-Icon unter den Reiter 'Dateneinsicht', dieser zeigt die Rohdaten in einer Listenansicht unterhalb der Wetterstationen an.
     Oberhalb der Listenansicht ist zusehen, welche Wetterstation gerade ausgewählt ist.

7. **Persistente Datenspeicherung**
   - Alle Daten müssen persistent in einer Datenbank gespeichert werden. -> Die persistente Datenspeicherung erfolgt über eine SQLite Datenbank.

**Eingabedaten**

- GUI-Bereich für die Auswahl von Dateien. -> Dies ist der Button mit der Beschriftung 'Datei einlesen', zu sehen ist dieser rechts oben im [Applikation Mockup](##Mockups ###Applikation Mockup), dieser öffnet ein Dateiauswahl Dialog.

**Ausgabedaten**

- Anzeige der Tagesberichte mit Diagramme -> Die Anzeige des Tagesberichts erfolgt über den jeweiligen Button mit 'Report' - Icon, über den Reiter 'Bericht erstellen'.

**III. Nicht funktionale Anforderungen**

**Qualitätsanforderungen**

**Benutzbarkeit**

1. **Datenzugriff jederzeit möglich**
   - Benutzer sollten jederzeit auf die Daten zugreifen können.

2. **Bericht-Export für jeden Tag möglich**
   - Die Software sollte die Möglichkeit bieten, Tagesberichte zu exportieren.

**Zuverlässigkeit**

- Die Software muss zuverlässig und stabil arbeiten.

**Effizienz**

1. **Verarbeitung der täglich erhaltenen '.csv'-Dateien, noch am selben Tag**
   - Die Software sollte die täglich empfangenen '.csv'-Dateien effizient verarbeiten können.

**Rahmenbedingungen**

**Organisatorische Rahmenbedingungen**

- Die Software wird für einen Verein entwickelt, der Wetterdaten von verschiedenen Wetterstationen sammelt.

**Technologische Rahmenbedingungen**

**Schnittstellen**

- Einlesen von '.csv'-Dateien über eine GUI-Schnittstelle (Filepicker | Dropzone).

**Plattform**

- Die Software wird für Microsoft Windows optimiert.

**Normative Rahmenbedingungen**

**Gesetze / Verordnungen**

- Die Softwareentwicklung muss geltenden Gesetzen und Verordnungen entsprechen.

**IV. Abnahme und Freigabe**

Das Pflichtenheft wird nach Fertigstellung an die relevanten Stakeholder zur Abnahme und Freigabe weitergeleitet.

**V. Änderungsmanagement**

Änderungen oder Ergänzungen dieser Anforderungen erfordern eine formelle Änderungsanforderung und müssen von den zuständigen Stakeholdern genehmigt werden.

**VI. Dokumentenhistorie**

| Version | Datum       | Autor           | Änderungen         |
| ------- | ----------- | --------------- | ------------------ |
| 1.0     | DD.MM.JJJJ  | Vorname Nachname | Erstellt das Dokument |

Dieses Pflichtenheft dient als Grundlage für die Entwicklung der Wetterdatenverarbeitungssoftware und muss von allen beteiligten Parteien genehmigt werden, bevor mit der Entwicklung begonnen wird.