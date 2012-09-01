define(["jquery",],
		function($){
	$.fn.bindInput = function( model ) {  
	    return this.each(function() {
	      var $this = $(this);
	      model.set($this.attr("name"),$this.val(),{silent: true});
	      
	    });
	  };
	$.fn.clearErrors = function() {  
		  return this.each(function() {			  
			  $(this).find(".error").removeClass("error").find(".help-block").text("");
			  $(".global-error",this).remove();
		  });
	  };
	  $.fn.displayError = function(error) {  		  
		  return this.each(function() {
			  if(!error.field){
				  return;
			  }
			  var key=error.field.replace(".","\\.");
			  key!=="" ? $('#'+key,this).addClass("error").find(".help-block").text(error.message) 
					  :$("<div class='control-group global-error error help-block'><span class='help-inline'></span></div>").find('.help-inline').text(error.message).end().prependTo($("form",this));
			  
		  });
	  };
		
});