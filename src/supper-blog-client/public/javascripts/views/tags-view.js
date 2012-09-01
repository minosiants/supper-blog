define(["jquery","underscore","backbone","util","text!/templates/tags.html"],
		function($,_,Backbone,util,tagsTemplate){
			return Backbone.View.extend({
				template:_.template(tagsTemplate),
				className: 'tags well clearfix',
				events: {
					'click .tag':  		'selectTag',									
			    },	
				initialize: function() {
		            _.bindAll(this, 'render');
		            this.collection.bind('reset', this.render);            
		        },
		        render: function() {        	
		        	$(this.el).html(this.template(util.wrap(this.collection.toJSON())));
		            return this;
		        },
		        selectTag:function(e){
		        	e.preventDefault();        	
		        	this.options.posts.filterByTag(e.target.innerText.replace("#",""));
		        }
				
			});
	
});

