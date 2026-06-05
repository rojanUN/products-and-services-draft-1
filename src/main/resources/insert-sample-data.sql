-- =============================================================
-- Sample INSERT queries for all active entities
-- Ordered by dependency (parent tables first)
-- 3 rows per table
-- =============================================================

-- ---------------------------------------------------------
-- 1. sr_service_classification (no dependencies)
-- BaseEntity fields: uid, version, created_at, last_modified_at, created_by_id, modified_by_id
-- DomainMetaDataEntity fields: name, description
-- StatusEnum: ACTIVE, INACTIVE, DELETED, REJECTED
-- Can self-reference via parent_service_classification_id
-- ---------------------------------------------------------
INSERT INTO sr_service_classification (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, parent_service_classification_id)
VALUES
(1001, 0, NOW(), NOW(), 1, 1, 'Retail Banking', 'Retail banking services classification', 'ACTIVE', NULL),
(1002, 0, NOW(), NOW(), 1, 1, 'Corporate Banking', 'Corporate banking services classification', 'ACTIVE', NULL),
(1003, 0, NOW(), NOW(), 1, 1, 'Digital Banking', 'Digital banking services classification', 'ACTIVE', 1001);

-- ---------------------------------------------------------
-- 2. sr_product_classification (no dependencies)
-- Same base fields as above
-- Can self-reference via parent_product_classification_id
-- ---------------------------------------------------------
INSERT INTO sr_product_classification (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status, parent_product_classification_id)
VALUES
(2001, 0, NOW(), NOW(), 1, 1, 'Deposit Products', 'Deposit related products', 'ACTIVE', NULL),
(2002, 0, NOW(), NOW(), 1, 1, 'Lending Products', 'Loan and lending products', 'ACTIVE', NULL),
(2003, 0, NOW(), NOW(), 1, 1, 'Savings Accounts', 'Savings account products', 'ACTIVE', 2001);

-- ---------------------------------------------------------
-- 3. sr_value_component_type (master, no dependencies)
-- MasterEntity adds: status
-- ---------------------------------------------------------
INSERT INTO sr_value_component_type (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(3001, 0, NOW(), NOW(), 1, 1, 'Fee', 'Fee component type', 'ACTIVE'),
(3002, 0, NOW(), NOW(), 1, 1, 'Tax', 'Tax component type', 'ACTIVE'),
(3003, 0, NOW(), NOW(), 1, 1, 'Discount', 'Discount component type', 'ACTIVE');

-- ---------------------------------------------------------
-- 4. sr_value_application (master, no dependencies)
-- ---------------------------------------------------------
INSERT INTO sr_value_application (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(4001, 0, NOW(), NOW(), 1, 1, 'Principal', 'Applied to principal amount', 'ACTIVE'),
(4002, 0, NOW(), NOW(), 1, 1, 'Interest', 'Applied to interest amount', 'ACTIVE'),
(4003, 0, NOW(), NOW(), 1, 1, 'Total', 'Applied to total amount', 'ACTIVE');

-- ---------------------------------------------------------
-- 5. sr_value_movement (master, no dependencies)
-- ---------------------------------------------------------
INSERT INTO sr_value_movement (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, status)
VALUES
(5001, 0, NOW(), NOW(), 1, 1, 'Debit', 'Debit movement type', 'ACTIVE'),
(5002, 0, NOW(), NOW(), 1, 1, 'Credit', 'Credit movement type', 'ACTIVE'),
(5003, 0, NOW(), NOW(), 1, 1, 'Both', 'Both debit and credit', 'ACTIVE');

-- ---------------------------------------------------------
-- 6. sr_service (depends on sr_service_classification)
-- Fields: name, service_classification_id
-- ---------------------------------------------------------
INSERT INTO sr_service (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, service_classification_id)
VALUES
(6001, 0, NOW(), NOW(), 1, 1, 'Account Opening Service', 1001),
(6002, 0, NOW(), NOW(), 1, 1, 'Loan Disbursement Service', 1002),
(6003, 0, NOW(), NOW(), 1, 1, 'Mobile Banking Service', 1003);

-- ---------------------------------------------------------
-- 7. sr_product_type (depends on sr_product_classification)
-- Fields: name, description, product_classification_id (NOT NULL)
-- ---------------------------------------------------------
INSERT INTO sr_product_type (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, product_classification_id)
VALUES
(7001, 0, NOW(), NOW(), 1, 1, 'Current Account', 'Current account product type', 2001),
(7002, 0, NOW(), NOW(), 1, 1, 'Personal Loan', 'Personal loan product type', 2002),
(7003, 0, NOW(), NOW(), 1, 1, 'Fixed Deposit', 'Fixed deposit product type', 2001);

-- ---------------------------------------------------------
-- 8. sr_product (depends on sr_product_type NOT NULL, sr_service optional)
-- Fields: name, description, product_type_id (NOT NULL), service_id, has_dynamic_product_attributes
-- ---------------------------------------------------------
INSERT INTO sr_product (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, description, product_type_id, service_id, has_dynamic_product_attributes)
VALUES
(8001, 0, NOW(), NOW(), 1, 1, 'Standard Current Account', 'Standard current account product', 7001, 6001, FALSE),
(8002, 0, NOW(), NOW(), 1, 1, 'Premium Personal Loan', 'Premium personal loan product', 7002, 6002, TRUE),
(8003, 0, NOW(), NOW(), 1, 1, '12-Month Fixed Deposit', '12-month fixed deposit product', 7003, NULL, FALSE);

-- ---------------------------------------------------------
-- 9. sr_value_component (depends on many, with CHECK constraint)
-- Check constraint: exactly ONE of the 5 owner columns must be non-NULL
-- Fields: name, status, computation_model, computation_event,
--         base_value_component_id, value_movement_id, value_application_id, value_component_type_id,
--         service_classification_id, product_classification_id, product_type_id, product_id, service_id,
--         date_from, date_to
-- ComputationModelEnum: FLAT, TIER_AMOUNT, TIER_PERCENTAGE, CASHBACK
-- ComputationEventEnum: COLLECTION, ROUTING, PAYOUT
-- ---------------------------------------------------------

-- Value component owned by service_classification (id=1001)
INSERT INTO sr_value_component (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, status, computation_model, computation_event, base_value_component_id, value_movement_id, value_application_id, value_component_type_id, service_classification_id, product_classification_id, product_type_id, product_id, service_id, date_from, date_to)
VALUES
(9001, 0, NOW(), NOW(), 1, 1, 'Account Opening Fee', 'ACTIVE', 'FLAT', 'COLLECTION', NULL, 5001, 4001, 3001, 1001, NULL, NULL, NULL, NULL, '2026-01-01', '2026-12-31');

-- Value component owned by product_classification (id=2001)
INSERT INTO sr_value_component (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, status, computation_model, computation_event, base_value_component_id, value_movement_id, value_application_id, value_component_type_id, service_classification_id, product_classification_id, product_type_id, product_id, service_id, date_from, date_to)
VALUES
(9002, 0, NOW(), NOW(), 1, 1, 'Deposit Processing Fee', 'ACTIVE', 'TIER_AMOUNT', 'ROUTING', NULL, 5001, 4001, 3001, NULL, 2001, NULL, NULL, NULL, '2026-01-01', '2026-12-31');

-- Value component owned by product_type (id=7002) with a base component reference
INSERT INTO sr_value_component (uid, version, created_at, last_modified_at, created_by_id, modified_by_id, name, status, computation_model, computation_event, base_value_component_id, value_movement_id, value_application_id, value_component_type_id, service_classification_id, product_classification_id, product_type_id, product_id, service_id, date_from, date_to)
VALUES
(9003, 0, NOW(), NOW(), 1, 1, 'Loan Interest Rate', 'ACTIVE', 'TIER_PERCENTAGE', 'PAYOUT', 9002, 5002, 4002, 3002, NULL, NULL, 7002, NULL, NULL, '2026-06-01', '2027-06-01');
