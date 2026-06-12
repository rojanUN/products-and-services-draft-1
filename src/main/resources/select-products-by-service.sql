-- Products linked to a specific service (only names)
-- Replace 'Account Opening Service' with the actual service name
SELECT p.name AS product_name
FROM sr_product p
JOIN sr_product_service ps ON ps.product_id = p.uid
JOIN sr_service s ON s.uid = ps.service_id
WHERE s.name = 'Account Opening Service'
ORDER BY p.name;
