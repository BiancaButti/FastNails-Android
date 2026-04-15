---
name: Feature request
about: Suggest an idea for this project
title: Feature
labels: ''
assignees: ''

---

### User Story
**As a** app user,
**I want to** see a manicure's profile screen,
**so that** I can view her details and book an appointment.

---

### Description
Implement the manicure profile screen, displaying all relevant information and a booking action button.

**The screen must display:**
- Profile photo
- Name
- Star rating
- Address
- List of services offered

**Interaction:**
- "Agendar" button visible and tappable
- Entry point: tapping a manicure row on the home screen list

> Use fake/hardcoded data to simulate the profile content.

---

### Tasks
- [ ] Create profile screen layout (photo, name, rating, address, services)
- [ ] Implement services list component
- [ ] Add "Agendar" button
- [ ] Wire up navigation from manicure list row → profile screen
- [ ] Create fake/hardcoded manicure profile data

---

### Out of Scope
- Real booking flow
- Backend integration
- Real data fetching
