define(["jquery","views/base-modal-view","text!/templates/signUp.html"],
		function($,BaseModalView,signUpTemplate){
			return BaseModalView.extend({
				templateSource:signUpTemplate,
				save:function(e){			
			    	e.preventDefault();
			    	this.clearErrors();
			    	$(this.el).find("input,textarea").bindInput(this.model);
			    	if(this.model.isValid()){
			    		$(this.el).find("form").attr("action",this.model.url).submit();
			    	}        	
				},
					
				
			});
});

