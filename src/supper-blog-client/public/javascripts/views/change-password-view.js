define(["views/base-modal-view","text!/templates/changePassword.html"],
		function(BaseModalView,changePasswordTemplate){
			return BaseModalView.extend({
				tmplateSource:changePasswordTemplate		
			});
});

