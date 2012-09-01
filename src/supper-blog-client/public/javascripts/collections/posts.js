define(["jquery","backbone","models/post","app"],
		function($,Backbone,Post,app){			
			var Posts=Backbone.Collection.extend({
		  		model: Post,
		  		url: app.API_HOST+"/posts",
		  		initialize:function(){
		  			this.openWebSocket();
		  		},
		  		selectPost:function(id){  			
		  			this.trigger("post:selected",this.get(id)||this.fetchPost(id));
		  		},
				fetchPost:function(id){
					var post=new Post({id:id})
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
		                var post=new Post(data);
		                that.models.push(post);
		                that.trigger('change', post, that);
		            };            
		            postWebSocket.onmessage = receiveEvent;
				}
		  			
			});
			return new Posts();
		}

);