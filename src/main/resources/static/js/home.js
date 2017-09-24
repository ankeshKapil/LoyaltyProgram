/**
 * 
 */
$(document).ready(function(){
	
	$('#add-transaction-model').modal({
		dismissible : false,
		opacity : .5,
	    complete: function() {
    	  document.getElementById("addtransaction").reset();

	    	//reset form forcefully
	    }

	});
	
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
					$('#add-transaction-model').modal('close');
					Materialize.toast("Alright! It's done ☺", 4000,'green');
				},
				error : function(e) {
					document.getElementById("addtransaction").reset();
					$('#add-transaction-model').modal('close');
					Materialize.toast(e.responseJSON.message, 4000,'red');
				}
			});

		}
	});
	
	getCards();
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
});

var cardsAutoComplete={};
function getCards(){
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