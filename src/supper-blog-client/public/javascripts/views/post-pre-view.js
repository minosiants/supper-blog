define(["views/post-view","text!/templates/postPreview.html"],
		function(PostView,postPreviewTemplate){
			return PostView.extend({
				templateSource:postPreviewTemplate,		
				events: {
					'click h1':  		'selectPost',
					'click .edit':  	'editPost',
			    },		
			    selectPost:function(e){
			    	e.preventDefault();
			    	this.options.posts.selectPost(this.model.id);
			    }
			    
			});
});

