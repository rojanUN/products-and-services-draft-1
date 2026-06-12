-- =============================================================
-- Sample INSERT queries for all schema entities (new schema)
-- Ordered by dependency (parent tables first)
-- 2-3 rows per table
-- All BaseEntity subclasses: uid, version, created_at, last_modified_at, created_by_id, modified_by_id
-- All MasterEntity subclasses also have: name, description, status
-- =============================================================

-- ---------------------------------------------------------
-- 1. sr_business_entity_category (MasterEntity, no deps)
-- ---------------------------------------------------------
INSERT INTO sr_business_entity_category (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(101, 0, NOW(), NOW(), 1, 1, 'business-category-1', 'business-category-1', 'ACTIVE'),
(102, 0, NOW(), NOW(), 1, 1, 'business-category-2', 'business-category-2', 'ACTIVE'),
(103, 0, NOW(), NOW(), 1, 1, 'business-category-3', 'business-category-3', 'ACTIVE');

-- ---------------------------------------------------------
-- 2. sr_country (MasterEntity, no deps)
-- ---------------------------------------------------------
INSERT INTO sr_country (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, alpha2_code, alpha3_code, numeric_code, dial_code, timezone)
VALUES
(201, 0, NOW(), NOW(), 1, 1, 'country-1', 'country-1', 'ACTIVE', 'C1', 'CTR1', '001', '+1', 'UTC'),
(202, 0, NOW(), NOW(), 1, 1, 'country-2', 'country-2', 'ACTIVE', 'C2', 'CTR2', '002', '+2', 'UTC+1'),
(203, 0, NOW(), NOW(), 1, 1, 'country-3', 'country-3', 'ACTIVE', 'C3', 'CTR3', '003', '+3', 'UTC+2');

-- ---------------------------------------------------------
-- 3. sr_currency_classification (MasterEntity, no deps)
-- ---------------------------------------------------------
INSERT INTO sr_currency_classification (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(301, 0, NOW(), NOW(), 1, 1, 'currency-classification-1', 'currency-classification-1', 'ACTIVE'),
(302, 0, NOW(), NOW(), 1, 1, 'currency-classification-2', 'currency-classification-2', 'ACTIVE'),
(303, 0, NOW(), NOW(), 1, 1, 'currency-classification-3', 'currency-classification-3', 'ACTIVE');

-- ---------------------------------------------------------
-- 4. sr_currency (BaseEntity, country/classification FKs commented out)
-- ---------------------------------------------------------
INSERT INTO sr_currency (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, alpha_three, numeric, symbol, default_rate_format, default_rate_masking, status)
VALUES
(401, 0, NOW(), NOW(), 1, 1, 'currency-1', 'CUR1', '401', '$',  '#,##0.00', 'XXX', 'ACTIVE'),
(402, 0, NOW(), NOW(), 1, 1, 'currency-2', 'CUR2', '402', '£',  '#,##0.00', 'XXX', 'ACTIVE'),
(403, 0, NOW(), NOW(), 1, 1, 'currency-3', 'CUR3', '403', 'S$', '#,##0.00', 'XXX', 'ACTIVE');

-- ---------------------------------------------------------
-- 5. sr_service_classification (BaseEntity, self-ref parent)
-- ---------------------------------------------------------
INSERT INTO sr_service_classification (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, parent_service_classification_id)
VALUES
(1001, 0, NOW(), NOW(), 1, 1, 'service-classification-1', 'service-classification-1', 'ACTIVE', NULL),
(1002, 0, NOW(), NOW(), 1, 1, 'service-classification-2', 'service-classification-2', 'ACTIVE', NULL),
(1003, 0, NOW(), NOW(), 1, 1, 'service-classification-3', 'service-classification-3', 'ACTIVE', 1001);

-- ---------------------------------------------------------
-- 6. sr_product_classification (BaseEntity, self-ref parent)
-- ---------------------------------------------------------
INSERT INTO sr_product_classification (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, parent_product_classification_id)
VALUES
(2001, 0, NOW(), NOW(), 1, 1, 'product-classification-1', 'product-classification-1', 'ACTIVE', NULL),
(2002, 0, NOW(), NOW(), 1, 1, 'product-classification-2', 'product-classification-2', 'ACTIVE', NULL),
(2003, 0, NOW(), NOW(), 1, 1, 'product-classification-3', 'product-classification-3', 'ACTIVE', 2001);

-- ---------------------------------------------------------
-- 7. sr_value_component_type (MasterEntity, no deps)
-- ---------------------------------------------------------
INSERT INTO sr_value_component_type (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(3001, 0, NOW(), NOW(), 1, 1, 'value-component-type-1', 'value-component-type-1', 'ACTIVE'),
(3002, 0, NOW(), NOW(), 1, 1, 'value-component-type-2', 'value-component-type-2', 'ACTIVE'),
(3003, 0, NOW(), NOW(), 1, 1, 'value-component-type-3', 'value-component-type-3', 'ACTIVE');

-- ---------------------------------------------------------
-- 8. sr_value_application (MasterEntity, no deps)
-- ---------------------------------------------------------
INSERT INTO sr_value_application (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(4001, 0, NOW(), NOW(), 1, 1, 'value-application-1', 'value-application-1', 'ACTIVE'),
(4002, 0, NOW(), NOW(), 1, 1, 'value-application-2', 'value-application-2', 'ACTIVE'),
(4003, 0, NOW(), NOW(), 1, 1, 'value-application-3', 'value-application-3', 'ACTIVE');

-- ---------------------------------------------------------
-- 9. sr_value_movement (MasterEntity, no deps)
-- ---------------------------------------------------------
INSERT INTO sr_value_movement (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(5001, 0, NOW(), NOW(), 1, 1, 'value-movement-1', 'value-movement-1', 'ACTIVE'),
(5002, 0, NOW(), NOW(), 1, 1, 'value-movement-2', 'value-movement-2', 'ACTIVE'),
(5003, 0, NOW(), NOW(), 1, 1, 'value-movement-3', 'value-movement-3', 'ACTIVE');

-- ---------------------------------------------------------
-- 10. sr_product_type (BaseEntity, depends on sr_product_classification)
-- ---------------------------------------------------------
INSERT INTO sr_product_type (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, product_classification_id)
VALUES
(7001, 0, NOW(), NOW(), 1, 1, 'product-type-1', 'product-type-1', 2001),
(7002, 0, NOW(), NOW(), 1, 1, 'product-type-2', 'product-type-2', 2002),
(7003, 0, NOW(), NOW(), 1, 1, 'product-type-3', 'product-type-3', 2001);

-- ---------------------------------------------------------
-- 11. sr_service (BaseEntity, depends on sr_business_entity_category, sr_service_classification)
-- ---------------------------------------------------------
INSERT INTO sr_service (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, service_category_id, service_classification_id)
VALUES
(6001, 0, NOW(), NOW(), 1, 1, 'service-1', 101, 1001),
(6002, 0, NOW(), NOW(), 1, 1, 'service-2', 102, 1002),
(6003, 0, NOW(), NOW(), 1, 1, 'service-3', 101, 1003);

-- ---------------------------------------------------------
-- 12. sr_product_component (BaseEntity, depends on sr_product_type)
-- ---------------------------------------------------------
INSERT INTO sr_product_component (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, product_type_id)
VALUES
(7101, 0, NOW(), NOW(), 1, 1, 'product-component-1', 'product-component-1', 7001),
(7102, 0, NOW(), NOW(), 1, 1, 'product-component-2', 'product-component-2', 7002),
(7103, 0, NOW(), NOW(), 1, 1, 'product-component-3', 'product-component-3', 7001);

-- ---------------------------------------------------------
-- 13. sr_value_component (BaseEntity, depends on self + value_movement/value_application/value_component_type)
-- No more owner FK columns — ownership via join tables
-- ---------------------------------------------------------
INSERT INTO sr_value_component (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, computation_model, computation_event, base_value_component_id, value_movement_id, value_application_id, value_component_type_id, date_from, date_to)
VALUES
(9001, 0, NOW(), NOW(), 1, 1, 'value-component-1', 'value-component-1', 'ACTIVE', 'FLAT', 'COLLECTION', NULL, 5001, 4001, 3001, '2026-01-01', '2026-12-31');

INSERT INTO sr_value_component (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, computation_model, computation_event, base_value_component_id, value_movement_id, value_application_id, value_component_type_id, date_from, date_to)
VALUES
(9002, 0, NOW(), NOW(), 1, 1, 'value-component-2', 'value-component-2', 'ACTIVE', 'TIER_AMOUNT', 'ROUTING', NULL, 5001, 4001, 3001, '2026-01-01', '2026-12-31');

INSERT INTO sr_value_component (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, computation_model, computation_event, base_value_component_id, value_movement_id, value_application_id, value_component_type_id, date_from, date_to)
VALUES
(9003, 0, NOW(), NOW(), 1, 1, 'value-component-3', 'value-component-3', 'ACTIVE', 'TIER_PERCENTAGE', 'PAYOUT', 9002, 5002, 4002, 3002, '2026-06-01', '2027-06-01');

-- ---------------------------------------------------------
-- 14. sr_product_and_service_classification (BaseEntity, depends on product + service classifications)
-- ---------------------------------------------------------
INSERT INTO sr_product_and_service_classification (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, product_classification_id, service_classification_id)
VALUES
(7501, 0, NOW(), NOW(), 1, 1, 'product-service-classification-1', 'product-service-classification-1', 2001, 1001),
(7502, 0, NOW(), NOW(), 1, 1, 'product-service-classification-2', 'product-service-classification-2', 2002, 1002),
(7503, 0, NOW(), NOW(), 1, 1, 'product-service-classification-3', 'product-service-classification-3', 2003, 1003);

-- ---------------------------------------------------------
-- 15. sr_product (BaseEntity, depends on product_classification, product_type, product_component, product_and_service_classification)
-- ---------------------------------------------------------
INSERT INTO sr_product (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, product_classification_id, product_type_id, product_component_id, product_and_service_classification_id, has_dynamic_attributes)
VALUES
(8001, 0, NOW(), NOW(), 1, 1, 'product-1', 'product-1', 2001, 7001, 7101, 7501, FALSE),
(8002, 0, NOW(), NOW(), 1, 1, 'product-2', 'product-2', 2002, 7002, 7102, 7502, TRUE),
(8003, 0, NOW(), NOW(), 1, 1, 'product-3', 'product-3', 2001, 7003, NULL, 7503, FALSE);

-- ---------------------------------------------------------
-- 16. sr_dynamic_product_attribute (BaseEntity, depends on sr_product)
-- Only for product 8002 (has_dynamic_attributes = TRUE)
-- ---------------------------------------------------------
INSERT INTO sr_dynamic_product_attribute (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, product_id, attribute_name, attribute_value, attribute_type)
VALUES
(8201, 0, NOW(), NOW(), 1, 1, 8002, 'attr-1', 'val-1', 'LINK'),
(8202, 0, NOW(), NOW(), 1, 1, 8002, 'attr-2', 'val-2', 'FIELD');

-- ---------------------------------------------------------
-- 17. sr_product_service (join table: product ←→ service, ManyToMany)
-- ---------------------------------------------------------
INSERT INTO sr_product_service (product_id, service_id)
VALUES
(8001, 6001),
(8002, 6002),
(8003, 6003);

-- ---------------------------------------------------------
-- 18. sr_product_value_component (join table: product ←→ value_component, ManyToMany)
-- ---------------------------------------------------------
INSERT INTO sr_product_value_component (product_id, value_component_id)
VALUES
(8001, 9002);

-- ---------------------------------------------------------
-- 19. sr_service_value_component (join table: service ←→ value_component, ManyToMany)
-- ---------------------------------------------------------
INSERT INTO sr_service_value_component (service_id, value_component_id)
VALUES
(6001, 9001),
(6002, 9002),
(6003, 9003);

-- ---------------------------------------------------------
-- 20. sr_product_and_service_classification_value_component (join table: classification ←→ value_component, ManyToMany)
-- ---------------------------------------------------------
INSERT INTO sr_product_and_service_classification_value_component (classification_id, value_component_id)
VALUES
(7501, 9001),
(7502, 9003);
