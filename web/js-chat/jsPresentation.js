varPresentation = {};

$(document).ready(function(){
    
    varPresentation.profileId = $("input[name='profileId']").val();

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
            var newElement = $('<li id="'+roomId+'" class="pull-left"><a href="#iframe" data-new="true" data-toggle="tab" data-url="' + url + '">' + roomName + '</a></li>');
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
    }
};

