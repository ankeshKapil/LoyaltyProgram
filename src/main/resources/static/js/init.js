$(document).ready(function() {
	$('.button-collapse').sideNav();
	$('.modal').modal({
		dismissible : false,
		opacity : .5

	});
	$('select').material_select();
	$('.routes-chips-placeholder').material_chip();
	$('.datepicker').pickadate({
		selectMonths : true,
		selectYears : 15,
	});

});
