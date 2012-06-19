;(function(){
	app=window.app||{};
	console.log(app.API_HOST);	
	var Posts=Backbone.Collection.extend({
  		model: app.Post,
  		url: app.API_HOST+"/posts",  		
  		selectPost:function(id){  			
  			this.trigger("post:selected",this.get(id)||this.fetchPost(id));
  		},
		fetchPost:function(id){
			var post=new app.Post({id:id})
			post.fetch();
			return post;
		},
		filterByTag:function(tag){
			this.url=app.API_HOST+"/posts?tags="+tag;
			this.fetch();
			this.trigger("posts:result");
		}		
  			
	});
	var CallendarPosts=Backbone.Collection.extend({
  		model: app.Post,
  		url: app.API_HOST+"/posts"
  			
	});
	var Tags=Backbone.Collection.extend({
  		model: app.Tag,
  		url: app.API_HOST+"/tags"
  			
	});

	app.posts=new Posts();
	app.callendarPosts=new CallendarPosts();
	
	app.tags=new Tags();
})();


		    

