define(["jquery","views/base-view"],
		function($,BaseView){
			var BaseModalView=BaseView.extend({		
				className:'modal',
				events: {
					'click .btn-close':		'close',
					'click .btn-primary':  	'save',
					'hidden':				'hidden'
					
			    },
			    postInitialize:function(){	    	
			    	this.model.on("sync",this.close,this);
			    	this.model.on("error",this.displayErrors,this);
			    },
		        close:function(){$(this.el).modal('hide');},
		        hidden:function(){$(this.el).remove();},
		        save:function(e){
		        	e.preventDefault();
		        	this.clearErrors();
		        	$(this.el).find("input,textarea").bindInput(this.model);
		        	this.model.save({wait: true});        	
				},
				displayErrors:function(model, errors){
					var $el=$(this.el);
					$.each(errors,function(i,error){
						$el.displayError(error);
					});
				},
				clearErrors:function(){
					$(this.el).clearErrors();
				}
			});
			return BaseModalView;
	
});