$(document).ready(function() {
    $('#submit-done').hide();
    $('#submit-error').hide();
    $('#submit-missing').hide();
    $('.status').hide();
    $('.btn-report').button();
});

$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
	if (o[this.name] !== undefined) {
	    if (!o[this.name].push) {
		o[this.name] = [ o[this.name] ];
	    }
	    o[this.name].push(this.value || '');
	} else {
	    o[this.name] = this.value || '';
	}
    });
    return o;
};

function reportForm(form) {
    var data = $(form).serializeObject();
    var action = $(form).attr("action");
    reportTrigger(action, data);
}

function reportTrigger(action, data) {
    $('.btn-report').button('loading');
    $('.status').show('fast');
    $('.status').html('<i class="fa fa-refresh fa-spin"></i>');
    var modal = $(data.modalKey);
    var reportCall = {
	contentType : 'application/json',
	type : 'POST',
	url : action,
	dataType : 'json',
	async : true,
	data : JSON.stringify(data),
	success : function fetchCallback(data) {
	    var status = data.status;
	    if (status == 'error') {
		$('#scrap-wait').html('<i class="fa fa-warning"></i>');
		$('.btn-report').button('reset');
	    } else if (status == 'OK') {
		$('#submit-done').fadeIn('slow', function() {
		    $('#comment').val('');
		    $('.status').hide('fast');
		});
		$('#submit-done').fadeOut('slow', function() {
		    $('.btn-report').button('reset');
		    modal.modal('hide');
		});
	    }
	},
	error : function(xhr, ajaxOptions, thrownError) {
	    var response;
	    $('.btn-report').button('reset');
	    $('.status').hide('fast');
	    if (xhr.responseText) {
		response = JSON.parse(xhr.responseText);
	    }
	    if (response.status == 'missing') {
		$('#submit-missing').show();
	    } else {
		$('#submit-error').show();
	    }
	}
    };
    $.ajax(reportCall);
}