define(["jquery","underscore","backbone","bootstrap",
        "models/session",
        "models/post",
        "models/profile",
        "models/password",
        "collections/posts",
        "views/create-post-view",
        "views/edite-profile-view",
        "views/change-password-view",
        "text!/templates/mainNavbar.html"],
		function($,_,Backbone,Bootstrap,
				Session,
				Post,
				profile,
				Password,
				posts,
				CreatePostModalView,
				EditeProfileModalView,
				ChangePasswordModalView,
				mainNavbarTemplate){

			var MainNavbarView=Backbone.View.extend({
			
				classNmae:'main-navbar',
				
				initialize: function() {
		            _.bindAll(this, 'render');
		            this.template=_.template(mainNavbarTemplate);            
		            var that=this;
		            Session.on('change:user',function(session){            	
		            	that.render();
		            });                    
		        },
				
		        render: function() {
		        	var $el=$(this.el);
		        	$el.html(this.template());
		        	if(Session.getUser()){
		        		$("#userMenu",$el).show();
		        		$("#signinMenu",$el).hide()
		        	}else{
		        		$("#userMenu",$el).hide();
		        		$("#signinMenu",$el).show()
		        	}
		            return this;
		        },
				
				events: {
					'click .new-post':  		'newPost',						
					'click .edit-profile':  	'editProfile',
					'click .change-password':  	'changePassword',
					'click .sign-out':  		'signOut',
					'click .sign-in':  			'signIn',
					'click .sign-up':  			'signUp'
			    },		
		        
			    
			    newPost:function(){
		        	var post=new Post();
		        	$(new CreatePostModalView({model:post}).render().el).modal();
		        	post.on("sync",function(){
		        		posts.add(post);
		        		posts.selectPost(post.id);
		        	});
		        	
		        },
			    editProfile:function(){
			    	$(new EditeProfileModalView({model:profile}).render().el).modal();	
			    },
		        changePassword:function(){
		        	$(new ChangePasswordModalView({model:new Password()}).render().el).modal();
		        },
			    signOut:function(){
			    	Session.logout();
			    },
		        signUp:function(e){
		        	e.preventDefault();
		        	 
		        	//$(new SignUpModalView({model:new app.SignUp()}).render().el).modal();
		        },
			    signIn:function(e){
			    	//e.preventDefault();
			    	///this.trigger('signin');
			    	//$(new SignInModalView({model:new app.SignIn()}).render().el).modal();
			    	//return false;
			    }
		});
			
		return MainNavbarView;	
	
});
