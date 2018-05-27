//Validating update channel type form fields on submit button click

(function($, W, D) {
	var JQUERY4U = {};

		JQUERY4U.UTIL = {
			setupFormValidation : function() {
				jQuery.validator.addMethod("numbers", function( value, element ) {
					return this.optional(element)
					|| /^[0-9]+$/i.test(value);
			    });
				$.validator.addMethod('minStrict', function (value,e1,param) {
				    return value >= param;
				});
				$("#channelTypesUpdateForm").validate({
					rules : {
						name : {
						      required : true
							  },
						
						status : {
							      required : true
							  },
						parameters : {
							      required : true
							  },
						
						backlog : {
							  numbers : true,
						      minStrict : 1
						      
						     }
					
					},

					messages : {
						name : {
			                   required :"Please Enter Channel Type Name"
		                  },
		              
		                status : {
								required : "Please Select Status",
							},
		                parameters : {
			                   required :"Mandatory field",
		                  },
						backlog : {
								 numbers : "Please Enter Numeric value",
								 minStrict : "Please Enter Numeric value Except Zero"
								
						}
						

					},
					submitHandler : function(form) {
						$("#cron_error").text(" ");
						var schedule=$("#cron_expression").val();
						var dtArray = schedule.split(" ");					
						var dtMonth=dtArray[3];
						var dtDay=dtArray[2];	
						var dtYear=dtArray[4];
						if(dtYear=='*'){
							dtYear = (new Date).getFullYear();
						}
						if (dtMonth == 2)
								  {
								 var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
							     if (dtDay> 29 || (dtDay ==29 && !isleap))
							    	 {
							    	 $("#cron_error").text("Please Select Valid Date for Selected Month");
							    	 return false;
							    	 }
								          
							  }
						else if(((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31))
							{
							$("#cron_error").text("Please Select Valid Date for Selected Month");
								
							return false;
							}
						else{
							form.submit();
						}
					}
				});
				
			}
		};

		//when the dom has loaded setup form validation rules
		$(D).ready(function($) {
			JQUERY4U.UTIL.setupFormValidation();
		});
	})(jQuery, window, document);

$(document).ready(function(){
	var expression=$("#cron_expression").val();
	if(expression==""){
		expression="* * * * *";
		$("#cron_expression").val("* * * * *");
	}
	
	//Generating Cron Expression
$('#cron_expression').click(function(){
		
		
		$('#ui-cronexpression-div').css("display","block");
		$('#selector').empty();
		$('#selector').jqCron({
	        enabled_minute: true,
	        multiple_dom: true,
	        multiple_month: true,
	        multiple_mins: true,
	        multiple_dow: true,
	        multiple_time_hours: true,
	        multiple_time_minutes: true,
	        default_period: 'week',
	        default_value: '* * * * *',
	        no_reset_button: false,
	        lang: 'en',
			bind_to: $('#cron_expression'),
	        bind_method: {
	            set: function($element, value) {
	                $('#cron_expression').val(value);
	            }
	        }
			
	    });
		
		});
	$('.jqCron-container .jqCron-cross').live("click",function(){
		$('#ui-cronexpression-div').css("display","none");
	});
$("#submit").click(function(){
				$(".errorblock").empty();
	});
});

