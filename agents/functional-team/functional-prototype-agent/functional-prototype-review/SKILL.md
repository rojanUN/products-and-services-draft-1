---
name: functional-prototype-review
description: Reviews or evaluates functional prototype against an architecture.
allowed-tools: Read, Grep, Glob, Write, Bash
---

# Functional Prototype Review

## Purpose

Compare a functional prototype document against a product architecture document, identify misalignments and gaps, then write a structured Markdown review to the working directory.

## Inputs

You need two documents before starting:

1. **Functional Prototype Document** — the specification being reviewed
2. **Product Architecture Document** — the reference architecture to compare against

If the user has not provided both documents (as file paths or pasted content), ask for them now:

> "Please provide the path (or content) of the **functional prototype document** and the **product architecture document**."

Do not proceed, guess, or fabricate content until both inputs are confirmed.

## Workflow

### Step 1 — Read and parse both documents

- Use `Read` to load each file (if paths were given) or treat pasted content as the source text.
- Extract: domains / bounded contexts, features, constraints, data models, interface contracts, non-functional requirements.

### Step 2 — Run the six review dimensions

Work through each dimension defined in [references/review-ref.md](references/review-ref.md):

1. Domain Alignment
2. Feature Comprehensiveness
3. Conflict & Violation Detection
4. Non-Functional Requirement Coverage
5. Data Model Consistency
6. Interface Contract Verification

For each dimension, collect findings with: term/item, spec evidence, architecture evidence, severity (HIGH / MEDIUM / LOW), and recommended fix.

### Step 3 — Compute summary counts

| Metric | Count |
|---|---|
| Misalignments | (total domain alignment issues) |
| Capability Gaps | (features in architecture not covered by spec) |
| Conflicts | (direct contradictions) |
| Unmapped Capabilities | (spec items with no architecture anchor) |

Assign a RAG status (Red / Amber / Green) to each dimension:
- **Red** — one or more HIGH findings
- **Amber** — MEDIUM findings only, no HIGH
- **Green** — LOW findings or none

### Step 4 — Determine output filename

Use the format: `prototype-review-YYYY-MM-DD.md`  
Get today's date via: `date +%Y-%m-%d`

### Step 5 — Write the review document

Render the output using the structure in [templates/review-output-template.md](templates/review-output-template.md).  
Write the file to the current working directory.

Report the saved filename to the user when done.
