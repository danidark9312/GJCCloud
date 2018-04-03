/**
 * Module for displaying "Waiting for..." dialog using Bootstrap 
 * Uso
 * waitingDialog.show('Dialog with callback on hidden',{onHide: function () {alert('Callback!');}});
 * waitingDialog.show('Custom message', {dialogSize: 'sm', progressType: 'warning'});
 * waitingDialog.show('Custom message');
 * waitingDialog.show();
 * @author Eugene Maslovich <ehpc@em42.ru>
 * https://github.com/ehpc/bootstrap-waitingfor
 */

/*(function(c,a){"function"===typeof define&&define.amd?define(["jquery"],function(d){return c.waitingDialog=a(d)}):c.waitingDialog=c.waitingDialog||a(c.jQuery)})(this,function(c){var a;return{show:function(d,g){"undefined"===typeof g&&(g={});"undefined"===typeof d&&(d="Loading");var b=c.extend({headerText:"",headerSize:3,headerClass:"",dialogSize:"m",progressType:"",contentElement:"p",contentClass:"content",onHide:null,onShow:null},g),e,f;a&&a.remove();a=c('<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;"><div class="modal-dialog modal-m"><div class="modal-content"><div class="modal-header" style="display: none;"></div><div class="modal-body"><div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div></div></div></div></div>');
a.find(".modal-dialog").attr("class","modal-dialog").addClass("modal-"+b.dialogSize);a.find(".progress-bar").attr("class","progress-bar");b.progressType&&a.find(".progress-bar").addClass("progress-bar-"+b.progressType);e=c("<h"+b.headerSize+" />");e.css({margin:0});b.headerClass&&e.addClass(b.headerClass);f=c("<"+b.contentElement+" />");b.contentClass&&f.addClass(b.contentClass);!1===b.headerText?(f.html(d),a.find(".modal-body").prepend(f)):b.headerText?(e.html(b.headerText),a.find(".modal-header").html(e).show(),
f.html(d),a.find(".modal-body").prepend(f)):(e.html(d),a.find(".modal-header").html(e).show());if("function"===typeof b.onHide)a.off("hidden.bs.modal").on("hidden.bs.modal",function(){b.onHide.call(a)});if("function"===typeof b.onShow)a.off("shown.bs.modal").on("shown.bs.modal",function(){b.onShow.call(a)});a.modal()},hide:function(){"undefined"!==typeof a&&a.modal("hide")}}});*/
var waitingDialog = {
		hide : function (){},
		show : function(){}
}