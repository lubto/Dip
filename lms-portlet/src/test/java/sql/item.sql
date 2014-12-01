DELETE FROM Category;
DELETE FROM Product;
DELETE FROM Item_basket;
DELETE FROM Basket;

INSERT INTO Category (id_category, name, description) VALUES (-67, 'Lubomir', 'popis');

INSERT INTO Product (id_product, id_category, name, description, price) VALUES (-1, -67, 'description', 'name', 500);

INSERT INTO Basket (id_basket, date, paid, userid) VALUES (-123, '2005-9-8', 0, 2);

INSERT INTO Item_basket (id_item, quantity, id_basket, id_product) VALUES (-12, 1, -123,-1);
 


 
