define(["backbone"],
		function(Backbone){
			var BaseModel=Backbone.Model.extend({
				setErrors:function(errors){
					if(errors){
						this.trigger('error', this, errors);
					}
				}	
				
			});
			return BaseModel;	
});