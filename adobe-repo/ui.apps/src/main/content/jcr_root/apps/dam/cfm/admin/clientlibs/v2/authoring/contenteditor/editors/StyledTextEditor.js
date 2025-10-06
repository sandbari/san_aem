/*
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2017 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

(function($, ns, window) {
    'use strict';

    var defaultCFMRTEConfig = {
        "useFixedInlineToolbar": "true",
        "rtePlugins": {
          "links": {
            "features": "*"
          },
          "misctools": {
            "features": "*"
          },
          "edit": {
            "features": "*"
          },
          "findreplace": {
            "features": "*"
          },
          "format": {
            "features": "*"
          },
          "image": {
            "features": "*"
          },
          "keys": {
            "features": "*"
          },
          "justify": {
            "features": "*"
          },
          "lists": {
            "features": "*"
          },
          "paraformat": {
            "features": "*"
          },
          "spellcheck": {
            "features": "*"
          },
          "styles": {
            "features": "*",
            "styles": {
              "warning": {
                "text": "Warning",
                "cssName": "warning"
              },
              "monospace": {
                "text": "Monospace",
                "cssName": "monospace"
              },
              "productdisclosure": {
                 "text": "Product Disclosure",
                 "cssName": "product-disclosure"
              },
              "morningstar3years": {
                 "text": "Morning Star 3Yr",
                 "cssName": "morning-star-3years"
              },
              "morningstar5years": {
                  "text": "Morning Star 5Yr",
                  "cssName": "morning-star-5years"
              },
              "morningstar10years": {
                   "text": "Morning Star 10Yr",
                   "cssName": "morning-star-10years"
              },
              "respectively": {
                   "text": "Respectively",
                   "cssName": "respectively"
              },
              "fontSize10": {
                  "text": "Font Size 10",
                  "cssName": "font-size-10"
                },
            "fontSize11": {
                "text": "Font Size 11",
                "cssName": "font-size-11"
              },
          "fontSize12": {
              "text": "Font Size 12",
              "cssName": "font-size-12"
            },
            "fontSize13": {
                "text": "Font Size 13",
                "cssName": "font-size-13"
              },
          "fontSize14": {
              "text": "Font Size 14",
              "cssName": "font-size-14"
            },
            "footerHeading": {
                "text": "Footer Heading",
                "cssName": "footer__heading"
              }
            }
          },
          "subsuperscript": {
            "features": "*"
          },
          "table": {
            "features": "*"
          },
          "undo": {
            "features": "*"
          },
          "generichtml": {
            "features": "*",
            "tools": {
              "comment": {
                "editor": "rawpayload",
                "converter": "comment",
                "editorConfig": {
                  "processor": "comment",
                  "title": "Edit Comment"
                }
              }
            }
          },
          "assets": {
            "features": "*",
              "tooltips": {
                  "insertasset": {
                      "title": Granite.I18n.get("Insert asset"),
                      "text": Granite.I18n.get("Insert asset")
                  }
              }
          },
          "cfmAnnotate": {
            "features": "*",
            "tooltips": {
              "annotate": {
                 "title": Granite.I18n.get("Annotate"),
                 "text": Granite.I18n.get("Annotate")
              }
            }
          }
        },
        "uiSettings": {
          "cui": {
            "additionalClasses": {
              "fullscreenstart": {
                "classes": "",
                "command": "fullscreen#start"
              }
            },
            "inline": {
              "toolbar": [
                "#format",
                "#justify",
                "#lists",
                "links#modifylink",
                "links#unlink",
                "#styles",
				"subsuperscript#subscript",
				"subsuperscript#superscript"
              ],
              "popovers": {
            	 "styles":{
            		 "ref": "styles",
                     "items": "styles:getStyles:styles-pulldown"
            	 },
                "justify": {
                  "ref": "justify",
                  "items": [
                    "justify#justifyleft",
                    "justify#justifycenter",
                    "justify#justifyright"
                  ]
                },
                "lists": {
                  "ref": "lists",
                  "items": [
                    "lists#unordered",
                    "lists#ordered",
                    "lists#outdent",
                    "lists#indent"
                  ]
                },
                "paraformat": {
                  "ref": "paraformat",
                  "items": "paraformat:getFormats:paraformat-pulldown"
                },
                "format": {
                  "ref": "format",
                  "items": [
                    "format#bold",
                    "format#italic",
                    "format#underline"
                  ]
                }
              }
            },
            "multieditorFullscreen": {
              "toolbar": [
                "#format",
                "#justify",
                "#lists",
                "links#modifylink",
                "links#unlink",
				"subsuperscript#subscript",
				"subsuperscript#superscript",
                "edit#paste-plaintext",
                "edit#paste-wordhtml",
                "table#createoredit",
                "#paraformat",
                /* "image#imageProps", */
                "assets#insertasset",
                "findreplace#find",
                "findreplace#replace",
                "spellcheck#checktext",
                "cfmAnnotate#annotate",
                "#styles",
                "misctools#specialchars",
                "misctools#sourceedit"
              ],
              "popovers": {
            	 "styles":{
             		 "ref": "styles",
                      "items": "styles:getStyles:styles-pulldown"
             	 },
                "paraformat": {
                  "ref": "paraformat",
                  "items": "paraformat:getFormats:paraformat-pulldown"
                },
                "justify": {
                  "ref": "justify",
                  "items": [
                    "justify#justifyleft",
                    "justify#justifycenter",
                    "justify#justifyright"
                  ]
                },
                "lists": {
                  "ref": "lists",
                  "items": [
                    "lists#unordered",
                    "lists#ordered",
                    "lists#outdent",
                    "lists#indent"
                  ]
                },
                "format": {
                  "ref": "format",
                  "items": [
                    "format#bold",
                    "format#italic",
                    "format#underline"
                  ]
                }
              }
            },
            "tableEditOptions": {
              "toolbar": [
                "table#insertcolumn-before",
                "table#insertcolumn-after",
                "table#removecolumn",
                "-",
                "table#insertrow-before",
                "table#insertrow-after",
                "table#removerow",
                "-",
                "table#mergecells-right",
                "table#mergecells-down",
                "table#mergecells",
                "table#splitcell-horizontal",
                "table#splitcell-vertical",
                "-",
                "table#selectrow",
                "table#selectcolumn",
                "-",
                "table#ensureparagraph",
                "-",
                "table#modifytableandcell",
                "table#removetable",
                "-",
                "undo#undo",
                "undo#redo",
                "-",
                "table#exitTableEditing",
                "-"
              ]
            }
          }
        },
        "htmlRules": {
            "genericHtml": {
                "converters": [
                    {
                        "type": "video",
                        "name": "video",
                        "detectors": [
                            {
                                "type": "element",
                                "tagName": "video"
                            }
                        ],
                        "thumbnailMaxWidth": 240,
                        "keepEmptyContainers": true
                    }, {
                        "type": "imagethumb",
                        "name": "imagethumb",
                        "detectors": [
                            {
                                "type": "element",
                                "tagName": "img"
                            }
                        ],
                        "thumbnailMaxWidth": 240,
                        "keepEmptyContainers": true
                    }
                ]
            }
        }
    };

    var channel = $(window.document);

    /**
     * options = {
     *     $formViewContainer: 'element in whose subtree form view RTE's editable is present'
     *     selectorForEditable: 'selector for editable',
     *     $textField: 'text field to store value to be submitted',
     *     editorType: 'text or table',
     *     externalStyleSheets: 'array of links to stylesheets for style plugin',
     *     customStart: 'true if editing should be triggered by rte-start event on editable, false otherwise'
     *     $rteToolbarContainer: 'dom under which toolbar of this editor should reside'
     * }
     */
    ns.StyledTextEditor = function(options) {
        ns.CFMEditor.call(this, options);
        this.rte = undefined;
        this.options = $.extend({}, options);
        this.$formViewContainer = this.options.$formViewContainer;
        this.$textField = this.options.$textField;
        this.editorType = this.options.editorType;
        this.externalStyleSheets = this.options.externalStyleSheets;
        this.htmlExtractor = new ns.HTMLExtractor();
        this._styleElements = [];
        if (this.externalStyleSheets && this.externalStyleSheets.length > 0) {
            this.externalStyleSheets = externalStyleSheets.split(",");
        } else {
            this.externalStyleSheets = [];
        }
    };

    ns.StyledTextEditor.prototype = Object.create(ns.CFMEditor.prototype);

    ns.StyledTextEditor.prototype.start = function() {
        this.$formViewContainer.removeClass("hidden");
        this.$editable = this.$formViewContainer.find(this.options.selectorForEditable);
        this.$editable.data("config", $.extend(true, {}, defaultCFMRTEConfig));
        var self = this;
        if (this.options.customStart) {
            this.$editable.on("rte-start", function() {
                if (self.$editable.data("useFixedInlineToolbar")) {
                    self._start();
                }
            });
        } else {
            if (this.$editable.data("useFixedInlineToolbar")) {
                this._start();
            }
        }
    };

    ns.StyledTextEditor.prototype._start = function() {
        var rtePluginsDefaults, configCallBack, index, $styleSheet, self = this;
        var rteOptions = {
            "element": self.$editable,
            "componentType": self.editorType,
            "preventCaretInitialize": true
        };
        var preferredTbType = this.$editable.data("preferredTbType");
        if (preferredTbType) {
            rteOptions["listeners"] = {};
            rteOptions["listeners"]["onStarted"] = function() {
                var ek = self.rte.editorKernel;
                if (!ek.hasBackgroundToolbar(preferredTbType)) {
                    ek.addBackgroundToolbar({
                        "tbType": preferredTbType,
                        "isSticky": true
                    });
                }
                ek.setActiveToolbar(preferredTbType);
            }
        }
        this._pushValue();
        if (this.editorType == "table") {
            rtePluginsDefaults = {
                "useColPercentage": false,
                    "rtePlugins": {
                        "table": {
                            "features": "*",
                            "defaultValues": {
                            "width": "100%"
                        },
                        "editMode": CUI.rte.plugins.TablePlugin.EDITMODE_TABLE
                    }
                }
            };
            configCallBack = function(config) {
                return Granite.Util.applyDefaults({}, rtePluginsDefaults, config);
            };
        }
        if (this.options.$rteToolbarContainer) {
            rteOptions["$ui"] = this.options.$rteToolbarContainer;
        }
        this.rte = new CUI.RichText(rteOptions);
        // add external style sheets to <head> element if not already present
        for (index = 0; index < this.externalStyleSheets.length; index++) {
            $styleSheet = $("head link[href='" + externalStyleSheets[index] +"']");
            if ($styleSheet.length <= 0) {
                $styleSheet = $("<link rel=\"stylesheet\" href=\"" + externalStyleSheets[index] + "\" type=\"text/css\">");
                $("head").append($styleSheet);
                this._styleElements.push($styleSheet);
            }
        }
        if (!$("#Editor").data("isReadOnly")) {
            this._initializeEventHandling();
            CUI.rte.ConfigUtils.loadConfigAndStartEditing(this.rte, self.$editable, configCallBack);
        }
    };

    ns.StyledTextEditor.prototype._initializeEventHandling = function() {
        var self = this;
        if (this.options.disableToolbarOnStart) {
            self.$editable.on("editing-start.styledEditor", function() {
                self.rte.editorKernel.disableToolbar();
            });
        }
        this.$editable.on("change.styledEditor", function() {
            self._syncValue();
            channel.trigger(ns.constants.EVENT_CONTENT_FRAGMENT_FIELD_MODIFIED);
        });
        this.$editable.on("focus.styledEditor", function(e) {
            self._triggerListeners.call(self, "cfm-editor-focus", e);
        });
        this.$editable.on("blur.styledEditor", function(e) {
            self._triggerListeners.call(self, "cfm-editor-blur", e);
        });
    };

    ns.StyledTextEditor.prototype.focus = function() {
        this.$editable.focus();
    };

    ns.StyledTextEditor.prototype._finalizeEventHandling = function() {
        this.$editable.off("editing-start.styledEditor");
        this.$editable.off("change.styledEditor");
        this.$editable.off("focus.styledEditor");
        this.$editable.off("blur.styledEditor");
    };

    ns.StyledTextEditor.prototype.adaptToContext = function(context) {
        if (context.undoConfig) {
            this.rte.setUndoConfig(context.undoConfig);
        }
        if (context.bkm) {
            CUI.rte.Selection.selectBookmark(this.rte.editorKernel.getEditContext(), context.bkm);
        }
    };

    ns.StyledTextEditor.prototype.getEditorAdapter = function() {
        if (!this.rteFullScreenAdapter) {
            this.rteFullScreenAdapter = new ns.RTEAdapter(this.rte);
        }
        return this.rteFullScreenAdapter;
    };
    
    ns.StyledTextEditor.prototype.end = function() {
        // todo do we need to un-initialize the member variables here, so that other methods don't work after finish ?
        var index;
        this._syncValue();
        this._finalizeEventHandling();
        this.removeAllEventListeners();
        this.rte.finish(false);
        for (index = 0; index < this._styleElements.length; index++) {
            this._styleElements[index].remove();
        }
        // todo doesn't look nice to remove here as its added by loadConfigAndStartEditing
        this.$editable.removeData("rteinstance");
        this.$formViewContainer.addClass("hidden");
        this.$editable.off("rte-start");
    };

    /**
     * Sync value from editable to text field.
     * @private
     */
    ns.StyledTextEditor.prototype._syncValue = function() {
        this.$textField.val(this.rte.getContent());
    };

    /**
     * Push value from text field to editable.
     * @private
     */
    ns.StyledTextEditor.prototype._pushValue = function() {
        this.$editable.empty().append(this.$textField.val());
    };

    ns.StyledTextEditor.prototype.getMimeType = function() {
        return "text/html";
    };

    ns.StyledTextEditor.prototype.getValue = function() {
        return this.rte.getContent();
    };

    ns.StyledTextEditor.prototype.setValue = function(value) {
        if (this.rte) {
            // reinitialize change state
            var context = this.rte.editorKernel.getEditContext();
            context.setState("DAM.CFM.dirtyHTMLState", false);
            context.setState("DAM.CFM.deferred", 0);
            context.setState("DAM.CFM.skipDirtyCheck", false);
            this.rte.setContent(value);
            ns.editor.Core.notifyModification(undefined, {
                editor: this,
                initial: true
            });
            this._initialValue = undefined;
        } else {
            this._initialValue = value;
        }
        channel.trigger(ns.constants.EVENT_CONTENT_FRAGMENT_FIELD_MODIFIED);

    };

    ns.StyledTextEditor.prototype.getPlainText = function() {
        return this.htmlExtractor.extract(this.$editable);
    };

    ns.StyledTextEditor.prototype.hasToolbar = function() {
        return true;
    };

})($, window.Dam.CFM, window);