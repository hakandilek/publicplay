$(document).ready(function() {
    $('.btn-rate').click(function() {
    	var key = $(this).attr('data-key');
    	var href = $(this).attr('data-href');
        var sel = '.btn-rate[data-key="' + key + '"]';
        var selRating = '.rating[data-key="' + key + '"]';
        var clicked = this;
    	
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$(sel).addClass("disabled");
    		},
    		success:function(data) {
    		    // successful request
    			$(sel).addClass("disabled");
    			$.each($(sel), function(idx, elm) {
    				var cli = $(clicked);
    				if (elm == clicked) {    					
    	                $(elm).children().removeClass("unrated").addClass("rated");
    				} else {
    	                $(elm).children().removeClass("rated").addClass("unrated");
    	                $(elm).removeClass("disabled");
    				}
    			});
    		    $(selRating).html(data);
    		},
    		error:function() {
    		    // failed request;
                $(sel).each(function (idx, elm) {
                    $(elm).html('<i class="icon-warning-sign"></i>');
                });
    		}
    	});
    });
});
