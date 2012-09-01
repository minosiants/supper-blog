define(["jquery","underscore","backbone","util","text!/templates/twoColumns.html"],
		function($,_,Backbone,util,twoColumnsTemplate){
			return  Backbone.View.extend({		
				template:_.template(twoColumnsTemplate),			
				initialize: function() {
					_.bindAll(this, 'render');			           
				},
				render: function() {        	
					$(this.el).html(this.template());
					return this;
				}		
			});
	
});
