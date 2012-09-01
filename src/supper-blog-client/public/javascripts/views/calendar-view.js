define(["jquery","underscore","backbone","yui","util","text!/templates/calendar.html"],
		function($,_,Backbone,YUI,util,calendarTemplate){
			console.log(YUI)
			var CalendarView=Backbone.View.extend({
				template:_.template(calendarTemplate),
				className: 'calendar well clearfix',
				initialize: function() {
		            _.bindAll(this, 'render');
		            
		        },
		        render: function() {        	
		        	$(this.el).html(this.template(util.wrap(this.collection.toJSON())));
		        	this.createCalendar(this.$('.blogs-calendar')[0]);          	
		            return this;
		        },
		        createCalendar:function(el){
		        	YUI().use('calendar', function (Y) {
		          	  Y.one('body').addClass('yui3-skin-sam');
		          	  var calendar = new Y.Calendar({
		          	          contentBox: el,    	         
		          	          showPrevMonth: true,
		          	          showNextMonth: true,
		          	          }).render();
		          	  calendar.selectDates(new Date());
		          	  
		          	  calendar.on("selectionChange", function (ev) {
		
		          	      var newDate = ev.newSelection[0];
		
		          	      console.log(newDate);
		          	    });
		
		
		          	});
		
		        }
				
			});
			return CalendarView; 
});
