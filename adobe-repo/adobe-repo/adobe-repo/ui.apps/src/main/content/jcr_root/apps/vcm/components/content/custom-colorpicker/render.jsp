<%@include file="/libs/granite/ui/global.jsp" %><%
%><%@page session="false"
          import="com.adobe.granite.ui.components.AttrBuilder,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.Field,
                  com.adobe.granite.ui.components.Tag" %>
                      <%
	String stored_attr_Value = null;				  
	Config cfg = cmp.getConfig();
    ValueMap vm = (ValueMap) request.getAttribute(Field.class.getName());
    Field field = new Field(cfg);

    boolean isMixed = field.isMixed(cmp.getValue());
    
    String name = cfg.get("name", String.class);


    Tag tag = cmp.consumeTag();
    AttrBuilder attrs = tag.getAttrs();

    attrs.add("id", cfg.get("id", String.class));
    attrs.addClass(cfg.get("class", String.class));
    attrs.addRel(cfg.get("rel", String.class));
    attrs.add("title", i18n.getVar(cfg.get("title", String.class)));

    attrs.addClass("coral-InputGroup");
    attrs.add("value", cfg.get("value", String.class)); 

    // Use JCR standard date format for storage
    // FIXME data-stored-format is a bad name; use data-value-format
    if (isMixed) {
        attrs.addClass("foundation-field-mixed");
    }
    
    attrs.addOthers(cfg.getProperties(), "id", "class", "rel", "title", "name", "value", "emptyText", "type", "displayedFormat", "minDate", "maxDate", "displayTimezoneMessage", "fieldLabel", "fieldDescription", "renderReadOnly", "ignoreData");
    

    AttrBuilder attrsInput = new AttrBuilder(request, xssAPI);
    attrsInput.addClass("coral-InputGroup-input coral-Textfield");
    attrsInput.add("name", name);

    attrsInput.addDisabled(cfg.get("disabled", false));
    attrsInput.add("type", cfg.get("type", "text"));

    if (isMixed) {
        attrsInput.add("placeholder", i18n.get("<Mixed Entries>")); 
    } else {
        attrsInput.add("value", vm.get("value", String.class));
		stored_attr_Value = vm.get("value", String.class);
        log.info("Current value:" + vm.get("value", String.class)+":");
        attrsInput.add("placeholder", i18n.getVar(cfg.get("emptyText", String.class)));
    }

    if (cfg.get("required", false)) {
        attrsInput.add("aria-required", true);
    }

    AttrBuilder typeAttrs = new AttrBuilder(request, xssAPI);
    typeAttrs.add("type", "hidden");
    typeAttrs.add("value", "Color");
    if (name != null && name.trim().length() > 0) {
        typeAttrs.add("name", name + "@TypeHint");
    }
	if(stored_attr_Value == null){
		
		stored_attr_Value = "#ECC";
	}
%><div <%= attrs.build() %>>
    <input id="full" <%= attrsInput.build() %>>
    <div class="coral-InputGroup-button">
        <button data-toggle="popover" data-target="#colorpicker" class="coral-Button coral-Button--square" id="customColorButton" type="button" title="<%= xssAPI.encodeForHTMLAttr(i18n.get("Date Picker")) %>">
            <i class="coral-Icon coral-Icon--sizeS coral-Icon coral-Icon--colorPalette"></i>
        </button>
    </div>
</div>


<div  id="colorpicker" class="coral-Popover"></div>
<script type="text/javascript">

  $(document).ready(function() {

   $("#full").spectrum({
    //color: "#ECC",
	color: "<%=stored_attr_Value%>",
    showInput: true,
    className: "full-spectrum",
    showInitial: true,
    showPalette: true,
    showSelectionPalette: true,
    maxSelectionSize: 10,
    preferredFormat: "hex",
    localStorageKey: "spectrum.demo",
    move: function (color) {
        
    },
    show: function () {
    
    },
    beforeShow: function () {
    
    },
    hide: function () {
    
    },
    change: function() {
        
    },
    palette: [
        ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)",
        "rgb(204, 204, 204)", "rgb(217, 217, 217)","rgb(255, 255, 255)"],
        ["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
        "rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"], 
        ["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)", 
        "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)", 
        "rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)", 
        "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)", 
        "rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)", 
        "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
        "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
        "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
        "rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)", 
        "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
    ]
});


  });

</script>