
$(document).ready(function() {

	$('#add-scheme-model').modal({
		dismissible : false,
		opacity : .5,
	    complete: function() {
	    	
	    	document.getElementById("addscheme").reset();
			document.getElementById("scheme-id").value=null;
			document.getElementById('prize_fields').innerHTML="";
			prizeCount=0;
	    	//reset form forcefully
	    }

	});


	$("#addscheme").validate({
		onfocusout : false,
		onkeyup : false,
		onclick : false,
		rules : {
			name : {
				required : true
			},
			"prize[]":{
				required:true
			},
			"targetVolume[]":{
				required:true
			}
		},
		//For custom messages
		messages : {
			name : {
				required : "Enter a scheme name",
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
			var scheme = {};
			var prizes=[];
			console.log(form.name.value);
			scheme["name"] =form.name.value;
			scheme["startDate"] =form.startDate.value;
			scheme["endDate"] =form.endDate.value;
			scheme["id"]=form.id.value;
			var prizeArray=document.getElementsByName("prize[]");
			var prizeIdArray=document.getElementsByName("prizeId[]");
			var targetVolumeArray=document.getElementsByName("targetVolume[]");
			var sizeofArray=Math.max(prizeArray.length,targetVolumeArray.length);

			for(var i=0;i< sizeofArray;i++){
				prizes.push({
					         prizeId:prizeIdArray[i].value,
							 prizeName:prizeArray[i].value,
					         targetFuel:targetVolumeArray[i].value
				            });
			}
			scheme["prizes"]=prizes;
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/admin/schemes",
				data : JSON.stringify(scheme),
				dataType : 'json',
				timeout : 600000,
				success : function(data) {
					document.getElementById("addscheme").reset();
					document.getElementById("scheme-id").value=null;
					document.getElementById('prize_fields').innerHTML="";
					prizeCount=0;
					$('#add-scheme-model').modal('close');
					table.ajax.reload();

				},
				error : function(e) {
					document.getElementById("addscheme").reset();
					document.getElementById("scheme-id").value=null;
					document.getElementById('prize_fields').innerHTML="";
					prizeCount=0;
					$('#add-scheme-model').modal('close');
					Materialize.toast(e.responseJSON.message, 4000,'red');
				}
			});

		}
	});

	//Datatable
	 var  table = $('#schemes').DataTable({
		ajax: {
			url:"/admin/schemes",

		},
		"scrollX": true,
		lengthChange:false,
        columns: [
                  {
                      "className":      'details-control',
                      "orderable":      false,
                      "data":           null,
                      "defaultContent": '<a  class=" btn-floating btn-large waves-effect waves-light indigo daerken-3"><i class="material-icons right">play_for_work</i></a>'
                  },
//            {"data":"id"},
            { "data": "name" },
            { "data": "startDate" },
            { "data": "endDate" },
//            {
//            	"data": "null",
//            	"defaultContent": '<a  class=" btn-floating btn-large waves-effect waves-light red scheme_delete tooltipped" data-position="left" data-delay="50" data-tooltip="Remove Scheme"><i class="material-icons right">delete</i></a>'
//            },
            {
            	"data":"null",
            	"defaultContent": '<a  class=" btn-floating btn-large waves-effect waves-light indigo darken-3 scheme_edit tooltipped" data-position="right" data-delay="50" data-tooltip="Edit Scheme"><i class="material-icons right">mode_edit</i></a>'
            }
       ],
  
		"drawCallback":function(){
			$('.tooltipped').tooltip({
				delay : 50,
				html:true
			});
			
			$("[id$=_filter] > label").css("fontSize", "20px");
		}

 });
	 $('#schemes tbody').on('click', 'td.details-control', function () {
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



	 $('#schemes').on('click', 'a.scheme_delete', function () {
		 var data = table.row($(this).closest('tr')).data();

		 $('a.scheme_delete').tooltip('remove');
	      deleteScheme(data,table);
	    } );
	 $('#schemes').on('click', 'a.scheme_edit', function () {
		 var data = table.row($(this).closest('tr')).data();
		 //fill form values

		for(var key in data){
			$("[name='"+key+"']").val(data[key]);

		}
		for(prize in data.prizes){
			prizeField();
			document.getElementsByName("prize[]")[prize].value=data.prizes[prize].prizeName;
			document.getElementsByName("targetVolume[]")[prize].value=data.prizes[prize].targetFuel;
			document.getElementsByName("prizeId[]")[prize].value=data.prizes[prize].prizeId;

		}

		$('#add-scheme-model').modal('open');
		Materialize.updateTextFields();
	    } );

});

function deleteScheme(row,table){

	$.ajax({
	    url: '/admin/schemes/'+row.id,
	    type: 'DELETE',
	    success: function(result) {
	    	table.ajax.reload();
	    }
	});

}

/* Formatting function for row details - modify as you need */
function formatChildRow ( d ) {
    // `d` is the original data object for the row
	var table='<table class="bordered">'+
	              '<tr>'+
                        '<th>Target Volume</th>'+
                        '<th>Prize</th>'+
                 '</tr>';
	var prizes=d.prizes.sort(function(prizeA,prizeB){
		return prizeB.targetFuel-prizeA.targetFuel;
	});
	for(prize in prizes){
		table+='<tr>'+
			       '<td>'+d.prizes[prize].targetFuel+' Lt</td>'+
			       '<td>'+d.prizes[prize].prizeName+'<td>'+
					'<tr>';
	}
    return table+ '</table>';
}
var prizeCount=0;
function prizeField(){
	prizeCount++;
	var objTo=document.getElementById('prize_fields');
	var divtest = document.createElement("div");
	divtest.setAttribute("class", "removeclass"+prizeCount);
	var rdiv='removeclass'+prizeCount;
	    divtest.innerHTML = '<div class="row" id="prizes">'
			+ '<div class="input-field col  s12 l5">'
			+ '<i class="material-icons prefix">arrow_upward</i> <input type="number" class="validate" id="target-volume" name="targetVolume[]">'
			+ '<label for="target">Target Volume</label></div>'
			+ '<div class="input-field col s12 l5"> <i class="material-icons prefix">card_giftcard</i>'
			+ '<input type="text" class="validate" id="prize" name="prize[]"> <label for="prize">Prize</label></div>'
			+ '<div class="col s12 l2">'
			+ '<button  onclick="removePrize('
			+ prizeCount
			+ ');" class=" btn-floating btn-large waves-effect waves-light red prize_delete tooltipped" data-position="right" data-delay="50" data-tooltip="Remove Prize"><i class="material-icons right">delete</i></button>'
			+ '</div>'
			+ '<input type="hidden"  id="prizeId" name="prizeId[]"></div>'
			+ '</div>';
    objTo.appendChild(divtest);
 /*   $('.tooltipped').tooltip({
		delay : 50,
		html:true
	});*/
}

function removePrize(rid) {
	   $('.removeclass'+rid).remove();
	   $('.removeclass'+rid).tooltip('remove');
}
