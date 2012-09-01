define(["jquery","underscore","backbone","util","text!/templates/profile.html"],
		function($,_,Backbone,util,profileTemplate){
			return Backbone.View.extend({		
				template:_.template(profileTemplate),
				className: 'profile well clearfix',
			
				initialize: function() {
		            _.bindAll(this, 'render');
		            this.model.bind('change', this.render);            
		        },
		        render: function() {        	
		        	$(this.el).html(this.template(util.wrap(this.model.toJSON())));
		            return this;
		        }
					
			});

});
