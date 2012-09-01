define(["jquery","backbone","models/base-model","app"],
		function($,Backbone,BaseModel,app){		
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
			return Post;	
});
