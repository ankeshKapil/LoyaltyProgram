
$(document).ready(function(){
	
	//if cardnumber is present in url search using cardnumber
	let params = (new URL(location)).searchParams;
	var cardNumber=params.get('cardnumber');
	$('#search-card-number').val(cardNumber);
	params.delete('cardnumber');
	window.history.replaceState({}, '', `${location.pathname}?${params}`);
	
	
	$('#add-transaction-model').modal({
		dismissible : false,
		opacity : .5,
	    complete: function() {
    	  document.getElementById("addtransaction").reset();
		  document.getElementById("transaction-id").value=null;
		  document.getElementById("transaction-createdOn").value=null;
		  $('#amount').prop('disabled',false);
		  $("#fuelVolumeLabel").text("Fuel Volume");
			$("#prize-select").hide();
	    	//reset form forcefully
	    }

	});
	//disable amount field and set volume label to points
	$('#redeem').click(function(){
		var redeem=this;
        
        if($("#card-number").valid() && redeem.checked){
        	setTransactionRedeem();
        }
        else{
        	resetTransactionRedeem();
        }
       
        
    });
	
	$("#card-number").change(function(){
		resetTransactionRedeem();
	});
	
	
	$( "#prize" )
	  .change(function() {
	    var text;
	    var value;
	    $( "#prize option:selected" ).each(function() {
	    	if($( this ).val()){
	    	  text= $( this ).text();
	   	      value=$( this ).val();
	   	      $( "#transaction-notes" ).val( text );
	   		  $( "#fuelVolume" ).val( value );
	    	}
	    	 Materialize.updateTextFields();
	    });
	    
	  })
	  .trigger( "change" );
	
	
	
	$("#addtransaction").validate({
		onfocusout : false,
		onkeyup : false,
		onclick : false,
		rules : {

			cardNumber:{
				required:true
			},
			fuelVolume:{
				required:true,
				min:0
			},
			amount:{
				required:true,
				min:0
			},

		},
		//For custom messages
		messages : {
			cardNumber : {
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
			var transaction = {};
			transaction["fuelVolume"]=form.fuelVolume.value;
			transaction["amount"]=form.amount.value;
			transaction["trxTime"]=form.trxTime.value;
			transaction["id"]=form.id.value;
			transaction["redeem"]=form.reedem.checked;
			transaction["transactionNotes"]=form.transactionNotes.value;
			var driver={};
			driver["cardNumber"]=form.cardNumber.value;
			transaction["driver"]=driver;
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/admin/transaction",
				data : JSON.stringify(transaction),
				dataType : 'json',
				timeout : 600000,
				success : function(data) {
					document.getElementById("addtransaction").reset();
					  document.getElementById("transaction-id").value=null;
					  document.getElementById("transaction-createdOn").value=null;
					$('#add-transaction-model').modal('close');
					table.ajax.reload();
				},
				error : function(e) {
					document.getElementById("addtransaction").reset();
					  document.getElementById("transaction-id").value=null;
					  document.getElementById("transaction-createdOn").value=null;
					$('#add-transaction-model').modal('close');
					Materialize.toast(e.responseJSON.message, 4000,'red');
				}
			});

		}
	});
	
	
	
	getSchemesForAutoComplete();
	 $('input.cards').autocomplete({
		    data: cardsAutoComplete,
		    limit: 5, // The max amount of results that can be shown at once.
						// Default: Infinity.
		    onAutocomplete: function(val) {
		      // Callback function when value is autcompleted.
		    },
		    minLength: 0, // The minimum length of the input for the
							// autocomplete to start. Default: 1.
		  });
	 
	 var  table = $('#transaction').DataTable({
		 "searching": false,
		 "scrollX": true,
		 ajax: {
			 url:"/admin/transaction",
			 "data": function ( d ) {
			        d.searchCardNumber = $('#search-card-number').val();
			        d.startDate=$("#start-date").val();
			        d.endDate=$("#end-date").val();
			    },
			 error:function(e){
				 Materialize.toast(e.responseJSON.message, 4000,'red');
			 }

		 },
		 lengthChange:false,
				 columns: [
						 
						 { "data": "driver.cardNumber" },
						 { "data": "trxTime" },
						 {"data":  "fuelVolume"},
						 {"data":"amount"},
      					 {"data":"redeem",
						   "render":(data,type,full)=>{
							   var trxType=(data==true?"Redeem":"Sale");
							   if(full.transactionNotes){
								   return '<span class="tooltipped" data-position="right" data-delay="50" data-tooltip="'+full.transactionNotes+'">'+trxType+'</span>';
							   }
							   else{
								   return trxType;
							   }
						   }
					     },
						 {
							 "data": "null",
							 "defaultContent": '<a  class=" btn-floating btn-large waves-effect waves-light red trx_delete tooltipped" data-position="left" data-delay="50" data-tooltip="Remove Transaction"><i class="material-icons right">delete</i></a>'
						 }
				],
				"columnDefs": [
				               {

				                   "render": function ( data, type, row ) {
				                	   
				                       return data==true?"Redeem":"Sale";
				                   },
				                   "targets": 4
				               },
				               {
				                   "render": function ( data, type, row ) {
				                	   
				                       return "<a class='driver-details waves-effect waves-teal btn-flat blue-text'>"+data+"<i class='material-icons right'>play_for_work</i></a>";
				                   },
				                   "targets": 0
				               }
				           ],
				"order": [[5, 'asc']],
		 "drawCallback":function(){
			 $('.tooltipped').tooltip({
				 delay : 50,
				 html:true
			 });
			 $("[id$=_filter] > label").css("fontSize", "20px");

		 }

		});
	 
	 $('#transaction tbody').on('click', 'a.driver-details', function () {
		 var tr = $(this).closest('tr');
		 var row = table.row( tr );

		 if ( row.child.isShown() ) {
				 // This row is already open - close it
				 row.child.hide();
				 tr.removeClass('shown');
		 }
		 else {
				 // Open this row
				 row.child( formatChildRow(row.data().driver) ).show();
				 tr.addClass('shown');
		 }
 } );
	 



			$('#transaction').on('click', 'a.trx_delete', function () {
			var data = table.row($(this).closest('tr')).data();
			
			$('a.trx_delete').tooltip('remove');
				 deleteTransaction(data,table);
			 } );


			
		$("#searchtrx").click(function(e){
			e.preventDefault();
			table.ajax.reload();
		}
		);
		
	
});

var cardsAutoComplete={};
function getSchemesForAutoComplete(){
	$.ajax({
			url:"/admin/getCardNumbers",
			async:false,
		   	success: function( cards ) {
		   	for(var card in cards){
		   		cardsAutoComplete[cards[card]]=null;
		   		}
		   	}
	});
}
function deleteTransaction(row,table){

	$.ajax({
	    url: '/admin/transaction/'+row.id,
	    type: 'DELETE',
	    success: function(result) {
	    	table.ajax.reload();
	    }
	});

}

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
	return '<h3>About Driver</h3>'+
		'<div class="row">'+
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
function populatePrizes(){
	$('#prize').empty();
	$('#prize').material_select('destroy');
	$('#prize').material_select();
	$.ajax({
		url:'/admin/getPrizes',
		data:{
			searchCardNumber:$("#card-number").val()
		},
		success:function(prizes){
			$('#prize').append($('<option>', { 
		        value: "",
		        text : "Choose your Prize",
		        selected:true,
		        disabled:true
		    }));

			$.each(prizes, function (i, prize) {
			    $('#prize').append($('<option>', { 
			        value: prize.targetFuel,
			        text : prize.prizeName 
			    }));
			});
			$('#prize').material_select();
		}
		
	});
}

function resetTransactionRedeem(){
	$('#redeem').prop('checked',false);
	$('#amount').prop('disabled',false);
	 $("#fuelVolumeLabel").text("Fuel Volume");
	$('#amount').val("");
	$('#prize').empty();
	$('#prize').append($('<option>', { 
        value: "",
        text : "Choose your Prize",
        selected:true,
        disabled:true
    }));
	$('#prize').prop('disabled',true);
	$("#prize-select").hide();
	$( "#transaction-notes" ).val("");
    $( "#fuelVolume" ).val("");
}
function setTransactionRedeem(){
	$('#amount').prop('disabled',true);
    $('#amount').val(0);
    $("#fuelVolumeLabel").text("Loyalty Points");
    Materialize.updateTextFields();
	$('#prize').prop('disabled',false);
	populatePrizes();
	$("#prize-select").show();
}