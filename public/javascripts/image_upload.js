$(document).ready( function() {
    var comp = $('#image-upload');
    var call = comp.attr("data-call");
    var text = comp.attr("data-text");
    comp.fineUploader({
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
	    uploadButton : '<i class="icon-plus icon-white"></i> ' + text
	}
    }).on('complete', function(event, id, filename, response) {
	if (response.success) {
	    var img = $('#image-preview');
	    img.attr('src', response.data.url);
	    img.attr('alt', filename);
	    $('#imageKey').val(response.data.key);
	}
    });
});
