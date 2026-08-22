INSERT INTO products (
    id, name, description, category, image_url, sku, price, created_at, updated_at
)
SELECT
    gen_random_uuid(),
    'Product ' || i,
    'Sample product ' || i,
    (i % 5),                      -- ordinal maps directly to enum order
    'https://example.com/products/product-' || i || '.jpg',
    'SKU-' || LPAD(i::text, 4, '0'),
    ROUND((100 + RANDOM() * 9900)::numeric, 2),
    NOW(),
    NOW()
FROM generate_series(1, 100) AS i;