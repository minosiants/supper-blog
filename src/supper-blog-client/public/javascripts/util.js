$(function(){
	app=window.app||{}
	var Util=function(){
	
	}
	Util.prototype.userName=function(name,surname,username,data){
		if(name==="undefined"||surname==="undefined"){
			return username;
		}
		return $.trim((name || "") +" "+ (surname || ""));  	 
		
	};
	Util.prototype.wrap=function(obj){
		return $.extend({data:obj},{});
	};
	Util.prototype.stripHost=function(url){
		return url.replace(app.API_HOST,"");
	};
	Util.prototype.date=function(ms){
		return moment(ms).format("MMM Do 'YY - h:mm");
	};
	app.util=new Util();
});