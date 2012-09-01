define(["jquery","views/base-view","views/create-post-view","text!/templates/post.html"],
		function($,BaseView,CreatePostModalView,postTemplate){
			return BaseView.extend({
				templateSource:postTemplate,
				tagName:'article',
				events: {			
					'click .edit':  	'editPost',
				},
				editPost:function(e){
					e.preventDefault();
					$(new CreatePostModalView({model:this.model}).render().el).modal();
					this.model.on("sync",function(){        		
						posts.selectPost(this.model.id);
					},this);
				}
			});
});

