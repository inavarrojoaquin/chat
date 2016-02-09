var varPresentation = {};

$(document).ready(function(){
    varPresentation.RELOAD_TIME = 8000;
    varPresentation.profileId = $("input[name='profileId']").val();
    
    jsPresentation.getMessageCount();

    $("#tabs").on("click", 'a:not(.noProccess)', function(){
        $("#iframe iframe").attr("src", "");
        var thisElement = $(this);
        var parentId = thisElement.parent().attr("id");
        var newTab = thisElement.data("new");
        var existTab = "";
        if($.isNumeric(parentId)){
            if(newTab == "false"){
                existTab = "&existTab=true";
            }else{
                thisElement.data("new","false");
            }
        }
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
            var newElement = $('<li id="'+roomId+'" class="pull-left dynamic"><a href="#iframe" data-new="true" data-toggle="tab" data-url="' + url + '">' + roomName + '<span class="badge">0</span></a></li>');
            newElement.insertBefore( reference );
            $("#tabs li.active").removeClass("active");     
            newElement.find("a").click();
        }else{
            $("#tabs li#"+roomId).find("a").click();
        }
    },
    
    removeTab: function(tabId){
        $("#tabs li#"+tabId).remove();
        $("#tabs li.pull-left").last().find("a").click();
    },
    
    getMessageCount: function(){
        var tabs = $("#tabs li.dynamic");
        tabs.each(function(index){
            var id = $(this).attr("id");
            $.ajax({
                url: "/chat/index.jsp?action=GetMessageCount",
                type: "post",
                dataType: "json",     
                data: {'roomId':id, 'profileId': varPresentation.profileId},
                error: function(hr) {},
                success: function(data) {
                    if(data.length != 0){
                        console.log("Room: " + id + " MessageCant: " + data.length);
                        $("#tabs li#"+id).find("span").text(data.length);
                    }
                }
            });
        });
        jsPresentation.refreshMessageCount();
    },
    
    refreshMessageCount: function(){
        setTimeout(jsPresentation.getMessageCount, varPresentation.RELOAD_TIME);
    }
};

