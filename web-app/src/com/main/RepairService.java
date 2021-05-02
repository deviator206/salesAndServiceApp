package com.main;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.main.impl.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.models.RepairServiceResponse;
import com.main.models.SearchRepairServiceResponse;
import com.main.models.UpdateRepairServiceResponse;
import com.main.models.AdminUpdateServiceModel;

@Path("/repair/")
public class RepairService {
	
	@Path("drop-from-customer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RepairServiceResponse createRepairRequest(JSONObject repairRequest) throws JSONException, InternalError, SQLException{
		CreateRepairRequestServiceImpl createRepairRequestServiceImpl = new CreateRepairRequestServiceImpl();
		createRepairRequestServiceImpl.setCustomerInfo(repairRequest.getJSONObject("customerInfo"));
		
		createRepairRequestServiceImpl.setProblemListInfo(repairRequest.getString("probList"));
		createRepairRequestServiceImpl.setAccessoryListInfo(repairRequest.getString("accList"));
		createRepairRequestServiceImpl.setShopUserComment(repairRequest.getString("shopUserComment"));
		createRepairRequestServiceImpl.setCustomerComment(repairRequest.getString("customerComment"));
		createRepairRequestServiceImpl.setTentative_quoted_costInfo(repairRequest.getString("tentative_quoted_cost"));
		createRepairRequestServiceImpl.setTentative_service_completion_dateInfo(repairRequest.getString("service_order_date"));
		createRepairRequestServiceImpl.setService_order_dateInfo(repairRequest.getString("service_order_date"));
		createRepairRequestServiceImpl.setServiceEstimation(repairRequest.getJSONObject("estimation"));
		
		createRepairRequestServiceImpl.setProductInfo(repairRequest.getJSONArray("productInfo"));
		
		createRepairRequestServiceImpl.setProductInfo(repairRequest.getJSONArray("productInfo"));
		createRepairRequestServiceImpl.setCourierInfo(repairRequest.getJSONObject("courierInfo"));
		//createRepairRequestServiceImpl.setTechnicianInfo(repairRequest.getJSONObject("technicianInfo"));
		createRepairRequestServiceImpl.setUserInfo(repairRequest.getJSONObject("userInfo"));
		createRepairRequestServiceImpl.setPaymentInfo(repairRequest.getJSONObject("paymentInfo"));
		createRepairRequestServiceImpl.execute();
		return createRepairRequestServiceImpl.getResponse();
		
	}
	
	
	
	@Path("pickup-by-customer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SearchRepairServiceResponse getRepairStatus(@QueryParam("query") String queryText,
													   @QueryParam("col") String queryOnColumn,
													   @QueryParam("type") String byType,
													   @QueryParam("startFrom") String startFrom,
													   @QueryParam("startTo") String startTo
			) throws JSONException, InternalError, SQLException{
		
		GetRepairRequestStatusImpl getRepairRequestStatusImpl = new GetRepairRequestStatusImpl();
		getRepairRequestStatusImpl.setQueryText(queryText);
		getRepairRequestStatusImpl.setQueryOnColumn(queryOnColumn);
		getRepairRequestStatusImpl.setByType(byType);
		getRepairRequestStatusImpl.setStartFrom(startFrom);
		getRepairRequestStatusImpl.setStartTo(startTo);
		getRepairRequestStatusImpl.setServiceMode();
		// getRepairRequestStatusImpl.setManipulatePayment(true);
		getRepairRequestStatusImpl.execute();
		return getRepairRequestStatusImpl.getSearchResult();
	}
	
	
	
	
	@Path("deliver-to-customer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SearchRepairServiceResponse deliverToCustomer(@QueryParam("serviceId") String serviceId,@QueryParam("serviceItems") String serviceItemList) throws JSONException, InternalError, SQLException{
		DeliverRepairRequestStatusImpl getRepairRequestStatusImpl = new DeliverRepairRequestStatusImpl();
		getRepairRequestStatusImpl.setServiceID(serviceId);
		getRepairRequestStatusImpl.setServiceItemList(serviceItemList);
		getRepairRequestStatusImpl.execute();
		return getRepairRequestStatusImpl.getSearchResult();
	}
	
	@Path("deliver-to-customer-final")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RepairServiceResponse deliverToCustomerFinal(JSONObject finalTransaction) throws JSONException, InternalError, SQLException{
		DeliverRepairRequestStatusImpl getRepairRequestStatusImpl = new DeliverRepairRequestStatusImpl();
		getRepairRequestStatusImpl.setFinalPayment(finalTransaction.getJSONObject("paymentInfo"));
		getRepairRequestStatusImpl.setServiceNumber(finalTransaction.getString("serviceNumber"));
		getRepairRequestStatusImpl.setOutwardCourierInfo(finalTransaction.getJSONObject("courierOutwardInfo"));
		getRepairRequestStatusImpl.setFinalDeliveryDate(finalTransaction.getString("finalDeliveryDate"));
		getRepairRequestStatusImpl.setMetadata("submitWithOutBill="+finalTransaction.getString("submitWithOutBill"));
		getRepairRequestStatusImpl.executeFinalPayment();
		return getRepairRequestStatusImpl.getInvoiceResult();
	}
	
	
	
	@Path("tech-new-jobs")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SearchRepairServiceResponse getRepairStatus(@QueryParam("query") String queryText,
				@QueryParam("col") String queryOnColumn,
				@QueryParam("status") String statusText,
				@QueryParam("type") String filterType,
				@QueryParam("startFrom") String startFrom,
				@QueryParam("startTo") String startTo) throws JSONException, InternalError, SQLException{
		
		GetRepairRequestStatusImpl getRepairRequestStatusImpl = new GetRepairRequestStatusImpl();
		getRepairRequestStatusImpl.setQueryText(queryText);
		getRepairRequestStatusImpl.setQueryOnColumn(queryOnColumn);
		getRepairRequestStatusImpl.setByType(filterType);
		getRepairRequestStatusImpl.setStartFrom(startFrom);
		getRepairRequestStatusImpl.setStartTo(startTo);
		getRepairRequestStatusImpl.setStatus(statusText);
		getRepairRequestStatusImpl.execute();
		return getRepairRequestStatusImpl.getSearchResult();
	}
	
	
	@Path("report-all-jobs")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SearchRepairServiceResponse getRepairReportStatus(@QueryParam("query") String queryText,
				@QueryParam("col") String queryOnColumn,
				@QueryParam("status") String statusText,
				@QueryParam("type") String filterType,
				@QueryParam("startFrom") String startFrom,
				@QueryParam("startTo") String startTo) throws JSONException, InternalError, SQLException{
		
		GetRepairRequestStatusImpl getRepairRequestStatusImpl = new GetRepairRequestStatusImpl();
		getRepairRequestStatusImpl.setQueryText(queryText);
		getRepairRequestStatusImpl.setQueryOnColumn(queryOnColumn);
		getRepairRequestStatusImpl.setByType(filterType);
		getRepairRequestStatusImpl.setStartFrom(startFrom);
		getRepairRequestStatusImpl.setStartTo(startTo);
		getRepairRequestStatusImpl.setStatus(statusText);
		getRepairRequestStatusImpl.setServiceMode();
		getRepairRequestStatusImpl.execute();
		return getRepairRequestStatusImpl.getSearchResult();
	}
	
	
	@Path("tech-job-status-update")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UpdateRepairServiceResponse updateRepairStatus(JSONObject repairRequest) throws JSONException, InternalError, SQLException{
		UpdateRepairRequestStatusImpl getRepairRequestStatusImpl = new UpdateRepairRequestStatusImpl();
		getRepairRequestStatusImpl.setUpdatedProductList(repairRequest.getJSONArray("updatedProductList"));
		getRepairRequestStatusImpl.execute();
		return getRepairRequestStatusImpl.getSearchResult();
	}

	@Path("admin-update")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AdminUpdateServiceModel adminUpdate(JSONObject adminRequest) throws JSONException, InternalError, SQLException{
		AdminUpdateServiceModel adminUpdateServiceModel = new AdminUpdateServiceModel();
		adminUpdateServiceModel.setOperationType(adminRequest.getString("op"));
		adminUpdateServiceModel.setDefaultValue(adminRequest.getString("defaultValue"));
		AdminUpdateServiceImpl adminUpdateService = new AdminUpdateServiceImpl();
		return adminUpdateService.execute(adminUpdateServiceModel);
	}
	

}
