define(["models/base-model"],
		function(BaseModel){
			var Password=BaseModel.extend({
				url:'/settings/password',		
			});
			return Password;
});