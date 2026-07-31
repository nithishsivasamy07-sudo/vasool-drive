# Loan Ledger

A lightweight, offline-first daily/weekly/monthly loan collection tracker for money lenders and field collection agents — built as an installable Progressive Web App (PWA).

Manage customers, disburse loans with auto-generated installment schedules, track daily collections, log expenses, and see your cash position at a glance — all working fully offline, with data stored securely on-device.

---

## ✨ Features

- **📊 Live dashboard** — Investment, Expense, Collection, and Balance at a glance, updated in real time
- **💰 Collection workflow** — three-tab flow (Collect / Pay / Completed) covering the full daily collection cycle
- **📝 Loan disbursement** — pick a customer, set principal/total payable/installments/frequency, and the full repayment schedule is generated automatically
- **👥 Customer management** — add, activate/deactivate, and track customers per line
- **💸 Expense tracking** — log expenses against custom, on-the-fly expense categories
- **📈 Reports** — daily and expense summaries computed live from your data
- **⚙️ Line management** — organize loans into Daily / Weekly / Monthly / Enterprise / Monthly-Interest lines
- **📴 Works offline** — installable to your home screen, no internet required after first load
- **🔒 Local-first** — your data stays on your device; no account, no cloud, no third party involved

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Framework | React + Vite + TypeScript |
| Styling | Tailwind CSS |
| Local storage | IndexedDB via Dexie.js |
| Routing | React Router |
| PWA / offline | vite-plugin-pwa |
| Charts | Recharts *(reports, optional)* |

No backend, no server, no database account needed to run this — everything lives in your browser's local storage.

---

## 🚀 Getting Started

### Prerequisites
- [Node.js](https://nodejs.org/) v18 or newer
- npm (comes with Node)

### Installation

```bash
# clone or download the project, then:
cd loan-ledger
npm install
```

### Run in development

```bash
npm run dev
```

Open the printed local URL (usually `http://localhost:5173`) in your browser.

### Build for production

```bash
npm run build
npm run preview   # preview the production build locally
```

### Install as an app (PWA)
Once running, open the site in Chrome/Edge on desktop or mobile and use **"Install App"** / **"Add to Home Screen"** from the browser menu. The app will then work offline and launch like a native app.

---

## 📱 App Structure

Five main sections, accessible via bottom navigation:

1. **Collection** — the home screen: daily dashboard + Collect/Pay/Completed tabs
2. **Expense** — log and review business expenses
3. **Customer** — manage your customer list
4. **Reports** — daily and expense summaries
5. **Settings** — manage lines (loan batches/routes)

---

## 🗃 Data Model

All data is stored locally via IndexedDB, structured around these core entities:

- **Line** — a collection route/batch (Daily, Weekly, Monthly, Enterprise, or Monthly-Interest)
- **Customer** — a borrower, tied to a Line
- **Loan** — principal, total payable, and installment terms for a Customer
- **CollectionEntry** — one record per installment due date, tracking paid/unpaid status
- **Expense** / **ExpenseType** — business expense tracking
- **Area** — optional geographic/route grouping

See `loan-ledger-web-spec.md` in this repo for the full schema and screen-by-screen specification this project was built from.

---

## 🗺 Roadmap / Known Gaps

This is a v1 **Basic Ledger** build. Intentionally out of scope for now:

- ☁️ Cloud sync / multi-device login
- 🌐 Multi-language support (e.g. Tamil)
- 📤 CSV import/export
- 📍 GPS tracking, staff accounts, role-based access
- 💳 Payment gateway / SMS integration

These are natural v2 candidates once the core local-only experience is solid.

---

## ⚖️ A note on originality

This project is built to match the **functionality and workflow** common to daily-collection loan tracking apps (customer management, installment schedules, daily collection cycles, dashboard math) — patterns shared by several apps in this category. It does **not** copy any existing app's code, exact visual design, icon, or branding. If you're extending this project commercially, keep it that way: original name, original icon, original color palette, your own codebase.

---

## 📄 License

*(Add your license of choice here — e.g. MIT, or "All rights reserved" if this is a private commercial project.)*
