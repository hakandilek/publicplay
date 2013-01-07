$(document).ready(function() {
	var supportOnInput = 'oninput' in document.createElement('input');

	$("[data-maxchars]").each(function() {
	    var $this = $(this);
	    var maxLength = parseInt($this.attr('data-maxchars'));
	    $this.attr('data-maxchars', null);
	    
	    var el = $('<span class="maxchars"> ' + maxLength + '</span>');
	    el.insertAfter($this);
	    
	    $this.bind(supportOnInput ? 'input' : 'keyup', function() {
	        var cc = $this.val().length;
	        
	        el.text(maxLength - cc);
	        
	        if(maxLength < cc) {
	            el.addClass('maxchars-warn');
	        } else {
	            el.removeClass('maxchars-warn');
	        }
	    });
	});
});
