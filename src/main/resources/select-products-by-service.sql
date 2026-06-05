-- Products linked to a specific service (only names)
-- Replace 'Account Opening Service' with the actual service name
SELECT p.name AS product_name
FROM sr_product p
JOIN sr_service s ON s.uid = p.service_id
WHERE s.name = 'Bank Transfer'
ORDER BY p.name;


