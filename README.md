# Brand Identity & Architecture — Loan Ledger Project

---

## Part 1 — Name

Avoid anything built on "Vasool" (Tamil/Hindi for "collection") — while it's a
generic word, staying distant from it keeps you clearly clear of the existing
apps by name as well as code. Instead, draw from the *ledger* itself —
Tamil Nadu's small-finance world runs on the physical account book
(கணக்கு பேனா / "kanakku pusthagam"), not on the act of collecting.

**Recommended: Kanakku** *(கணக்கு — "account / reckoning")*
A single, real Tamil word meaning "the accounts." Short, easy to say in
English or Tamil, and it names the *ledger*, not the collector — which
fits a tool used as much for record-keeping as for chasing payments.

Pairing options:
- **Kanakku** (standalone — cleanest, most brandable)
- **Kanakku Book**
- **KanakkuPro** (if you want a more commercial/SaaS-sounding variant)

Alternates, if Kanakku doesn't feel right:
| Name | Meaning | Character |
|---|---|---|
| **Thavanai** | "installment" (தவணை) | Names the core unit of the business — precise, a little more technical-sounding |
| **Pathivu** | "entry / record" (பதிவு) | Neutral, works well if you expand beyond lending later |
| **Kaiyedu** | "handbook" (கையேடு, literally "hand-book") | Warm, approachable, less finance-coded |
| **DailyBaki** | "baki" = balance/due, common Tamil/Hindi business word | More literal/functional, less distinctive |

Go with **Kanakku** unless one of the alternates resonates more — it's the
one with real cultural weight and zero collision risk.

---

## Part 2 — Visual Identity

The visual world here isn't generic fintech — it's the **physical ledger
book**: ruled paper, red ink for dues, black ink for paid, rubber stamps,
and — the specific reference this design plan is built around — **greenbar
paper**, the pale green-and-white striped continuous paper accountants used
for decades of hand- and machine-kept ledgers. That striping is the natural,
already-solved answer to "how do you make a dense list of numbers scannable"
— which is exactly what the Collect/Completed/Customer/Expense screens are.

### Color — 6 named values

| Token | Hex | Use |
|---|---|---|
| `paper` | `#FBFAF6` | App background — warm off-white, not stark white |
| `greenbar` | `#E7EFE3` | Alternating row stripe in every list (the signature element) |
| `ink` | `#1E2B23` | Primary text — a deep green-black, not pure black (evokes ledger ink, softer than `#000`) |
| `ink-red` | `#B23A2E` | Overdue / due amounts / "not paid" — real accounting red-ink convention |
| `ink-green` | `#2F7A4C` | Collected / paid / positive balance — matches the money-green already used in your screenshots, kept but redefined as *ink*, not a generic SaaS-green |
| `stamp` | `#33447A` | Brand accent — buttons, active tab, focus states. A rubber-stamp indigo-blue, distinct from the red/green already carrying financial meaning, so it never gets confused with "good/bad" signals |

This deliberately avoids both common AI-default palettes (warm-cream +
terracotta, and near-black + neon accent) — the cream tone here is paired
with a *striped ledger system and ink-red/ink-green semantics*, not a
terracotta accent, and there's no dark mode default.

### Type — 3 roles

| Role | Typeface | Why |
|---|---|---|
| **Numerals / amounts** | **IBM Plex Mono**, tabular figures, semibold for totals | Money columns must align vertically like a real ledger — monospace numerals are the actual functional reason, not a stylistic flourish |
| **UI / body text** | **Inter** | Excellent legibility at small sizes on cheap Android phones in bright outdoor light, which is the real-world condition this app is used in |
| **Tamil text** | **Hind Madurai** | Designed specifically to pair proportionally with Latin grotesk faces like Inter — use for the Settings language toggle and any Tamil strings, so English/Tamil switch without a jarring type-scale jump |

Type scale: keep it small and disciplined — this is a data-dense utility
app. Headers 20–24px semibold, body 15–16px regular, ledger amounts 16–18px
mono semibold, captions/labels 12–13px.

### Signature element
**Greenbar row striping**, executed cleanly: every scrollable list (Collect,
Completed, Customer, Expense) alternates `paper` / `greenbar` per row,
exactly like real accounting paper. Status badges (PAID / NOT PAID) render
as small **stamp-style rounded rectangles** with a slightly rotated
(1–2°) transform and a subtle inset border, referencing a rubber ink stamp
without literally illustrating one.

### Layout notes
- Mobile-first, single column, bottom tab bar (matches your original spec)
- Dashboard header numbers set in the mono face, large, tabular — no
  gradient cards, no shadow-heavy "fintech dashboard" tiles. Flat, ruled,
  paper-like.
- A thin `ink`-colored horizontal rule under the dashboard header and above
  the tab bar — reinforcing the ruled-paper feel structurally, not
  decoratively.

---

## Part 3 — Technical Architecture

### Layered architecture

```
┌─────────────────────────────────────────────┐
│  UI Layer (React components, per screen)     │
│  Collection / Expense / Customer / Reports /  │
│  Settings — each a route, built from shared   │
│  primitives (ListRow, StampBadge, AmountText) │
└───────────────────┬───────────────────────────┘
                    │ reads/writes via hooks
┌───────────────────▼───────────────────────────┐
│  State Layer (Zustand stores, one per domain)  │
│  useLineStore / useCustomerStore /             │
│  useLoanStore / useExpenseStore                │
└───────────────────┬───────────────────────────┘
                    │ calls
┌───────────────────▼───────────────────────────┐
│  Repository Layer (plain TS classes/functions) │
│  LineRepository, CustomerRepository,           │
│  LoanRepository, ExpenseRepository —           │
│  business logic lives here (e.g. installment   │
│  schedule generation, Balance calculation)      │
└───────────────────┬───────────────────────────┘
                    │ queries
┌───────────────────▼───────────────────────────┐
│  Data Layer (Dexie.js over IndexedDB)          │
│  db.ts defines tables matching the schema in    │
│  loan-ledger-web-spec.md                        │
└─────────────────────────────────────────────────┘
```

Why this shape: repositories keep business logic (installment math, balance
calculation) out of both the UI and the raw database layer, so it's testable
in isolation and swappable later — e.g. when you add cloud sync in v2, only
the Repository layer needs to change (add a sync call after each local
write); the UI and State layers don't need to know storage moved.

### Folder structure

```
src/
├── app/
│   ├── App.tsx                # router + bottom nav shell
│   └── routes.tsx
├── db/
│   └── db.ts                  # Dexie schema definition
├── repositories/
│   ├── lineRepository.ts
│   ├── customerRepository.ts
│   ├── loanRepository.ts      # installment schedule generation lives here
│   └── expenseRepository.ts
├── stores/
│   ├── useLineStore.ts
│   ├── useCustomerStore.ts
│   ├── useLoanStore.ts
│   └── useExpenseStore.ts
├── screens/
│   ├── collection/
│   │   ├── CollectionScreen.tsx
│   │   ├── CollectTab.tsx
│   │   ├── PayTab.tsx
│   │   └── CompletedTab.tsx
│   ├── expense/ExpenseScreen.tsx
│   ├── customer/CustomerScreen.tsx
│   ├── reports/ReportsScreen.tsx
│   └── settings/SettingsScreen.tsx
├── components/                # shared UI primitives
│   ├── ListRow.tsx             # implements the greenbar striping
│   ├── StampBadge.tsx          # PAID/NOT PAID badge
│   ├── AmountText.tsx          # mono, tabular, color-coded by sign
│   └── BottomNav.tsx
├── styles/
│   └── tokens.css              # the color/type tokens from Part 2, as CSS vars
└── types/
    └── models.ts                # Line, Customer, Loan, CollectionEntry, etc.
```

### State management pattern
One Zustand store per domain (Line, Customer, Loan, Expense). Each store:
1. Holds the in-memory list for the *active* Line
2. Exposes actions (`addCustomer`, `disburseLoan`, `markPaid`, etc.) that
   call the matching Repository, then re-fetch/update local state
3. Components subscribe only to the slice they need, so a payment update on
   the Collect tab doesn't re-render the Customer screen

No global "god store" — keep domains separate, matching the repository
boundaries above.

### Offline-first data flow
1. All reads/writes go straight to IndexedDB via Dexie — there is no network
   round-trip in v1, so every action is instant regardless of connectivity
2. `vite-plugin-pwa` precaches the app shell (JS/CSS/fonts) on first load, so
   the app itself opens offline, not just the data
3. A visible "last updated locally at HH:MM" indicator (not a fake "synced"
   label) sets honest expectations that this is on-device only until v2

### Migration path to v2 (cloud sync) — don't build now, but design shouldn't block it
- Repository methods should be the *only* place that touches Dexie directly
- When cloud sync is added, each Repository write gets a paired "push to
  server" call (fire-and-forget or queued), and a background pull merges
  remote changes — because the UI/State layers already only talk to
  Repositories, this doesn't require touching screens or stores

### Business logic that must live in the Repository layer, not components
- **Installment schedule generation** (`LoanRepository.createLoan`) — given
  principal, total payable, count, and frequency, generate N
  `CollectionEntry` rows spaced by the frequency
- **Balance calculation** — `Investment − Expense + Collection`, computed
  from live Dexie queries, not stored as a static field (avoids drift)
- **Overdue detection** — a `CollectionEntry` is overdue if `dueDate < today
  && isPaid === false`; the day-count shown in parentheses (e.g. "(7)") is
  `today − dueDate` in days

---

## Handoff checklist for Antigravity
When you start the build, paste this file **alongside**
`loan-ledger-web-spec.md` — the spec defines *what* to build screen by
screen; this file defines *how it should look* and *how the code should be
organized*. Point the agent at both before it starts scaffolding.
