$(document).ready(function(){
	
	var  dormantTable = $('#dormantTable').DataTable({
		 ajax: {
			 url:"/admin/dormantdrivers",

		 },
		 "scrollX": true,
		 lengthChange:false,
				 columns: [
						 { "data": "firstName",
							"render":(data,type,full)=>{
								return full.firstName+" "+full.lastName;
							}
						 },
						 {"data": "phoneNumber" },
						 {"data": "cardNumber" },
						 {"data":"vehicleNumber"},
						 {"data":"totalFuelVolume"},
						 {"data":"lastFuelingTime"},
						 {"data":"createdOn"},
				]
		 });

	 $('#dormantTable tbody').on( 'click', 'tr', function () {
	        $(this).toggleClass('selected');
	    } );
	 $('#select-all-dmt').on( 'click', function () {
	        $("#dormantTable tbody tr").addClass('selected');
	    } );
	 $('#clear-all-dmt').on( 'click', function () {
	        $("#dormantTable tbody tr").removeClass('selected');
	    } );
	 $('#notify-dmt').click( function () {
		 var selectedData=dormantTable.rows('.selected').data();
		 var drivers=[];
		 for(var i=0;i< selectedData.length;i++){
			 drivers.push( selectedData[i]);
		 }
		 $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/admin/notifydormantdrivers",
				data : JSON.stringify(drivers),
				dataType : 'text',
				timeout : 600000,
				success : function(data) {
					Materialize.toast(data, 4000,'green');
					$("#dormantTable tbody tr").removeClass('selected');
				},
				error : function(e) {
					
					Materialize.toast(e.responseJSON.message, 4000,'red');
				}
			});
	        
	    } );
	 
	 
	 
	
});
	
	
	
