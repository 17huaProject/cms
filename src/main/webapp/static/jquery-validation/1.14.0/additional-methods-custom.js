	


	//手机号
	$.validator.addMethod("phone", function(value, element) {
		regex = /^1[0-9]{10}$/g;
		return this.optional(element) || regex.test(value);
	}, $.validator.format("请输入正确的手机号"));
	
	
	//身份证号
	$.validator.addMethod("idcard", function(value, element) {
		regex = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/g;
		return this.optional(element) || regex.test(value);
	}, $.validator.format("请输入正确的身份证号"));

	
	
	
	
	
	
	
	
	
	
	
	
	