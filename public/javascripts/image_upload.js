$(document).ready(function() {
    $('.image-upload').each(function(index) {
	var call = $(this).attr("data-call");
	var text = $(this).attr("data-text");
	$(this).fineUploader({
	    request : {
		forceMultipart : false,
		paramsInBody : false,
		endpoint : call
	    },
	    multiple : false,
	    validation : {
		allowedExtensions : [ 'jpeg', 'jpg', 'gif', 'png' ],
		sizeLimit : 512000
	    },
	    text : {
		uploadButton : '<i class="fa fa-plus-square"></i> ' + text
	    }
	}).on('complete', function(event, id, filename, response) {
	    if (response.success) {
		$('.image-preview').each(function(index) {
		    $(this).attr('src', response.data.url);
		    $(this).attr('alt', filename);
		});
		$('.imageKey').each(function(index) {
		    $(this).val(response.data.key);
		});
	    }
	});
    });
});
