define(["jquery","moment","app"],function($,moment,app){
	
	var Util=function(){
	
	}
	Util.prototype.userName=function(name,surname,username,data){
		if(name==="undefined"||surname==="undefined"){
			return username;
		}
		return $.trim((name || "") +" "+ (surname || ""));  	 
		
	};
	
	Util.prototype.stripHost=function(url){
		return url.replace(app.API_HOST,"");
	};
	Util.prototype.date=function(ms){
		return moment(ms).format("MMM Do 'YY - h:mm");
	};
	Util.prototype.validateEmptyFields=function(obj){
		var errors=[];
		$.each( obj, function(field, value){		    
		    if(_.isEmpty(value)){
		    	errors.push({field:field,message:"Should not be empty"})
		    }
		});
		return errors;
	}
	Util.prototype.wrap=function(obj){		
		return $.extend({data:$.extend(obj,{util:new Util()})},{});
	};
	return new Util();
});