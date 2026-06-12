-- =============================================================
-- SELECT queries to visualize sample data (new schema)
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
SELECT uid, name, description, status FROM sr_business_entity_category ORDER BY uid;
SELECT uid, name, description, status FROM sr_currency_classification ORDER BY uid;
SELECT uid, name, alpha_three, symbol, status FROM sr_currency ORDER BY uid;
SELECT uid, name, alpha2_code, alpha3_code, dial_code FROM sr_country ORDER BY uid;

-- ---------------------------------------------------------
-- 4. Services with their classification and category
-- ---------------------------------------------------------
SELECT s.uid,
       s.name,
       sc.name AS service_classification,
       bec.name AS service_category
FROM sr_service s
LEFT JOIN sr_service_classification sc ON sc.uid = s.service_classification_id
LEFT JOIN sr_business_entity_category bec ON bec.uid = s.service_category_id
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
-- 6. Products with product type, component, and classification
-- ---------------------------------------------------------
SELECT p.uid,
       p.name,
       p.description,
       pt.name          AS product_type,
       pc.name          AS product_component,
       psc.name         AS product_service_classification,
       pcl.name         AS product_classification,
       p.has_dynamic_attributes
FROM sr_product p
LEFT JOIN sr_product_type pt ON pt.uid = p.product_type_id
LEFT JOIN sr_product_component pc ON pc.uid = p.product_component_id
LEFT JOIN sr_product_and_service_classification psc ON psc.uid = p.product_and_service_classification_id
LEFT JOIN sr_product_classification pcl ON pcl.uid = p.product_classification_id
ORDER BY p.uid;

-- ---------------------------------------------------------
-- 7. Products and their linked Services (ManyToMany)
-- ---------------------------------------------------------
SELECT p.name AS product_name,
       s.name AS service_name
FROM sr_product_service ps
JOIN sr_product p ON p.uid = ps.product_id
JOIN sr_service s ON s.uid = ps.service_id
ORDER BY p.name, s.name;

-- ---------------------------------------------------------
-- 8. Value Components — fully resolved
-- ---------------------------------------------------------
SELECT vc.uid,
       vc.name,
       vc.status,
       vc.computation_model,
       vc.computation_event,
       vc.date_from,
       vc.date_to,
       bvc.name                   AS based_on_component,
       vmt.name                   AS value_movement,
       va.name                    AS value_application,
       vct.name                   AS component_type
FROM sr_value_component vc
LEFT JOIN sr_value_component bvc ON bvc.uid = vc.base_value_component_id
LEFT JOIN sr_value_movement vmt ON vmt.uid = vc.value_movement_id
LEFT JOIN sr_value_application va ON va.uid = vc.value_application_id
LEFT JOIN sr_value_component_type vct ON vct.uid = vc.value_component_type_id
ORDER BY vc.uid;

-- ---------------------------------------------------------
-- 9. Value Component ownership via join tables
-- ---------------------------------------------------------
-- Linked to products
SELECT p.name AS owner_name, 'Product' AS owner_type, vc.name AS value_component
FROM sr_product_value_component pvc
JOIN sr_product p ON p.uid = pvc.product_id
JOIN sr_value_component vc ON vc.uid = pvc.value_component_id

UNION ALL

-- Linked to services
SELECT s.name, 'Service', vc.name
FROM sr_service_value_component svc
JOIN sr_service s ON s.uid = svc.service_id
JOIN sr_value_component vc ON vc.uid = svc.value_component_id

UNION ALL

-- Linked to product-and-service classifications
SELECT psc.name, 'Classification', vc.name
FROM sr_product_and_service_classification_value_component cvc
JOIN sr_product_and_service_classification psc ON psc.uid = cvc.classification_id
JOIN sr_value_component vc ON vc.uid = cvc.value_component_id

ORDER BY owner_type, owner_name;

-- ---------------------------------------------------------
-- 10. Summary: count of value components per owner type
-- ---------------------------------------------------------
SELECT 'Product' AS owner_type, COUNT(*) AS cnt FROM sr_product_value_component
UNION ALL
SELECT 'Service', COUNT(*) FROM sr_service_value_component
UNION ALL
SELECT 'Classification', COUNT(*) FROM sr_product_and_service_classification_value_component
ORDER BY owner_type;

-- ---------------------------------------------------------
-- 11. Dynamic Product Attributes
-- ---------------------------------------------------------
SELECT dpa.uid,
       p.name AS product_name,
       dpa.attribute_name,
       dpa.attribute_value,
       dpa.attribute_type
FROM sr_dynamic_product_attribute dpa
JOIN sr_product p ON p.uid = dpa.product_id
ORDER BY dpa.uid;

-- ---------------------------------------------------------
-- 12. Hierarchy: service classification tree
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
-- 13. Hierarchy: product classification tree
-- ---------------------------------------------------------
SELECT pc.uid,
       pc.name,
       pc.status,
       ppc.name        AS parent_name,
       (SELECT COUNT(*) FROM sr_product_classification sub WHERE sub.parent_product_classification_id = pc.uid) AS child_count
FROM sr_product_classification pc
LEFT JOIN sr_product_classification ppc ON ppc.uid = pc.parent_product_classification_id
ORDER BY pc.parent_product_classification_id NULLS FIRST, pc.uid;
