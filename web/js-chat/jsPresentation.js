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
    
    setTab: function(url, roomName){
        var reference = $('#tabs li.pull-right').last();
        var newElement = $('<li><a href="#iframe" data-toggle="tab" id="' + roomName + '" data-url="' + url + '">' + roomName + '</a></li>');
        newElement.insertBefore( reference );
        
        newElement.find("a").click(function(){
            var url = $(this).data("url");
            $("#iframe iframe").attr("src", url);
        });
        $("#tabs li.active").removeClass("active");
        newElement.find("a").click();
        
    }
};

