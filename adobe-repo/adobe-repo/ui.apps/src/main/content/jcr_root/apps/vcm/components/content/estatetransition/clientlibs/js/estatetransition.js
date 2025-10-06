var filesList;
var totalFileSize;
var fileNameComp;
var count = 0;
var environment="";
var userPoolId;
var congnitoIdPool;
var cognitoAWSRegion;
var awsAppClientId;
var grecaptchaResponse ="";
var tableContentRemoved=false;
var isEmpty=false;
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
    filesList.splice((row.rowIndex-1), 1)
  	row.parentNode.removeChild(row);
    validateFile("#fileUploadBtn", "#fileUpload_error", "");
    var table = document.getElementById('fileUploadTable');
    if(table.rows.length == 1){
        $("#fileId").val("");

        filesList = [];

    }
    var div=$('#fileUploadTable').html();
        $('#fileUploadTable').html(div);

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

function validateDuplicateFiles(fname){
    var isFileIsDuplicate=true;
    var table = document.getElementById('fileUploadTable');
    for (var r = 1, n = table.rows.length; r < n; r++) {
        for (var c = 0, m = table.rows[r].cells.length; c < m; c++) {

            if(table.rows[r].cells[0].innerHTML == fname){

            	isFileIsDuplicate= false;

            }

        }
    }

   return isFileIsDuplicate;
}


function handlechange(){

    var inp = document.getElementById('fileId');

    for (i = 0; i < inp.files.length; i++) {

        let file = inp.files[i];
        filesList.push(file);
        var fileInit = file.size;
        fileInit = (fileInit / 1024);
        if (totalFileSize < 20480) {
            if (fileInit < 5120) {
				var table = document.getElementById('fileUploadTable');
                var rows = table.rows.length;
                if(rows <= 10){
                    var fname = file.name; // THE NAME OF THE FILE.
                    var fsize = file.size; // THE SIZE OF THE FILE.
                    let fileSize = (fsize / 1024);
                    totalFileSize = (totalFileSize + fileSize);
                    var isFileIsDuplicate = validateDuplicateFiles(fname);
                    if (isFileIsDuplicate) {

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
                                                                    '<td class="table_content">'+Math.ceil((fsize / 1024)) +' KB</td>'+
                                                                        '<td class="table_content"><button class="btn-primary" id="target" onclick="removeFile(this)" value="'+fname+'" aria-label="Delete">Delete<span class="btn-triangle-rt" aria-hidden="true"></span></button></td>'+
                                                                            '</tr>'+
                                                                                '</table></div>';
                        enableSubmitBtn();

                        validateFile("#fileUploadBtn", "#fileUpload_error", "");

                        $("#fileId").val("");

                    }
                }else{
                    validateFile("#fileUploadBtn", "#fileUpload_error", "maxFiles");
                }

            }else {
                validateFile("#fileUploadBtn", "#fileUpload_error", "fileSizeMore");
            }
        }else {
            validateFile("#fileUploadBtn", "#fileUpload_error", "totalFileSize")
        }

    }

}


 function validateFile(id, error, fileError) {

        if (fileError == 'fileSizeMore') {
            $('span#fileUpload_error').text("Please upload the file less than 5MB");
            $(error).show();
        } else if (fileError == 'totalFileSize') {
            $('span#fileUpload_error').text("Please upload total file size less than 20MB");
            $(error).show();
        } else if (fileError == 'maxFiles') {
            $('span#fileUpload_error').text("Please upload Max 10 files");
            $(error).show();
        } else if (fileError == 'noFiles') {
            $('span#fileUpload_error').text("Please upload a file");
            $(error).show();
        } else {
            $(error).hide();

        }

    }

function enableSubmitBtn(){
	var table = document.getElementById('fileUploadTable');
    if($('#p_firstName').val().length > 0 && $('#p_lastName').val().length > 0 &&
        $('#p_coutryCode').val().length > 0 && $('#p_phoneNumber').val().length > 0 &&
            $('#email').val().length > 0 && $('#d_firstName').val().length > 0 &&
                $('#d_lastName').val().length > 0 && $('#month').val() !="select" &&
                    $('#year').val().length > 0 && $('#day').val().length > 0 && table.rows.length > 1 &&
                        grecaptchaResponse.length > 0){

                            var ssn = 0;
                            if ($("#ssn_first").val() > 0 && $("#ssn_second").length > 0 && $("#ssn_third").val() > 0
                            		&& $('#ssn_third').val().length == 4 &&  $('#ssn_second').val().length == 2 &&$('#ssn_first').val().length == 3 ) {
                                ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();

                            }
                        if(ssn > 0){
							 	$('#submitId').removeAttr("disabled");
                                $("#submitId").css("background-color","#486d90");
                                $("#submitId").css("color","#fff !important");
                                $('#btnSp').addClass("btn-triangle-rt");
                          }else if(($("#memberId").val() > 0 && $("#memberId").val().length == 9)){

                            $('#submitId').removeAttr("disabled");
                            $("#submitId").css("background-color","#486d90");
                            $("#submitId").css("color","#fff !important");
                            $('#btnSp').addClass("btn-triangle-rt");

                        }else{
                            $('#submitId').attr("disabled", "disabled");
                            $("#submitId").css("background-color","#d2d2d2");
                            $("#submitId").css("color","#49494a");
                            $('#btnSp').removeClass("btn-triangle-rt");
                        }
                    }else{
                        $('#submitId').attr("disabled", "disabled");
                        $("#submitId").css("background-color","#d2d2d2");
                        $("#submitId").css("color","#49494a");
                        $('#btnSp').removeClass("btn-triangle-rt");
                    }
}

function onchangeMonth(){

    if ($("#month").val() != "select" && $("#day").val() > 0 && $("#year").val() > 0) {
        var today = new Date();
        var currentYear=new Date().getFullYear();
        var day = today.getDate();
        var month = today.getMonth()+1;
        $("#date_error").hide();
        showSignIn = true;
        $(this).css('border', '');
        return  convertStrDatetoDate(currentYear, month, day);

    } else {
        $(this).css('border', '2px solid #e60000');
        $('span#date_error').text("Please enter a valid date of death");
        $('#date_error').show();
    }
    if($("#month").val() == 2){
        var year=new Date().getFullYear();
        if ((0 == year % 4) && (0 != year % 100) || (0 == year % 400)) {
            if($('#day').val() > 29){
                $('#day').val("")
            }
        } else {
            if($('#day').val() > 28){
                $('#day').val("")
            }
        }
        return false;
    }
    enableSubmitBtn();

}

function onchangeMonth_birth(){
    if ($("#b_month").val() != "select" && $("#b_day").val() > 0 && $("#b_year").val() > 0) {
        var today = new Date();
        var currentYear=new Date().getFullYear();
        var day = today.getDate();
        var month = today.getMonth()+1;
        $("#b_date_error").hide();
        showSignIn = true;
        $(this).css('border', '');
        //return  convertStrDatetoDate(currentYear, month, day);

    }else if ($("#b_month").val() == "select" && $("#b_day").val() == 0 && $("#b_year").val() == 0) {
        $("#b_date_error").hide();
        showSignIn = true;
        $(this).css('border', '');
        //return  convertStrDatetoDate(currentYear, month, day);

    }
    if($("#b_month").val() == 2){
        var year=new Date().getFullYear();
        if ((0 == year % 4) && (0 != year % 100) || (0 == year % 400)) {
            if($('#b_day').val() > 29){
                $('#b_day').val("")
            }
        } else {
            if($('#b_day').val() > 28){
                $('#b_day').val("")
            }
        }
        return false;
    }
    enableSubmitBtn();

}

function dateCompare(date, todayDate){
    const date1 = new Date(date);
    const date2 = new Date(todayDate);
    var isDateVal =false;
    if(date1 > date2){
        isDateVal = false;
    } else if(date1 < date2){
		isDateVal = true;
    } else{
        isDateVal = true;
    }
    if(isDateVal){
        showSignIn = true;
        $('#date_error').hide();
        $(this).css('border', '');
    }else{
        $(this).css('border', '2px solid #e60000');
        $('span#date_error').text("Please enter a valid date of death");
        $('#date_error').show();
    }
    return isDateVal;
}

function convertStrDatetoDate(currentYear, month, day){
     if(month < 9){
            month ="0"+month;
        }
    if( $("#month").val() != "select" && $("#day").val() > 0){
        var dateFromPage = $("#year").val()+"-"+$("#month").val()+"-"+$("#day").val();
        var todayDate = currentYear+"-"+month+"-"+day;
        var isDeteVal = dateCompare(dateFromPage, todayDate);
        if(isDeteVal){
            return true;
        }else{
            $('#year').val("");
            $('#day').val("");
            $('#month').val("select");
            return false;
        }
    }
}

function compareDateOfbithAndDeath(){
    if(($("#year").val() > 0 && $("#year").val().length == 4) && ($("#b_year").val() > 0 && $("#b_year").val().length == 4 ) &&
         $("#month").val() != "select" && $("#b_month").val() != "select" && $("#day").val() > 0 && $("#b_day").val() > 0 ){
             var deathDate = $("#year").val()+"-"+$("#month").val()+"-"+$("#day").val();
             var birthDate = $("#b_year").val()+"-"+$("#b_month").val()+"-"+$("#b_day").val();
             var isDeteVal = false;
             if(deathDate.length > 0 && birthDate.length > 0){
                 isDeteVal = dateCompare(birthDate, deathDate);
             }
            if(deathDate.length > 0){
                $('#date_error').hide();
            }


            if(isDeteVal){
                $("#b_date_error").hide();
                $('#date_error').hide();
            	showSignIn = true;
            	$(this).css('border', '');
            }else{
                $("#b_year").val("");
                $("#b_day").val("");
                $("#b_month").val("select");
				$("#b_month").focus();
                $(this).css('border', '2px solid #e60000');
                $('span#b_date_error').text("Please enter a valid birth year");
               // $("#b_date_error").show();
                return false;
            }
         }
}
$(document).ready(function() {

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
           }else if($('#month').val() == 2){

                   if(dayValue > 29){
                       $('#day').val("")
                   }

               return false;
           }else{
               	var today = new Date();
                var currentYear=new Date().getFullYear();
                var day = today.getDate();
                var month = today.getMonth()+1;
               return  convertStrDatetoDate(currentYear, month, day);
           }
           return false; //stop character from entering input
       }

         var dayVal= $('#day').val();
         if(dayVal > 31){
             $('#day').val("")
             return false;
         }else if($('#month').val() == 2){

             if(dayVal > 29){
                 $('#day').val("")
             }

               return false;
           }else{
               	var today = new Date();
                var currentYear=new Date().getFullYear();
                var day = today.getDate();
                var month = today.getMonth()+1;
             return  convertStrDatetoDate(currentYear, month, day);
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
           }else if($('#b_month').val() == 2){
                   if(dayValue > 29){
                       $('#b_day').val("")
                   }
               return false;
           }
            if($('#b_day').val() > 0 && $('#b_year').val() > 0 && $('#b_month').val() != "select"){
             $("#b_date_error").hide();
        	showSignIn = true;
        	$(this).css('border', '');
        }
           return false; //stop character from entering input
       }

         var dayVal= $('#b_day').val();
         if(dayVal > 31){
              $('#b_day').val("")
             return false;
           }else if($('#b_month').val() == 2){
                   if(dayVal > 29){
                       $('#b_day').val("")
                   }

               return false;
           }

        if($('#b_day').val() > 0 && $('#b_year').val() > 0 && $('#b_month').val() != "select"){
             $("#b_date_error").hide();
        	showSignIn = true;
        	$(this).css('border', '');
        }
         return true;

   });


    $('#year').keyup(function(event){

        var today = new Date();
        var currentYear=new Date().getFullYear();
		var day = today.getDate();
        var month = today.getMonth()+1;

       if(event.which != 8 && isNaN(String.fromCharCode(event.which))){
           $('#year').val($('#year').val().replace(/\D/g, ''));
           var yearValue= $('#year').val();
           if(yearValue > currentYear){
               $('#year').val("")
               compareDateOfbithAndDeath();
               return false;
           }else{
               if(yearValue.length == 4 && currentYear > 0 ){
                   compareDateOfbithAndDeath();
                 return  convertStrDatetoDate(currentYear, month, day);
               }
           }
           compareDateOfbithAndDeath()
           return false; //stop character from entering input
       }
         const yearVal= $('#year').val();

         if(yearValue > currentYear){
               $('#year').val("")
               return false;
           }else{

               if($('#year').val().length == 4 && currentYear > 0 ){
                   if( $("#month").val() != "select" && $("#day").val() > 0){
                        	var dateFromPage = $('#year').val()+"-"+$("#month").val()+"-"+$("#day").val();
                       		var todayDate = currentYear+"-"+month+"-"+day;
                       		var isDeteVal = dateCompare(dateFromPage, todayDate);
                       if(isDeteVal){

                           return true;
                       }else{
                           $('#year').val("");
                           $('#day').val("");
                           $('#month').val("select");
                           return false;
                       }
                  	}

               }
           }
		compareDateOfbithAndDeath()
         return true;
   });


    $('#fileUploadBtn').click(function(){ $('#fileId').click() });

    $('#b_year').keyup(function(event){
        var currentYear= new Date().getFullYear();
       if(event.which != 8 && isNaN(String.fromCharCode(event.which))){
           $('#b_year').val($('#b_year').val().replace(/\D/g, ''));
           var yearValue= $('#b_year').val();
           if(yearValue > currentYear){
               $('#b_year').val("")
               return false;
           }
            if($('#b_day').val() > 0 && $('#b_year').val() > 0 && $('#b_month').val() != "select"){
             	$("#b_date_error").hide();
        		showSignIn = true;
        		$(this).css('border', '');
        	}
           compareDateOfbithAndDeath();
           return false; //stop character from entering input
       }
         var yearVal= $('#b_year').val();
         if(yearVal > currentYear){
             $('#b_year').val("")
             return false;
         }
        if($('#b_day').val() > 0 && $('#b_year').val() > 0 && $('#b_month').val() != "select"){
            $("#b_date_error").hide();
        	showSignIn = true;
        	$(this).css('border', '');
        }
		compareDateOfbithAndDeath()
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
                if($('#memberId').val().length == 9 && $('#memberId').val() > 0){
 						$("#ssnormember_error").hide();
                    	showSignIn = true;
                    	$(this).css('border', '');
                }else if($('#ssn_third').val().length == 4 &&  $('#ssn_second').val().length == 2 &&
                    	(this.value.length==$(this).attr("maxlength"))){
                        	$("#ssnormember_error").hide();
                        	$('#ssn_second').removeClass(':focus');
                            $('#ssn_third').removeClass(':focus');
                        	showSignIn = true;
                        	$(this).css('border', '');
                    }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                    }else {
                        $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                        $("#ssnormember_error").show();
                    }
            }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                    }
            enableSubmitBtn();
            return false;
        }
        $('#ssn_first').val($('#ssn_first').val().replace(/\D/g, ''));
        if($('#ssn_first').val().match(regExp)){
            if(this.value.length==$(this).attr("maxlength")){
           		document.getElementById("ssn_second").focus();
                validateSSN();
        	}
        }
        if($('#memberId').val().length == 9 && $('#memberId').val() > 0){
 						$("#ssnormember_error").hide();
                    	showSignIn = true;
                    	$(this).css('border', '');
        }else if($('#ssn_third').val().length == 4 &&  $('#ssn_second').val().length == 2 &&
            (this.value.length==$(this).attr("maxlength"))){
                $("#ssnormember_error").hide();
                $('#ssn_second').removeClass(':focus');
                $('#ssn_third').removeClass(':focus');
                showSignIn = true;
                $(this).css('border', '');
        }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
        }else {
            //$(this).css('border', '2px solid #e60000');
            $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
            $("#ssnormember_error").show();
        }
         enableSubmitBtn();

    });

    $('#ssn_second').keyup(function(evt){

        var regExp = "^\\d+$";
       	var charCode = (evt.which) ? evt.which : evt.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57)){
            $('#ssn_second').val($('#ssn_second').val().replace(/\D/g, ''));
            if($('#ssn_second').val().match(regExp)){
                if(this.value.length==$(this).attr("maxlength")){
                    document.getElementById("ssn_third").focus();
                }if($('#memberId').val().length == 9 && $('#memberId').val() > 0){
 						$("#ssnormember_error").hide();
                    	showSignIn = true;
                    	$(this).css('border', '');
                }else if($('#ssn_first').val() > 0 &&  $('#ssn_first').val().length == 3 &&
                        $('#ssn_third').val() > 0 && $('#ssn_third').val().length == 4 &&
                            $('#ssn_second').val() > 0 &&  $('#ssn_second').val().length == 2){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                                $('#ssn_third').removeClass(':focus');
                                $('#ssn_first').removeClass(':focus');
                    }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                            }else {
                        //$(this).css('border', '2px solid #e60000');
                        $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                        $("#ssnormember_error").show();
                    }

            }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                            }
			enableSubmitBtn();
            return false;
        }

        $('#ssn_second').val($('#ssn_second').val().replace(/\D/g, ''));
        if($('#ssn_second').val().match(regExp)){
            if(this.value.length==$(this).attr("maxlength")){
           		document.getElementById("ssn_third").focus();
        	}
            if($('#memberId').val().length == 9 && $('#memberId').val() > 0){
                    $("#ssnormember_error").hide();
                    showSignIn = true;
                    $(this).css('border', '');
                }else if($('#ssn_first').val() > 0 &&  $('#ssn_first').val().length == 3 &&
                        $('#ssn_third').val() > 0 && $('#ssn_third').val().length == 4 &&
                            $('#ssn_second').val() > 0 &&  $('#ssn_second').val().length == 2){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                                $('#ssn_third').removeClass(':focus');
                                $('#ssn_first').removeClass(':focus');
                    }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                            }else {
                       // $(this).css('border', '2px solid #e60000');
                        $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                        $("#ssnormember_error").show();
                    }
        }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                            }
        enableSubmitBtn();
    });

     $('#memberId').keyup(function(evt){


         var ssn = 0;
         if ($("#ssn_first").val() > 0 && $("#ssn_second").length > 0 && $("#ssn_third").val() > 0
         && $('#ssn_third').val().length == 4 &&  $('#ssn_second').val().length == 2 &&$('#ssn_first').val().length == 3 ) {
             ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();

         }
         if(ssn > 0){
             $("#ssnormember_error").hide();
             showSignIn = true;
             $(this).css('border', '');
			enableSubmitBtn()
         }else if($('#memberId').val().length == 9 && $('#memberId').val() > 0){
            	$("#ssnormember_error").hide();
             	showSignIn = true;
             	$(this).css('border', '');
             	$('#ssn_third').removeClass(':focus');
             	$('#ssn_first').removeClass(':focus');
         }else{
             //$(this).css('border', '2px solid #e60000');
             $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
             $("#ssnormember_error").show();
         }

         enableSubmitBtn()
    });

     $('#ssn_third').keyup(function(evt){
        var regExp = "^\\d+$";
       	var charCode = (evt.which) ? evt.which : evt.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57)){
            $('#ssn_third').val($('#ssn_third').val().replace(/\D/g, ''));
            if($('#ssn_third').val().match(regExp)){
                if($('#memberId').val().length == 9 && $('#memberId').val() > 0){
 						$("#ssnormember_error").hide();
                    	showSignIn = true;
                    	$(this).css('border', '');
                }else if( $('#ssn_first').val() > 0 &&  $('#ssn_first').val().length == 3 &&
                        ($('#ssn_second').val().length > 0 && $('#ssn_second').val().length == 2) &&
                            $('#ssn_third').val().length > 0 && $('#ssn_third').val().length == 4){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                                $('#ssn_second').removeClass(':focus');
                                $('#ssn_first').removeClass(':focus');
                            }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                            }else {
                               // $(this).css('border', '2px solid #e60000');
                                $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                                $("#ssnormember_error").show();
                            }
            }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                    }
            enableSubmitBtn();

            return false;
        }
        $('#ssn_third').val($('#ssn_third').val().replace(/\D/g, ''));
        if($('#ssn_third').val().match(regExp)){
            if($('#memberId').val().length == 9 && $('#memberId').val() > 0){
 						$("#ssnormember_error").hide();
                    	showSignIn = true;
                    	$(this).css('border', '');
                }else if( $('#ssn_first').val() > 0 &&  $('#ssn_first').val().length == 3 &&
                        ($('#ssn_second').val().length > 0 && $('#ssn_second').val().length == 2 ) &&
                            $('#ssn_third').val().length > 0 && $('#ssn_third').val().length == 4){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                                $('#ssn_second').removeClass(':focus');
                                $('#ssn_first').removeClass(':focus');
                    }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                    } else {
                        //$(this).css('border', '2px solid #e60000');
                        $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                        $("#ssnormember_error").show();
                    }
                 enableSubmitBtn();
        }else if($('#ssn_first').val() =="" && $('#ssn_second').val() =="" && $('#ssn_third').val() ==""){
                                $("#ssnormember_error").hide();
                                showSignIn = true;
                                $(this).css('border', '');
                    }

         enableSubmitBtn();

    });

    $("div.sc-inlrYM.ejhNYg>button").click(function() {

        if ($("#password").attr("type") === "password") {
            $("#password").attr("type", "text");
        } else {
            $("#password").attr("type", "password");
        }
    });


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

    $('#p_coutryCode').click(
        function() {
            validate("#p_coutryCode", "#countryCode_error");
        });

    $('#email').click(function(){
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
	$('#b_day').click(
        function() {
            validate("#b_day", "#date_error");
        });

    $('#b_year').click(
        function() {
            validate("#b_year", "#date_error");
        });


	$('#memberId').click(
        function() {
            validateSSN()
        });

     $('#ssn_third').click(
        function() {
            validate("#ssn_third", "#ssnormember_error");
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
				basicValidationforUsername(id, "#email_error");
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

            if (attrValue == '#b_day') {
                validate(attrValue, "#b_date_error");
            }
            if (attrValue == '#b_year') {
                validate(attrValue, "#b_date_error");
            }

            var ssn = 0;
            if ($("#ssn_first").val().length > 0 && $("#ssn_second").val().length > 0 && $("#ssn_third").val().length > 0
                && $("#ssn_third").val().length == 4 && $("#ssn_second").val().length == 2 && $("#ssn_first").val().length == 3) {
                    ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
            }

            if (attrValue == '#ssn_third' && ssn != 0) {
                validate(attrValue, "#ssnormember_error");
            }

            if (attrValue == '#memberId' && ssn == 0) {
                validate(attrValue, "#ssnormember_error");
                enableSubmitBtn();
            }

            if (attrValue == '#ssn_third' && ssn == 0) {
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
                    if ($(id).val() == '' && $(id).val().length < 2) {
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
                if ($("#ssn_first").val().length > 0 && $("#ssn_second").val().length > 0 && $("#ssn_third").val().length > 0
                    && $("#ssn_third").val().length == 4 && $("#ssn_second").val().length == 2 && $("#ssn_first").val().length == 3) {
                        ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
                    }

                if($("#ssn_third").val() > 0){
					ssn = 0;
					if ($("#ssn_first").val().length > 0 && $("#ssn_second").val().length > 0 && $("#ssn_third").val().length > 0
                    	&& $("#ssn_third").val().length == 4 && $("#ssn_second").val().length == 2 && $("#ssn_first").val().length == 3) {
                        	ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
                    }
                    if(ssn == 0 && $("#memberId").val() == 0){
                        $(this).css('border', '1px solid #d2d2d2');
                    	basicValidationforUsername(id, error);
                    }
                }

                if (id == "#memberId" && ssn == 0) {
                    $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                } else {
                    $(this).css('border', '1px solid #d2d2d2');
                    basicValidationforUsername(id, error);
                }


                if (id == "#b_day" && $("#b_day").val() == 0) {
                    $('span#ssnormember_error').text("Please enter a valid date of birth");
                } else {
                    if($("#b_year").val() > 0 && $("#b_month").val() !="select"){
						$(error).hide();
                		showSignIn = true;
                		$(this).css('border', '');
                    }else{
                    	$(this).css('border', '1px solid #d2d2d2');
                    	basicValidationforUsername(id, error);
                    }
                }

                 if (id == "#b_year" && $("#b_year").val() == 0) {
                    $('span#ssnormember_error').text("Please enter a valid date of birth");
                } else {
                    if($("#b_day").val() > 0 && $("#b_month").val() !="select"){
						$(error).hide();
                		showSignIn = true;
                		$(this).css('border', '');
                    }else{
                    	$(this).css('border', '1px solid #d2d2d2');
                    	basicValidationforUsername(id, error);
                    }
                }

                //$(error).show();
            } else {
                $(this).css('border', '1px solid #d2d2d2');
                basicValidationforUsername(id, error);
            }
        });
    }


    function validateSSN(){
        var ssn = 0;
        if ($("#ssn_first").val() > 0 && $("#ssn_second").val() > 0 && $("#ssn_third").val() > 0
        && $("#ssn_third").val().length == 4 && $("#ssn_second").val().length == 2 && $("#ssn_first").val().length == 3) {
            ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
        }
        if (ssn > 0) {
            $("#ssnormember_error").hide();
            showSignIn = true;
            $(this).css('border', '');
        }else if($("#memberId").val() > 0 && $("#memberId").val().length < 9) {
            $(this).css('border', '2px solid #e60000');
            $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
            $('#ssnormember_error').show();
            return false;
        }
        return true;

    }
    function basicValidationforUsername(id, error) {


        if (id == "#day") {
            if (/^\+?\d+$/.test($(id).val()) && $(id).val() > 0) {
                $(error).hide();
                showSignIn = true;
                $(this).css('border', '');
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
            }else if (/^\+?\d+$/.test($(id).val()) && $(id).val().length < 4 && $("#day").val() > 0 && $("#month").val() != "select") {

                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();
            } else if (/^\+?\d+$/.test($(id).val()) && $(id).val().length == 4 && $("#day").val() == 0 && $("#month").val() != "select") {

                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();
            }else {
                $(error).hide();
                showSignIn = true;
                $(this).css('border', '');
            }
        }

        if("#month" == id){

            if (/^\+?\d+$/.test($(id).val())) {
                $(error).hide();
                showSignIn = true;
                $(this).css('border', '');
            } else {
                $(this).css('border', '2px solid #e60000');
                $('span#date_error').text("Please enter a valid date of death");
                $(error).show();
            }

        }
        if (id == "#p_coutryCode") {
            if (/^\+?\d+$/.test($(id).val())) {
                $(error).hide();
                showSignIn = true;
                $(this).css('border', '');
            } else {
                $(this).css('border', '2px solid #e60000');
                $('span#countryCode_error').text("Please enter a valid country code");
                $(error).show();
            }
        }

        if (id == "#p_phoneNumber") {

            if ($(id).val().length == 10) {
                $('#phoneNumber_error').hide();
            	showSignIn = true;
            	$(this).css('border', '');
            } else {
                $(this).css('border', '2px solid #e60000');
                 $('.Input_fieldPhoneNumber__2Mqft').show();
                $('span#phoneNumber_error').text("Please enter a valid phone number");
                 $(error).show();
            }
        }

        if (id == "#email") {

            if ($(id).val().length > 3) {
                let emailValue = $(id).val().trim();
				$(id).val(emailValue);
                if (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(emailValue)) {

                    $("#email_error").hide();
                    showSignIn = true;
                    $(this).css('border', '');
                } else {
                    $(this).css('border', '2px solid #e60000');
                    $('span#email_error').text("Please enter a valid email address");
                    $("#email_error").show();
                    $(error).show();
                }

            } else {
                $(this).css('border', '2px solid #e60000');
                $('span#email_error').text("Please enter a valid email address");
                $(error).show();
            }
        }

        if (id == "#memberId") {

            var ssn = 0;
            if ($("#ssn_first").val().length > 0 && $("#ssn_second").val().length > 0 && $("#ssn_third").val().length > 0
                && $("#ssn_third").val().length == 4 && $("#ssn_second").val().length == 2 && $("#ssn_first").val().length == 3) {
                    ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
                }

            if ((ssn > 0 && ssn.length == 9) && ($(id).val().length == 0)) {
                $("#ssnormember_error").hide();
                showSignIn = true;
                $(this).css('border', '');
                enableSubmitBtn()
            }else if($(id).val() > 0 && $(id).val().length == 9 && (ssn > 0 && ssn.length == 9)){
                $("#ssnormember_error").hide();
                showSignIn = true;
                $(this).css('border', '');
                enableSubmitBtn()
            }else if($(id).val() > 0 && $(id).val().length == 9 && (ssn == 0)){
                $("#ssnormember_error").hide();
                showSignIn = true;
                $(this).css('border', '');
                enableSubmitBtn()
            }else {
                //$(this).css('border', '2px solid #e60000');
                $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                $(error).show();
            }
            enableSubmitBtn()
        }


        if (id == "#ssn_third") {

           validateSSN();
        }

        if (id == "#ssn_second") {
            validateSSN();
        }

        if (id == "#ssn_second") {
            validateSSN();
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
                $(this).css('border', '');
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
            $(this).css('border', '');
        }
         }

		enableSubmitBtn();
    }


    $('#submitId').click(function() {

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
            requestor = requestor + '"firstName":' + '"' + r_firstName.trim() + '",';
        } else {
            basicValidationforUsername("#p_firstName", "#yourName_error");
            $("#p_firstName").focus();
            return false;
        }


        if ($("#p_lastName").val().length > 0) {
            r_lastName = $("#p_lastName").val();
            requestor = requestor + '"lastName":' + '"' + r_lastName.trim() + '",';
        } else {

            basicValidationforUsername("#p_lastName", "#yourName_error");
            $("#p_lastName").focus();
            return false;
        }

        requestor = requestor + '"middleName":' + '"' + r_MiddleName.trim() + '",';

        if ($("#p_coutryCode").val() == '') {
            basicValidationforUsername("#p_coutryCode", "#countryCode_error");
            $("#p_coutryCode").focus();
            return false;
        } else {
            countryCode = $("#p_coutryCode").val();
            requestor = requestor + '"countryCode":' + '"' + countryCode + '",';
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
            email = $("#email").val().trim();
            $("#email").val(email);
            if (email.length > 0 && /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email)) {
                   requestor = requestor + '"email":' + '"' + email.trim() + '",';
            }else{
                $("#email").focus();
                basicValidationforUsername("#email", "#email_error");
                return false;
            }

        }

        if ($("#d_firstName").val().length > 0) {
            m_firstName = $("#d_firstName").val();
            member = member + '"firstName":' + '"' + m_firstName.trim() + '",';
        } else {
            $("#d_firstName").focus();
            basicValidationforUsername("#d_firstName", "#name_error");
            return false;
        }

        if ($("#d_lastName").val().length > 0) {
            m_lastName = $("#d_lastName").val();
            member = member + '"lastName":' + '"' + m_lastName.trim() + '",';
        } else {
            $("#d_lastName").focus();
            basicValidationforUsername("#d_lastName", "#name_error");
            return false;
        }

        member = member + '"middleName":' + '"' + m_middleName.trim() + '",';

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

        if ($("#day").val() > 0 && $("#year").val() > 0 && $("#month").val() != "select") {
            m_dateOfDeath = month + "-" + day + "-" + year;
            member = member + '"dod":"' + m_dateOfDeath + '",';
        } else {
            member = member + '"dod":"",';
        }

        if($("#b_year").val() > 0 && $("#b_day").val() ==0 && $("#b_month").val() != "select"){
            	$("#b_day").focus();

                $('span#b_date_error').text("Please enter a valid birth year");
                $("#b_date_error").show();
                return false;
        }else if($("#b_year").val() > 0 && $("#b_day").val() > 0 && $("#b_month").val() == "select"){
            $("#b_month").focus();
            $('span#b_date_error').text("Please enter a valid birth year");
            $("#b_date_error").show();
            return false;
        }else if($("#b_year").val() == 0 && $("#b_day").val() > 0 && $("#b_month").val() != "select"){
            $("#b_year").focus();
            $('span#b_date_error').text("Please enter a valid birth year");
            $("#b_date_error").show();
            return false;
        }else if($("#b_year").val() == 0 && $("#b_day").val() == 0 && $("#b_month").val() != "select"){
            $("#b_day").focus();
            $('span#b_date_error').text("Please enter a valid birth year");
            $("#b_date_error").show();
            return false;
        }else if($("#b_year").val() == 0 && $("#b_day").val() > 0 && $("#b_month").val() == "select"){
            $("#b_month").focus();
            $('span#b_date_error').text("Please enter a valid birth year");
            $("#b_date_error").show();
            return false;
        }else if($("#b_year").val() == 0 && $("#b_day").val() == 0 && $("#b_month").val() != "select"){
            $("#b_day").focus();
            $('span#b_date_error').text("Please enter a valid birth year");
            $("#b_date_error").show();
            return false;
        }else{
            $("#b_date_error").hide();
            showSignIn = true;
            $(this).css('border', '');

        }

        if ($("#b_day").val() > 0 && $("#b_year").val() > 0 && $("#b_month").val() != "select"
        		&& $("#b_year").val().length == 4) {
            m_dateOfBirth = b_month + "-" + b_day + "-" + b_year;
            member = member + '"dob":"' + m_dateOfBirth + '",';
        } else {
            member = member + '"dob":"",';
        }

         if(($("#year").val() > 0 && $("#year").val().length == 4) && (b_year > 0 && b_year.length == 4 ) &&
         $("#month").val() != "select" && $("#b_month").val() != "select" && $("#day").val() > 0 && $("#b_day").val() > 0 ){

             var deathDate = $("#year").val()+"-"+$("#month").val()+"-"+$("#day").val();
             var birthDate = b_year+"-"+$("#b_month").val()+"-"+$("#b_day").val();
             var isDeteVal = false;
             if(deathDate.length > 0 && birthDate.length > 0){
                 isDeteVal = dateCompare(birthDate, deathDate);
             }

            if(isDeteVal){
                $("#b_date_error").hide();
            	showSignIn = true;
                $(this).css('border', '');

            }else{
                $("#b_year").val("");
                $("#b_day").val("");
                $("#b_month").val("select");
				$("#b_month").focus();
               	$(this).css('border', '2px solid #e60000');
                $('span#b_date_error').text("Please enter a valid birth year");
                $("#b_date_error").show();
                return false;
            }

        }
        if (($("#ssn_first").val() > 0 && $("#ssn_first").val().length == 3 ) &&
            ($("#ssn_second").val() > 0 && $("#ssn_second").val().length ==2 ) &&
                ($("#ssn_third").val() > 0 && $("#ssn_third").val().length == 4)) {
                    ssn = $("#ssn_first").val() + $("#ssn_second").val() + $("#ssn_third").val();
                    member = member + '"ssn":' + '"' + ssn + '",';
                }else{
                    member = member + '"ssn":"",';
                }


        if ($("#memberId").val() == '' && ($("#ssn_first").val() == '' && $("#ssn_second").val() == '' && $("#ssn_third").val() == '')) {
            $("#ssn_first").focus();
            basicValidationforUsername("#memberId", "#ssnormember_error");
            return false;
        }else if($("#memberId").val() >0 && $("#memberId").val().length < 9){
            $("#memberId").focus();
            basicValidationforUsername("#memberId", "#ssnormember_error");
            return false;
        }else if(($("#ssn_first").val() > 0 && $("#ssn_first").val().length < 3) ||
                     ($("#ssn_second").val() > 0 && $("#ssn_second").val().length < 2) ||
                        ($("#ssn_third").val() > 0 && $("#ssn_third").val().length < 4)) {
                            $("#ssn_first").focus();
                            $(this).css('border', '2px solid #e60000');
                        	$('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                        	$('#ssnormember_error').show();
                        	return false;
                        }else if(ssn == 0 && ($("#ssn_first").val() > 0 || $("#ssn_second").val() >0 || $("#ssn_third").val() > 0)){
                            $("#ssn_first").focus();
                            $(this).css('border', '2px solid #e60000');
                            $('span#ssnormember_error').text("Please enter a valid ssn or member Id");
                            $('#ssnormember_error').show();
                            return false;
                        }else{
                            $('#ssnormember_error').hide();
                            showSignIn = true;
                            $(this).css('border', '');

                        }


        member = member + '"vcmId":' + '"' + vcmId + '"}';

        var table = document.getElementById('fileUploadTable');
        if (table.rows.length == 1) {
            validateFile("#fileUploadBtn", "#fileUpload_error", "noFiles");
            return false;
        }


        addSurvivorRelationDetailsByPost(grecaptchaResponse, requestor, member);


    });

    function addSurvivorRelationDetailsByPost(grecaptchaResponse, requestor, member) {

		getSpinner();
        var propertiesJson = '{\n "token":"' + grecaptchaResponse + '", \n' + requestor + '\n' + member + '}';

        //var fi = document.getElementById('F_Id');
       // filesList.push(fi.files.item(0));

        var formData = new FormData();
        //formData.append('attachments',fi.files.item(0).files[0]);
        for (var i = 0; i < filesList.length; i++) {
            formData.append('attachments', filesList[i]);
        }
        formData.append('propertiesJson', propertiesJson);

		var requestUrl=$("#srSaveDetailsEndPoint").val();
        var apiKey = $("#xApiKeyId").val();
		 $('#errorMsg').hide();
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

                const element = document.getElementById("globalWSpiiner");
				element.remove();
                var successVal = response.success;
                $('body').addClass('active');
				$(window).scrollTop(0)
            	var responseHTML =JSON.stringify(response);
            	var srConfirmationPageURL = $("#srConfirmEndPoint").val();
            	//console.log("srConfirmationPageURL "+ srConfirmationPageURL);
                if(successVal == true){
                    window.location.href = srConfirmationPageURL;
                }else{
                    	$('body').addClass('active');
                        $(window).scrollTop(0)
                        var responseHTML = $.parseHTML(response);
                        var containerHTML = $(responseHTML).find('.container').html();
                        var wrapperExpDiv = $("<div>", {
                            "class": "globalExperiencePopUpWrapper",
                            "id": "globalExPopUpWrapper"
                        });

                        var exp_popUp_container_div = $("<div>", {
                            "id": "vcm-exp-popup",
                            "class": "vcm-popup",
                            "style": "display:block; top: 18% !important;"
                        });
                        var message = "";
                        if(response.message.length > 0){
                            message = response.message;
                        }
                        exp_popUp_container_div.append("<div id='popup-header' class='popup-header'><p class='popUpErrorMsg' id='popupErrorMsg'>"+
                        message + ".</p><span class='vcm-popup-btn-close'></span></div>");
                        exp_popUp_container_div.append(containerHTML);
                        $(wrapperExpDiv).append(exp_popUp_container_div);
                        $(wrapperExpDiv).append("<div id='popup-mask' class='popup-mask'  style='display: block;'></div>");
                        $('body').append(wrapperExpDiv);

                     	$('.globalExperiencePopUpWrapper').fadeIn().delay(15000).fadeOut(() => { clearPopUpdiv(); })
                        $('body').removeClass('active');

                }

            },
            error: function(error) {
				//document.getElementById('globalWSpiiner').innerHTML = "";
                const element = document.getElementById("globalWSpiiner");
				element.remove();

               	$('body').addClass('active');
				$(window).scrollTop(0)
            	var responseHTML = $.parseHTML(error);
                var containerHTML = $(responseHTML).find('.container').html();
                var wrapperExpDiv = $("<div>", {
                    "class": "globalExperiencePopUpWrapper",
                    "id": "globalExPopUpWrapper"
                });

                var exp_popUp_container_div = $("<div>", {
                    "id": "vcm-exp-popup",
                    "class": "vcm-popup",
                    "style": "display:block; top: 18% !important;"
                });
                var errorResponse = error.responseText;
            	var message = "";
				//console.log("error msg "+errorResponse);
            	if (typeof errorResponse == 'undefined') {
                   message = "We are not able to process your request at this time, please try again later, or contact a Representative for assistance."
                }else{
                    errorResponse = JSON.parse(errorResponse);
                    message = errorResponse.message;
                }
                exp_popUp_container_div.append("<div id='popup-header' class='popup-header'><p class='popupError popUpErrorMsg' id='popupErrorMsg'>"+
                message+"</p><span class='vcm-popup-btn-close'></span></div>");

                exp_popUp_container_div.append(containerHTML);

                $(wrapperExpDiv).append(exp_popUp_container_div);
                $(wrapperExpDiv).append("<div class='popup-mask' id ='popup-mask' style='display: block;'></div>");
                $('body').append(wrapperExpDiv);

                $('.globalExperiencePopUpWrapper').fadeIn().delay(15000).fadeOut(() => { clearPopUpdiv(); })
                $('body').removeClass('active');

            }
        });
    }

});

function clearPopUpdiv(){
    const element = document.getElementById("globalExPopUpWrapper");
    element.remove();
}

function recaptchaCallback(){
	grecaptchaResponse = grecaptcha.getResponse();
    enableSubmitBtn();
}


function getSpinner(){
     var wrapperExpDiv = $("<div>", {
         "class": "globalExperiencePopUpWrapper",
         "id": "globalWSpiiner"
     });

    var exp_popUp_container_div = $("<div>", {
        "id": "wSpinner",
        "class": "loader loader-popup",
        "style": "display:block;"
    });
    $(wrapperExpDiv).append(exp_popUp_container_div);
    $(wrapperExpDiv).append("<div id='wSpinner-popupMask' class='popup-mask-spinner'  style='display: block;'></div>");
    $('body').append(wrapperExpDiv);
}