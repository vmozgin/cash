INSERT INTO category (id, name)
VALUES ('8713f0e9-31c3-459c-9bd5-070747e22d01',
        'category_1'),
       ('8713f0e9-31c3-459c-9bd5-070747e22d02',
        'category_2');

INSERT INTO book(id, author, title, category_id)
VALUES ('282f05a1-89a9-4ff7-93c5-985071867901',
        'author_1',
        'title_1',
        '8713f0e9-31c3-459c-9bd5-070747e22d01'),
       ('282f05a1-89a9-4ff7-93c5-985071867902',
        'author_2',
        'title_2',
        '8713f0e9-31c3-459c-9bd5-070747e22d01');