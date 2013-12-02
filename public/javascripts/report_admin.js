function doClick(key, href, btnClass, htmlAfterSuccess, reload) {
    var sel = '.' + btnClass + '[data-key="' + key + '"]';
    var clicked = this;
	
    $.ajax({
    	type: 'GET',
		url: href,
		beforeSend: function() {
		    // append a loading image
		    $(sel).addClass("disabled");
		},
		success: function(data) {
		    // successful request
		    $(sel).addClass("disabled");
		    $(sel).each(function (idx, elm) {
		    	$(elm).html(htmlAfterSuccess);
		    });
		    if (reload) location.reload();
		},
		error: function(xhr, ajaxOptions, thrownError) {
		    // failed request;
		    $(sel).each(function (idx, elm) {
		    	$(elm).html('<i class="fa fa-warning"></i>');
		    });
		}
	});
}

$(document).ready(function() {
    $('.btn-ignore').click(function () { doClick($(this).attr('data-key'), $(this).attr('data-href'), 'btn-ignore', '<i class="fa fa-minus"></i> &nbsp; ignored', false); });
    $('.btn-approve').click(function () { doClick($(this).attr('data-key'), $(this).attr('data-href'), 'btn-approve', '<i class="fa fa-check"></i> &nbsp; approved', true); });
    $('.btn-expired').click(function () { doClick($(this).attr('data-key'), $(this).attr('data-href'), 'btn-expired', '<i class="fa fa-strikethrough"></i> &nbsp; marked', true); });
    $('.btn-remove').click(function () { doClick($(this).attr('data-key'), $(this).attr('data-href'), 'btn-remove', '<i class="fa fa-trash-o"></i> &nbsp; removed', true); });
});
