# 💅 FastNails

**Status:** In Progress 🚧

**Type:** Android Mobile Application

**Architecture:** Clean Architecture + Jetpack Compose

**Android Studio:** Meerkat (2024.3.1)

**Kotlin:** 2.0.20

---

FastNails is a mobile application that connects users to nail care professionals on demand, prioritizing **speed, simplicity, and a fluid experience**.

The goal is to allow users to quickly schedule an appointment in everyday situations — such as unexpected commitments or self-care moments — with a strong focus on **usability and operational efficiency**.

---

## 🎯 Objective

* Reduce friction in accessing beauty services
* Connect users and professionals efficiently
* Ensure a simple and predictable experience
* Validate an on-demand service model in a controlled environment

---

## 🚀 MVP

The first version of the system includes:

* User authentication and registration
* On-demand service request
* Service selection:

  * Hands
  * Feet
* Geographic restriction (initial regions defined)
* List of available professionals
* Appointment confirmation
* Status tracking until completion

> Advanced features will be introduced iteratively.

---

## 🧱 Architecture

The project follows the principles of **Clean Architecture**, ensuring clear separation of responsibilities and proper dependency inversion.

### 📐 Layers

#### 🎨 Presentation

Responsible for UI rendering and user interaction.

* Jetpack Compose
* ViewModels (`ViewModel` + `StateFlow`)
* UI state management (loading, success, error)
* Navigation (Navigation Compose)

> This layer contains no business logic.

---

#### 🧠 Domain

The core layer of the system.

* Entities
* Use Cases
* Repository interfaces

> Fully independent from frameworks, UI, and data sources.

---

#### 🔌 Data

Handles data operations and external integrations.

* Repository implementations
* Remote data sources (API integration)
* DTO ↔ Entity mapping

---

### 🔄 Dependency Flow

```
UI → ViewModel → UseCase → Repository (interface) → Repository (implementation)
```

---

## ♿ Accessibility

The application is built with accessibility in mind, ensuring an inclusive experience:

* TalkBack support
* Meaningful accessibility labels (`contentDescription`)
* Proper contrast and readability

---

## 🧪 Unit Tests

The project includes unit test coverage for the **Domain** and **Data** layers, ensuring business rules and core flows are properly validated.

---

## 🎨 Design System

The project uses a **custom Design System**, implemented as a dedicated module and versioned independently.

This module centralizes:

* Colors
* Typography
* Components (Buttons, Cards, etc.)
* Theming

---

<p align="center">
  <strong>Developed by Bianca Butti</strong><br/>
  <sub>FastNails • Mobile Engineering (iOS & Android)</sub>
</p>
