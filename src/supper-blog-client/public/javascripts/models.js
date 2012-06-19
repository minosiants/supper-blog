;(function(){
	app=window.app||{};
	
	var BaseModel=Backbone.Model.extend({
		setErrors:function(errors){
			if(errors){
				this.trigger('error', this, errors);
			}
		}	
		
	});
	
	var validateEmptyFields=function(obj){
		var errors=[];
		$.each( obj, function(field, value){		    
		    if(_.isEmpty(value)){
		    	errors.push({field:field,message:"Should not be empty"})
		    }
		});
		return errors;
	}
	
	var Post=BaseModel.extend({
		urlRoot:app.API_HOST+"/posts",
		title:function(){
			return this.get('title');
		},		
		initialize:function(){
			 $.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
			        options.xhrFields = {
			          withCredentials: true
			        };
			       
			      });
		},
		validate:function(attributes){
			var mergedAttributes = _.extend(_.clone(this.attributes), attributes)
			var errors=[];
			if(_.isEmpty(mergedAttributes.title)){				
				errors.push({field:"title",message:"Should not be empty"})
			}
			if(_.isEmpty(mergedAttributes.content)){				
				errors.push({field:"content",message:"Should not be empty"})
			}
			if(errors.length>0){
				return errors;
			}
		}
	});
	Post.create=function(id){
		var p=new Post({id:id})	
		p.fetch();
		return p;
	}
	
	var Profile=BaseModel.extend({
		url:app.API_HOST+'/settings/profile',
		
	});
	var Tag=BaseModel.extend({		
		
	});
	var Password=BaseModel.extend({
		url:'/settings/password',		
	});
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
	var SignIn=BaseModel.extend({
		url:'/signin',
		validate:function(attributes){
			var mergedAttributes = _.extend(_.clone(this.attributes), attributes)			
			var errors=validateEmptyFields(mergedAttributes);	
			
			if(errors.length>0){
				this.trigger('error', this, errors);
				return errors;
			}
			return ;
		}
	});
	
	app.Post=Post;
	app.profile=new Profile();
	app.Tag=Tag
	app.Password=Password
	app.SignUp=SignUp
	app.SignIn=SignIn
})();