INSERT INTO inventory (
    id,
    warehouse_id,
    product_id,
    quantity,
    reserved_quantity,
    updated_at
)
SELECT
    gen_random_uuid(),
    w.id,
    p.id,
    FLOOR(RANDOM() * 1000)::int,
    0,
    NOW()
FROM warehouses w
CROSS JOIN products p;