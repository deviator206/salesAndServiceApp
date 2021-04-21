# java_basic_rest_impl
Basic REST implementation ready project with deploymentdescriptor &amp; pom.xml

###
after adding jersey-json & adding params in web.xml
recieved error - "java.lang.NoSuchMethodError: com.sun.jersey.core.reflection.ReflectionHelper.getContextClassLoaderPA()Ljava/security/PrivilegedAction"

> had used the version - 1.8 of jersery-json
but changed it to 
<dependency>
		<groupId>com.sun.jersey</groupId>
		<artifactId>jersey-json</artifactId>
		<version>1.19.3</version>
	</dependency>


###
All issue resolved 
working uRL
GET
http://localhost:8080/vogellaRestImpl/rest/hello/getstring
http://localhost:8080/vogellaRestImpl/rest/hello/getxml
http://localhost:8080/vogellaRestImpl/rest/hello/getjson
	
###
>support for json input added
http://localhost:8080/vogellaRestImpl/rest/hello/jsoninput
{"name":"Send Name"}
POST

###
crud operations

DB creation

create table validEmporiumUser (empId int NOT NULL AUTO_INCREMENT , empName varchar(20), empPassword varchar(20),empRole varchar(20), primary key (empId));

alter table validEmporiumUser1 modify column empId int NOT NULL AUTO_INCREMENT;

select * from validEmporiumUser;

insert into validEmporiumUser (empName, empPassword) values ('admin','admin','ADMIN')
insert into validEmporiumUser (empName, empPassword) values ('tech','tech','TECH')

select * from validEmporiumUser;


connecting to localhost:3306

### 
creation of tables

// CUSTOMER INFO
create table EMP_CUSTOMER_TABLE (id int NOT NULL AUTO_INCREMENT, name varchar(20), address varchar(30), phone int,primary key (id));
alter table EMP_CUSTOMER_TABLE modify column phone varchar(15);
alter table EMP_CUSTOMER_TABLE modify column address varchar(50);
insert into EMP_CUSTOMER_TABLE (name, address,phone) values ('sachin','pune kp road','9765896417');

insert into EMP_CUSTOMER_TABLE (name, address,phone) values ('mohan','pune DP road','8865896417');
select * from EMP_CUSTOMER_TABLE;

select * from EMP_CUSTOMER_TABLE where name LIKE 'an%' OR name LIKE '%an' OR name LIKE '%n%';

// PRODUCT
create table EMP_PRODUCT_TABLE (id int NOT NULL AUTO_INCREMENT, brandName varchar(20), brandModel varchar(30), serialNumber varchar(20), primary key (id));
insert into EMP_PRODUCT_TABLE (brandName, brandModel,serialNumber) values ('CanonEOs450','s70000','1001505887832');
select * from EMP_PRODUCT_TABLE

............
REST implementation
GET : http://localhost:8080/vogellaRestImpl/rest/customer/serach-customer?text=a
POST : http://localhost:8080/vogellaRestImpl/rest/customer/create 
{"customerName":"tech bhai","customerAddress":"tech1 ka address nahi malum","customerPhone":"87654433212"}


// Creation of Product REST

// PRODUCT
create table EMP_PRODUCT_TABLE (id int NOT NULL AUTO_INCREMENT, brandName varchar(20), brandModel varchar(30), serialNumber varchar(20), primary key (id));
insert into EMP_PRODUCT_TABLE (brandName, brandModel,serialNumber,price) values ('NIKON','ssd20000','1001505887832',12899);

alter table EMP_PRODUCT_TABLE add column price int(30);

alter table EMP_PRODUCT_TABLE add column tax_type varchar(30);
select * from EMP_PRODUCT_TABLE


create implementation 
POST
URL : http://localhost:8080/vogellaRestImpl/rest/product/create
Content-Type: application/json

payload :
{"productList" : [
  
  			{"isNew":true,"brandName":"Canon-1","brandModel":"s7001","serialNumber":"X99999","price":9999,"taxType":"6"},
{"isNew":true,"brandName":"Canon-2","brandModel":"s7002","serialNumber":"X99990","price":29999,"taxType":"13.5"},
  {"isNew":true,"brandName":"Canon-3","brandModel":"s7003","serialNumber":"X99989","price":19999,"taxType":"0"}
]}

OUTPUT
{
"status": true,
"counterValue": 3
}


//search product
http://localhost:8080/vogellaRestImpl/rest/product/search-product?text=nik&type=NAME
{
  "status": true,
  "singleProductModelList": [
    {
      "id": 4,
      "name": "Canon-1",
      "model": "s7001",
      "sn": "X99999",
      "price": 9999,
      "taxType": "6"
    },
    {
      "id": 5,
      "name": "Canon-2",
      "model": "s7002",
      "sn": "X99990",
      "price": 29999,
      "taxType": "13.5"
    },
    {
      "id": 6,
      "name": "Canon-3",
      "model": "s7003",
      "sn": "X99989",
      "price": 19999,
      "taxType": "0"
    }
  ]
}



type=NAME
type=MODEL
type=SN


-------------
git delete FOLDER
git rm -r <FOLDER_NAME>
--------------

GENERATE INVOICE
create table SALES_INVOICE_TABLE (id int(30) AUTO_INCREMENT NOT NULL ,actualInvoiceId varchar(60) , defaultValue varchar(60) default 'CE/2017-18/', primary key(id));
alter table SALES_INVOICE_TABLE ADD UNIQUE(actualInvoiceId);
insert into SALES_INVOICE_TABLE (actualInvoiceId) values ('CE/2017-18/1')
insert into SALES_INVOICE_TABLE (actualInvoiceId) values ('CE/2017-18/2')
select * from SALES_INVOICE_TABLE;

select defaultValue from SALES_INVOICE_TABLE;
select count(*) from SALES_INVOICE_TABLE


ADD PAYMENT FOR INVOICE

//table for payment type
create table PAYMENT_DETAILS_TABLE(id int(40) AUTO_INCREMENT NOT NULL,cash varchar(60), cheqNo varchar(60),bankName varchar(60),ifscCode varchar(60),cheqDate varchar(60),accountNo varchar(60),cardNo varchar(60),primary key(id))
alter table PAYMENT_DETAILS_TABLE add column invoice_id varchar(60)
alter table PAYMENT_DETAILS_TABLE add column invoice_tin varchar(60)


insert into PAYMENT_DETAILS_TABLE(cash,invoice_id,invoice_tin) values ('5000','CE/2017-18/6','2763039355V');
insert into PAYMENT_DETAILS_TABLE(cheqNo,cheqDate,bankName,invoice_id,invoice_tin) values ('345678093218','12/6/2017','SBI','CE/2017-18/6','2763039355V');
insert into PAYMENT_DETAILS_TABLE(cardNo,bankName,invoice_id,invoice_tin) values ('345678093234324823418','Standarad Chartered Bank','CE/2017-18/6','2763039355V');
select * from PAYMENT_DETAILS_TABLE



type: CASH | CARD | CHEQ
-cash
-cardNumber
-bankName
-cheqNo
-cheqDate

REST IMPLEMENTATION
http://localhost:8080/vogellaRestImpl/rest/invoice/sales
payload :
{"paymentInfo" :{
	"type":"CASH",
     "cash":"6000"
	}
}

{"paymentInfo" :{
	"type":"CARD",
     "cardNumber":"6000-1290-1290-1290",
     "bankName":"Satin Bank man"
     "amount":1000
	}
}

{"paymentInfo" :{
	"type":"CHEQ",
     "cheqNo":"298611",
     "cheqDate":"23/6/2017",
     "bankName":"PakBank"
	}
}
-----
output:
{
  "status": true,
  "invoiceId": "CE/2017-18/26",
  "vatTinNumber": "2763039355V",
  "counterValue": 2
}


........

final changed DB
create table validEmporiumUser (empId int NOT NULL AUTO_INCREMENT , empName varchar(20), empPassword varchar(20),empRole varchar(20), primary key (empId));

alter table validEmporiumUser1 modify column empId int NOT NULL AUTO_INCREMENT;

select * from validEmporiumUser;

insert into validEmporiumUser (empName, empPassword) values ('admin','admin','ADMIN')
insert into validEmporiumUser (empName, empPassword) values ('tech','tech','TECH')

select * from validEmporiumUser;


// CUSTOMER INFO
create table EMP_CUSTOMER_TABLE (id int NOT NULL AUTO_INCREMENT, name varchar(20), address varchar(30), phone int,primary key (id));
alter table EMP_CUSTOMER_TABLE modify column phone varchar(15);
alter table EMP_CUSTOMER_TABLE modify column address varchar(50);
insert into EMP_CUSTOMER_TABLE (name, address,phone) values ('sachin','pune kp road','9765896417');

insert into EMP_CUSTOMER_TABLE (name, address,phone) values ('mohan','pune DP road','8865896417');
select * from EMP_CUSTOMER_TABLE;

select * from EMP_CUSTOMER_TABLE where name LIKE 'an%' OR name LIKE '%an' OR name LIKE '%n%';

// PRODUCT
create table EMP_PRODUCT_TABLE (id int NOT NULL AUTO_INCREMENT, brandName varchar(20), brandModel varchar(30), serialNumber varchar(20), primary key (id));
insert into EMP_PRODUCT_TABLE (brandName, brandModel,serialNumber,price) values ('NIKON','ssd20000','1001505887832',12899);

alter table EMP_PRODUCT_TABLE add column price int(30);

alter table EMP_PRODUCT_TABLE add column tax_type varchar(30);

select * from EMP_PRODUCT_TABLE where id in (1,4,6);


//
Generate Invoice- so Invoice table.
create table SALES_INVOICE_TABLE (id int(30) AUTO_INCREMENT NOT NULL ,actualInvoiceId varchar(60) , defaultValue varchar(60) default 'CE/2017-18/', primary key(id));
alter table SALES_INVOICE_TABLE ADD UNIQUE(actualInvoiceId);
insert into SALES_INVOICE_TABLE (actualInvoiceId) values ('CE/2017-18/1')
insert into SALES_INVOICE_TABLE (actualInvoiceId) values ('CE/2017-18/2')
select * from SALES_INVOICE_TABLE;

select defaultValue from SALES_INVOICE_TABLE;
select count(*) from SALES_INVOICE_TABLE

alter table SALES_INVOICE_TABLE add column vat_tin_number varchar(60) default'2763039355V';
select defaultValue,vat_tin_number from SALES_INVOICE_TABLE;


//table for payment type
create table PAYMENT_DETAILS_TABLE(id int(40) AUTO_INCREMENT NOT NULL,cash varchar(60), cheqNo varchar(60),bankName varchar(60),ifscCode varchar(60),cheqDate varchar(60),accountNo varchar(60),cardNo varchar(60),primary key(id))
alter table PAYMENT_DETAILS_TABLE add column invoice_id varchar(60)
alter table PAYMENT_DETAILS_TABLE add column invoice_tin varchar(60)
alter table PAYMENT_DETAILS_TABLE add column amount varchar(60)
alter table PAYMENT_DETAILS_TABLE modify column amount FLOAT(10,2)

insert into PAYMENT_DETAILS_TABLE(cash,invoice_id,invoice_tin) values ('5000','CE/2017-18/6','2763039355V');
insert into PAYMENT_DETAILS_TABLE(cheqNo,cheqDate,bankName,invoice_id,invoice_tin) values ('345678093218','12/6/2017','SBI','CE/2017-18/6','2763039355V');
insert into PAYMENT_DETAILS_TABLE(cardNo,bankName,invoice_id,invoice_tin) values ('345678093234324823418','Standarad Chartered Bank','CE/2017-18/6','2763039355V');
select * from PAYMENT_DETAILS_TABLE


//Sales Table with order information
create table SALES_ORDER_TABLE (
sale_id int(30) not null auto_increment,
invoice_id varchar(60) not null,
customer_id varchar(60) not null, 
product_id varchar(60) not null,
product_unit_price float(10,2) not null, 
product_qty float(10,2) not null,
product_price_with_qty float(10,2) not null,
tax_type varchar(60) ,
tax_rate varchar(60),
order_date TIMESTAMP default CURRENT_TIMESTAMP ,
tax_amount float(10,2) ,
total_amount float(10,2) not null, primary key(sale_id))


alter table SALES_ORDER_TABLE add column tax_value float(10,2)
select * from SALES_ORDER_TABLE

select * from emp_customer_table
select * from emp_product_table


select * from SALES_ORDER_TABLE
-------------------

REST:
POST:
http://localhost:8080/vogellaRestImpl/rest/invoice/sales


input:
{
  "paymentInfo": [
    {
      "amount": 76393.98,
      "type": "CASH"
    }
  ],
  "customerInfo": {
    "id":"5"
  },
  "productInfo": [
    {
      "id":"2",
      "name": "CanonEOs450",
      "model": "s70000",
      "sn": "1001505887832",
      "quantity": 1,
      "price": 15669,
      "totalPrice": 15669,
	  
      "taxType": "VAT-1",
      "taxValue": 13.5,
      "taxAmmount": 2115.315,
      "grandTotal": 17784.315,
      "taxRate": 13.5,
      "orderDate":"2017-05-25 04:38:25"
    },
    {
      "id":"",
      "name": "NIKON",
      "model": "ssd20000",
      "sn": "1001505887832",
      "quantity": 3,
      "price": 12899,
      "totalPrice": 38697,
      "taxType": "VAT-2",
      "taxValue": 5.5,
      "taxAmmount": 2128.335,
      "grandTotal": 40825.335,
      "taxRate": 5.5,
      "orderDate":"2017-05-25 04:38:25"
    }
  ]
}

output:
{
  "status": true,
  "counter": 1,
  "invoiceId": "CE/2017-18/47",
  "vatTinNumber": "2763039355V",
  "customerServiceResponse": null,
  "counterValue": 1,
  "productServiceResponse": null
}


// 18th JUNE
http://localhost:8080/vogellaRestImpl/rest/repair/drop-from-customer
PAYLOAD POST
{"selectedProductList":[],"problemLists":["Water Damage","Fungus in Binocular"],"accessoryList":["Battery","Body Cap"],"shopUserComment":"asdasdddwqwwwwww","customerComment":"asddsa","tentative_quoted_cost":0,"customerInfo":{"id":25,"name":"SANDEEP","address":"Puneasdasdasdads","phone":"9999999999","email":"","alternateNo":""},"productInfo":[{"name":"NIKON","id":19,"model":"ssd20000","sn":"asdasasdads"}],"service_order_date":"2017-06-18 07:05:54","tentative_service_completion_date":"2017-06-18 07:05:54","courierInfo":{"isCourier":false,"courierName":"","courierPhone":"","courierDocumentNo":""},"courierOutwardInfo":{"isCourier":false,"courierName":"","courierPhone":"","courierDocumentNo":""},"pageMode":"SERVICE_DROP","serviceDate":"2017-06-18 07:05:54","paymentInfo":{"paymentType":"cash","paymentTypes":[{"name":"Cash","value":"cash"},{"name":"Card Pyment","value":"card"},{"name":"Cheque","value":"cheq"},{"name":"Online","value":"online"}],"cardTypes":["RuPay","VISA","MaeterCard","American Express","Chase","Discover"],"cash":{"amount":0},"card":{"amount":0,"bankName":"","cardNumber":"","expDate":"","cardNetwork":"","cardBank":""},"cheq":{"amount":0,"bankName":"","cheqNo":"","cheqDate":""},"online":{"amount":0,"payMode":"","transactionId":"","remark":""}},"userInfo":{"status":true,"role":"ADMIN","userName":"admin","id":1},"accList":"Battery,Body Cap","probList":"Water Damage,Fungus in Binocular"}
OUTPUT
{
  "status": true,
  "repairReceiptId": "CE/2017-18/42"
}

