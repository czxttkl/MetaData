/*
 * Ext JS Library 1.1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://www.extjs.com/license
 */

Ext.ComponentMgr=function(){var A=new Ext.util.MixedCollection();return{register:function(B){A.add(B)},unregister:function(B){A.remove(B)},get:function(B){return A.get(B)},onAvailable:function(D,C,B){A.on("add",function(E,F){if(F.id==D){C.call(B||F,F);A.un("add",C,B)}})}}}();Ext.Component=function(A){A=A||{};if(A.tagName||A.dom||typeof A=="string"){A={el:A,id:A.id||A}}this.initialConfig=A;Ext.apply(this,A);this.addEvents({disable:true,enable:true,beforeshow:true,show:true,beforehide:true,hide:true,beforerender:true,render:true,beforedestroy:true,destroy:true});if(!this.id){this.id="ext-comp-"+(++Ext.Component.AUTO_ID)}Ext.ComponentMgr.register(this);Ext.Component.superclass.constructor.call(this);this.initComponent();if(this.renderTo){this.render(this.renderTo);delete this.renderTo}};Ext.Component.AUTO_ID=1000;Ext.extend(Ext.Component,Ext.util.Observable,{hidden:false,disabled:false,rendered:false,disabledClass:"x-item-disabled",allowDomMove:true,hideMode:"display",ctype:"Ext.Component",actionMode:"el",getActionEl:function(){return this[this.actionMode]},initComponent:Ext.emptyFn,render:function(B,A){if(!this.rendered&&this.fireEvent("beforerender",this)!==false){if(!B&&this.el){this.el=Ext.get(this.el);B=this.el.dom.parentNode;this.allowDomMove=false}this.container=Ext.get(B);this.rendered=true;if(A!==undefined){if(typeof A=="number"){A=this.container.dom.childNodes[A]}else{A=Ext.getDom(A)}}this.onRender(this.container,A||null);if(this.cls){this.el.addClass(this.cls);delete this.cls}if(this.style){this.el.applyStyles(this.style);delete this.style}this.fireEvent("render",this);this.afterRender(this.container);if(this.hidden){this.hide()}if(this.disabled){this.disable()}}return this},onRender:function(B,A){if(this.el){this.el=Ext.get(this.el);if(this.allowDomMove!==false){B.dom.insertBefore(this.el.dom,A)}}},getAutoCreate:function(){var A=typeof this.autoCreate=="object"?this.autoCreate:Ext.apply({},this.defaultAutoCreate);if(this.id&&!A.id){A.id=this.id}return A},afterRender:Ext.emptyFn,destroy:function(){if(this.fireEvent("beforedestroy",this)!==false){this.purgeListeners();this.beforeDestroy();if(this.rendered){this.el.removeAllListeners();this.el.remove();if(this.actionMode=="container"){this.container.remove()}}this.onDestroy();Ext.ComponentMgr.unregister(this);this.fireEvent("destroy",this)}},beforeDestroy:function(){},onDestroy:function(){},getEl:function(){return this.el},getId:function(){return this.id},focus:function(A){if(this.rendered){this.el.focus();if(A===true){this.el.dom.select()}}return this},blur:function(){if(this.rendered){this.el.blur()}return this},disable:function(){if(this.rendered){this.onDisable()}this.disabled=true;this.fireEvent("disable",this);return this},onDisable:function(){this.getActionEl().addClass(this.disabledClass);this.el.dom.disabled=true},enable:function(){if(this.rendered){this.onEnable()}this.disabled=false;this.fireEvent("enable",this);return this},onEnable:function(){this.getActionEl().removeClass(this.disabledClass);this.el.dom.disabled=false},setDisabled:function(A){this[A?"disable":"enable"]()},show:function(){if(this.fireEvent("beforeshow",this)!==false){this.hidden=false;if(this.rendered){this.onShow()}this.fireEvent("show",this)}return this},onShow:function(){var A=this.getActionEl();if(this.hideMode=="visibility"){A.dom.style.visibility="visible"}else{if(this.hideMode=="offsets"){A.removeClass("x-hidden")}else{A.dom.style.display=""}}},hide:function(){if(this.fireEvent("beforehide",this)!==false){this.hidden=true;if(this.rendered){this.onHide()}this.fireEvent("hide",this)}return this},onHide:function(){var A=this.getActionEl();if(this.hideMode=="visibility"){A.dom.style.visibility="hidden"}else{if(this.hideMode=="offsets"){A.addClass("x-hidden")}else{A.dom.style.display="none"}}},setVisible:function(A){if(A){this.show()}else{this.hide()}return this},isVisible:function(){return this.getActionEl().isVisible()},cloneConfig:function(B){B=B||{};var C=B.id||Ext.id();var A=Ext.applyIf(B,this.initialConfig);A.id=C;return new this.constructor(A)}});
Ext.BoxComponent=function(A){Ext.BoxComponent.superclass.constructor.call(this,A);this.addEvents({resize:true,move:true})};Ext.extend(Ext.BoxComponent,Ext.Component,{boxReady:false,deferHeight:false,setSize:function(B,D){if(typeof B=="object"){D=B.height;B=B.width}if(!this.boxReady){this.width=B;this.height=D;return this}if(this.lastSize&&this.lastSize.width==B&&this.lastSize.height==D){return this}this.lastSize={width:B,height:D};var C=this.adjustSize(B,D);var F=C.width,A=C.height;if(F!==undefined||A!==undefined){var E=this.getResizeEl();if(!this.deferHeight&&F!==undefined&&A!==undefined){E.setSize(F,A)}else{if(!this.deferHeight&&A!==undefined){E.setHeight(A)}else{if(F!==undefined){E.setWidth(F)}}}this.onResize(F,A,B,D);this.fireEvent("resize",this,F,A,B,D)}return this},getSize:function(){return this.el.getSize()},getPosition:function(A){if(A===true){return[this.el.getLeft(true),this.el.getTop(true)]}return this.xy||this.el.getXY()},getBox:function(A){var B=this.el.getSize();if(A){B.x=this.el.getLeft(true);B.y=this.el.getTop(true)}else{var C=this.xy||this.el.getXY();B.x=C[0];B.y=C[1]}return B},updateBox:function(A){this.setSize(A.width,A.height);this.setPagePosition(A.x,A.y);return this},getResizeEl:function(){return this.resizeEl||this.el},getPositionEl:function(){return this.positionEl||this.el},setPosition:function(A,F){this.x=A;this.y=F;if(!this.boxReady){return this}var B=this.adjustPosition(A,F);var E=B.x,D=B.y;var C=this.getPositionEl();if(E!==undefined||D!==undefined){if(E!==undefined&&D!==undefined){C.setLeftTop(E,D)}else{if(E!==undefined){C.setLeft(E)}else{if(D!==undefined){C.setTop(D)}}}this.onPosition(E,D);this.fireEvent("move",this,E,D)}return this},setPagePosition:function(A,C){this.pageX=A;this.pageY=C;if(!this.boxReady){return }if(A===undefined||C===undefined){return }var B=this.el.translatePoints(A,C);this.setPosition(B.left,B.top);return this},onRender:function(B,A){Ext.BoxComponent.superclass.onRender.call(this,B,A);if(this.resizeEl){this.resizeEl=Ext.get(this.resizeEl)}if(this.positionEl){this.positionEl=Ext.get(this.positionEl)}},afterRender:function(){Ext.BoxComponent.superclass.afterRender.call(this);this.boxReady=true;this.setSize(this.width,this.height);if(this.x||this.y){this.setPosition(this.x,this.y)}if(this.pageX||this.pageY){this.setPagePosition(this.pageX,this.pageY)}},syncSize:function(){delete this.lastSize;this.setSize(this.el.getWidth(),this.el.getHeight());return this},onResize:function(D,B,A,C){},onPosition:function(A,B){},adjustSize:function(A,B){if(this.autoWidth){A="auto"}if(this.autoHeight){B="auto"}return{width:A,height:B}},adjustPosition:function(A,B){return{x:A,y:B}}});
(function(){Ext.Layer=function(D,C){D=D||{};var E=Ext.DomHelper;var G=D.parentEl,F=G?Ext.getDom(G):document.body;if(C){this.dom=Ext.getDom(C)}if(!this.dom){var H=D.dh||{tag:"div",cls:"x-layer"};this.dom=E.append(F,H)}if(D.cls){this.addClass(D.cls)}this.constrain=D.constrain!==false;this.visibilityMode=Ext.Element.VISIBILITY;if(D.id){this.id=this.dom.id=D.id}else{this.id=Ext.id(this.dom)}this.zindex=D.zindex||this.getZIndex();this.position("absolute",this.zindex);if(D.shadow){this.shadowOffset=D.shadowOffset||4;this.shadow=new Ext.Shadow({offset:this.shadowOffset,mode:D.shadow})}else{this.shadowOffset=0}this.useShim=D.shim!==false&&Ext.useShims;this.useDisplay=D.useDisplay;this.hide()};var A=Ext.Element.prototype;var B=[];Ext.extend(Ext.Layer,Ext.Element,{getZIndex:function(){return this.zindex||parseInt(this.getStyle("z-index"),10)||11000},getShim:function(){if(!this.useShim){return null}if(this.shim){return this.shim}var D=B.shift();if(!D){D=this.createShim();D.enableDisplayMode("block");D.dom.style.display="none";D.dom.style.visibility="visible"}var C=this.dom.parentNode;if(D.dom.parentNode!=C){C.insertBefore(D.dom,this.dom)}D.setStyle("z-index",this.getZIndex()-2);this.shim=D;return D},hideShim:function(){if(this.shim){this.shim.setDisplayed(false);B.push(this.shim);delete this.shim}},disableShadow:function(){if(this.shadow){this.shadowDisabled=true;this.shadow.hide();this.lastShadowOffset=this.shadowOffset;this.shadowOffset=0}},enableShadow:function(C){if(this.shadow){this.shadowDisabled=false;this.shadowOffset=this.lastShadowOffset;delete this.lastShadowOffset;if(C){this.sync(true)}}},sync:function(C){var I=this.shadow;if(!this.updating&&this.isVisible()&&(I||this.useShim)){var F=this.getShim();var H=this.getWidth(),E=this.getHeight();var D=this.getLeft(true),J=this.getTop(true);if(I&&!this.shadowDisabled){if(C&&!I.isVisible()){I.show(this)}else{I.realign(D,J,H,E)}if(F){if(C){F.show()}var G=I.adjusts,K=F.dom.style;K.left=(Math.min(D,D+G.l))+"px";K.top=(Math.min(J,J+G.t))+"px";K.width=(H+G.w)+"px";K.height=(E+G.h)+"px"}}else{if(F){if(C){F.show()}F.setSize(H,E);F.setLeftTop(D,J)}}}},destroy:function(){this.hideShim();if(this.shadow){this.shadow.hide()}this.removeAllListeners();var C=this.dom.parentNode;if(C){C.removeChild(this.dom)}Ext.Element.uncache(this.id)},remove:function(){this.destroy()},beginUpdate:function(){this.updating=true},endUpdate:function(){this.updating=false;this.sync(true)},hideUnders:function(C){if(this.shadow){this.shadow.hide()}this.hideShim()},constrainXY:function(){if(this.constrain){var G=Ext.lib.Dom.getViewWidth(),C=Ext.lib.Dom.getViewHeight();var L=Ext.get(document).getScroll();var K=this.getXY();var H=K[0],F=K[1];var I=this.dom.offsetWidth+this.shadowOffset,D=this.dom.offsetHeight+this.shadowOffset;var E=false;if((H+I)>G+L.left){H=G-I-this.shadowOffset;E=true}if((F+D)>C+L.top){F=C-D-this.shadowOffset;E=true}if(H<L.left){H=L.left;E=true}if(F<L.top){F=L.top;E=true}if(E){if(this.avoidY){var J=this.avoidY;if(F<=J&&(F+D)>=J){F=J-D-5}}K=[H,F];this.storeXY(K);A.setXY.call(this,K);this.sync()}}},isVisible:function(){return this.visible},showAction:function(){this.visible=true;if(this.useDisplay===true){this.setDisplayed("")}else{if(this.lastXY){A.setXY.call(this,this.lastXY)}else{if(this.lastLT){A.setLeftTop.call(this,this.lastLT[0],this.lastLT[1])}}}},hideAction:function(){this.visible=false;if(this.useDisplay===true){this.setDisplayed(false)}else{this.setLeftTop(-10000,-10000)}},setVisible:function(E,D,G,H,F){if(E){this.showAction()}if(D&&E){var C=function(){this.sync(true);if(H){H()}}.createDelegate(this);A.setVisible.call(this,true,true,G,C,F)}else{if(!E){this.hideUnders(true)}var C=H;if(D){C=function(){this.hideAction();if(H){H()}}.createDelegate(this)}A.setVisible.call(this,E,D,G,C,F);if(E){this.sync(true)}else{if(!D){this.hideAction()}}}},storeXY:function(C){delete this.lastLT;this.lastXY=C},storeLeftTop:function(D,C){delete this.lastXY;this.lastLT=[D,C]},beforeFx:function(){this.beforeAction();return Ext.Layer.superclass.beforeFx.apply(this,arguments)},afterFx:function(){Ext.Layer.superclass.afterFx.apply(this,arguments);this.sync(this.isVisible())},beforeAction:function(){if(!this.updating&&this.shadow){this.shadow.hide()}},setLeft:function(C){this.storeLeftTop(C,this.getTop(true));A.setLeft.apply(this,arguments);this.sync()},setTop:function(C){this.storeLeftTop(this.getLeft(true),C);A.setTop.apply(this,arguments);this.sync()},setLeftTop:function(D,C){this.storeLeftTop(D,C);A.setLeftTop.apply(this,arguments);this.sync()},setXY:function(F,D,G,H,E){this.fixDisplay();this.beforeAction();this.storeXY(F);var C=this.createCB(H);A.setXY.call(this,F,D,G,C,E);if(!D){C()}},createCB:function(D){var C=this;return function(){C.constrainXY();C.sync(true);if(D){D()}}},setX:function(C,D,F,G,E){this.setXY([C,this.getY()],D,F,G,E)},setY:function(G,C,E,F,D){this.setXY([this.getX(),G],C,E,F,D)},setSize:function(E,F,D,H,I,G){this.beforeAction();var C=this.createCB(I);A.setSize.call(this,E,F,D,H,C,G);if(!D){C()}},setWidth:function(E,D,G,H,F){this.beforeAction();var C=this.createCB(H);A.setWidth.call(this,E,D,G,C,F);if(!D){C()}},setHeight:function(E,D,G,H,F){this.beforeAction();var C=this.createCB(H);A.setHeight.call(this,E,D,G,C,F);if(!D){C()}},setBounds:function(J,H,K,D,I,F,G,E){this.beforeAction();var C=this.createCB(G);if(!I){this.storeXY([J,H]);A.setXY.call(this,[J,H]);A.setSize.call(this,K,D,I,F,C,E);C()}else{A.setBounds.call(this,J,H,K,D,I,F,C,E)}return this},setZIndex:function(C){this.zindex=C;this.setStyle("z-index",C+2);if(this.shadow){this.shadow.setZIndex(C+1)}if(this.shim){this.shim.setStyle("z-index",C)}}})})();
Ext.Shadow=function(C){Ext.apply(this,C);if(typeof this.mode!="string"){this.mode=this.defaultMode}var D=this.offset,B={h:0};var A=Math.floor(this.offset/2);switch(this.mode.toLowerCase()){case"drop":B.w=0;B.l=B.t=D;B.t-=1;if(Ext.isIE){B.l-=this.offset+A;B.t-=this.offset+A;B.w-=A;B.h-=A;B.t+=1}break;case"sides":B.w=(D*2);B.l=-D;B.t=D-1;if(Ext.isIE){B.l-=(this.offset-A);B.t-=this.offset+A;B.l+=1;B.w-=(this.offset-A)*2;B.w-=A+1;B.h-=1}break;case"frame":B.w=B.h=(D*2);B.l=B.t=-D;B.t+=1;B.h-=2;if(Ext.isIE){B.l-=(this.offset-A);B.t-=(this.offset-A);B.l+=1;B.w-=(this.offset+A+1);B.h-=(this.offset+A);B.h+=1}break}this.adjusts=B};Ext.Shadow.prototype={offset:4,defaultMode:"drop",show:function(A){A=Ext.get(A);if(!this.el){this.el=Ext.Shadow.Pool.pull();if(this.el.dom.nextSibling!=A.dom){this.el.insertBefore(A)}}this.el.setStyle("z-index",this.zIndex||parseInt(A.getStyle("z-index"),10)-1);if(Ext.isIE){this.el.dom.style.filter="progid:DXImageTransform.Microsoft.alpha(opacity=50) progid:DXImageTransform.Microsoft.Blur(pixelradius="+(this.offset)+")"}this.realign(A.getLeft(true),A.getTop(true),A.getWidth(),A.getHeight());this.el.dom.style.display="block"},isVisible:function(){return this.el?true:false},realign:function(A,M,L,D){if(!this.el){return }var I=this.adjusts,G=this.el.dom,N=G.style;var E=0;N.left=(A+I.l)+"px";N.top=(M+I.t)+"px";var K=(L+I.w),C=(D+I.h),F=K+"px",J=C+"px";if(N.width!=F||N.height!=J){N.width=F;N.height=J;if(!Ext.isIE){var H=G.childNodes;var B=Math.max(0,(K-12))+"px";H[0].childNodes[1].style.width=B;H[1].childNodes[1].style.width=B;H[2].childNodes[1].style.width=B;H[1].style.height=Math.max(0,(C-12))+"px"}}},hide:function(){if(this.el){this.el.dom.style.display="none";Ext.Shadow.Pool.push(this.el);delete this.el}},setZIndex:function(A){this.zIndex=A;if(this.el){this.el.setStyle("z-index",A)}}};Ext.Shadow.Pool=function(){var B=[];var A=Ext.isIE?"<div class=\"x-ie-shadow\"></div>":"<div class=\"x-shadow\"><div class=\"xst\"><div class=\"xstl\"></div><div class=\"xstc\"></div><div class=\"xstr\"></div></div><div class=\"xsc\"><div class=\"xsml\"></div><div class=\"xsmc\"></div><div class=\"xsmr\"></div></div><div class=\"xsb\"><div class=\"xsbl\"></div><div class=\"xsbc\"></div><div class=\"xsbr\"></div></div></div>";return{pull:function(){var C=B.shift();if(!C){C=Ext.get(Ext.DomHelper.insertHtml("beforeBegin",document.body.firstChild,A));C.autoBoxAdjust=false}return C},push:function(C){B.push(C)}}}();
