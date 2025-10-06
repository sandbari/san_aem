(function (document, $, ns){

    "use strict";

    $(window).adaptTo("foundation-registry").register("foundation.validation.validator", {
        selector: '[data-foundation-validation=country-name-number]',
        validate: function (el){
            let element = $(el);
            let value = element.val();
            let pattern = element.data('pattern');

            let patterns = {
                alpha : /^[a-zA-Z][a-zA-Z '\(\)-]*$/,
                numeric : /^\s*(\+|-)?((\d+(\.\d+)?)|(\.\d+))\s*$/,
                phone : /^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\s\./0-9]*$/g
            }
            let errors = {
                alpha : 'Enter only alphabets, allowed special characters- \'\(\)-',
                numeric : 'Enter only number values',
                phone : 'Enter number in XXXX-XXX-XXXX format'
            }

            if(pattern && patterns[pattern]){
                if(!patterns[pattern].test(value)){
                    return errors[pattern];
                }
            }
        }
    });

})(document, Granite.$, Granite.author);