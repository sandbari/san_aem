var filesList;
var totalFileSize;
var fileNameComp;
var count = 0;
var environment="";
var userPoolId;
var congnitoIdPool;
var cognitoAWSRegion;
var awsAppClientId;

function IsNumeric3(evt) {
	var ctyCode="";
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode != 43 && charCode > 31 && (charCode < 48 || charCode > 57)){
       	$('#p_coutryCode').val("+"+$('#p_coutryCode').val().replace(/\D/g, ''));
    	return false;
    }
    ctyCode = $('#p_coutryCode').val().replace(/\D/g, '');
    $('#p_coutryCode').val("+"+ctyCode)
	return true;
}

function removeFile(btn){
	var row = btn.parentNode.parentNode;
  	row.parentNode.removeChild(row);
}

function dayValidation(evt){

   var ASCIICode = (evt.which) ? evt.which : evt.keyCode
   if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57)){
       return false;
   }

    if($("#day").val() < 1 || $("#day").val() > 31){
        return false;
    }
    return true;


}

 function dialog() {

        filesList = [];
        var fi = document.getElementById('F_Id');

        var fileInit = fi.files.item(0).size;
        fileInit = (fileInit / 1024);

        if (totalFileSize < 9216) {

            if (fileInit < 5120) {
                filesList.push(fi.files.item(0));
                var fileListSize = (filesList.length);
                if (filesList.length > 0) {

                    for (const element of filesList) { // You can use `let` instead of `const` if you like
                        var fi = element;
                        var fname = fi.name; // THE NAME OF THE FILE.
                        var fsize = fi.size; // THE SIZE OF THE FILE.
                        let fileSize = (fsize / 1024);
                        totalFileSize = (totalFileSize + fileSize);

                        if (count < 5) {
                            if (fileNameComp !== fname) {
                                // SHOW THE EXTRACTED DETAILS OF THE FILE.
                                document.getElementById('uploadIdList').innerHTML =
                                    document.getElementById('uploadIdList').innerHTML + '<br /> ' +
                                        '<div class="row "style="overflow-x:auto;">'+
										'<table style="width:100%">'+
                                            '<colgroup>'+
                                            '<col style="width: auto" />'+
                                            '<col style="width: 33.3333%" />'+
                                           '<col style="width: 33.3333%" />'+
                                          '</colgroup>'+
                                            '<tr>'+
                                                '<td class="table_content">'+fname+'</td>'+
                                                   '<td class="table_content">'+fsize+'</td>'+
                                                       '<td class="table_content"><button class="btn-primary" id="target" onclick="removeFile(this)" value="'+fname+'"><span class="btn-triangle-rte" >Delete</span> </button></td>'+
                                                           '</tr>'+
                                                       			'</table></div>';


                                fileNameComp = fname;
                                //count = (count+1);
                                validateFile("#fileUploadBtn", "#fileUpload_error", "");
                            }
                        } else {
                            validateFile("#fileUploadBtn", "#fileUpload_error", "maxFiles");
                        }

                        validateFile("#fileUploadBtn", "#fileUpload_error", "");
                    }

                }

            } else {
                validateFile("#fileUploadBtn", "#fileUpload_error", "fileSizeMore");
            }
        } else {
            validateFile("#fileUploadBtn", "#fileUpload_error", "totalFileSize")

        }
    }

 function validateFile(id, error, fileError) {

        if (fileError == 'fileSizeMore') {
            $('span#fileUpload_error').text("Please upload the file less than 5MB");
            $(error).show();
        } else if (fileError == 'totalFileSize') {
            $('span#fileUpload_error').text("Total File size is less than 20 MB");
            $(error).show();
        } else if (fileError == 'maxFiles') {
            $('span#fileUpload_error').text("Total File size is less than 20 MB");
            $(error).show();
        } else if (fileError == 'noFiles') {
            $('span#fileUpload_error').text("Please upload a file");
            $(error).show();
        } else {
            $(error).hide();

        }

    }

function enableSubmitBtn(){

    if($('#p_firstName').val().length > 0 && $('#p_lastName').val().length > 0 &&
        $('#p_coutryCode').val().length > 0 && $('#p_phoneNumber').val().length > 0 &&
            $('#email').val().length > 0 && $('#d_firstName').val().length > 0 &&
                $('#d_lastName').val().length > 0 && $('#month').val() !="select" &&
                    $('#year').val().length > 0 && $('#day').val().length > 0 ){
                        $('#submitId').removeAttr("disabled");
                    }else{
                        $('#submitId').attr("disabled", "disabled");
                    }
}


$(document).ready(function() {


   	var hostName = window.location.hostname;
    var environment = hostName.split(".")[0];
	$('#submitId').attr("disabled", "disabled");
    fileNameComp = "";
    var showSignIn = false;
    filesList = [];
    totalFileSize = 0;
    $("#uploadIdList").empty();
    $("#F_Id").val('');
    $("#p_coutryCode").val("+1");

    count = 0;

     $('#day').keyup(function(event){
       if(event.which != 8 && isNaN(String.fromCharCode(event.which))){
           $('#day').val($('#day').val().replace(/\D/g, ''));
           var dayValue= $('#day').val();
           if(dayValue > 31){
               $('#day').val("")
               return false;
           }
           return false; //stop character from entering input
       }

         var dayVal= $('#day').val();
         if(dayVal > 31){
             $('#day').val("")
             return false;
         }
         return true;

   });

    $('#b_day').keyup(function(event){
       if(event.which != 8 && isNaN(String.fromCharCode(event.which))){
           $('#b_day').val($('#b_day').val().replace(/\D/g, ''));
           var dayValue= $('#b_day').val();
           if(dayValue > 31){
               $('#b_day').val("")
               return false;
           }
           return false; //stop character from entering input
       }

         var dayVal= $('#b_day').val();
         if(dayVal > 31){
             $('#b_day').val("")
             return false;
         }
         return true;

   });

    $('#year').keyup(function(event){
        var currentYear= new Date().getFullYear();
       if(event.which != 8 && isNaN(String.fromCharCode(event.which))){
           $('#year').val($('#year').val().replace(/\D/g, ''));
           var yearValue= $('#year').val();
           if(yearValue > currentYear){
               $('#year').val("")
               return false;
           }
           return false; //stop character from entering input
       }
         var yearVal= $('#year').val();
         if(yearVal > currentYear){
             $('#year').val("")
             return false;
         }
         return true;
   });

    $('#b_year').keyup(function(event){
        var currentYear= new Date().getFullYear();
       if(event.which != 8 && isNaN(String.fromCharCode(event.which))){
           $('#b_year').val($('#b_year').val().replace(/\D/g, ''));
           var yearValue= $('#b_year').val();
           if(yearValue > currentYear){
               $('#b_year').val("")
               return false;
           }
           return false; //stop character from entering input
       }
         var yearVal= $('#b_year').val();
         if(yearVal > currentYear){
             $('#b_year').val("")
             return false;
         }
         return true;

   });
    $('#ssn_first').keyup(function(evt){
        var regExp = "^\\d+$";
       	var charCode = (evt.which) ? evt.which : evt.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57)){
            $('#ssn_first').val($('#ssn_first').val().replace(/\D/g, ''));
            if($('#ssn_first').val().match(regExp)){
                if(this.value.length==$(this).attr("maxlength")){
                    document.getElementById("ssn_second").focus();
                }
            }
            return false;
        }
        $('#ssn_first').val($('#ssn_first').val().replace(/\D/g, ''));
        if($('#ssn_first').val().match(regExp)){
            if(this.value.length==$(this).attr("maxlength")){
           		document.getElementById("ssn_second").focus();
        	}

        }

    });

    $('#ssn_second').keyup(function(evt){
        var regExp = "^\\d+$";
       	var charCode = (evt.which) ? evt.which : evt.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57)){
            $('#ssn_second').val($('#ssn_second').val().replace(/\D/g, ''));
            if($('#ssn_second').val().match(regExp)){
                if(this.value.length==$(this).attr("maxlength")){
                    document.getElementById("ssn_third").focus();
                }

            }
            return false;
        }

        $('#ssn_second').val($('#ssn_second').val().replace(/\D/g, ''));
        if($('#ssn_second').val().match(regExp)){
            if(this.value.length==$(this).attr("maxlength")){
           		document.getElementById("ssn_third").focus();
        	}

        }

    });

     $('#memberId').keyup(function(evt){

         if($('#memberId').val().length == 9){
             enableSubmitBtn()
         }
    });

     $('#ssn_third').keyup(function(evt){
        var regExp = "^\\d+$";
       	var charCode = (evt.which) ? evt.which : evt.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57)){
            $('#ssn_third').val($('#ssn_third').val().replace(/\D/g, ''));
			if($('#ssn_third').val() > 0 && $('#ssn_third').val().length == 4){
                if($('#ssn_third').val().match(regExp)){
                    if(this.value.length==$(this).attr("maxlength")){
                        document.getElementById("memberId").focus();
                    }
                }
                enableSubmitBtn();
            }
            return false;
        }
        $('#ssn_third').val($('#ssn_third').val().replace(/\D/g, ''));
        if($('#ssn_third').val().match(regExp)){
            if(this.value.length==$(this).attr("maxlength")){
           		document.getElementById("memberId").focus();
        	}
        }
         if($('#ssn_third').val() > 0 && $('#ssn_third').val().length == 4){
             enableSubmitBtn();
         }


    });


    $("div.sc-inlrYM.ejhNYg>button").click(function() {

        if ($("#password").attr("type") === "password") {
            $("#password").attr("type", "text");
        } else {
            $("#password").attr("type", "password");
        }
    });

     /*$('#p_firstName, #p_lastName').on("input", function() {
        var firstName = $("#p_firstName").val();
        var lastName = $("#p_lastName").val();
        if (username.length > 4 && password) {
            $('#button>div.mainButtonCorner').show();
            $('#button').css('color', '#FFFFFF ');
            $('#button').css('background-color', '#486D90 ');
            $('#button').prop('disabled', false);
        } else {
            $('#button>div.mainButtonCorner').hide();
            $('#button').css('color', '#49494A ');
            $('#button').css('background-color', '#D2D2D2 ');
            $('#button').prop('disabled', true);
        }
    });*/
    $('#p_phoneNumber').on("input", function() {
        var phoneNumberVal = $("#p_phoneNumber").val();
        if (phoneNumberVal.length <= 10) {
            $('#button>div.mainButtonCorner').show();
            $('#button').css('color', '#FFFFFF ');
            $('#button').css('background-color', '#486D90 ');
            $('#button').prop('disabled', false);
        } else {
            $('#button>div.mainButtonCorner').hide();
            $('#button').css('color', '#49494A ');
            $('#button').css('background-color', '#D2D2D2 ');
            $('#button').prop('disabled', true);
        }
    });

    $('#p_firstName').click(
        function() {
            validate("#p_firstName", "#yourName_error");
        });
    $('#p_lastName').click(
        function() {
            validate("#p_lastName", "#yourName_error");
        });


    $('#d_firstName').click(
        function() {
            validate("#d_firstName", "#name_error");
        });

    $('#d_lastName').click(
        function() {
            validate("#d_lastName", "#name_error");
        });

    $('#p_phoneNumber').click(
        function() {
            validate("#p_phoneNumber", "#phoneNumber_error");
        });

    $('#email').click(
        function() {
            validateEmail("#email", "#email_error");
        });

    $('#day').click(
        function() {
            validate("#day", "#date_error");
        });

    $('#year').click(
        function() {
            validate("#year", "#date_error");
        });

    $('#memberId').click(
        function() {
            validate("#memberId", "#ssnormember_error");
        });


    $('#fileUploadBtn').click(
        function() {
            var fileupload = $("#F_Id");
            $("#F_Id").click();
            fileupload.change(function() {
                dialog();
            })

        });

    function validateEmail(id, error) {
        $(id).on("focus", function() {
            if ($(this).val() == '') {
                $(this).css('border', '2px solid #004a98');
                showSignIn = true;
            } else {
                $(this).css('border', '1px solid #004a98');
                showSignIn = true;
            }
        }).on("blur", function() {
            if ($(id).val() == '') {
                $(this).css('border', '2px solid #e60000');
                $('span#email_error').text("Please enter a valid email address");
                $(error).show();
            } else {
                $(this).css('border', '1px solid #d2d2d2');
                basicValidationforUsername(id, "#email_error")
            }

        });
    }

    $('input').keydown(function(e) {
        enableSubmitBtn();
        var code = e.keyCode || e.which;
        $(this).css('border', '');
        if (code == '9') {
            let attrValue = "#" + $(this).attr('id');
            if (attrValue == '#p_firstName') {
                if ($(this).val() == '') {
                    validate(attrValue, "#yourName_error");
                }
            }

            if (attrValue == '#p_lastName') {
                if ($(this).val() == '') {
                    validate(attrValue, "#yourName_error");
                }
            }

            if (attrValue === "#p_coutryCode") {
                validate(attrValue, "#countryCode_error");
            }


            if (attrValue == "#p_phoneNumber") {
                if($(attrValue).val().length == 10){
					if($('#phoneNumber_error').is(":visible")){
                          $('#Input_fieldPhoneNumber__2Mqft').hide();
                     } else{
                          $('#phoneNumber_error').show();
                     }

                }else{
                	validate(attrValue, "#phoneNumber_error");
                }

            }
            if (attrValue == '#email') {
                validateEmail(attrValue, "#email_error");
            }

            if (attrValue == '#d_firstName' || attrValue == '#d_lastName') {
                if ($(this).val() == '') {
                    validate(attrValue, "#name_error");
                }
            }

            if (attrValue == '#day') {
                validate(attrValue, "#date_error");
            }

            if (attrValue == '#year') {
                validate(attrValue, "#date_error");
            }

            var ssn = 0;
            if ($("#ssn_first").val().length > 0 && $("#ssn_second").val().length > 0 && $("#ssn_third").val().length > 0) {
                ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();

            }

            if (attrValue == '#ssn_third' && ssn != 0) {
                validate(attrValue, "#ssnormember_error");
            }

            if (attrValue == '#memberId' && ssn == 0) {
                validate(attrValue, "#ssnormember_error");
            }

        }
    });

    // validation on fields
    function validate(id, error) {

        let p_firstNameValue = $("#p_firstName").val();
        let lastNameValue = $(id).val();
        let d_firstNameValue = $("#d_firstName").val();

        $(id).on("focus", function() {
            if ($(this).val() == '') {
                $(this).css('border', '2px solid #004a98');
                showSignIn = true;
            } else {
                $(this).css('border', '1px solid #004a98');
                showSignIn = true;
            }

        }).on("blur", function() {

            if ($(this).val() == '') {
                $(this).css('border', '2px solid #e60000');

                if ("#p_lastName" == id && p_firstNameValue == '') {
                    $('span#yourName_error').text("Please enter your first and last name");
                } else if (id == '#p_firstName') {
                    $('span#yourName_error').text("Please enter your first name");
                } else if (id == '#p_lastName') {
                    $('span#yourName_error').text("Please enter your last name");
                }

                if (id == "#p_coutryCode") {
                    if ($(id).val() == '') {
                        $('span#countryCode_error').text("Please enter a country code");
                    }else{
                        $(this).css('border', '1px solid #d2d2d2');
                    	basicValidationforUsername(id, error);
                    }
                }


                if (id == '#p_phoneNumber') {
                    if ($(id).val() == '') {
                        $('span#phoneNumber_error').text("Please enter a valid phone number");
                    }else{
                        $(this).css('border', '1px solid #d2d2d2');
                    	basicValidationforUsername(id, error);
                    }
                }

                if ("#d_lastName" == id && d_firstNameValue == '') {
                    $('span#name_error').text("Please enter decedant's first and last name");
                } else if (id == '#d_firstName') {
                    $('span#name_error').text("Please enter decedant's first name");
                } else if (id == '#d_lastName') {
                    $('span#name_error').text("Please enter decedant's last name");
                }

                if ("#day" == id || "#year" == id) {
                    $('span#date_error').text("Please enter a valid date of death");
                }

                var ssn = 0;
                if ($("#ssn_first").val().length > 0 && $("#ssn_second").val().length > 0 && $("#ssn_third").val().length > 0) {
                    ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();


                }

                if (id == "#memberId" && ssn == 0) {
                    $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                } else {
                    $(this).css('border', '1px solid #d2d2d2');
                    basicValidationforUsername(id, error);
                }
                $(error).show();
            } else {
                $(this).css('border', '1px solid #d2d2d2');
                basicValidationforUsername(id, error);
            }
        });
    }

    function basicValidationforUsername(id, error) {

        if (id == "#day") {
            if (/^\+?\d+$/.test($(id).val())) {
                $(error).hide();
                showSignIn = true;
            } else {
                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();
            }
        }

        if (id == "#year") {

            if (/^\+?\d+$/.test($(id).val()) && $("#day").val() == '') {
                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();

            } else if (/^\+?\d+$/.test($(id).val()) && $("#day").val() > 0 && $("#month").val() == "select") {

                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();
            } else if ($(id).val() == '' && $("#day").val() > 0 && $("#month").val() == "select") {

                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();
            } else if (/^\+?\d+$/.test($(id).val()) && $("#day").val() > 0 && $("#month").val() == "select") {

                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();
            } else {
                $(error).hide();
                showSignIn = true;
            }
        }

        if (id == "#p_coutryCode") {
            if (/^\+?\d+$/.test($(id).val())) {
                $(error).hide();
                showSignIn = true;
            } else {
                $(this).css('border', '2px solid #e60000');
                $('span#countryCode_error').text("Please enter a valid country code");
                $(error).show();
            }
        }

        if (id == "#p_phoneNumber") {

            if ($(id).val().length == 10) {
                $('.Input_fieldPhoneNumber__2Mqft').hide();
            	showSignIn = true;
            } else {
                $(this).css('border', '2px solid #e60000');
                 $('.Input_fieldPhoneNumber__2Mqft').show();
                $('span#phoneNumber_error').text("Please enter a valid phone number");
                 $(error).show();
            }
        }

        if (id == "#email") {
            if ($(id).val().length > 3) {
                let emailValue = $(id).val();
                if (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(emailValue)) {
                    $(".Input_fieldEmail__2Mqft").hide();
                    showSignIn = true;
                } else {
                    $(this).css('border', '2px solid #e60000');
                    $('span#email_error').text("Please enter a valid email address");
                    $(".Input_fieldEmail__2Mqft").show();
                }

            } else {
                $(this).css('border', '2px solid #e60000');
                $('span#email_error').text("Please enter a valid email address");
                $(error).show();
            }
        }

        if (id == "#memberId") {
            var ssn = 0;
            if ($("#ssn_first").val().length > 0 && $("#ssn_second").val().length > 0 && $("#ssn_third").val().length > 0) {
                ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
            }

            if (ssn > 0 && ssn.length == 9) {
                $(error).hide();
                showSignIn = true;
            }else if($(id).val() > 0 && $(id).val().length == 9){
                $(error).hide();
                showSignIn = true;
            }
            else {
                $(this).css('border', '2px solid #e60000');
                $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                $(error).show();
            }
        }
        if(id == '#d_lastName' || id == '#d_firstName'){
            if ($("#d_lastName").val() == '' && $("#d_firstName").val() == '') {
                $(this).css('border', '2px solid #e60000');
                $('span#name_error').text("Please enter decedant's first and last name");
                $(error).show();
            }else if(id == "#d_lastName" && $("#d_lastName").val() == ''){
                $(this).css('border', '2px solid #e60000');
                $('span#name_error').text("Please enter decedant's last name");
                $(error).show();
            }else if(id == "#d_lastName" && $("#d_lastName").val().length > 0  && $("#d_firstName").val() == ''){
                $(this).css('border', '2px solid #e60000');
                $('span#name_error').text("Please enter decedant's first name");
                $(error).show();
            }else if(id == "#d_firstName" && $("#d_firstName").val() == ''){
                $(this).css('border', '2px solid #e60000');
                $('span#name_error').text("Please enter decedant's first name");
                $(error).show();
            }else {
                $(error).hide();
                showSignIn = true;
            }
        }

		 if(id == '#p_lastName' || id == '#p_firstName'){
             if ($("#p_lastName").val() == '' && $("#p_firstName").val() == '') {
            $(this).css('border', '2px solid #e60000');
            $('span#yourName_error').text("Please enter your first and last name");
            $(error).show();
        }else if (id == '#p_lastName' && $("#p_lastName").val() == ''){
            $(this).css('border', '2px solid #e60000');
            $('span#yourName_error').text("Please enter your last name");
            $(error).show();

        }else if (id == '#p_lastName' && $("#p_lastName").val().length > 0 && $("#p_firstName").val() == ''){
            $(this).css('border', '2px solid #e60000');
            $('span#yourName_error').text("Please enter your first name");
            $(error).show();

        }else if (id == '#p_firstName' && $("#p_firstName").val() == ''){
            $(this).css('border', '2px solid #e60000');
            $('span#yourName_error').text("Please enter your first name");
            $(error).show();

        } else {
            $(error).hide();
            showSignIn = true;
        }
         }


    }


    $('#submitId').click(function() {

		console.log("inside submit");
        var grecaptchaResponse = grecaptcha.getResponse();
        var r_firstName = "";
        var r_lastName = "";
        var r_MiddleName = $("#p_middleName").val();
        var countryCode = "";
        var phoneNumber = "";
        var email = "";
        var m_firstName = "";
        var m_lastName = "";
        var m_middleName = $("#m_middleName").val();
        var ssn = "";
        var vcmId = $("#memberId").val();
        var day = "";
        var month = "";
        var year = "";
        var b_day = $("#b_day").val();
        var b_month = $("#b_month").val();
        var b_year = $("#b_year").val();
        var m_dateOfDeath = "";
        var m_dateOfBirth = "";
        var relationship = $("input:radio[name=related_person]:checked").val();

        var requestor = '"requestor" : {';
        var member = '"member" : {';
        var isEmpty = $("#uploadIdList").html() === "";



        if ($("#p_firstName").val().length > 0) {
            r_firstName = $("#p_firstName").val();
            requestor = requestor + '"firstName":' + '"' + r_firstName + '",';
        } else {
            basicValidationforUsername("#p_firstName", "#yourName_error");
            $("#p_firstName").focus();
            return false;
        }


        if ($("#p_lastName").val().length > 0) {
            r_lastName = $("#p_lastName").val();
            requestor = requestor + '"lastName":' + '"' + r_lastName + '",';
        } else {

            basicValidationforUsername("#p_lastName", "#yourName_error");
            $("#p_lastName").focus();
            return false;
        }

        requestor = requestor + '"middleName":' + '"' + r_MiddleName + '",';

        if ($("#p_coutryCode").val() == '') {
            basicValidationforUsername("#p_coutryCode", "#countryCode_error");
            $("#p_coutryCode").focus();
            return false;
        } else {
            countryCode = $("#p_coutryCode").val();
            requestor = requestor + '"phoneNumber":' + '"' + countryCode + '",';
        }


        if ($("#p_phoneNumber").val().length == 10 && /\d+$/.test($("#p_phoneNumber").val())) {
            phoneNumber = $("#p_phoneNumber").val();
            requestor = requestor + '"phoneNumber":' + '"' + phoneNumber + '",';
        } else {
			$("#p_phoneNumber").focus();
            basicValidationforUsername("#p_phoneNumber", "#phoneNumber_error");

            return false;
        }

         if ($("#email").val() == '') {
            $("#email").focus();
            basicValidationforUsername("#email", "#email_error");
            return false;
        } else {
            email = $("#email").val();
            requestor = requestor + '"email":' + '"' + email + '",';
        }

        if ($("#d_firstName").val().length > 0) {
            m_firstName = $("#d_firstName").val();
            member = member + '"firstName":' + '"' + m_firstName + '",';
        } else {
            $("#d_firstName").focus();
            basicValidationforUsername("#d_firstName", "#name_error");
            return false;
        }

        if ($("#d_lastName").val().length > 0) {
            m_lastName = $("#d_lastName").val();
            member = member + '"lastName":' + '"' + m_lastName + '",';
        } else {
            $("#d_lastName").focus();
            basicValidationforUsername("#d_lastName", "#name_error");
            return false;
        }

        member = member + '"middleName":' + '"' + m_middleName + '",';

        if ($("#p_lastName").val() == '' && $("#p_firstName").val() == '') {
            $("#p_firstName").focus();
            basicValidationforUsername("#p_lastName", "#yourName_error");
            return false;
        }else if($("#p_lastName").val() == ''){
            $("#p_lastName").focus();
            basicValidationforUsername("#p_lastName", "#yourName_error");
            return false;
        }else if($("#p_firstName").val() == ''){
            $("#p_firstName").focus();
             basicValidationforUsername("#p_firstName", "#yourName_error");
             return false;
         }

        if ($("#d_lastName").val() == '' && $("#d_firstName").val() == '') {
            $("#d_firstName").focus();
            basicValidationforUsername("#d_lastName", "#name_error");
            return false;
        }else if($("#d_lastName").val() == '' ){
            $("#d_lastName").focus();

            basicValidationforUsername("#d_lastName", "#name_error");
            return false;
        }else if($("#d_firstName").val() == ''){
            $("#d_firstName").focus();
            basicValidationforUsername("#d_firstName", "#name_error");
            return false;
        }


        requestor = requestor + '"preferredTime": {' + '"time":"9:00-10:00","timeZone": "EST"},';
        requestor = requestor + '"relationship":"' + relationship + '" },';

        if ($("#day").val() == '') {
            $("#day").focus();
            basicValidationforUsername("#day", "#date_error");
            return false;
        } else if($("#day").val() >0 && $("#day").val() <=31) {
            day = $("#day").val();
        }else{
            $("#day").focus();
            basicValidationforUsername("#day", "#date_error");
            return false;
        }

        if ($("#year").val() == '') {
            $("#year").focus();
            basicValidationforUsername("#year", "#date_error");
            return false;
        } else {
            year = $("#year").val();
        }

        if ($("#month").val() == "select" || $('#month').val() == "") {
            $("#month").focus();
            basicValidationforUsername("#year", "#date_error");
            return false;
        } else {
            month = $("#month").val();
        }

        if ($("#day").val() > 0 && $("#year").val() > 0 && $("#b_day").val() > 0 && $("#month").val() != "select") {
            m_dateOfDeath = month + "-" + day + "-" + year;
            member = member + '"dod":"' + m_dateOfDeath + '",';
        } else {
            member = member + '"dod":"",';
        }

        if ($("#b_day").val() > 0 && $("#b_year").val() > 0 && $("#b_month").val() > 0 && $("#b_month").val() != "select") {
            m_dateOfBirth = b_month + "-" + b_day + "-" + b_year;
            member = member + '"dob":"' + m_dateOfBirth + '",';
        } else {
            member = member + '"dob":"",';
        }

         if(($("#year").val() > 0 && $("#year").val().length == 4) && (b_year > 0 && b_year.length == 4 )){
            if(b_year > $("#year").val()){
                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid birth year");
                $("#date_error").show();
                return false;
            }else{
               	$("#date_error").hide();
            	showSignIn = true;
            }

        }
        if (($("#ssn_first").val().length > 0 && $("#ssn_first").val().length == 3 ) &&
            ($("#ssn_second").val().length > 0 && $("#ssn_second").val().length ==2 ) &&
                ($("#ssn_third").val().length > 0 && $("#ssn_third").val().length == 4)) {
            ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
            member = member + '"ssn":' + '"' + ssn + '",';
        }

        if ($("#memberId").val() == '' && ($("#ssn_first").val() == '' && $("#ssn_second").val() == '' && $("#ssn_third").val() == '')) {
            $("#ssn_first").focus();
            basicValidationforUsername("#memberId", "#ssnormember_error");
            return false;
        }

        member = member + '"vcmId":' + '"' + vcmId + '"}';

        if (isEmpty) {
            validateFile("#fileUploadBtn", "#fileUpload_error", "noFiles");
            return false;
        }

        addSurvivorRelationDetailsByPost(grecaptchaResponse, requestor, member);


    });

    function addSurvivorRelationDetailsByPost(grecaptchaResponse, requestor, member) {

        var propertiesJson = '{\n "token":"' + grecaptchaResponse + '", \n' + requestor + '\n' + member + '}';
        filesList = [];
        var fi = document.getElementById('F_Id');
        filesList.push(fi.files.item(0));
        var formData = new FormData();
        //formData.append('attachments',fi.files.item(0).files[0]);
        for (var i = 0; i < filesList.length; i++) {
            formData.append('attachments', filesList[i]);
        }
        formData.append('propertiesJson', propertiesJson);

		var requestUrl=$("#srSaveDetailsEndPoint").val();
        var apiKey = $("#xApiKeyId").val();
		console.log("requestUrl "+ requestUrl+ " apiKey "+ apiKey);

        $.ajax({
            type: 'POST',
            url: requestUrl,
            data: formData,
            processData: false, // tell jQuery not to process the data
            headers: {
                "x-api-key": apiKey
            },
            crossDomain: true,
            beforeSend: function(xhr) {
                xhr.withCredentials = true;
            },
            contentType: false,
            success: function(response) {
               window.location.hostname
            },
            error: function(error) {
                console.log('error ' + error);
            }
        });
    }

});