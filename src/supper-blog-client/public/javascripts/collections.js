;(function(){
	app=window.app||{};
	console.log(app.API_HOST);	
	var Posts=Backbone.Collection.extend({
  		model: app.Post,
  		url: app.API_HOST+"/posts",
  		initialize:function(){
  			this.openWebSocket();
  		},
  		selectPost:function(id){  			
  			this.trigger("post:selected",this.get(id)||this.fetchPost(id));
  		},
		fetchPost:function(id){
			var post=new app.Post({id:id})
			post.fetch();
			return post;
		},
		filterByTag:function(tag){
			this.url=app.API_HOST+"/posts/tags/"+tag;
			var that=this;
			this.fetch({success:function(){
				that.trigger("posts:result");
			}});
			
		},
		openWebSocket:function(){
			var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
		    var postWebSocket = new WS(app.WS_HOST+"/ws/posts");
		    var that=this;
		    var receiveEvent = function(event) {
                console.log(event);
		    	var data = JSON.parse(event.data);
                
                if(data.error) {
                	postWebSocket.close();
                    return;
                } 
                var post=new app.Post(data);
                that.models.push(post);
                that.trigger('change', post, that);
            };            
            postWebSocket.onmessage = receiveEvent;
		}
  			
	});
	var CallendarPosts=Backbone.Collection.extend({
  		model: app.Post,
  		url: app.API_HOST+"/posts"
  			
	});
	var Tags=Backbone.Collection.extend({
  		model: app.Tag,
  		url: app.API_HOST+"/tags"
  			
	});

	app.posts=new Posts();
	app.callendarPosts=new CallendarPosts();
	
	app.tags=new Tags();
})();


		    

