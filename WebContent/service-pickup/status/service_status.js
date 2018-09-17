'use strict';

angular.module('salesApp.service_status', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/service-status', {
    templateUrl: 'service-pickup/status/service_status.html',
    controller: 'ServiceStatusController'
  });
}])

.controller('ServiceStatusController', ['$scope', '$http', '$uibModal', '$log','Util' ,function($scope, $http, $modal, $log,Util) {
	$scope.printPage = Util.printPage;
	 $scope.backendItemStatusMap= {
		        "IN PROGRESS" :"IP",
		        "NOT STARTED" :"NS",
		        "TECHNICIAN HANDLE":"TH",
		        "COMPLETE" : "C",
		        "CUSTOMER APPROVAL": "CA",
		        "PART PENDING" :"PP",
		        "CANNOT BE REPAIRED" :"CBR"
		    }
	 $scope.uiToBackendMap= {
		        "IP":"IN PROGRESS",
		        "NS":"NOT STARTED" ,
		        "TH":"TECHNICIAN HANDLE",
		        "C":"COMPLETE" ,
		         "CA":"CUSTOMER APPROVAL",
		        "PP":"PART PENDING" ,
		        "CBR":"CANNOT BE REPAIRED",
		        "DTC":"DELIVERED TO CUSTOMER"
		    }
 $scope.init = function(){
   $scope.resetModel();
 }
 $scope.resetModel = function(){
   $scope.statusSearchTextModel = "";
   $scope.itemSelectionError ="";
   $scope.currentJobStatusList=[];
   $scope.serviceDateFromModel="";
   $scope.serviceDateTo=new Date();
   $scope.statusSearchFilterOptions = [ "CUSTOMER_NAME","CUSTOMER_PHONE", "SERVICE_ID","SERIAL_NUMBER", "PRODUCT_NAME"];
   $scope.searchFilterByOptions=["SEARCH BY TEXT","SEARCH BY DATE"];
   $scope.selectedFilterByOptionForStatus = $scope.searchFilterByOptions[0]
   //$scope.statusSearchFilterOptions =  [ "ALL","SERVICE_ID","SERIAL_NUMBER", "PRODUCT_NAME","CUSTOMER_PHONE", "CUSTOMER_NAME"];
   $scope.statusSelectedSearchFilterOptionsModel  =$scope.statusSearchFilterOptions[0];


 }
 $scope.onFilterByOptionIsChanged = function(){
	  $scope.serviceDateFromModel="";
	   $scope.serviceDateTo=new Date();
	   $scope.statusSearchTextModel ="";
	}
 
 $scope.reloadPage = function(event){
	 this.$close();
 }
 
 
	$scope.viewBill = function(obj) 
	{
		if (obj.advancePayment) {
			if(obj.paymentSingleModel && !obj.paymentSingleModel.paymentType) {
				obj.paymentInfo['paymentType'] = 'cash';
				obj.paymentInfo['cash'] = {
						amount:obj.advancePayment
				};
			}
			
		}
		
		$scope.serviceResponse = obj;
		$scope.serviceResponse.repairReceiptId = obj.serviceNumber;
		$scope.serviceRequest = obj;
		$scope.paymentInfo = obj.paymentInfo;
		if ($scope.serviceRequest && $scope.serviceRequest.accessoryList && typeof $scope.serviceRequest.accessoryList === 'string') {
		    // this is a string
			$scope.serviceRequest.accessoryList = $scope.serviceRequest.accessoryList.split(",");
		}
		
		if ($scope.serviceRequest && $scope.serviceRequest.problemLists && typeof $scope.serviceRequest.problemLists === 'string') {
		    // this is a string
			$scope.serviceRequest.problemLists = $scope.serviceRequest.problemLists.split(",");
		}
		
		if ($scope.serviceRequest && $scope.serviceRequest.problemList && typeof $scope.serviceRequest.problemList === 'string') {
		    // this is a string
			$scope.serviceRequest.problemLists = $scope.serviceRequest.problemList.split(",");
		}
		
		
		
		// $scope.serviceRequest.problemLists = $scope.serviceRequest.problemLists.split(",");
		$scope.receiptXtraName = "NAIK "
		if (obj.serviceStatus !== 'DTC') {
			$scope.receiptType = 'ESTIMATE'
			// $scope.receiptXtraName = "NAIK "
		} else {
			$scope.receiptType = 'INVOICE'
		}
		
		
		Util.openPrintPopUp($scope, 'service-drop-maybe');
	}
 $scope.statusSearchTextAsPerFilterOption = function(){
	 var obj = {};
	 
	 if ($scope.selectedFilterByOptionForStatus === 'SEARCH BY DATE') {
		 if ($scope.serviceDateFromModel === ""  || $scope.serviceDateTo === "" ) {
			 $scope.serviceSearchCriteriaIncomplete = "Please Enter Valid Date";
			 return false;
		 }
		 obj = {
					"query":"",
					"type":"BY_DATE",
					"col":"",
					"startFrom":new Date($scope.serviceDateFromModel).toMysqlFormat() ,
					"startTo":new Date($scope.serviceDateTo).toMysqlFormat() 
				}
	 }else {
		 obj = {
				 	"type":"BY_QUERY",
		        	"query": $scope.statusSearchTextModel,
		        	"col": $scope.statusSelectedSearchFilterOptionsModel,
		        	"startFrom":"",
		        	"startTo":""
		       	
		        }
		 
	 }
	  
    // $http.get('fixture/searchForStatus.json?v='+(Math.random()),{
	  $http.get('rest/repair/pickup-by-customer?v='+(Math.random()),{
         params: obj 
       }).then(function(response){
           $scope.currentJobStatusList = response.data.searchResults;
       });

 }
}]);

Date.prototype.toMysqlFormat = function() {
    return this.getFullYear() + "-" + twoDigits(1 + this.getMonth()) + "-" + twoDigits(this.getDate()) + " " + twoDigits(this.getHours()) + ":" + twoDigits(this.getMinutes()) + ":" + twoDigits(this.getSeconds());
};


function twoDigits(d) {
    if(0 <= d && d < 10) return "0" + d.toString();
    if(-10 < d && d < 0) return "-0" + (-1*d).toString();
    return d.toString();
}
