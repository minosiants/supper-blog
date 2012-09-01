define(["views/base-modal-view","text!/templates/createPost.html"],
		function(BaseModalView,createPostTemplate){
			return BaseModalView.extend({
				templateSource:createPostTemplate							
			});
});