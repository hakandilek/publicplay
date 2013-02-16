$(document).ready(function() {
	$('[data-hide="true"]').hide();

	$('.btn-follow').click(function() {
    	var href = $(this).attr('data-href');
    	
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$('.btn-follow').addClass("disabled");
    		},
    		success:function(data) {
    		    // successful request
    			$('.btn-follow').hide();
    			$('.btn-unfollow').show();
    			$('.btn-follow').removeClass("disabled");
    		},
    		error:function() {
    		    // failed request;
                $('.btn-follow').each(function (idx, elm) {
                    $(elm).html('<i class="icon-warning-sign"></i>');
                });
    		}
    	});
    });

	$('.btn-unfollow').click(function() {
    	var href = $(this).attr('data-href');
    	
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$('.btn-unfollow').addClass("disabled");
    		},
    		success:function(data) {
    		    // successful request
    			$('.btn-unfollow').hide();
    			$('.btn-follow').show();
    			$('.btn-unfollow').removeClass("disabled");
    		},
    		error:function() {
    		    // failed request;
                $('.btn-follow').each(function (idx, elm) {
                    $(elm).html('<i class="icon-warning-sign"></i>');
                });
    		}
    	});
    });
});
