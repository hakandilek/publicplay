$(document).ready(function() {
	$('[data-hide="true"]').hide();

	$('.btn-follow').click(function() {
    	var href = $(this).attr('data-href');
    	var currentId = $(this).parent().attr("id");
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$(jq(currentId)).children('.btn-follow').addClass("disabled");
    		},
    		success:function(data) {
    		    // successful request
    			$(jq(currentId)).children('.btn-follow').hide();
    			$(jq(currentId)).children('.btn-unfollow').show();
    			$(jq(currentId)).children('.btn-follow').removeClass("disabled");
    		},
    		error:function() {
    		    // failed request;
                $(jq(currentId)).children('.btn-follow').each(function (idx, elm) {
                    $(elm).html('<i class="icon-warning-sign"></i>');
                });
    		}
    	});
    });

	$('.btn-unfollow').click(function() {
    	var href = $(this).attr('data-href');
    	var currentId = $(this).parent().attr("id");
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$(jq(currentId)).children('.btn-unfollow').addClass("disabled");
    		},
    		success:function(data) {
    		    // successful request
    			$(jq(currentId)).children('.btn-unfollow').hide();
    			$(jq(currentId)).children('.btn-follow').show();
    			$(jq(currentId)).children('.btn-unfollow').removeClass("disabled");
    		},
    		error:function() {
    		    // failed request;
               $(jq(currentId)).children('.btn-unfollow').each(function (idx, elm) {
                    $(elm).html('<i class="icon-warning-sign"></i>');
                });
    		}
    	});
    });
});
//Escape chars
function jq(myid) { 
   return '#' + myid.replace(/(:|\.)/g,'\\$1');
}
