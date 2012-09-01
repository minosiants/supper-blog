define(["jquery","underscore","backbone","views/post-pre-view"],
		function($,_,Backbone,PostPreView){
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
		        renderOnChange:function(post){
		        	var postHtml=$('<li></li>').append(new PostPreView({model:post,posts:this.collection}).render().el);
		            $(postHtml)
				        .hide()
				        .css('opacity',0.0)
				        .prependTo(this.el)
				        .slideDown('slow')
				        .animate({opacity: 1.0});        	
		        }
		              
			});
			return PostsView;

});


