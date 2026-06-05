-- =============================================================
-- SELECT queries to visualize sample data
-- Joins resolve FK references to show meaningful names
-- =============================================================

-- ---------------------------------------------------------
-- 1. Service Classifications (self-join for parent)
-- ---------------------------------------------------------
SELECT sc.uid,
       sc.name,
       sc.description,
       sc.status,
       psc.name AS parent_classification
FROM sr_service_classification sc
LEFT JOIN sr_service_classification psc ON psc.uid = sc.parent_service_classification_id
ORDER BY sc.uid;

-- ---------------------------------------------------------
-- 2. Product Classifications (self-join for parent)
-- ---------------------------------------------------------
SELECT pc.uid,
       pc.name,
       pc.description,
       pc.status,
       ppc.name AS parent_classification
FROM sr_product_classification pc
LEFT JOIN sr_product_classification ppc ON ppc.uid = pc.parent_product_classification_id
ORDER BY pc.uid;

-- ---------------------------------------------------------
-- 3. Master Data (simple lists)
-- ---------------------------------------------------------
SELECT uid, name, description, status FROM sr_value_component_type ORDER BY uid;

SELECT uid, name, description, status FROM sr_value_application ORDER BY uid;

SELECT uid, name, description, status FROM sr_value_movement ORDER BY uid;

-- ---------------------------------------------------------
-- 4. Services with their classification
-- ---------------------------------------------------------
SELECT s.uid,
       s.name,
       sc.name AS service_classification
FROM sr_service s
LEFT JOIN sr_service_classification sc ON sc.uid = s.service_classification_id
ORDER BY s.uid;

-- ---------------------------------------------------------
-- 5. Product Types with their classification
-- ---------------------------------------------------------
SELECT pt.uid,
       pt.name,
       pt.description,
       pc.name AS product_classification
FROM sr_product_type pt
LEFT JOIN sr_product_classification pc ON pc.uid = pt.product_classification_id
ORDER BY pt.uid;

-- ---------------------------------------------------------
-- 6. Products with product type and optional service
-- ---------------------------------------------------------
SELECT p.uid,
       p.name,
       p.description,
       pt.name          AS product_type,
       s.name           AS service,
       p.has_dynamic_product_attributes
FROM sr_product p
LEFT JOIN sr_product_type pt ON pt.uid = p.product_type_id
LEFT JOIN sr_service s ON s.uid = p.service_id
ORDER BY p.uid;

-- ---------------------------------------------------------
-- 7. Value Components — fully resolved
--     Shows all FK references plus which owner entity
--     the component belongs to (exactly one per CHECK constraint)
-- ---------------------------------------------------------
SELECT vc.uid,
       vc.name,
       vc.status,
       vc.computation_model,
       vc.computation_event,
       vc.date_from,
       vc.date_to,
       -- Resolved FK references
       bvc.name                   AS based_on_component,
       vmt.name                   AS value_movement,
       va.name                    AS value_application,
       vct.name                   AS component_type,
       -- Owner (exactly one is non-null per CHECK constraint)
       COALESCE(
               'ServiceClassification: ' || sc.name,
               'ProductClassification: ' || pc.name,
               'ProductType: ' || pt.name,
               'Product: ' || pr.name,
               'Service: ' || sv.name
       )                          AS owner
FROM sr_value_component vc
LEFT JOIN sr_value_component bvc ON bvc.uid = vc.base_value_component_id
LEFT JOIN sr_value_movement vmt ON vmt.uid = vc.value_movement_id
LEFT JOIN sr_value_application va ON va.uid = vc.value_application_id
LEFT JOIN sr_value_component_type vct ON vct.uid = vc.value_component_type_id
LEFT JOIN sr_service_classification sc ON sc.uid = vc.service_classification_id
LEFT JOIN sr_product_classification pc ON pc.uid = vc.product_classification_id
LEFT JOIN sr_product_type pt ON pt.uid = vc.product_type_id
LEFT JOIN sr_product pr ON pr.uid = vc.product_id
LEFT JOIN sr_service sv ON sv.uid = vc.service_id
ORDER BY vc.uid;

-- ---------------------------------------------------------
-- 8. Summary: count of value components per owner type
-- ---------------------------------------------------------
SELECT 'ServiceClassification' AS owner_type, COUNT(*) AS cnt FROM sr_value_component WHERE service_classification_id IS NOT NULL
UNION ALL
SELECT 'ProductClassification', COUNT(*) FROM sr_value_component WHERE product_classification_id IS NOT NULL
UNION ALL
SELECT 'ProductType', COUNT(*) FROM sr_value_component WHERE product_type_id IS NOT NULL
UNION ALL
SELECT 'Product', COUNT(*) FROM sr_value_component WHERE product_id IS NOT NULL
UNION ALL
SELECT 'Service', COUNT(*) FROM sr_value_component WHERE service_id IS NOT NULL
ORDER BY owner_type;

-- ---------------------------------------------------------
-- 9. Hierarchy: service classification tree
-- ---------------------------------------------------------
SELECT sc.uid,
       sc.name,
       sc.status,
       psc.name        AS parent_name,
       (SELECT COUNT(*) FROM sr_service_classification sub WHERE sub.parent_service_classification_id = sc.uid) AS child_count
FROM sr_service_classification sc
LEFT JOIN sr_service_classification psc ON psc.uid = sc.parent_service_classification_id
ORDER BY sc.parent_service_classification_id NULLS FIRST, sc.uid;

-- ---------------------------------------------------------
-- 10. Hierarchy: product classification tree
-- ---------------------------------------------------------
SELECT pc.uid,
       pc.name,
       pc.status,
       ppc.name        AS parent_name,
       (SELECT COUNT(*) FROM sr_product_classification sub WHERE sub.parent_product_classification_id = pc.uid) AS child_count
FROM sr_product_classification pc
LEFT JOIN sr_product_classification ppc ON ppc.uid = pc.parent_product_classification_id
ORDER BY pc.parent_product_classification_id NULLS FIRST, pc.uid;




