define(["jquery","underscore","backbone","models/session","util","views/jquery-plugins-view"],
		function($,_,Backbone,Session,util){
	
			var BaseView=Backbone.View.extend({		
				initialize: function() {
		            _.bindAll(this, 'render');
		            this.template=_.template(this.templateSource);
		            if(this.model){
		            	this.model.bind('change', this.render);
		            }
		            var that=this;
		            Session.on('change:user',function(session){
		            	that.render();
		            });
		            if(this.postInitialize){
		            	this.postInitialize();
		            }
		            
		        },
		        render: function() {
		        	var data={data:this.model ? this.model.toJSON() : (this.collection ? this.collection.toJSON():""),
		        			options:this.extraOptions||{}};
		        	data.data.util=util;		        	
		        	var $el=$(this.el);
		        	var $td=this.template(data)        	
		        	//$("textarea",$td).wysihtml5();
		        	
		        	$el.html($td);
		        	
		        	if(Session.getUser()){
		        		$(".private",$el).show();        		
		        	}else{        		
		        		$(".private",$el).hide();
		        	} 
		        	
		            return this;
		        }
			});
			return BaseView;
	
});

