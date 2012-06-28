$(function(){
	app=window.app||{}
	var Session=app.Session;
	$.fn.bindInput = function( model ) {  
	    return this.each(function() {
	      var $this = $(this);
	      model.set($this.attr("name"),$this.val(),{silent: true});
	      
	    });
	  };
	$.fn.clearErrors = function() {  
		  return this.each(function() {			  
			  $(this).find(".error").removeClass("error").find(".help-block").text("");
			  $(".global-error",this).remove();
		  });
	  };
	  $.fn.displayError = function(error) {  		  
		  return this.each(function() {
			  if(!error.field){
				  return;
			  }
			  var key=error.field.replace(".","\\.");
			  key!=="" ? $('#'+key,this).addClass("error").find(".help-block").text(error.message) 
					  :$("<div class='control-group global-error error help-block'><span class='help-inline'></span></div>").find('.help-inline').text(error.message).end().prependTo($("form",this));
			  
		  });
	  };
	
	var util=app.util;
	
	var BaseView=Backbone.View.extend({		
		initialize: function() {
            _.bindAll(this, 'render');
            this.template=_.template($(this.templateName).html());
            if(this.model){
            	this.model.bind('change', this.render);
            }
            var that=this;
            Session.on('change:user',function(session){
            	that.render();
            });
            if(this.postInitialize){
            	this.postInitialize();
            }
            
        },
        render: function() {
        	var data={data:this.model ? this.model.toJSON() : (this.collection ? this.collection.toJSON():""),
        			options:this.extraOptions||{}};
        	
        	var $el=$(this.el);
        	var $td=this.template(data)        	
        	//$("textarea",$td).wysihtml5();
        	
        	$el.html($td);
        	
        	if(Session.getUser()){
        		$(".private",$el).show();        		
        	}else{        		
        		$(".private",$el).hide();
        	} 
        	
            return this;
        }
	});
	
	var BaseModalView=BaseView.extend({		
		className:'modal',
		events: {
			'click .btn-close':		'close',
			'click .btn-primary':  	'save',
			'hidden':				'hidden'
			
	    },
	    postInitialize:function(){	    	
	    	this.model.on("sync",this.close,this);
	    	this.model.on("error",this.displayErrors,this);
	    },
        close:function(){$(this.el).modal('hide');},
        hidden:function(){$(this.el).remove();},
        save:function(e){
        	e.preventDefault();
        	this.clearErrors();
        	$(this.el).find("input,textarea").bindInput(this.model);
        	this.model.save({wait: true});        	
		},
		displayErrors:function(model, errors){
			var $el=$(this.el);
			$.each(errors,function(i,error){
				$el.displayError(error);
			});
		},
		clearErrors:function(){
			$(this.el).clearErrors();
		}
	});
	
	var CreatePostModalView=BaseModalView.extend({
		templateName:'#create-post-modal-template'							
	});
		
	var EditeProfileModalView=BaseModalView.extend({
		templateName:'#edite-profile-modal-template'
	});
	var ChangePasswordModalView=BaseModalView.extend({
		templateName:'#change-password-modal-template'		
	});
	
	var SignInView=BaseModalView.extend({
		templateName:'#sign-in-modal-template',
		save:function(e){			
        	e.preventDefault();
        	this.clearErrors();        	
        	this.model.login($("form",this.el).serializeObject());
        	this.model.on('login:success',function(){        		
				Backbone.history.navigate("/",true);        		
        	});
        	
		},
		extraOptions:{
			embedded:true
		}
			
		
	});
	
	var SignUpModalView=BaseModalView.extend({
		templateName:'#sign-up-modal-template',
		save:function(e){			
        	e.preventDefault();
        	this.clearErrors();
        	$(this.el).find("input,textarea").bindInput(this.model);
        	if(this.model.isValid()){
        		$(this.el).find("form").attr("action",this.model.url).submit();
        	}        	
		},
			
		
	});
	var SignUpView=SignUpModalView.extend({		
		extraOptions:{
			embedded:true
		}
	});
	
	
	var MainNavbarView=Backbone.View.extend({
		
		templateName:'#main-navbar-template',		
		classNmae:'main-navbar',
		
		initialize: function() {
            _.bindAll(this, 'render');
            this.template=_.template($(this.templateName).html());            
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
        	var post=new app.Post();
        	$(new CreatePostModalView({model:post}).render().el).modal();
        	post.on("sync",function(){
        		app.posts.add(post);
        		app.posts.selectPost(post.id);
        	});
        	
        },
	    editProfile:function(){
	    	$(new EditeProfileModalView({model:app.profile}).render().el).modal();	
	    },
        changePassword:function(){
        	$(new ChangePasswordModalView({model:new app.Password()}).render().el).modal();
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
		
	var PostView=BaseView.extend({
		templateName:'#post-template',
		tagName:'article',
		events: {			
			'click .edit':  	'editPost',
		},
		editPost:function(e){
			e.preventDefault();
			$(new CreatePostModalView({model:this.model}).render().el).modal();
			this.model.on("sync",function(){        		
				app.posts.selectPost(this.model.id);
			},this);
		}
	});
	var PostPreView=PostView.extend({
		templateName:'#post-preview-template',		
		events: {
			'click h1':  		'selectPost',
			'click .edit':  	'editPost',
	    },		
	    selectPost:function(e){
	    	e.preventDefault();
	    	this.options.posts.selectPost(this.model.id);
	    }
	    
	});
	
	var PostsView=Backbone.View.extend({		
		className:'posts unstyled',
		tagName:'ul',
		initialize: function() {
            _.bindAll(this, 'render','renderPost');
            this.collection.bind('reset', this.render);
            this.collection.bind('change', this.renderOnChange,this);

        },
        
        render: function() {     
        	this.collection.each(this.renderPost);
        	this.rendered=true;
            return this;
        },
        renderPost:function(post){
        	$(this.el).append($('<li></li>').append(new PostPreView({model:post,posts:this.collection}).render().el));	
        },
        renderOnChange:function(){
        	console.log("renderOnChange");
        }
              
	});

	var ProfileView=Backbone.View.extend({		
		template:_.template($('#profile-template').html()),
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
	var CalendarView=Backbone.View.extend({
		template:_.template($('#calendar-template').html()),
		className: 'calendar well clearfix',
		initialize: function() {
            _.bindAll(this, 'render');
            
        },
        render: function() {        	
        	$(this.el).html(this.template(util.wrap(this.collection.toJSON())));
        	this.createCalendar(this.$('.blogs-calendar')[0]);          	
            return this;
        },
        createCalendar:function(el){
        	YUI().use('calendar', function (Y) {
          	  Y.one('body').addClass('yui3-skin-sam');
          	  var calendar = new Y.Calendar({
          	          contentBox: el,    	         
          	          showPrevMonth: true,
          	          showNextMonth: true,
          	          }).render();
          	  calendar.selectDates(new Date());
          	  
          	  calendar.on("selectionChange", function (ev) {

          	      var newDate = ev.newSelection[0];

          	      console.log(newDate);
          	    });


          	});

        }
		
	});
	var TagsView=Backbone.View.extend({
		template:_.template($('#tags-template').html()),
		className: 'tags well clearfix',
		events: {
			'click .tag':  		'selectTag',									
	    },	
		initialize: function() {
            _.bindAll(this, 'render');
            this.collection.bind('reset', this.render);            
        },
        render: function() {        	
        	$(this.el).html(this.template(util.wrap(this.collection.toJSON())));
            return this;
        },
        selectTag:function(e){
        	e.preventDefault();        	
        	this.options.posts.filterByTag(e.target.innerText.replace("#",""));
        }
		
	});
	var TwoColumnsView=Backbone.View.extend({		
		template:_.template($('#two-columns-template').html()),			
		initialize: function() {
			_.bindAll(this, 'render');			           
		},
		render: function() {        	
			$(this.el).html(this.template());
			return this;
		}		
	});
	
	
	var AlertError=BaseView.extend({
		templateName:'#alert-error-template'
		
	})
	
	app.MainNavbarView=MainNavbarView;
	app.PostsView=PostsView;
	app.PostView=PostView;
	app.ProfileView=ProfileView;
	app.TagsView=TagsView;
	app.CalendarView=CalendarView;
	app.SignUpView=SignUpView;
	app.SignInView=SignInView;
	app.TwoColumnsView=TwoColumnsView;
	
});