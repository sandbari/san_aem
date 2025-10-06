(function ($, $document, gAuthor) {
    var EAEM_MAX_ITEMS = "eaem-max-items",
        EAEM_MIN_ITEMS = "eaem-min-items",
        DATA_MF_NAME = "data-granite-coral-multifield-name",
        RS_MULTIFIELD = "granite/ui/components/coral/foundation/form/multifield",
        SLING_RES_TYPE = "sling:resourceType",
        msg ="";
 
    $document.on("dialog-ready", addMinMaxCheck);
 
    function addMinMaxCheck(){
        $.ajax(getDialogPath() + ".infinity.json").done(handler);
 
        function handler(data) {
            var mfProperties = {};
 
            fillItemsOfMultifield(data, mfProperties);
 
            _.each(mfProperties, function(mfProps, mfName){
                addValidator($("[" + DATA_MF_NAME + "='" + mfName + "']"),
                                mfProps[EAEM_MAX_ITEMS], mfProps[EAEM_MIN_ITEMS]);
            });
        }
    }
 
    function addValidator($multifield, maxItems, minItems){
        if(maxItems){
            maxItems = parseInt(maxItems);
        }
 
        if(minItems){
            minItems = parseInt(minItems);
        }
 
        $.validator.register({
            selector: "#" + $multifield.attr("id"),
            validate: validate
        });
 
        function validate(){
            var count = $multifield[0]._items.length;
 
            if(maxItems && (count > maxItems) ){
			    msg = "Maximum allowed : " + maxItems + " items";
				showDialog();
                return msg;
            }
 
            if(minItems && (count < minItems) ){
			    msg = "Minimum required : " + minItems + " items";
				showDialog();
				return msg;
            }
 
            return null;
        }
    }
 
    function getDialogPath(){
        var gAuthor = Granite.author,
            currentDialog = gAuthor.DialogFrame.currentDialog, dialogPath ;
 
        if(currentDialog instanceof gAuthor.actions.PagePropertiesDialog){
            var dialogSrc = currentDialog.getConfig().src;
            dialogPath = dialogSrc.substring(0, dialogSrc.indexOf(".html"));
        }else{
            var editable = gAuthor.DialogFrame.currentDialog.editable;
 
            if(!editable){
                console.log("EAEM - editable not available");
                return;
            }
 
            dialogPath = editable.config.dialog;
        }
 
        return dialogPath;
    }
 
    function fillItemsOfMultifield(data, mfProperties){
        if(!_.isObject(data) || _.isEmpty(data)){
            return mfProperties;
        }
 
        _.each(data, function(value, key){
            if(_.isObject(value) && !_.isEmpty(value)){
                mfProperties = fillItemsOfMultifield(value, mfProperties);
            }else{
                if( (key == SLING_RES_TYPE) && (value == RS_MULTIFIELD)){
                    mfProperties[data.field.name] = data;
                }
            }
        });

        return mfProperties;
    }
	function setDialog(msg){
		var dialog = new Coral.Dialog().set({
			id: 'myDialog',
			header: {
			  innerHTML: 'Warning'
			},
			content: {
			  innerHTML: msg
			},
			footer: {
			  innerHTML: '<button is="coral-button" variant="primary" coral-close>Ok</button>'
			}
		});
	   document.body.appendChild(dialog);
   }
	function showDialog() {
	    setDialog(msg);
		var dialog = document.querySelector('#myDialog');
		dialog.show();
	}
}(jQuery, jQuery(document), Granite.author));
