$(document).ready(function() {
	$('[data-hide="true"]').hide();

	$('.btn-follow').click(function() {
    	var href = $(this).attr('data-href');
    	var currentId = $(this).attr('id');
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$("a"+jq(currentId)+".btn-follow").addClass("disabled");
    		},
    		success:function(data) {
    		    // successful request
    			$("a"+jq(currentId)+".btn-follow").hide();
    			$("a"+jq(currentId)+".btn-unfollow").show();
    			$("a"+jq(currentId)+".btn-follow").removeClass("disabled");
    		},
    		error:function() {
    		    // failed request;
                $("a"+jq(currentId)+".btn-follow").each(function (idx, elm) {
                    $(elm).html('<i class="icon-warning-sign"></i>');
                });
    		}
    	});
    });

	$('.btn-unfollow').click(function() {
    	var href = $(this).attr('data-href');
    	var currentId = $(this).attr('id');
        $.ajax({
        	type: 'GET',
    		url: $(this).attr('data-href'),
    		beforeSend:function() {
    			// append a loading image
    			$("a"+jq(currentId)+".btn-unfollow").addClass("disabled");
    		},
    		success:function(data) {
    		    // successful request
    			$("a"+jq(currentId)+".btn-unfollow").hide();
    			$("a"+jq(currentId)+".btn-follow").show();
    			$("a"+jq(currentId)+".btn-unfollow").removeClass("disabled");
    		},
    		error:function() {
    		    // failed request;
                $("a"+jq(currentId)+".btn-unfollow").each(function (idx, elm) {
                    $(elm).html('<i class="icon-warning-sign"></i>');
                });
    		}
    	});
    });
});
function jq(myid) { 
   return '#' + myid.replace(/(:|\.)/g,'\\$1');
}
