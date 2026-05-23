# AetherHUD 

> Simple. Clean. Powerful.

A modern minimalist PvP HUD mod for Minecraft 1.21.4 (Fabric).

## Modules
| Module | Description |
|--------|-------------|
| FPS | Current frames per second |
| Ping | Server latency in ms |
| CPS | Left + right clicks per second |
| Armor | Equipped armor items |
| Coordinates | XYZ position (color coded) |
| Direction | Compass facing + yaw degrees |
| Speed | Horizontal movement in blocks/sec |
| Combo | Hit streak counter (color shifts at 4/8/15) |
| Target HUD | Name + animated health bar of crosshair target |
| Reach | Distance to targeted entity |
| Keystrokes | Live WASD + LMB/RMB display |
| Session Playtime | Time since mod loaded |
| Potion HUD | Active effects + duration |

## Controls
- **H** — Open HUD Editor
- **Drag** modules to reposition
- **Right-click** module in editor → toggle / scale
- **ESC** — Save and close editor

## Building

### Via GitHub Actions (recommended)
Push to `main` branch — the workflow builds automatically and uploads the JAR as an artifact.

### Local build
You need JDK 21 and Gradle 8.8.

```bash
gradle build
```

Jar will be at `build/libs/aetherhud-1.0.0.jar`

## Compatibility
- Minecraft 1.21.4
- Fabric Loader ≥ 0.16.9
- Works with Sodium
- PojavLauncher / MojoLauncher compatible

## Author
SparkyNox — [Modrinth](https://modrinth.com/user/sparkynox)
