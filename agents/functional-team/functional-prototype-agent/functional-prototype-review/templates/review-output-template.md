# Review Output Template

Use this template verbatim when rendering the output document. Replace all `{{ }}` placeholders with actual values. Preserve all section headings, table structures, and formatting rules exactly.

---

```markdown
<!-- ============================================================
     SECTION 1 — COVER PAGE
     ============================================================ -->

# Functional Prototype Review

**Subtitle:** {{ PROTOTYPE_FILENAME }} vs. {{ ARCHITECTURE_FILENAME }}

| Field | Value |
|---|---|
| Date | {{ YYYY-MM-DD }} |
| Prototype | {{ PROTOTYPE_FILENAME }} |
| Architecture | {{ ARCHITECTURE_FILENAME }} |
| Classification | INTERNAL — DRAFT |
| Prepared by | Claude Code / functional-prototype-review skill |

---

<!-- ============================================================
     SECTION 2 — EXECUTIVE SUMMARY
     ============================================================ -->

## Executive Summary

### RAG Status

| Dimension | RAG | Finding Count |
|---|---|---|
| Domain Alignment | {{ RAG_DOMAIN }} | {{ COUNT_DOMAIN }} |
| Feature Comprehensiveness | {{ RAG_FEATURE }} | {{ COUNT_FEATURE }} |
| Conflicts & Violations | {{ RAG_CONFLICT }} | {{ COUNT_CONFLICT }} |
| Non-Functional Requirements | {{ RAG_NFR }} | {{ COUNT_NFR }} |
| Data Model Consistency | {{ RAG_DATA }} | {{ COUNT_DATA }} |
| Interface Contract | {{ RAG_INTERFACE }} | {{ COUNT_INTERFACE }} |

> **Legend:** 🔴 Red = HIGH finding present | 🟡 Amber = MEDIUM only | 🟢 Green = LOW / none

### Summary Counts

| Metric | Count |
|---|---|
| Total Misalignments | {{ TOTAL_MISALIGNMENTS }} |
| Capability Gaps | {{ TOTAL_GAPS }} |
| Conflicts | {{ TOTAL_CONFLICTS }} |
| Unmapped Capabilities | {{ TOTAL_UNMAPPED }} |

### Overall Health

{{ 2–3 sentence narrative summarising the general state of alignment between the prototype and architecture. Highlight the most critical concern and the strongest area of alignment. }}

---

<!-- ============================================================
     SECTION 3 — DOMAIN ALIGNMENT ANALYSIS
     ============================================================ -->

## 1. Domain Alignment Analysis

### Findings

| Term | Spec Says | Architecture Says | Severity |
|---|---|---|---|
| {{ TERM_1 }} | {{ SPEC_SAYS_1 }} | {{ ARCH_SAYS_1 }} | {{ SEV_1 }} |
| {{ TERM_2 }} | {{ SPEC_SAYS_2 }} | {{ ARCH_SAYS_2 }} | {{ SEV_2 }} |

<!-- Severity row colouring (Markdown renderers that support HTML): -->
<!-- HIGH rows: apply red background if renderer supports inline HTML -->
<!-- MEDIUM rows: apply amber background -->
<!-- LOW rows: apply grey background -->

### Analysis

{{ Narrative paragraph explaining the domain alignment findings. Describe the pattern of misalignment, likely root cause, and impact on downstream development. }}

---

<!-- ============================================================
     SECTION 4 — FEATURE COMPREHENSIVENESS ANALYSIS
     ============================================================ -->

## 2. Feature Comprehensiveness Analysis

### Coverage Summary

| Feature ID | Feature Name | Coverage Status |
|---|---|---|
| F-01 | {{ FEATURE_NAME_1 }} | {{ Full / Partial / None }} |
| F-02 | {{ FEATURE_NAME_2 }} | {{ Full / Partial / None }} |

<!-- Coverage colouring: Full = green | Partial = amber | None = red -->

### Gap Detail

For each Partial or None entry, provide a specific recommendation:

**{{ FEATURE_ID }} — {{ FEATURE_NAME }}**
- **Coverage:** {{ Partial / None }}
- **What is missing:** {{ Description of what the spec does not address }}
- **Recommendation:** {{ Specific action to close the gap }}

---

<!-- ============================================================
     SECTION 5 — CONFLICTS AND VIOLATIONS
     ============================================================ -->

## 3. Conflicts and Violations

<!-- One sub-section per conflict, numbered C-01, C-02, etc.          -->
<!-- Use a red border note or blockquote to signal severity.           -->

### C-01 — {{ CONFLICT_TITLE }}

> ⚠️ **Conflict** — {{ one-sentence description }}

| Field | Detail |
|---|---|
| Classification | {{ Security / Data Flow / Technology / Lifecycle / Other }} |
| Spec Evidence | {{ Quote or reference from the prototype document }} |
| Architecture Evidence | {{ Quote or reference from the architecture document }} |
| Recommended Fix | {{ Specific change required to resolve the conflict }} |

### C-02 — {{ CONFLICT_TITLE }}

> ⚠️ **Conflict** — {{ one-sentence description }}

| Field | Detail |
|---|---|
| Classification | {{ Classification }} |
| Spec Evidence | {{ Evidence }} |
| Architecture Evidence | {{ Evidence }} |
| Recommended Fix | {{ Fix }} |

<!-- Add C-03, C-04, … as needed. If no conflicts found, write: -->
<!-- > No conflicts detected in this review. -->

---

<!-- ============================================================
     SECTION 6 — PRIORITY ACTION LIST
     ============================================================ -->

## 4. Priority Action List

| Priority | Action | Owner | Location in Spec |
|---|---|---|---|
| P1 | {{ ACTION_1 }} | {{ OWNER_1 }} | {{ SPEC_LOCATION_1 }} |
| P1 | {{ ACTION_2 }} | {{ OWNER_2 }} | {{ SPEC_LOCATION_2 }} |
| P2 | {{ ACTION_3 }} | {{ OWNER_3 }} | {{ SPEC_LOCATION_3 }} |
| P3 | {{ ACTION_4 }} | {{ OWNER_4 }} | {{ SPEC_LOCATION_4 }} |

<!-- Priority row colouring:                                           -->
<!-- P1 = red row (must fix before development starts)                 -->
<!-- P2 = amber row (fix during specification review)                  -->
<!-- P3 = grey row (improvement for future iteration)                  -->

**Priority Key:**
- **P1** — Must fix before development starts
- **P2** — Fix during specification review
- **P3** — Improvement for future iteration
```
