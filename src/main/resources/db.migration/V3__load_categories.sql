INSERT INTO public.categories (id, name, url, usable)
VALUES (1, 'Здоровье', 'health', NULL),
       (2, 'Одежда', 'clothes', NULL),
       (3, 'Мебель', 'furniture', NULL),
       (4, 'Язык', 'language', NULL),
       (5, 'Аренда', 'rental', NULL);

UPDATE categories
SET usable = TRUE;
