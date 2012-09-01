define(["underscore","models/base-model"],
		function(_,BaseModel){
				var SignUp=BaseModel.extend({
					url:'/signup',		
					validate:function(attributes){
						var mergedAttributes = _.extend(_.clone(this.attributes), attributes)			
						var errors=validateEmptyFields(mergedAttributes);	
						if(mergedAttributes.password!==mergedAttributes.confirm){
							errors.push({field:"password",message:"Does not mutch"})
						}
						if(errors.length>0){
							this.trigger('error', this, errors);
							return errors;
						}
						return ;
					}
				});	
				return SignUp;
});