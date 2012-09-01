define(["jquery","underscore","backbone","app","util"],
		function($,_,Backbone,app,util){	
		
			var Session = Backbone.Model.extend({
				  
				url:app.API_HOST+'/session',
			    initialize: function () {
			      var that = this;	     
			      $.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
			        options.xhrFields = {
			          withCredentials: true
			        };
			        
			      });
			    },
			    validate:function(attributes){	    	
			    	if(attributes.user===false){
			    		return;
			    	}
					var mergedAttributes = _.extend(_.clone(this.attributes), attributes)			
					var errors=util.validateEmptyFields(mergedAttributes);	
					
					if(errors.length>0){
						this.trigger('error', this, errors);
						return errors;
					}
					return ;
				},
			    login: function(creds) {	      
			    	var that = this;
			    	this.save(creds, {
			         success: function () {	
			        	 that.trigger('login:success', that);	        	 
			         }
			      });
			    },
			    logout: function() {
			      // Do a DELETE to /session and clear the clientside data
			      var that = this;
			      this.destroy({
			        success: function (model, resp) {
			          model.clear();
			          model.id = null;	          
			          model.set({'user':false});	   	          
			        }	    
			      });      
			    },
			    getSession: function(callback) {
			      this.fetch({
			          success: callback
			      });
			    },
			    getUser:function(){
			    	return this.get('user');
			    }
			  });
			return new Session();
});