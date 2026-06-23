# Review Reference — Functional Prototype vs. Architecture

This file defines the evaluation criteria for each of the six review dimensions. The skill reads this file during Step 2 of its workflow.

---

## Dimension 1: Domain Alignment

**Goal:** Verify that every domain, bounded context, and core term in the functional prototype maps cleanly to the architecture vocabulary.

**What to check:**
- All domain terms in the prototype exist in the architecture (or are explicitly extended from it).
- Domain boundaries in the prototype do not contradict domain boundaries in the architecture.
- Ubiquitous language is consistent — the same concept is not called two different things across the two documents.

**Severity guide:**
| Severity | Condition |
|---|---|
| HIGH | A core domain is missing from one document, or a term maps to a conflicting concept |
| MEDIUM | A term is used inconsistently but can be inferred to mean the same thing |
| LOW | Minor naming variation with no semantic difference |

---

## Dimension 2: Feature Comprehensiveness

**Goal:** Confirm that every capability listed in the architecture is addressed by the prototype specification.

**What to check:**
- Create a feature inventory from the architecture (all named features, modules, or capabilities).
- For each architecture feature, determine coverage in the prototype: **Full**, **Partial**, or **None**.
- Flag features with **None** as capability gaps.
- Flag features with **Partial** coverage with a description of what is missing.

**Severity guide:**
| Severity | Condition |
|---|---|
| HIGH | Architecture feature has no coverage in the prototype |
| MEDIUM | Architecture feature is partially addressed; key behaviour is omitted |
| LOW | Minor detail omitted; core behaviour is covered |

---

## Dimension 3: Conflict and Violation Detection

**Goal:** Identify direct contradictions — cases where the prototype specifies behaviour that conflicts with the architecture's rules, constraints, or guarantees.

**What to check:**
- Security or access control rules: does the prototype violate any constraint defined in the architecture?
- Data flow direction: does the prototype route data in a way the architecture prohibits?
- Technology or layer constraints: does the prototype mandate a component the architecture explicitly excludes?
- Lifecycle rules: does the prototype trigger state transitions the architecture marks as invalid?

**Severity guide:**
| Severity | Condition |
|---|---|
| HIGH | Direct, unambiguous contradiction that would cause a broken build or runtime failure |
| MEDIUM | The conflict is resolvable with a clear design decision but is currently contradictory |
| LOW | Edge case conflict unlikely to surface in normal operation |

**Conflict ID convention:** C-01, C-02, … (assigned in discovery order)

---

## Dimension 4: Non-Functional Requirement Coverage

**Goal:** Ensure the prototype addresses NFRs (performance, scalability, security, availability, observability) that the architecture mandates.

**What to check:**
- List all NFRs stated in the architecture.
- For each NFR, does the prototype include a corresponding acceptance criterion, constraint, or design note?
- Latency targets, SLA thresholds, throughput limits — are they propagated?

**Severity guide:**
| Severity | Condition |
|---|---|
| HIGH | A mandatory NFR (security, data residency, SLA) has no coverage |
| MEDIUM | NFR is acknowledged but no measurable criterion is given |
| LOW | NFR is covered; minor clarification would improve precision |

---

## Dimension 5: Data Model Consistency

**Goal:** Verify that entities, attributes, relationships, and cardinality rules are consistent across both documents.

**What to check:**
- Every entity named in the prototype exists in the architecture data model (or is a justified extension).
- Primary key and foreign key relationships agree.
- Cardinality (one-to-many, many-to-many) is the same in both documents.
- Required vs. optional attributes are consistent.

**Severity guide:**
| Severity | Condition |
|---|---|
| HIGH | An entity or relationship is defined differently enough to break referential integrity |
| MEDIUM | Attribute or cardinality mismatch requiring a schema change |
| LOW | Naming difference or optional attribute discrepancy |

---

## Dimension 6: Interface Contract Verification

**Goal:** Confirm that API endpoints, event schemas, and integration contracts in the prototype conform to the architecture's interface definitions.

**What to check:**
- Every API endpoint in the prototype exists in the architecture contract (or is a new, justified addition).
- Request/response schemas match (field names, types, required/optional).
- Event topics, message formats, and payload schemas are consistent.
- Authentication and authorisation schemes are applied as the architecture specifies.

**Severity guide:**
| Severity | Condition |
|---|---|
| HIGH | Interface contract is incompatible — would break a consumer |
| MEDIUM | Schema mismatch that requires a negotiated change |
| LOW | Minor field renaming or additional optional field |

---

## RAG Assignment Summary

After collecting all findings across all six dimensions, assign a RAG status per dimension:

| RAG | Rule |
|---|---|
| Red | At least one HIGH-severity finding in this dimension |
| Amber | No HIGH findings; at least one MEDIUM finding |
| Green | Only LOW findings, or no findings at all |
