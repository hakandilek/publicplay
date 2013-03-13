$(document).ready(function() {
	$.fn.editable.defaults.mode = 'inline';
	$.fn.editable.defaults.ajaxOptions = {
		contentType : 'application/json',
		type : 'PUT',
		dataType : 'json'
	};
	$('.user-role').editable({
		params : function(params) {
			return JSON.stringify(params);
		}
	});
});
