define(["models/base-model","app"],
		function(BaseModel,app){		
			var Profile=BaseModel.extend({
				url:app.API_HOST+'/settings/profile',
		
			});
			return new Profile();
			
});