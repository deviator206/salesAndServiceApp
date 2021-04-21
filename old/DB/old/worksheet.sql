select * from payment_details_table where invoice_id = "NCE/2018-19/431"


select * from SERVICE_INFO_TABLE where  service_order_number LIKE '%431%' ORDER BY id DESC

select * from SERVICE_INFO_TABLE where  service_order_number LIKE '%NCE/2018-19/444%' ORDER BY id DESC


select * from sales_invoice_table where actualInvoiceId = 'CE/2018-19/173';

select * from sales_invoice_table


select * from sales_order_table where invoice_id = 'CE/2018-19/175'

select * from payment_details_table where invoice_id = 'CE/2018-19/186'

select * from emp_customer_table where phone like'%77777%' or alternate_number like '%77777%' 
 
 select * from emp_product_table where id = '24'

select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where   t2.invoice_id like'%17%' 


select * from emp_customer_table t1
 inner join sales_order_table t2 on t1.id = t2.customer_id
 inner join emp_product_table t3 on t2.product_id = t3.id 
 inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id 
 where t1.name like'%sandeep%' 
 order by t2.sale_id DESC
 
 
 select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where   t2.invoice_id like'%186%' order by t2.sale_id DESC 

select * from emp_product_table