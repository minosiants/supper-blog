$(function(){
	app=window.app||{};
	var u=app.util;
	var Session=app.Session;
	var SupperBlogRouter=Backbone.Router.extend({
		
		routes: {
			"":                 "home",  
			"posts/:id":        "post",  			
			"posts?:query":     "search",
			"signup":     "signup",
			"signin":     "signin"
			
		},
		initialize:function(){
			 _.bindAll(this, 'signup', 'signin');
			this.mainNavbarView= new app.MainNavbarView({
				
			});
			
			this.mainNavbarView.bind("signin",this.onSignin,this);
			
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
			Session.getSession();	
			
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
		signup:function(){						
			$("#content").empty().append(new app.SignUpView({model:new app.SignUp()}).render().el);
			this.renderCommon();
		},
		signin:function(){
			$("#content").empty().append(new app.SignInView({model:  app.Session}).render().el);
			this.renderCommon();		
		},
		onSignin:function(){
			Backbone.history.navigate("/signin",true);
		}
	});
	
	
	app.supperBlogRouter= new SupperBlogRouter();
	 Backbone.history.start({
        pushState: true
     });
	/* Router.navigate();*/
	
});