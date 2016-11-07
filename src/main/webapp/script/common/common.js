
function forbiddenPage(){
    if($('#ajaxWaitingMsg')==undefined || $('#ajaxWaitingMsg').length==0){
        $("<div class='modal hide fade' id='ajaxWaitingMsg'>"+
            "<div class='modal-header'>"+
            "<button type='button' class='close' data-dismiss='modal'>×</button>"+
            " <h3>系统提示：</h3> </div> <div class='modal-body'> " +
            "<p>正在处理，请稍候……</p> </div></div>").appendTo("body");
    }
    $('#ajaxWaitingMsg').modal('show');
}
/**
 * 释放页面
 * @return
 */
function releasePage(){
    $('#ajaxWaitingMsg').modal('hide');
}