$(function(){
	app=window.app||{};
	var u=app.util;
	var SupperBlogRouter=Backbone.Router.extend({
		
		routes: {
			"":                 "home",  
			"posts/:id":        "post",  			
			"posts?:query":     "search",
			"signup":     "signUp",
			"signin":     "signIn"
			
		},
		initialize:function(){
			
			this.mainNavbarView= new app.MainNavbarView({
				
			});
			this.profileView=new app.ProfileView({
				model: app.profile
			});
			this.tagsView=new app.TagsView({
				collection: app.tags,
				posts:app.posts
			});
			this.calendarView=new app.CalendarView({
				collection: app.callendarPosts
			});
			app.posts.bind("post:selected",this.onSelectedPost,this);
			app.posts.bind("posts:result",this.onResult,this);
			
		},
		
		home: function() {									
			$("#posts").empty().append(new app.PostsView({collection: app.posts}).render().el);
			this.renderCommon();			
		},

		post: function(id) {			
			app.posts.selectPost(id);
		},
		onSelectedPost:function(post){
			$("#posts").empty().append(new app.PostView({model:post}).render().el);
			this.renderCommon();
			Backbone.history.navigate("posts/"+post.id);
			
		},
		renderCommon:function(){
			this.insert("#main-navbar",this.mainNavbarView);
			this.insert("#profile",this.profileView);
			this.insert("#calendar",this.calendarView);
			this.insert("#tags",this.tagsView);
		},
		
		insert:function(id,view){
			var $el=$(id);					
			if($el.is(":empty")){
				$el.append(view.render().el);
			}
			
		},
		search:function(q){
			console.log(q);
		},
		onResult:function(){
			Backbone.history.navigate(u.stripHost(app.posts.url));
		},
		signUp:function(){			
			var model=new app.SignUp();
			model.set(app.form.data||{});
			$("#content").empty().append(new app.SignUpView({model:model}).render().el);
			model.setErrors(app.form.errors);			
			this.renderCommon();
		},
		signIn:function(){
			var model=new app.SignIn();
			model.set(app.form.data||{});
			$("#content").empty().append(new app.SignInView({model: model}).render().el);
			model.setErrors(app.form.errors);
			this.renderCommon();
		}
	});
	
	
	app.supperBlogRouter= new SupperBlogRouter();
	 Backbone.history.start({
        pushState: true
     });
	/* Router.navigate();*/
	
});