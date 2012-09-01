define(["backbone","views/base-modal-view","text!/templates/signIn.html","serializeObject"],
		function(Backbone,BaseModalView,signInTemplate){
			return BaseModalView.extend({
				templateSource:signInTemplate,
				save:function(e){			
		        	e.preventDefault();
		        	this.clearErrors();        	
		        	this.model.login($("form",this.el).serializeObject());
		        	this.model.on('login:success',function(){        		
		        		Backbone.history.navigate("/",true);        		
		        	});
		        	
				},
				extraOptions:{
					embedded:true
				}
					
				
			});
});

