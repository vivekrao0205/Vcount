<div align="center">
  <img src="https://raw.githubusercontent.com/vivekrao0205/Vcount/main/screens/logo.png" width="120" alt="Vcount Logo"/>
</div>


  # 📱 Vcount – Smart Day Counter Android App
  A minimal Android app for counting daily progress.
</div>

---

## Overview

**Vcount** is a clean and modern **day-tracking app** built using **Java in Android Studio**.  
It helps users **track, count, and manage daily activities or habits** through a smooth, pastel-themed UI.  
The app focuses on **simplicity, speed, and ease of use**, perfect for personal productivity or habit tracking.

---

## Features

-  **Splash Screen** – Displays the app logo and name “Vcount”.
-  **Home Screen** – Shows “Recent” list with counters for each activity.
-  **Details/Add Screen** – Add or edit an activity with day increment/decrement.
-  **Modern UI** – Rounded cards, pastel colors, and simple layout.
-  **Lightweight Architecture** – Java + XML using Material Components.

---

##  Tech Stack

| Component | Technology |
|------------|-------------|
| **Language** | Java |
| **IDE** | Android Studio |
| **UI Design** | XML Layouts |
| **Architecture** | Activity-based (MVVM-ready) |
| **Database (optional)** | Room |

---

##  Screens Overview

| Screen | Description |
|:--|:--|
| **Splash** | Displays circular logo and app name “Vcount”. |
| **Home** | Lists “Recent” cards with counters and (+ / –) controls. |
| **Details/Add** | Add or edit activities and track day count. |

---

## Project Structure

```
app/
├── java/
│   └── com/vivek/vcount/
│       ├── MainActivity.java
│       ├── SplashActivity.java
│       ├── HomeActivity.java
│       ├── DetailsActivity.java
│       └── models/
│           └── [Model Classes]
│
├── res/
│   ├── layout/
│   │   ├── activity_splash.xml
│   │   ├── activity_home.xml
│   │   └── activity_details.xml
│   │
│   └── drawable/
│       ├── logo_vcount.xml
│       └── icons.xml
│
└── AndroidManifest.xml

```

##  App Preview
<div align="center">
  <img src="https://raw.githubusercontent.com/vivekrao0205/Vcount/main/screens/ui.png" width="400" alt="UI Preview"/>
</div>

---

## 👨‍💻 Author
**Vivek Rao**  
Developed with ❤️ using Android Studio 

