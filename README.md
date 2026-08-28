# PortfolioLauncher

Kleine JavaFX-Desktop-App, um eigene Projekte als Karten zu zeigen und direkt zu starten.

- Bild per Drag & Drop auf ein Fenster ziehen, dann Titel/Status/Beschreibung/Tech-Stack eingeben
- Jede Karte wird als YAML-Datei gespeichert
- "Projekt öffnen" führt einen frei einstellbaren Startbefehl aus (funktioniert für jede Sprache, z.B. `./nob && ./build/game` für ein C/nob.h-Projekt oder `python main.py`)
- "Bearbeiten" öffnet entweder die Eingabemaske erneut oder den Quellcode-Ordner, je nachdem was beim Anlegen eingestellt wurde

## Bauen und starten

Braucht keine lokale Java-Installation außer zum ersten Bauen (Gradle lädt sich bei Bedarf selbst ein passendes JDK 21):

```
./gradlew run
```

## Fertige Version ohne Java-Installation

Bei jedem Push baut GitHub Actions automatisch eigenständige Programme für Linux, Windows und macOS (jeweils mit eigener Java-Laufzeit drin, kein Java auf dem Zielrechner nötig). Zu finden unter dem Tab **Actions** dieses Repos, im jeweils neuesten Lauf ganz unten bei "Artifacts".

Entpacken und `bin/PortfolioApp` (bzw. `PortfolioApp.exe` unter Windows) starten.
