/**
 * The  default AEM required field validator validates the field even though it is hidden. 
 * The required field field validator code given below is implemented to  get around the 
 * problem that a hidden required field cannot be submitted if it has an empty value. To
 * use in Touch UI dialogs, add the class "field-required" to a textfield. The validator 
 * keys off of the ".field-required" selector.
 *
 * The code has been modified from:
 * http://www.nateyolles.com/blog/2016/02/aem-touch-ui-custom-validation
 * 
 */
$.validator
    .register({
      selector : '.field-required',
      validate : function(el) {
        var field, value;

        field = el.closest(".coral-Form-field");
        value = el.val();
        var hidden = $(field).hasClass('hide') || $(field).closest('.hide').hasClass('hide');

        if (!hidden && (value == null || value === '')) {
          return Granite.I18n.get('The field is required');
        }
        return null;
      },
      show : function(el, message) {
        var fieldErrorEl, field, error;

        fieldErrorEl = $("<span class='coral-Form-fielderror coral-Icon coral-Icon--alert" +
         " coral-Icon--sizeS' data-init='quicktip' data-quicktip-type='error' />");
        field = el.closest(".coral-Form-field");

        field.attr("aria-invalid", "true").toggleClass("is-invalid", true);

        field.nextAll(".coral-Form-fieldinfo").addClass(
            "u-coral-screenReaderOnly");

        error = field.nextAll(".coral-Form-fielderror");

        if (error.length === 0) {
          var arrow = field.closest("form").hasClass("coral-Form--vertical") ? "right"
              : "top";

          fieldErrorEl.attr("data-quicktip-arrow", arrow).attr(
              "data-quicktip-content", message).insertAfter(field);
        } else {
          error.data("quicktipContent", message);
        }
      },
      clear : function(el) {
        var field = el.closest(".coral-Form-field");

        field.removeAttr("aria-invalid").removeClass("is-invalid");

        field.nextAll(".coral-Form-fielderror").tooltip("hide").remove();
        field.nextAll(".coral-Form-fieldinfo").removeClass(
            "u-coral-screenReaderOnly");
      }
    });