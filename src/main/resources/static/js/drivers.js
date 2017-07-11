$(document).ready(function(){
	
	$("#delete-driver").modal({
	      dismissible: false, // Modal can be dismissed by clicking outside of the modal
	      startingTop: '10%', // Starting top style attribute
	      endingTop: '10%',
	      opacity: .5, // Opacity of modal background
	});

	$('#add-driver-model').modal({
		dismissible : false,
		opacity : .5,
	    complete: function() {
	    	document.getElementById("driverform").reset();
			  document.getElementById("driver-id").value=null;
			  document.getElementById("created-on").value=null;
			  document.getElementById("updated-on").value=null;
			  document.getElementById("total-volume").value=null;
			  
	    	//reset form forcefully
	    }

	});
	$("#driverform").validate({
		onfocusout : false,
		onkeyup : false,
		onclick : false,
		rules : {
			firstName : {
				required : true,
			},
			phoneNumber : {
				required : true,
				minlength:10,
				maxlength:10

			},
			cardNumber:{
				required:true
			},
			vehicleNumber:{
				required:true
			},
			scheme:{
				required:true
			},
			cardNumber:{
				required:true
			}

		},
		//For custom messages
		messages : {
			firstName : {
				required : "This Field is required",

			}

		},
		errorElement : 'div',

		errorPlacement : function(error, element) {
			var placement = $(element).data('error');
			if (placement) {
				$(placement).append(error)
			} else {
				error.insertAfter(element);
			}
		},
		submitHandler : function(form) {
			var driver = {};
			console.log(form.firstName.value);
			driver["cardNumber"]=form.cardNumber.value;
			driver["firstName"]=form.firstName.value;
			driver["lastName"]=form.lastName.value;
			driver["phoneNumber"]=form.phoneNumber.value;
			driver["idCardType"]=form.idCardType.value;
			driver["idCardNumber"]=form.idCardNumber.value;
			driver["vehicleNumber"]=form.vehicleNumber.value;
			driver["address"]=form.address.value;
			driver["id"]=form.id.value;
			driver["createdOn"]=form.createdOn.value;
			driver["updatedOn"]=form.updatedOn.value;
			driver["loyaltyPoints"]=form.loyaltyPoints.value;
			if(!form.totalFuelVolume.value){
				form.totalFuelVolume.value=0;
			}
			driver["totalFuelVolume"]=form.totalFuelVolume.value;
			// var routesChips=$('#driver-routes').material_chip('data');
			// var driverRoutes=[];
			// for(chip in routesChips){
			// 	driverRoutes.push(chip.tag);
			// }
			// driver["commonRoutes"]=driverRoutes;
			var schemeText=form.scheme.value;
			var schemeId=schemeText.slice(0,schemeText.indexOf('-'));
			var scheme={
				id:schemeId
			}
			driver["scheme"]=scheme;
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/admin/drivers",
				data : JSON.stringify(driver),
				dataType : 'json',
				timeout : 600000,
				success : function(data) {
					document.getElementById("driverform").reset();
					document.getElementById("driver-id").value=null;
					  document.getElementById("created-on").value=null;
					  document.getElementById("updated-on").value=null;
					  document.getElementById("total-volume").value=null;
					$('#add-driver-model').modal('close');
					table.ajax.reload();
				},
				error : function(e) {
					document.getElementById("driverform").reset();
					document.getElementById("driver-id").value=null;
					  document.getElementById("created-on").value=null;
					  document.getElementById("updated-on").value=null;
					  document.getElementById("total-volume").value=null;
					$('#add-driver-model').modal('close');
					Materialize.toast(e.message, 4000);
				}
			});

		}
	});

     var schemeData=getSchemesForAutoComplete();
	  $('input.schemes').autocomplete({
		    data: schemeData,
		    limit: 20, // The max amount of results that can be shown at once.
						// Default: Infinity.
		    onAutocomplete: function(val) {
		      // Callback function when value is autcompleted.
		    },
		    minLength: 0, // The minimum length of the input for the
							// autocomplete to start. Default: 1.
		  });

			var  table = $('#drivers').DataTable({
			 ajax: {
				 url:"/admin/drivers",

			 },
			 lengthChange:false,
					 columns: [
										 {
												 "className":      'details-control',
												 "orderable":      false,
												 "data":           null,
												 "defaultContent": '<a  class=" btn-floating btn-large waves-effect waves-light indigo daerken-3"><i class="material-icons right">play_for_work</i></a>'
										 },
							 {"data":"id"},
							 { "data": "firstName" },
							 { "data": "lastName" },
							 { "data": "cardNumber" },
							 {"data":"phoneNumber"},
							 {"data":"vehicleNumber"},
							 {"data":"totalFuelVolume"},
							 {
								 "data": "null",
								 "defaultContent": '<a  class=" btn-floating btn-large waves-effect waves-light red driver_delete  tooltipped" data-position="left" data-delay="50" data-tooltip="Remove Driver"><i class="material-icons right">delete</i></a>'
							 },
							 {
								 "data":"null",
								 "defaultContent": '<a  class=" btn-floating btn-large waves-effect waves-light indigo darken-3 driver_edit tooltipped" data-position="right" data-delay="50" data-tooltip="Edit Driver"><i class="material-icons right">mode_edit</i></a>'
							 }
					],
					"order": [[1, 'asc']],
			 "drawCallback":function(){
				 $('.tooltipped').tooltip({
					 delay : 50,
					 html:true
				 });
			 }

			});
			$('#drivers tbody').on('click', 'td.details-control', function () {
						 var tr = $(this).closest('tr');
						 var row = table.row( tr );

						 if ( row.child.isShown() ) {
								 // This row is already open - close it
								 row.child.hide();
								 tr.removeClass('shown');
						 }
						 else {
								 // Open this row
								 row.child( formatChildRow(row.data()) ).show();
								 tr.addClass('shown');
						 }
				 } );



			$('#drivers').on('click', 'a.driver_delete', function () {
				
				var data = table.row($(this).closest('tr')).data();

				$('a.driver_delete').tooltip('remove');
					 deleteDriver(data,table);
				 } );
			$('#drivers').on('click', 'a.driver_edit', function () {
				var data = table.row($(this).closest('tr')).data();
				//fill form values

			 for(var key in data){
				 $("[name='"+key+"']").val(data[key]);
				 if(key==='scheme'){
					 $("[name='"+key+"']").val(data[key].id +'-'+data[key].name);
				 }
				 

			 }

			 $('#add-driver-model').modal('open');
			 Materialize.updateTextFields();
				 } );


});
function deleteDriver(row,table){
	
	$("#delete-driver").modal('open');
	$(".driver_delete_agree").click(function(){
		$.ajax({
		    url: '/admin/drivers/'+row.id,
		    type: 'DELETE',
		    success: function(result) {
		    	table.ajax.reload();
		    }
		});
	});
	


}
function getSchemesForAutoComplete(){
	var schemesAutoComplete={
	};

	$.ajax({
			url:"/admin/schemes",
			async:false,
		   	success: function( schemes ) {
		   	var schemeData=schemes.data;
		   	for(var obj in schemeData){
			  schemesAutoComplete[schemeData[obj].id+'-'+schemeData[obj].name]=null;
		   		}
		   	}
	});
	return schemesAutoComplete;
}

/* Formatting function for row details - modify as you need */
function formatChildRow ( d ) {
	var prizes=d.scheme.prizes.sort(function(prizeA,prizeB){
		return prizeB.targetFuel-prizeA.targetFuel;
	});
	var eligibleForPrize={
			prizeName:"None"
	};
	for(prize in prizes){
		if(d.loyaltyPoints>=prizes[prize].targetFuel){
			eligibleForPrize=prizes[prize];
			break;
		}
	}
	
	
	return '<div class="row">'+
		'<div class="col s12">'+
			'<div class="card-panel">'+
			   '<div class="row">'+
			  	'<div class="col s6">Loyalty Points</div>'+
				 '<div class="col s6">'+d.loyaltyPoints+' Points</div>'+
				'</div><hr>'+
				  '<div class="row">'+
				  	'<div class="col s6">Total Volume</div>'+
					 '<div class="col s6">'+d.totalFuelVolume+' Lt</div>'+
					'</div><hr>'+
				'<div class="row">'+
				   '<div class="col s6">Created On</div>'+
					 '<div class="col s6">'+d.createdOn+'</div>'+
				'</div><hr>'+
				'<div class="row">'+
				   '<div class="col s6">Last Updated On</div>'+
					 '<div class="col s6">'+d.updatedOn+'</div>'+
				'</div><hr>'+
				'<div class="row">'+
				   '<div class="col s6">Identity Card Type</div>'+
					 '<div class="col s6">'+d.idCardType+'</div>'+
				'</div><hr>'+
				'<div class="row">'+
				   '<div class="col s6">Identity Card Number</div>'+
					 '<div class="col s6">'+d.idCardNumber+'</div>'+
				'</div><hr>'+
				'<div class="row">'+
				   '<div class="col s6">Address</div>'+
					 '<div class="col s6">'+d.address+'</div>'+
				'</div><hr>'+
				'<div class="row">'+
				   '<div class="col s6">Scheme</div>'+
					 '<div class="col s6">'+d.scheme.name+'</div>'+
				'</div><hr>'+
				'<div class="row">'+
				   '<div class="col s6">Achieved Prize</div>'+
					 '<div class="col s6">'+eligibleForPrize.prizeName+'</div>'+
				'</div><hr>'+
			'</div>'+
		'</div>'+
	'</div>';
}
