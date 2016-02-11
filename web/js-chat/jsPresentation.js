var varPresentation = {};

$(document).ready(function(){
    varPresentation.RELOAD_TIME = 5000;
    varPresentation.profileId = $("input[name='profileId']").val();
    /*Only for compare message dates*/
    varPresentation.oldTab = $("#tabs li.active");
    /********************************/
    
    jsPresentation.getMessageCount();
                        
    $("#tabs").on("click", 'a:not(.noProccess)', function(){
        $("#iframe iframe").attr("src", "");
        var thisElement = $(this);
        var parentId = thisElement.parent().attr("id");
        /*Only for compare message dates*/
        thisElement.find("span").text(0);
        var oldTabId = varPresentation.oldTab.attr("id");
        var lastAccessToRoom = $("body input:hidden[id='"+oldTabId+"']");
        /********************************/
        var newTab = thisElement.data("new");
        var existTab = "";
        if($.isNumeric(parentId)){
            if(newTab == "false"){
                existTab = "&existTab=true";
                var count = thisElement.data("count");
                thisElement.data("count", count+1);
            }else{
                thisElement.data("new","false");
            }
        }
        /*Only for compare message dates*/
        if($.isNumeric(oldTabId)){
            lastAccessToRoom.val(new Date().toLocaleString());
        }
        varPresentation.oldTab = thisElement.parent(); //change oldTab for actual tab
        /********************************/
        var url = thisElement.data("url");
        $("#iframe iframe").attr("src", url+existTab); 
    });
    
});

var jsPresentation = {
    
    getProfileId :  function(){
        return varPresentation.profileId;
    },
    
    setTab: function(url, roomName, roomId){ 
        var existTab = $("#tabs li#"+roomId).length;
        if(existTab == 0){
            var reference = $('#tabs li.pull-right').last();
            var newTabElement = $('<li id="'+roomId+'" class="pull-left dynamic"><a href="#iframe" data-new="true" data-count="1" data-toggle="tab" data-url="' + url + '">' + roomName + '<span class="badge">0</span></a></li>');
            newTabElement.insertBefore( reference );
            $("#tabs li.active").removeClass("active");
            
            /*Only for compare message dates*/
            var $lastAccessToRoom = $("<input type='hidden' id='"+roomId+"' value='"+ new Date().toLocaleString() +" ' >");
            $lastAccessToRoom.appendTo("body");
            /********************************/
            
            newTabElement.find("a").click();
        }else{
            $("#tabs li#"+roomId).find("a").click();
        }
    },
    
    removeTab: function(tabId){
        $("body input#room"+tabId).remove();
        $("#tabs li#"+tabId).remove();
        $("#tabs li.pull-left").last().find("a").click();
    },
    
    getMessageCount: function(){
        var tabs = $("#tabs li.dynamic:not(.active)");
        tabs.each(function(index){
            var id = $(this).attr("id");
            var lastAccessToRoom = $("body input:hidden[id='"+id+"']").val();
            var messageCount = 0;
            $.ajax({
                url: "/chat/index.jsp?action=GetMessageCount",
                type: "post",
                dataType: "json",     
                data: {'roomId':id, 'profileId': varPresentation.profileId},
                error: function(hr) {},
                success: function(data) {
                    if(data.length != 0){
                        $.each(data, function(index, element) {
                            var messageDate = new Date(element.datetimeOfCreation).toLocaleString();
                            if(messageDate > lastAccessToRoom){
                                messageCount += 1;
                            }
                        });
                        $("#tabs li#"+id).find("span").text(messageCount);
                    }
                }
            });
        });
        jsPresentation.refreshMessageCount();
    },
    
    refreshMessageCount: function(){
        setTimeout(jsPresentation.getMessageCount, varPresentation.RELOAD_TIME);
    },
    
    recentlyCreatedTab: function(tabId){
        return $("#tabs li#"+tabId).find("a").data("count") == 1 ? true : false;
    }
};

//Funciona correctamente si quiero llamar a una funcion que se encuentra dentro del iframe
//probando: function(){
//      window.frames[0].jsHome.prueba();  
//    },

