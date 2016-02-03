varPresentation = {};

$(document).ready(function(){
    
    varPresentation.profileId = $("input[name='profileId']").val();
    
    //var id = $("#tabs li.active > a").attr("id");
    $("#tabs").on("click", 'a', function(){
        var url = $(this).data("url");
        $("#iframe iframe").attr("src", url);
    });
    
});

var jsPresentation = {
    getProfileId :  function(){
        return varPresentation.profileId;
    },
    
    setTab: function(url, roomName, roomId){
        var reference = $('#tabs li.pull-right').last();
        var newElement = $('<li id="'+roomId+'" class="pull-left"><a href="#iframe" data-toggle="tab" data-url="' + url + '">' + roomName + '</a></li>');
        newElement.insertBefore( reference );
        
        newElement.find("a").click(function(){
            var url = $(this).data("url");
            $("#iframe iframe").attr("src", url);
        });
        $("#tabs li.active").removeClass("active");
        newElement.find("a").click();
        
    },
    
    removeTab: function(tabId){
        $("#tabs li#"+tabId).remove();
        $("#tabs li.pull-left").last().find("a").click();
    }
};

