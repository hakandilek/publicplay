$(document).ready(function() {
    $('.btn-ignore').click(function() {
    	var key = $(this).attr('data-key');
    	var href = $(this).attr('data-href');
        var sel = '.btn-ignore[data-key="' + key + '"]';
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
