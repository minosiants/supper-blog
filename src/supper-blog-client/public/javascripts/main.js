requirejs.config({
		baseUrl:'assets/javascripts',
		paths: {
			underscore:'lib/underscore',
	    	backbone:'lib/backbone',
	    	jquery:'lib/jquery-1.7.1.min',
	    	bootstrap:'lib/bootstrap',
	    	yui:'lib/yui-base-min',
	    	calendarBase:'lib/calendar-base-min',
	    	calendar:'lib/calendar-min',
	    	text:'lib/text',
	    	moment:'lib/moment',
	    	serializeObject:'lib/jquery.serializeObject'
	    		
	    	
	    },
	  shim: {
	    underscore: {
	      exports: '_'
	    },
	    backbone: {
	      deps: ["underscore", "jquery"],
	      exports: "Backbone"
	    },
	    bootstrap:{
	    	deps: ["jquery"],
	    	exports: "bootstrap" 
	    },
	    yui:{
	    	//deps: ["calendarBase","calendar"],
	    	exports: "YUI"
	    },
	    moment:{
	    	exports:"moment"
	    },
	    serializeObject:{
	    	exports:"serializeObject"
	    }
	  }
	});



requirejs(["jquery",
         "routers/supper-blog",
         "collections/posts",
         "models/profile",
         "collections/tags"], function($,supperBlogRouter,posts,profile,tags) {
	var uri=$("#uri").val();
	supperBlogRouter.navigate(uri, {trigger: true});			             
	if(posts.length==0 && (uri!=="/signup"||uri!=="/signin")){
		posts.fetch();
		profile.fetch();
		tags.fetch();
	}	
});