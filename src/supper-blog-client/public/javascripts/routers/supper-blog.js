define(["jquery","underscore","backbone",
        "models/post",
        "views/main-navbar-view",
        "views/profile-view",
        "views/two-columns-view", 
        "views/tags-view",
        "views/calendar-view",
        "views/posts-view",
        "views/post-view",
        "views/sign-up-view",
        "views/sign-in-view",
        "collections/posts",
        "collections/tags",
        "collections/calendar-posts",
        "models/session",
        "models/profile"],
		function($,_,Backbone,Post,
				MainNavbarView,ProfileView,TwoColumnsView,TagsView,CalendarView,PostsView,PostView,
				SignUpView,SignInView,posts,tags,callendarPosts,Session,profile){
	var SupperBlogRouter=Backbone.Router.extend({

		routes: {
			"":                 "home",  
			"posts/:id":        "post",  			
			"posts?q=:query":     "search",
			"posts/tags/:tags": "filterByTag",
			"signup":     		"signup",
			"signin":     		"signin"

		},
		initialize:function(){
			 _.bindAll(this, 'signup', 'signin');
			this.mainNavbarView= new MainNavbarView({

			});

			this.mainNavbarView.bind("signin",this.onSignin,this);

			this.profileView=new ProfileView({
				model: profile
			});
			this.twoColumnsView=new TwoColumnsView();

			this.tagsView=new TagsView({
				collection: tags,
				posts:posts
			});
			this.calendarView=new CalendarView({
				collection: callendarPosts
			});
			posts.bind("post:selected",this.onSelectedPost,this);
			posts.bind("posts:result",this.onResult,this);

			Session.getSession();


		},

		home: function() {
			this.insert("#twoCollumnContent",this.twoColumnsView);
			$("#oneCollumnContent").empty();
			$("#posts").empty().append(new PostsView({collection: posts}).render().el);
			this.renderCommon();			
		},

		post: function(id) {			
			posts.selectPost(id);
		},
		onSelectedPost:function(post){
			this.insert("#twoCollumnContent",this.twoColumnsView);
			$("#posts").empty().append(new PostView({model:post}).render().el);
			this.renderCommon();
			Backbone.history.navigate("posts/"+post.id);

		},
		renderCommon:function(){
			this.insert("#main-navbar",this.mainNavbarView);
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
		filterByTag:function(tags){			
			posts.filterByTag(tags);			
		},
		search:function(q){
			console.log(q);
		},
		onResult:function(){			
			this.insert("#twoCollumnContent",this.twoColumnsView);			
			$("#oneCollumnContent").empty();
			$("#posts").empty().append(new PostsView({collection: posts}).render().el);
			this.renderCommon();
			Backbone.history.navigate(u.stripHost(posts.url));
		},
		signup:function(){
			$("#twoCollumnContent").empty();
			$("#oneCollumnContent").empty().append(new SignUpView({model:new SignUp()}).render().el);
			this.renderCommon();
		},
		signin:function(){
			$("#twoCollumnContent").empty();
			$("#oneCollumnContent").empty().append(new SignInView({model:Session}).render().el);
			this.renderCommon();		
		}
//		,
//		onSignin:function(){
//			Backbone.history.navigate("/signin",true);
//		}
	});


	var sbr=new SupperBlogRouter();
	 Backbone.history.start({
        pushState: true
     });
	 return sbr;
});