define(["jquery","backbone","models/post","app"],
		function($,Backbone,Post,app){
			var CallendarPosts=Backbone.Collection.extend({
		  		model: Post,
		  		url: app.API_HOST+"/posts"
		  			
			});
			return new CallendarPosts();
		}
);