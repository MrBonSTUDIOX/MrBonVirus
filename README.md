# MrBon Virus — Android Horror Simulation Application

**MrBon Virus** is an interactive full-screen Android horror simulation application featuring custom visuals, synchronized audio jumpscares, glitch effects, and the simulated **IronTurboMax** antivirus scanner.

## Disclaimer
This application is strictly a harmless visual and audio horror entertainment simulation:
- No real device shutdown occurs.
- No personal files are accessed, scanned, or deleted.
- No background services or malware behavior exist.
- Zero dangerous Android permissions are requested.

## Features
- **Immediate Horror Experience:** Plays a 1-second jumpscare audio upon launch followed by a 1-minute horror & hacking soundtrack with synchronized glitch animations.
- **Hidden 60-Second Timer (Ending 1 — Failed):** If the user fails to launch IronTurboMax within 60 seconds, a second jumpscare triggers followed by a simulated system shutdown screen (`ENDING 1 — FAILED`).
- **IronTurboMax Antivirus Simulation (Ending 2 — Success):** Tapping the blue IronTurboMax logo starts a 15-second visual scan with animated system directories, progress bar, and status messages.
- **10% Scan Failure Chance:** Each scan has a 10% probability of triggering `IRON TURBOMAX ERROR`, allowing the user to retry.

## Automated APK Build (GitHub Actions)
This repository includes a pre-configured GitHub Actions workflow (`.github/workflows/build.yml`).
1. Push this project to a GitHub repository (`main` or `master` branch).
2. Open the **Actions** tab in your GitHub repository.
3. Select the completed **Build Android APK** workflow run.
4. Download the **`MrBon-Virus-APK`** artifact to obtain `app-debug.apk`.
