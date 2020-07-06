/* Deliverable 3
*  Team 18
*  Jonah Cole, Ryan Cluff, Dylan Sund
*/

/* Select the names of all employees who work at Chicago branch and who have
* fulfilled at least one order.
*/
SELECT E.Name
FROM EMPLOYEE AS E, FULFILLED_BY AS F, WORKS_AT AS W
WHERE W.LocationId = 'Chicago'
AND W.eSSN = F.eSSN
AND F.eSSN = E.SSN;

/* Select the phone number of the Anaheim location */
SELECT PhoneNumber
FROM LOCATION 
WHERE LocationId = 'Anaheim';

/* Select the price of all products that are currently in an order and the 
* the number of that product being purchased is over 2. 
*/
SELECT P.Price 
FROM PRODUCT AS P, ORDER_CONTAINS AS OC
WHERE OC.ItemCount > 2
AND OC.ProductId = P.ProductId;

/* Select names of all customers who have an order placed */
SELECT C.Name
FROM CUSTOMER AS C, CUSTOMER_ORDER AS O
WHERE O.CEmail = C.Email;

/* Select names of all products from Seattle that are currently in stock */
SELECT P.Name
FROM PRODUCT AS P, STORED_AT AS S
WHERE S.LocationId = 'Seattle' 
AND S.ItemCount > 0
AND S.ProductId = P.ProductId;
