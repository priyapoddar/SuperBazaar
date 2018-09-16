use SuperBazaar;
select * from ProductDetails;
select * from Cart;
select * from UserDetails;
select * from Cart;
use SuperBazaar;Alter table UserDetails Add Type varchar(256) null;
Alter table UserDetails
Modify column PhoneNo varchar(256);
Insert into UserDetails (UserName,Gender,PhoneNO,UserPassword,Email,Type)
values('Kaustubh Deo','Male',721,'ka123','kaustubhdeo@gmail.com','Admin');
select * from Cart;
Update UserDetails
Set UserName='Priya Poddar' where UserID=1;
Update UserDetails
Set Type="Admin" where UserID=4;
Alter table ProductDetails
Modify column ProductPrice float4;
Alter table ProductDetails
Modify column ProductName varchar(1000);
Alter table ProductDetails
Modify column ProductName varchar(10000);
Alter table ProductDetails
Modify column isActive bit(1);
Alter table ProductDetails
Modify column isAvailable bit(1);
ALTER TABLE ProductDetails MODIFY ProductID int NOT NULL PRIMARY KEY AUTO_INCREMENT;

SHOW INDEX FROM ProductDetails;
SHOW INDEX FROM Cart;
SHOW INDEX FROM Cart;
ALTER TABLE ProductDetails MODIFY ProductPrice float4;
ALTER TABLE ProductDetails DROP PRIMARY KEY;
ALTER TABLE ProductDetails DROP index ProductPrice;
ALTER TABLE Cart DROP index ProductPrice;
ALTER TABLE Cart DROP index ProductID;
ALTER TABLE Cart DROP index UserID;
SHOW CREATE TABLE Cart;
ALTER TABLE Cart DROP FOREIGN KEY cart_ibfk_3;
ALTER TABLE SuperBazaar.Cart ADD FOREIGN KEY (ProductPrice) REFERENCES ProductDetails (ProductPrice);
 ALTER TABLE SuperBazaar.Cart  ADD FOREIGN KEY (ProductPrice) REFERENCES ProductDetails (ProductPrice);
select * from Cart;
use SuperBazaar;
ALTER TABLE Cart
ADD CONSTRAINT cart_ibfk_3
FOREIGN KEY (ProductPrice) REFERENCES ProductDetails(ProductPrice);

Update Cart set TotalAmount=606 where ProductID=5;
show index from Cart;
ALTER TABLE Cart
Add Column ProductName varchar(3072);
ALTER TABLE Cart
Add Column IsPaid varchar(256);
ALTER TABLE Cart
ADD CONSTRAINT 
FOREIGN KEY (ProductName) REFERENCES ProductDetails(ProductName);
Truncate table Cart;
use SuperBazaar;
select * from ProductDetails;
select * from Cart;
show index from ProductDetails;
show create Table ProductDetails;

ALTER TABLE SuperBazaar.ProductDetails
	CHANGE Quant_Available Qty_Available int;
    Update ProductDetails set Qty_Available=17 where ProductID=1;
    Update ProductDetails set Qty_Available=7,Quant_sold=3 where ProductID=19;
    Update ProductDetails set Qty_Available=30,Quant_sold=20 where ProductID=21;
    select * from ProductDetails;
    Create table Feedback (
   UserID int null,
   Rating varchar(256) null,
   Constraint 
  FOREIGN KEY (UserID)
  REFERENCES UserDetails (UserID)
    );
   Insert into ProductDetails (ProductID,ProductName,ProductPrice,ProductQuantity,Qty_Available,Quant_sold,isActive,isAvailable)
values(3,'Kenley Water Bottle 1l',20,100,70,30,1,1);
select * from ProductDetails;
    select * from UserDetails;
    
    Truncate table Cart;
    select * from Feedback;
    select * from ProductDetails;
    Insert into Feedback(UserID,Rating) values(2,'Excellent');
    Truncate table Feedback;
     Update Feedback set Rating='Excellent' WHERE  UserID IN (2,3);