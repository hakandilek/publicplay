$(document).ready(function() {
    $('.rate').click(function() {
    	var key = $(this).attr('data-key');
    	var href = $(this).attr('data-href');
        var sel = '.rate[data-key="' + key + '"]';
        var selRating = '.rating[data-key="' + key + '"]';
    	
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$(sel).each(function (idx, elm) {
                    if (idx == 0)
    				    $(elm).html('<i class="icon-asterisk"></i>');
    				else
    					$(elm).hide();
    			});
    		},
    		success:function(data) {
    		    // successful request
                $(sel).each(function (idx, elm) {
                    if (idx == 0)
                        $(elm).html('<i class="icon-ok"></i>');
                    else
                        $(elm).hide();
                    $(elm).bind('click', false);
                });
    		    $(selRating).html(data);
    		},
    		error:function() {
    		    // failed request;
                $(sel).each(function (idx, elm) {
                    if (idx == 0)
                        $(elm).html('<i class="icon-warning-sign"></i>');
                    else
                        $(elm).hide();
                });
    		}
    	});
    });
});
