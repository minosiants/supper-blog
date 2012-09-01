define(["jquery","backbone","models/tag","app"],
		function($,Backbone,Tag,app){
			var Tags=Backbone.Collection.extend({
		  		model: Tag,
		  		url: app.API_HOST+"/tags"
		  			
			});
			return new Tags();
		}
);