define(["views/base-modal-view","text!/templates/editeProfile.html"],
		function(BaseModalView,editeProfileTemplate){
			return BaseModalView.extend({
				templateSource:editeProfileTemplate
			});
});