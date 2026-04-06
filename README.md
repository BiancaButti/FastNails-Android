# 💅 FastNails

**Status:** In Progress 🚧

**Type:** Android Mobile Application

**Architecture:** Clean Architecture + Jetpack Compose

**Android Studio:** Meerkat (2024.3.1)

**Kotlin:** 2.0.20

FastNails is a mobile application that connects users to nail care professionals on demand, prioritizing **speed, simplicity, and a fluid experience**.

The goal is to allow users to quickly schedule an appointment in everyday situations — such as unexpected commitments or self-care moments — with a focus on **usability and operational efficiency**.

---

## 🎯 Objective

- Reduce friction in accessing beauty services
- Connect users and professionals efficiently
- Ensure a simple and predictable experience
- Validate an on-demand service model in a controlled environment

---

## 🚀 MVP

The first version of the system includes:

- User authentication and registration
- On-demand service request
- Service selection:
  - Hands
  - Feet
- Geographic restriction (initial regions defined)
- List of available professionals
- Appointment confirmation
- Status tracking until completion

Advanced features will be introduced iteratively.

---

## 🧱 Architecture

The project follows the principles of **Clean Architecture**, with clear separation of responsibilities and dependency inversion.

### 📐 Layers

#### 🎨 Presentation

Responsible for the interface and user interaction.

- Jetpack Compose
- ViewModels (`ViewModel` + `StateFlow`)
- State management (loading, success, error)
- Navigation (Jetpack Navigation Compose)

> The presentation layer contains no business logic.

---

#### 🧠 Domain

The core layer of the system.

- Entities
- Use Cases
- Repository protocols (interfaces)

> Independent of frameworks, UI, and infrastructure.

---

#### 🔌 Data

Responsible for communication with external sources.

- Repository implementations
- API integration (Retrofit)
- DTO to entity conversion

---

### 🔄 Dependency Flow

View → ViewModel → UseCase → Repository (interface) → Repository (implementation)


---

## ♿ Accessibility

The app was built with accessibility support, ensuring an inclusive experience for all users:

- TalkBack support
- Descriptive labels on interactive elements (`contentDescription`)
- Adequate contrast and readability

---

## 🧪 Unit Tests

The project includes unit test coverage across the Domain and Data layers, validating the main use cases and business rules.

---

## 🎨 Design System

The project uses a **custom Design System**, developed as a dedicated module and versioned independently.

---

<p align="center">
  <strong>Developed by Bianca Butti</strong><br/>
  <sub>FastNails • iOS and (in the near future) Android Engineering</sub>
</p>
