# Loan Ledger (starter project)

An original Android app in the same category as Vasool Drive / Vasool Book —
daily/weekly/monthly loan collection tracking for money lenders and micro-finance agents.

## What's included (v1 — Basic Ledger scope)
- **Data layer**: Room database with entities for `Line`, `Customer`, `Loan`,
  `CollectionEntry`, `Expense`, `ExpenseType`, `Area`
- **Repositories**: Collection (Balance = Investment − Expense + Collection),
  Customer, Loan (generates installment schedules), Expense, Line
- **Screens (all functional, wired to the database)**:
  - **Collection** — dashboard header + Collect / Pay / Completed tabs.
    The Pay tab is a real loan-creation form (pick customer, enter principal/
    total payable/installments/frequency, disburse).
  - **Customer** — list + add-customer dialog + activate/deactivate/delete
  - **Expense** — list + add-expense dialog (create expense types on the fly)
  - **Reports** — Daily Summary and Expense Summary computed from live data
  - **Settings** — Line manager (create lines with type + starting investment)
- App auto-creates a "Default Line" on first launch so it isn't empty.

## Still not built / known gaps
- Area and Expense Type management screens (Settings only has "Line" wired up;
  Area/Expense Type/Backup rows are visible but not yet clickable)
- Line Summary / Investment Summary / Missing Customer Summary reports
  (need multi-line switching in the UI first — currently the app always
  operates on the first/default line)
- Tamil language support (`res/values-ta/strings.xml` not added — all text
  is currently hardcoded English strings in the Composables, not string
  resources, so this needs a refactor pass first)
- CSV Import/Export
- No app icon yet — manifest uses the system default icon as a placeholder
  (`android:icon="@android:drawable/sym_def_app_icon"`) so the build doesn't
  fail on a missing resource. Swap in your own launcher icon before release.
- **This has not been compiled or run** — I don't have an Android SDK/emulator
  in this environment to verify it builds. There is a real chance of a typo,
  a missing import, or a Compose API mismatch somewhere. Budget time for a
  first-build debugging pass.
- Auth / cloud sync if you want the "login from any device" feature later

## How to open this project
1. Install **Android Studio** (Koala or newer).
2. Open this folder directly — Android Studio will detect `settings.gradle.kts` and sync automatically.
3. Let Gradle download dependencies (needs internet on first sync).
4. Run on an emulator or physical device (minSdk 24 = Android 7.0+).

## Important — about "100% same as Vasool Drive"
This project is built to match Vasool Drive's **functionality** (loan types, daily
collection workflow, dashboard math, tab structure) because that's not something
copyright protects — business logic and generic UI patterns (tabs, lists, dashboards)
are functional ideas, and several real competing apps (Vasool Drive, Vasool Collection
Tracker, Vasool App, Vasool Lite, Vasool Diary) already coexist doing the same thing.

What you should NOT do: copy their exact icon, exact color palette + logo, exact
copy-text, or their compiled code — that's copyright/trademark infringement of
their actual product, regardless of how the app is built. Use your own name, your
own icon/color scheme, and this original codebase, and you're on solid ground.

## Suggested next move
This is a multi-week build. For the fastest path from here, open this folder in
**Claude Code** (desktop or terminal) and work through the "Not yet built" list
screen-by-screen — it's much better suited to iterating on a real Android Studio
project than a chat window.
