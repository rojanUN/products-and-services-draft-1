# Design Considerations: Product and Service Abstraction Layer

## 1.1 Overview

### 1.1.1 Problem Statement

The objective is to design a Product and Service Abstraction Layer that provides a unified, configurable metadata model for representing products, services, and value components.

This layer acts as a foundational platform capability that enables downstream orchestration systems (such as the Value Flow Engine) to resolve:

* Product definitions
* Service definitions
* Value computation rules
* Inheritance and override configurations

The system must support:

* Dynamic product modeling across multiple asset types (currency, commodity, etc.)
* Reusable service definitions across products
* Configurable value computation rules
* Hierarchical inheritance with override capability

### 1.1.2 Primary Considerations

The abstraction layer must achieve the following:

Product Abstraction

* Support dynamic definition of products (e.g., currency, commodity, financial instruments)
* Avoid hardcoded or domain-specific product constraints
* Allow products to be fully configurable metadata entities

**Service Abstraction**

* Model services as reusable operational capabilities
* Support many-to-many relationship between products and services
* Enable service reuse across multiple products

**Value Component Abstraction**

* Define value components as reusable computation units:

Value components must support flexible financial computation and accounting enrichment requirements.

The abstraction must support configurable computation structures for:

* Fees
* Taxes
* Commissions
* Reward points
* Settlement adjustments / Discounts
* Service Charge

This flexibility is required to:

* Reuse computation definitions across products and services
* Support product-specific and partner-specific computation behavior
* Enable configurable accounting-related value mappings for downstream systems
* Support override and inheritance-based configuration resolution
* Avoid hardcoded financial computation structures
* Support financial and operational constructs such as fees, commissions, spreads, taxes, routing rules
* Attach value components to products and services

**Computation Metadata**
Each value component must define:

* Computation model (e.g., FIXED, PERCENTAGE, SLAB, FORMULA)
* Computation event (e.g., COLLECTION, ROUTING, CANCEL, PAYOUT)
* Component type (classification of value behavior)

**Configuration Resolution**

* Support inheritance of value components across Product Type → Product → Service
* Support override behavior at more specific levels
* Apply best-match resolution strategy for final effective configuration

**Platform Consumption**

* Provide resolved metadata to orchestration systems (Value Flow Engine)
* Enable enrichment of transactional context with product and value definitions

### 1.1.3 Secondary Considerations

The following are explicitly out of scope for this layer:

* Execution of financial business logic (handled by downstream domains)
* Transaction processing or orchestration execution (handled by Value Flow Engine)
* Partner onboarding or external API management
* Real-time settlement or payment execution logic

This layer is strictly a metadata and configuration abstraction layer.

## 1.2 Core Design Considerations

### 1.2.1 Product and Service Separation

Products and services are modeled as independent but related constructs.

* Products define “what exists”
* Services define “what can be done”

A product may expose multiple services, and services may be shared across multiple products.

This relationship is modeled as many-to-many to ensure maximum reuse and flexibility.

### 1.2.2 Product Type Abstraction

Product Types act as higher-level classification and configuration containers.

Product Types define:

* Default value components
* Reusable configuration templates
* Baseline behavior for products under the type

Product Types are dynamically configurable and not static enums.

### 1.2.3 Value Component Model

Value components represent reusable computation definitions.

They are designed to model:

* Financial calculations
* Operational adjustments
* Routing and decision metadata

Value components are attachable at multiple levels:

* Product Type
* Product
* Service

### 1.2.4 Inheritance and Override Strategy

Value components follow a hierarchical inheritance model:

Product Type → Product → Service

Key rules:

* Lower-level definitions inherit from higher-level definitions
* More specific configurations override inherited ones
* A best-match resolution strategy determines the effective configuration

This ensures:

* Consistency by default
* Flexibility where required

### 1.2.5 Computation Model Abstraction

Computation models define how a value component is evaluated.

Supported conceptual models include:

* FIXED
* PERCENTAGE
* SLAB
* FORMULA
* CONFIGURABLE STRATEGY-BASED EXECUTION

Computation models are metadata-driven and not hardcoded logic.

### 1.2.6 Computation Events

Value components are associated with lifecycle execution events such as:

* COLLECTION
* ROUTING
* CANCEL
* PAYOUT

These events define when a value component becomes relevant in the orchestration lifecycle.

### 1.2.7 Resource-Based Modeling

Products, Product Types, Services, and Value Components are all represented as Application Resources.

This abstraction enables:

* Centralized configuration management
* Extensibility for new resource types
* Unified metadata handling across the platform

Resources may represent both:

* Business-level constructs
* Orchestration-level constructs
* Technical configuration entities

### 1.2.8 Metadata Consumption by Orchestration Layer

This layer exists to support downstream systems such as the Value Flow Engine.

It provides:

* Resolved product definitions
* Resolved service definitions
* Applicable value components
* Computation metadata
* Inheritance-resolved configuration output

It does not execute business logic itself.

### 1.2.9 Dynamic Configuration Model

The system must support dynamically adjustable configuration of:

* Products
* Services
* Value components
* Product types

All configurations must be runtime-resolvable without requiring code changes.

### 1.2.10 Extensibility

The model must be extensible in following aspects:

* Multi-tenancy (tenant-specific overrides)
* Partner-specific configurations
* Versioning of products/services/components
* Chained computation models

### 1.2.11 Future Support

The model must support future enhancements including:

* Conditional value component execution
* Dependency-based computation ordering

### 1.2.12 Domain Boundary Constraints

This layer must remain strictly on a platform abstraction layer.

It must NOT contain:

* Revenue logic:
  + The Products & Services module may define:
    - Available fees
    - Possible computation structures
    - Configurable components
  + But it should not decide:
    - Profit strategy
    - Margin strategy
    - Revenue Optimization
* Pricing policy rules:
  + The Products and Services module must define:
    - Supported supports the following calculation methods: FIXED, PERCENTAGE, SLAB, FORMULA etc.
  + But should not define:
    - Nepal corridor fee must be 2%
    - Premium users get 50% discount
    - Weekend transfer costs extra

These are in fact business policies that belong to a domain layer like revenue management, campaign management, etc.

* Settlement logic
* Transaction execution logic

These responsibilities belong to downstream domain layers.

## 1.3 Open Considerations

The following items remain to be finalized:

* Product versioning strategy
* Service versioning and lifecycle management
* Deterministic rules for best-match resolution scoring
* Conflict resolution strategy for overlapping value components
* Runtime caching strategy for resolved configurations
* Separation between business resources and technical/API resources
* Multi-tenant override precedence rules
* Audit and traceability of configuration changes