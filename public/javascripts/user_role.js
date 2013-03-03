$(document).ready(function() {
    $.fn.editable.defaults.mode = 'inline';
    $.fn.editable.defaults.ajaxOptions = {
	contentType : 'application/json',
	type : 'POST',
	dataType : 'json'
    };
    $('.user-role').editable();
});
