INSERT INTO areas (id, name, url, usable, created_at, updated_at)
VALUES (1, 'Санкт-Петербург', 'saint-petersburg', NULL, NOW(), NOW()),
       (2, 'Москва', 'moscow', NULL, NOW(), NOW()),
       (3, 'Уфа', 'ufa', NULL, NOW(), NOW()),
       (4, 'Новосибирск', 'novosibirsk', NULL, NOW(), NOW()),
       (5, 'Екатеринбург', 'ekaterinburg', NULL, NOW(), NOW());

UPDATE areas
SET usable = TRUE;
